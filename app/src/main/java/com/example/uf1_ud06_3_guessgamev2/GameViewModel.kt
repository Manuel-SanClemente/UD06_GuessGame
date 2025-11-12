package com.example.uf1_ud06_3_guessgamev2

import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    val words = listOf("Android", "Fragment", "Kotlin", "Model")
    var secretWord  = words.random().uppercase()
    var secretWordDisplay = ""
    var guesses = mutableListOf<Char>()
    var lives = 8

    init {
        secretWordDisplay = generateSecretWordDisplay()
    }

    fun generateSecretWordDisplay() =
        secretWord.map {
            if (it in guesses) it
            else '_'
        }.joinToString("")

    fun makeGuess(guess: String){
        if(guess.length > 0) {
            val letter = guess.uppercase()[0]
            guesses.add(letter)

            secretWordDisplay = generateSecretWordDisplay()
            if(!secretWord.contains(letter)) lives -= 1
        }
    }

    fun win() = secretWord == secretWordDisplay
    fun lost() = lives <= 0
    fun restart() {
        guesses.clear()
        lives = 8
        secretWord = words.random().uppercase()
        secretWordDisplay = generateSecretWordDisplay()
    }

    fun resultMessage() =
        if (win()) "Ganaste!\n  La palabra secreta era $secretWord"
        else  "Oops, perdiste!\n  La palabra secreta era $secretWord"

}