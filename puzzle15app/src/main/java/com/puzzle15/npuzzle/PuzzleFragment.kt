package com.puzzle15.npuzzle

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.puzzle15.npuzzle.databinding.FragmentPuzzleBinding

/**
 * Created by tokgozmusa on 28/10/2017.
 */

class PuzzleFragment : Fragment() {
    private lateinit var binding: FragmentPuzzleBinding

    private val logTag = "PuzzleFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(logTag, "onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPuzzleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val main15PuzzleActivity: Main15PuzzleActivity = requireActivity() as Main15PuzzleActivity
        val puzzleBoardView = PuzzleBoardView(main15PuzzleActivity, main15PuzzleActivity.n)
        binding.puzzleContainer.addView(puzzleBoardView)

        binding.buttonNewGame.setOnClickListener {
            puzzleBoardView.initGame()
            puzzleBoardView.invalidate()
        }
    }
}
