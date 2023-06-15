package com.example.countdownday2

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.countdownday2.R.*
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationStartContainer
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.w3c.dom.Text
import java.io.IOException
import java.time.LocalDateTime


class MainActivity : AppCompatActivity() {
    private val taskList = ArrayList<Item>()
    private val finishedList = ArrayList<Item>()
    private var savedBg:String? = ""
    private var savedText:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        val fabf : FloatingActionButton = findViewById(id.fab_future)
        val myCardViewf : MaterialCardView = findViewById(id.myCardView_future)
        val transformationLayoutf : TransformationLayout = findViewById(id.transformationLayout_future)

        val fabc : FloatingActionButton = findViewById(id.fab_change)
        val myCardViewc : MaterialCardView = findViewById(id.myCardView_change)
        val transformationLayoutc : TransformationLayout = findViewById(id.transformationLayout_change)

        val fabw : FloatingActionButton = findViewById(id.fab_weather)
        val myCardVieww : MaterialCardView = findViewById(id.myCardView_weather)
        val transformationLayoutw : TransformationLayout = findViewById(id.transformationLayout_weather)

        val fabs: FloatingActionButton = findViewById(id.fab_save)

        val listView_future : ListView = findViewById(id.listView_future)
        val add_task : ImageView = findViewById(id.add1)

        recoveryAll()
        updateWeather("101250607")
        println("------------------------------")

        fabf.setOnClickListener {
            transformationLayoutf.startTransform()
        }
        // finish transformation when touching the myCardView.
        myCardViewf.setOnClickListener {
            transformationLayoutf.finishTransform()
        }

        fabc.setOnClickListener {
            transformationLayoutc.startTransform()
        }
        myCardViewc.setOnClickListener {
            transformationLayoutc.finishTransform()
        }

        fabw.setOnClickListener(){
            transformationLayoutw.startTransform()
        }
        myCardVieww.setOnClickListener(){
            transformationLayoutw.finishTransform()
        }

        //以下是显示listView的代码(已完成)
        initUnfinishedList()
        val adapter = ItemAdapter(this, layout.task_item,taskList)
        listView_future.adapter = adapter

        listView_future.setOnItemClickListener(){ parent, view, position, id ->
            val task = taskList[position]
            Toast.makeText(this, task.name, Toast.LENGTH_SHORT).show()
        }

        listView_future.setOnItemClickListener(){ parent, view, position, id ->
            val task = taskList[position]
            Toast.makeText(this, task.name, Toast.LENGTH_SHORT).show()
        }

        //以下是显示listView的代码(已完成)

        //添加事项
        add_task.setOnClickListener{
            val data = "Hello add..."
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra("extra_data", data)
            //startActivity(intent)
            startActivityForResult(intent, 1)
        }

        //编辑背景
        val changeBg : TextView = findViewById(R.id.change_background)
        changeBg.setOnClickListener{
            val data = "请选择~"
            val intent = Intent(this, BgChangeActivity::class.java)
            intent.putExtra("notice_data", data)
            startActivityForResult(intent, 2)

        }
        //编辑文字
        val changeText : TextView = findViewById(id.change_words)
        val myText : TextView = findViewById(id.my_words)

        changeText.setOnClickListener {
            val text = myText.text.toString()
            val intent = Intent(this, ChangeTextActivity::class.java)
            intent.putExtra("text_data", text)
            startActivityForResult(intent, 3)
        }

        //保存编辑
        fabs.setOnClickListener(){
            val editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit()
            editor.putBoolean("is_saved", true)
            editor.putString("bg_saved", savedBg)
            editor.putString("text_saved", savedText)
            editor.apply()
            Toast.makeText(this, "已保存更改", Toast.LENGTH_SHORT).show()
        }

        //天气


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1 -> if(resultCode == RESULT_OK){
                val returnedData = data?.getStringExtra("data_return")
                Log.d("MainActivity->1", "returned data is $returnedData")

            }

            2 -> if(resultCode == RESULT_OK){
                val month = data?.getStringExtra("month_returned")
                Log.d("MainActivity->2", "month${month}")
                updateBackground(month)
            }

