package com.example.tic_tac_toe

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.tic_tac_toe.databinding.ActivitySmallBoardBinding


class SmallBoardActivity : AppCompatActivity(), View.OnClickListener  {

    private lateinit var binding: ActivitySmallBoardBinding

    private val tab= arrayOf(
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0)
    )
    var crossMove = true;
    var move = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySmallBoardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.textinfo.text = getString(R.string.cross)

        binding.b00.setOnClickListener(this)
        binding.b01.setOnClickListener(this)
        binding.b02.setOnClickListener(this)
        binding.b10.setOnClickListener(this)
        binding.b11.setOnClickListener(this)
        binding.b12.setOnClickListener(this)
        binding.b20.setOnClickListener(this)
        binding.b21.setOnClickListener(this)
        binding.b22.setOnClickListener(this)

    }

    override fun onBackPressed() {
        winNobody()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.b00 -> {pressButton(binding.b00,0,0)}
            R.id.b01 -> {pressButton(binding.b01,0,1)}
            R.id.b02 -> {pressButton(binding.b02,0,2)}
            R.id.b10 -> {pressButton(binding.b10,1,0)}
            R.id.b11 -> {pressButton(binding.b11,1,1)}
            R.id.b12 -> {pressButton(binding.b12,1,2)}
            R.id.b20 -> {pressButton(binding.b20,2,0)}
            R.id.b21 -> {pressButton(binding.b21,2,1)}
            R.id.b22 -> {pressButton(binding.b22,2,2)}
        }
    }


    private fun winO(){
        val intent = Intent();
        intent.putExtra("result", "O");
        setResult(Activity.RESULT_OK, intent);
        finish()
    }

    private fun winX(){
        val intent = Intent();
        intent.putExtra("result", "X");
        setResult(Activity.RESULT_OK, intent);
        finish()
    }

    private fun winNobody(){
        val intent = Intent();
        intent.putExtra("result", getString(R.string.nobody));
        setResult(Activity.RESULT_OK, intent);
        finish()
    }

    private fun pressButton(button: ImageButton, x:Int, y:Int){
        move++
        if (crossMove) {
            button.setImageResource(R.drawable.krzyyk)
            tab[x][y]=1
            if (checkWin()){
                winX()
            }
            crossMove = false
            binding.textinfo.text = getString(R.string.circle)
        }
        else{
            button.setImageResource(R.drawable.k_ko)
            tab[x][y]=2
            if (checkWin()){
                winO()
            }
            crossMove = true
            binding.textinfo.text = getString(R.string.cross)
        }
        button.isEnabled = false
        if (move == 9) winNobody()
    }

    private fun checkWin(): Boolean {
        for (i in 0..2) {
            if ((tab[i][0] == tab[i][1]) && (tab[i][1] == tab[i][2]) && tab[i][0] != 0)
                return true
        }
        for (i in 0..2) {
            if ((tab[0][i] == tab[1][i]) && (tab[1][i] == tab[2][i]) && tab[0][i] != 0)
                return true
        }
        if ((tab[0][0] == tab[1][1]) && (tab[1][1] == tab[2][2]) && tab[0][0] != 0)
            return true
        else if ((tab[0][2] == tab[1][1]) && (tab[1][1] == tab[2][0]) && tab[0][2] != 0)
            return true
        return false
    }

}