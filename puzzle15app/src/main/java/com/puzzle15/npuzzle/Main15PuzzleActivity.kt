package com.puzzle15.npuzzle

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import android.util.Log

/**
 * Created by tokgozmusa on 28/10/2017.
 */

class Main15PuzzleActivity : AppCompatActivity() {

    private val logTag = "MainFragment"

    val fragmentManager: FragmentManager = supportFragmentManager

    var n = 3 // size of the puzzle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(logTag, "onCreate")
        setContentView(R.layout.activity_main_15_puzzle)
        showMainFragment()
    }

    private fun showMainFragment() {
        val transaction = fragmentManager.beginTransaction()
        val fragment = MainFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()
    }

    fun showPuzzleFragment(newN: Int) {
        n = newN
        val transaction = fragmentManager.beginTransaction()
        val fragment = PuzzleFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
