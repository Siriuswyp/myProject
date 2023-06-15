package com.example.countdownday2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.widget.ListView
import android.widget.Toast

class BgChangeActivity : AppCompatActivity() {
    private val bgList = ArrayList<Bg>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bg_change)

        initBg()
        val adapter_bg = BgAdapter(this, R.layout.bg_item ,bgList)
        val LVB: ListView = findViewById(R.id.listView_bg)
        LVB.adapter = adapter_bg

        val notice_data = intent.getStringExtra("notice_data")
        Toast.makeText(this, notice_data, Toast.LENGTH_SHORT).show()

        LVB.setOnItemClickListener(){parent, view, position, id ->
            val bg = bgList[position]
            Toast.makeText(this, "成功选择了${bg.name}作为背景", Toast.LENGTH_SHORT).show()
            val intent = Intent()
            var returned_m = "none"
            when(bg.name){
                "一月" -> returned_m = "january"
                "二月" -> returned_m = "february"
                "三月" -> returned_m = "march"
                "四月" -> returned_m = "april"
                "五月" -> returned_m = "may"
                "六月" -> returned_m = "june"
                "七月" -> returned_m = "july"
                "八月" -> returned_m = "august"
                "九月" -> returned_m = "september"
                "十月" -> returned_m = "october"
                "十一月" -> returned_m = "november"
                else -> returned_m = "december"
            }
            intent.putExtra("month_returned", returned_m)
            setResult(RESULT_OK, intent)
            finish()

        }

    }


    private fun initBg(){
        repeat(1){
            bgList.add(Bg("一月", R.drawable.january))
            bgList.add(Bg("二月", R.drawable.february))
            bgList.add(Bg("三月", R.drawable.march))
            bgList.add(Bg("四月", R.drawable.april))
            bgList.add(Bg("五月", R.drawable.may))
            bgList.add(Bg("六月", R.drawable.june))
            bgList.add(Bg("七月", R.drawable.july))
            bgList.add(Bg("八月", R.drawable.august))
            bgList.add(Bg("九月", R.drawable.september))
            bgList.add(Bg("十月", R.drawable.october))
            bgList.add(Bg("十一月", R.drawable.november))
            bgList.add(Bg("十二月", R.drawable.december))
        }
    }
}