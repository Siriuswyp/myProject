package com.example.countdownday2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class ChangeTextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_text)
        val button :TextView = findViewById(R.id.text_ok_button)
        var text = intent.getStringExtra("text_data")
        val textView : TextView = findViewById(R.id.edit_text)

        if (text != null) {
            Log.d("ChangeTextActivity...", text)
        }
        else{
            text = "还未填写..."
        }

        textView.hint = text
        var return_data = ""
        return_data = text

        button.setOnClickListener(){
            return_data = textView.text.toString()
            val intent = Intent()
            intent.putExtra("data_return", return_data)
            setResult(RESULT_OK, intent)
            finish()
        }

    }
}