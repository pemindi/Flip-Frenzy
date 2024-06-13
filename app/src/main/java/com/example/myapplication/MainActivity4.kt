package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val imageButton = findViewById<ImageButton>(R.id.imageButton2)

        imageButton.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)

        }
    }
}