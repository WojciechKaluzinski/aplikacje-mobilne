package com.example.lista1_2b

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lista1_2b.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var score = 0
    private var num1 = 0
    private var num2 = 0
    private var eq = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        score = 0
        roll()
    }

    private fun roll() {
        binding.points.text = "Punkty:" + score;
        num1 = Random.nextInt(19) + 1
        num2 = Random.nextInt(19) + 1
        eq = Random.nextInt(3)
        binding.equation.text = num1.toString() + " ? " + num2.toString() + " = " + solve(num1, num2, eq)
    }


    operator fun plus(view: View?) {
        if (eq == 0) {
            Toast.makeText(this, R.string.good, Toast.LENGTH_SHORT).show()
            score++
        } else {
            Toast.makeText(this, R.string.bad, Toast.LENGTH_SHORT).show()
            score--
        }
        roll()
    }

    operator fun minus(view: View?) {
        if (eq == 1) {
            Toast.makeText(this, R.string.good, Toast.LENGTH_SHORT).show()
            score++
        } else {
            Toast.makeText(this, R.string.bad, Toast.LENGTH_SHORT).show()
            score--
        }
        roll()
    }

    fun multi(view: View?) {
        if (eq == 2) {
            Toast.makeText(this, R.string.good, Toast.LENGTH_SHORT).show()
            score++
        } else {
            Toast.makeText(this, R.string.bad, Toast.LENGTH_SHORT).show()
            score--
        }
        roll()
    }

    fun restart(view: View?) {
        score = 0
        roll()
    }

    fun nwe_eq(view: View?) {
        roll()
    }


    private fun solve(num1: Int, num2: Int, eq: Int): Int {
        val result: Int

        result = if (eq == 0) {
            num1 + num2
        } else if (eq == 1) {
            num1 - num2
        } else {
            num1 * num2
        }
        return result
    }
}

