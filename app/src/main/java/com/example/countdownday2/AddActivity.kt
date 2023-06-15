package com.example.countdownday2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

       val buttonOk: TextView = findViewById(R.id.add_item_ok_button)
        val extraData = intent.getStringExtra("extra_data")
        Log.d("Add", "extra data is $extraData")

        buttonOk.setOnClickListener{
            val intent = Intent()
            intent.putExtra("data_return", "Hello Main...")
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}