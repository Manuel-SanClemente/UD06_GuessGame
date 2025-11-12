package com.example.uf1_ud06_3_guessgamev2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.uf1_ud06_3_guessgamev2.databinding.FragmentGameBinding
import com.google.android.material.snackbar.Snackbar
class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    val model: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wordObserver = Observer<String> { newWord ->
            binding.txtWord.text = newWord
        }

        model.secretWordDisplay.observe(viewLifecycleOwner, wordObserver)

        val livesObserver = Observer<Int> { newLives ->
            binding.txtLives.text = "Quedanche $newLives vidas."
        }

        model.lives.observe(viewLifecycleOwner, livesObserver)

        model.clearInput.observe(viewLifecycleOwner) {
            binding.txtGuess.text.clear()
        }

        binding.buttonNext.setOnClickListener {
            if(binding.txtGuess.text.length>0){
                model.makeGuess(binding.txtGuess.text.toString())
                if (model.win() || model.lost())
                    view.findNavController().navigate(R.id.action_gameFragment_to_resultFragment)
            }else{
                Snackbar.make(view, "Introduce una letra", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
    /*
        fun updateScreen(){
            binding.txtWord.text = model.secretWordDisplay.value
            binding.txtLives.text = "Te quedan ${model.lives} vidas"
            binding.txtGuess.text = null
        }
    */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}