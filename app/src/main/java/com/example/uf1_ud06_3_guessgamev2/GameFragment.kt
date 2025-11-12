package com.example.uf1_ud06_3_guessgamev2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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
        updateScreen()

        binding.buttonNext.setOnClickListener {
            if(binding.txtGuess.text.length>0){
                model.makeGuess(binding.txtGuess.text.toString())
                updateScreen()
                if (model.win() || model.lost())
                    view.findNavController().navigate(R.id.action_gameFragment_to_resultFragment)
            }else{
                Snackbar.make(view, "Introduce una letra", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    fun updateScreen(){
        binding.txtWord.text = model.secretWordDisplay
        binding.txtLives.text = "Te quedan ${model.lives} vidas"
        binding.txtGuess.text = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}