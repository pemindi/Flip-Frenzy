package com.example.myapplication
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Button

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        // Find the Buttons
        val imageButton = findViewById<ImageButton>(R.id.imageButton)
        val button= findViewById<Button>(R.id.button6)
        val button1= findViewById<Button>(R.id.button5)

        // Set OnClickListener to navigate to the next activity
        imageButton.setOnClickListener {
            val intent = Intent(this, MainActivity4::class.java)
            startActivity(intent)

        }

        // Set OnClickListener to navigate to the next activity
        button.setOnClickListener {
            Log.d("MainActivity3", "Button clicked")
            val intent = Intent(this, MainActivity5::class.java)
            startActivity(intent)
        }

        // Set OnClickListener to navigate to the next activity
        button1.setOnClickListener {
            Log.d("MainActivity3", "Button clicked")
            val intent = Intent(this, MainActivity6::class.java)
            startActivity(intent)
        }

    }
}