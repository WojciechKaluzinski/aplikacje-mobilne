package com.example.tic_tac_toe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.tic_tac_toe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        showResult(getString(R.string.nobody))
    }

    private fun showResult(info: String?) {
        binding.result.text = getString(R.string.last_win) + info;
    }

    fun smallBoard(view: View) {
        val intent = Intent(this, SmallBoardActivity::class.java)
        startActivityForResult(intent, 123)
    }

    fun bigBoard(view: View) {
        val intent = Intent(this, BigBoardActivity::class.java)
        startActivityForResult(intent, 321)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123){
            showResult(data?.getStringExtra("result"));
        }
        if (requestCode == 321){
            showResult(data?.getStringExtra("result"));
        }
    }

}