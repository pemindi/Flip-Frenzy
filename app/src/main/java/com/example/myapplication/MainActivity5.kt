package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageButton
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.view.Gravity

class MainActivity5 : AppCompatActivity() {

    private lateinit var imageButtons: Array<ImageButton>
    private lateinit var playButton: Button
    private lateinit var replayButton: Button
    private lateinit var scoreTextView: TextView
    private lateinit var movesTextView: TextView
    private lateinit var timerTextView: TextView

    private var firstCardIndex: Int? = null
    private var secondCardIndex: Int? = null
    private var score = 0
    private var moves = 0
    private var timer: CountDownTimer? = null
    private var timeElapsed = 0L

    private val images = mutableListOf(
        R.drawable.b00, R.drawable.b00,
        R.drawable.b01, R.drawable.b01,
        R.drawable.b31, R.drawable.b31
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)


        val imageButton1 = findViewById<ImageButton>(R.id.imageButton17)

        imageButton1.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)

        }

        // Initialize UI elements
        imageButtons = arrayOf(
            findViewById(R.id.imageButton3),
            findViewById(R.id.imageButton4),
            findViewById(R.id.imageButton6),
            findViewById(R.id.imageButton7),
            findViewById(R.id.imageButton8),
            findViewById(R.id.imageButton9)
        )
        playButton = findViewById(R.id.button7)
        replayButton = findViewById(R.id.replayButton)
        scoreTextView = findViewById(R.id.textView6)
        movesTextView = findViewById(R.id.textView7)
        timerTextView = findViewById(R.id.textView5)

        // Set click listener for the play button
        playButton.setOnClickListener {
            startGame()
        }

        // Set click listener for the replay button
        replayButton.setOnClickListener {
            restartGame()
        }
    }

    private fun startGame() {
        // Reset game parameters
        score = 0
        moves = 0
        timeElapsed = 0L
        updateUI()

        // Shuffle the images
        images.shuffle()

        // Assign images to ImageButtons
        imageButtons.forEachIndexed { index, button ->
            button.setImageResource(R.drawable.baseline_help_outline_24)
            button.setOnClickListener {
                onCardClicked(index)
            }
        }

        // Start the timer
        timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeElapsed += 1000
                updateTimer()
            }

            override fun onFinish() {
                // Timer finished
            }
        }
        timer?.start()
    }

    private fun onCardClicked(index: Int) {
        val currentImageButton = imageButtons[index]

        // Check if the card is already matched or flipped
        if (currentImageButton.alpha == 1f) {
            // Check if it's the first or second card
            if (firstCardIndex == null) {
                firstCardIndex = index
                flipCard(currentImageButton)
            } else if (secondCardIndex == null && index != firstCardIndex) {
                secondCardIndex = index
                flipCard(currentImageButton)

                // Check for a match after a short delay
                currentImageButton.postDelayed({
                    checkForMatch()
                }, 500)
            }
        }
    }

    private fun flipCard(imageButton: ImageButton) {
        imageButton.animate().apply {
            duration = 200
            scaleX(0f)
            withEndAction {
                imageButton.setImageResource(images[imageButtons.indexOf(imageButton)])
                imageButton.animate().apply {
                    duration = 200
                    scaleX(1f)
                }
            }
        }
        moves++
        updateUI()
    }

    private fun checkForMatch() {
        val firstImage = images[firstCardIndex!!]
        val secondImage = images[secondCardIndex!!]

        if (firstImage == secondImage) {
            // Match found
            imageButtons[firstCardIndex!!].alpha = 0.3f
            imageButtons[secondCardIndex!!].alpha = 0.3f
            score += 3
            Toast.makeText(this, "Match found!", Toast.LENGTH_SHORT).show()

            // Check if all cards are matched
            if (imageButtons.all { it.alpha == 0.3f }) {
                // All cards matched, end the game
                endGame()
            }
        } else {
            // No match, flip back
            flipCardBack(imageButtons[firstCardIndex!!])
            flipCardBack(imageButtons[secondCardIndex!!])
        }

        // Reset indices
        firstCardIndex = null
        secondCardIndex = null

        updateUI()
    }

    private fun flipCardBack(imageButton: ImageButton) {
        imageButton.animate().apply {
            duration = 200
            scaleX(0f)
            withEndAction {
                imageButton.setImageResource(R.drawable.baseline_help_outline_24)
                imageButton.animate().apply {
                    duration = 200
                    scaleX(1f)
                }
            }
        }
    }

    private fun endGame() {
        timer?.cancel()
        showPopup()
        Toast.makeText(this, "Congratulations! You've matched all the cards!", Toast.LENGTH_LONG).show()
    }

    private fun updateUI() {
        scoreTextView.text = "Score: $score"
        movesTextView.text = "Moves: $moves"
    }

    private fun updateTimer() {
        val minutes = (timeElapsed / 60000).toInt()
        val seconds = ((timeElapsed % 60000) / 1000).toInt()
        timerTextView.text = String.format("%02d:%02d", minutes, seconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }


    private fun restartGame() {
        // Stop the timer
        timer?.cancel()

        // Reset game parameters
        firstCardIndex = null
        secondCardIndex = null
        score = 0
        moves = 0
        timeElapsed = 0L

        // Reset UI elements
        updateUI()
        timerTextView.text = "00:00"

        // Reset all cards to initial state
        imageButtons.forEach { button ->
            button.setImageResource(R.drawable.baseline_help_outline_24)
            button.isEnabled = true
            button.alpha = 1f
        }

        // Shuffle images
        images.shuffle()

        // Set click listeners for the cards
        setCardClickListeners()

        // Start a new game
        startGame()
    }

    private fun setCardClickListeners() {
        imageButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                onCardClicked(index)
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showPopup() {
        // Inflate the popup window layout
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_window_layout, null)

        // Get references to elements in the popup layout
        val scoreTextView = popupView.findViewById<TextView>(R.id.textView6)
        val movesTextView = popupView.findViewById<TextView>(R.id.textView7)
        val timerTextView = popupView.findViewById<TextView>(R.id.textView8)
        val closeButton = popupView.findViewById<Button>(R.id.popup_close_button)

        // Set popup content
        scoreTextView.text = "Score: $score"
        movesTextView.text = "Moves: $moves"
        val minutes = (timeElapsed / 60000).toInt()
        val seconds = ((timeElapsed % 60000) / 1000).toInt()
        timerTextView.text = "Time: ${String.format("%02d:%02d", minutes, seconds)}"


        // Create the popup window
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val popupWindow = PopupWindow(popupView, width, height, true)

        // Set transparent background for the popup
        popupWindow.setBackgroundDrawable(getDrawable(android.R.color.transparent))

        // Make popup non-focusable
        popupWindow.isFocusable = false

        // Close button click listener
        closeButton.setOnClickListener {
            popupWindow.dismiss()
            // You can optionally handle the close button click here
        }

        // Show the popup window
        popupWindow.showAtLocation(findViewById(R.id.parent_layout), Gravity.CENTER, 0, 0)
    }
}
