package com.example.uf1_ud06_3_guessgamev2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    val words = listOf("Android", "Fragment", "Kotlin", "Model")
    var secretWord  = words.random().uppercase()
    var secretWordDisplay = MutableLiveData<String>()
    var lives = MutableLiveData<Int>(8)
    var guesses = mutableListOf<Char>()
    val clearInput = MutableLiveData<Unit>()

    init {
        secretWordDisplay.value = generateSecretWordDisplay()
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

            secretWordDisplay.value = generateSecretWordDisplay()
            if(!secretWord.contains(letter))
                lives.value = lives.value?.minus(1)
            clearInput.value= Unit
        }
    }

    fun win() = secretWord == secretWordDisplay.value
    fun lost() = (lives.value ?: 0) <= 0
    fun restart() {
        guesses.clear()
        lives.value = 8
        secretWord = words.random().uppercase()
        secretWordDisplay.value = generateSecretWordDisplay()
    }

    fun resultMessage() =
        if (win()) "Ganaste!\n  La palabra secreta era $secretWord"
        else  "Oops, perdiste!\n  La palabra secreta era $secretWord"

}