            3 -> if(resultCode == RESULT_OK){
                val new_text = data?.getStringExtra("data_return")
                Log.d("MainActivity->3", "--${new_text}")
                savedText = new_text
                updateText(new_text)
            }
        }
    }


    private fun initUnfinishedList(){
        repeat(2){
            taskList.add(Item("AAA", drawable.unfinished))
            taskList.add(Item("BBB", drawable.unfinished))
            taskList.add(Item("CCC", drawable.unfinished))
            taskList.add(Item("GGG", drawable.unfinished))
            taskList.add(Item("MMM", drawable.unfinished))
            taskList.add(Item("OOO", drawable.unfinished))
            taskList.add(Item("PPP", drawable.unfinished))
            taskList.add(Item("PPA", drawable.unfinished))
            taskList.add(Item("SSS", drawable.unfinished))
            taskList.add(Item("WWW", drawable.unfinished))
        }
    }

    //更新背景
    private fun updateBackground(month: String?){
        var bg: ImageView = findViewById(R.id.my_background)
        savedBg = month
        when(month){
            "january" -> bg.setBackgroundResource(R.drawable.january_background)
            "february" -> bg.setBackgroundResource(R.drawable.february_background)
            "march" -> bg.setBackgroundResource(R.drawable.march_background)
            "april" -> bg.setBackgroundResource(R.drawable.april_background)
            "may" -> bg.setBackgroundResource(R.drawable.may_background)
            "june" -> bg.setBackgroundResource(R.drawable.june_background)
            "july" -> bg.setBackgroundResource(R.drawable.july_background)
            "august" -> bg.setBackgroundResource(R.drawable.august_background)
            "september" -> bg.setBackgroundResource(R.drawable.september_background)
            "october" -> bg.setBackgroundResource(R.drawable.october_background)
            "november" -> bg.setBackgroundResource(R.drawable.november_background)
            else -> bg.setBackgroundResource(R.drawable.december_background)
        }
    }

    private fun updateText(new_text: String?){
        val text:TextView = findViewById(R.id.my_words)
        text.setText(new_text)
    }

    private fun recoveryAll(){
        val prefs = getSharedPreferences("data", Context.MODE_PRIVATE)
        val isSaved = prefs.getBoolean("is_saved", false)
        println(isSaved)
        if(isSaved){
            val bg_saved:String? = prefs.getString("bg_saved", "")
            updateBackground(bg_saved)
            val text_saved:String? = prefs.getString("text_saved", "hello, world...")

            updateText(text_saved)
        }
    }

    fun updateWeather(citycode:String) {
        // 使用Thread类创建新线程
            val client = OkHttpClient()
            val url = "https://devapi.qweather.com/v7/weather/now?location="+citycode+
                    "&key=cb88fcc9f9ee4d8c86dbeade241f8b9c"
            val request = Request.Builder().url(url).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // 处理请求失败的情况
                    Log.e("now", "请求失败：${e.message}")
                }

                @SuppressLint("SetTextI18n")
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call, response: Response) {
                    // 处理请求成功的情况
                    val jsonString = response.body?.string()
                    Log.i("now", "请求成功：$jsonString")

                    data class nowVo(
                        var temp: String,//气温
                        var text: String,//天气
                        var windDir: String,//风向
                        var windScale: String,//风力
                        var humidity: String, //湿度
                        var icon:String, //天气代码
                    )
                    data class ResponseVo(
                        var code: Int,
                        var fxLink: String,
                        var updateTime: String,
                        var now: nowVo
                    )

                    val gson = Gson()
                    val weather = gson.fromJson(jsonString, ResponseVo::class.java)
                    Log.i("now", "请求成功：${weather.now.text}")

                    val city_image: ImageView = findViewById(R.id.weather_pic)
                    val city_temp: TextView = findViewById(R.id.weather_temp)
                    val city_desc: TextView = findViewById(R.id.weather_desc)
                    val city_weekday: TextView = findViewById(R.id.week_day)


                    city_temp.text = "${weather.now.temp}℃"
                    city_desc.text = weather.now.text
                    when(LocalDateTime.now().dayOfWeek.value){
                        1 -> city_weekday.text = "星期一"
                        2 -> city_weekday.text = "星期二"
                        3 -> city_weekday.text = "星期三"
                        4 -> city_weekday.text = "星期四"
                        5 -> city_weekday.text = "星期五"
                        6 -> city_weekday.text = "星期六"
                        7 -> city_weekday.text = "星期日"
                    }
                    when(weather.now.icon.toInt()){
                        100, 150 -> city_image.setImageResource(R.drawable.biz_plugin_weather_qing)
                        101,102,103,151,152,153 -> city_image.setImageResource(R.drawable.biz_plugin_weather_duoyun)
                        104 -> city_image.setImageResource(R.drawable.biz_plugin_weather_yin)
                        300,301,350,351 -> city_image.setImageResource(R.drawable.biz_plugin_weather_zhenyu)
                        302,303 -> city_image.setImageResource(R.drawable.biz_plugin_weather_leizhenyu)
                        304 -> city_image.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao)
                        305,309 -> city_image.setImageResource(R.drawable.biz_plugin_weather_xiaoyu)
                        306,313,314,399 -> city_image.setImageResource(R.drawable.biz_plugin_weather_zhongyu)
                        307,308,315 -> city_image.setImageResource(R.drawable.biz_plugin_weather_dayu)
                        310,316 -> city_image.setImageResource(R.drawable.biz_plugin_weather_baoyu)
                        311,317 -> city_image.setImageResource(R.drawable.biz_plugin_weather_dabaoyu)
                        312,318 -> city_image.setImageResource(R.drawable.biz_plugin_weather_tedabaoyu)
                        400 -> city_image.setImageResource(R.drawable.biz_plugin_weather_xiaoxue)
                        401 -> city_image.setImageResource(R.drawable.biz_plugin_weather_zhongxue)
                        402 -> city_image.setImageResource(R.drawable.biz_plugin_weather_daxue)
                        403 -> city_image.setImageResource(R.drawable.biz_plugin_weather_baoxue)
                        404,405,406,456 -> city_image.setImageResource(R.drawable.biz_plugin_weather_yujiaxue)
                        407,457 -> city_image.setImageResource(R.drawable.biz_plugin_weather_zhenxue)
                        500,501,502,509,510,511,512,513,514,515 -> city_image.setImageResource(R.drawable.biz_plugin_weather_wu)
                        503,504,507,508 -> city_image.setImageResource(R.drawable.biz_plugin_weather_shachenbao)
                        else -> println("==================")
                    }
                }
            })
    }


}