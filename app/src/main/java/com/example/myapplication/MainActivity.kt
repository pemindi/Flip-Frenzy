package com.example.myapplication
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create a Handler on the main (UI) thread
        val handler = Handler(Looper.getMainLooper())

        // Post a delayed task to navigate to the next activity after 5 seconds
        handler.postDelayed({
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish() // Optional: Finish this activity to prevent returning back to it
        }, 3000) // Delay in milliseconds (5 seconds)
    }
}
