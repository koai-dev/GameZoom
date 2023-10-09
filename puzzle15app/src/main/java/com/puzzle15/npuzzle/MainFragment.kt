package com.puzzle15.npuzzle

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.puzzle15.npuzzle.databinding.FragmentMainBinding

/**
 * Created by tokgozmusa on 28/10/2017.
 */

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    private val logTag = "MainFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(logTag, "onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val main15PuzzleActivity: Main15PuzzleActivity = requireActivity() as Main15PuzzleActivity
        binding.buttonThreeXThree.setOnClickListener {
            main15PuzzleActivity.showPuzzleFragment(3)
        }
        binding.buttonFourXFour.setOnClickListener {
            main15PuzzleActivity.showPuzzleFragment(4)
        }
        binding.buttonFiveXFive.setOnClickListener {
            main15PuzzleActivity.showPuzzleFragment(5)
        }
    }
}
