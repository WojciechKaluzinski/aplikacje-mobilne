package com.example.tic_tac_toe

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.tic_tac_toe.databinding.ActivityBigBoardBinding


class BigBoardActivity : AppCompatActivity(), View.OnClickListener  {

    private lateinit var binding: ActivityBigBoardBinding

    private val tab= arrayOf(
        arrayOf(0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0)
    )
    var crossMove = true;
    var move = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBigBoardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.textinfo.text = getString(R.string.cross)

        binding.b00.setOnClickListener(this)
        binding.b01.setOnClickListener(this)
        binding.b02.setOnClickListener(this)
        binding.b03.setOnClickListener(this)
        binding.b04.setOnClickListener(this)

        binding.b10.setOnClickListener(this)
        binding.b11.setOnClickListener(this)
        binding.b12.setOnClickListener(this)
        binding.b13.setOnClickListener(this)
        binding.b14.setOnClickListener(this)

        binding.b20.setOnClickListener(this)
        binding.b21.setOnClickListener(this)
        binding.b22.setOnClickListener(this)
        binding.b23.setOnClickListener(this)
        binding.b24.setOnClickListener(this)

        binding.b30.setOnClickListener(this)
        binding.b31.setOnClickListener(this)
        binding.b32.setOnClickListener(this)
        binding.b33.setOnClickListener(this)
        binding.b34.setOnClickListener(this)

        binding.b40.setOnClickListener(this)
        binding.b41.setOnClickListener(this)
        binding.b42.setOnClickListener(this)
        binding.b43.setOnClickListener(this)
        binding.b44.setOnClickListener(this)

    }

    override fun onBackPressed() {
        winNobody()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.b00 -> {pressButton(binding.b00,0,0)}
            R.id.b01 -> {pressButton(binding.b01,0,1)}
            R.id.b02 -> {pressButton(binding.b02,0,2)}
            R.id.b03 -> {pressButton(binding.b03,0,3)}
            R.id.b04 -> {pressButton(binding.b04,0,4)}

            R.id.b10 -> {pressButton(binding.b10,1,0)}
            R.id.b11 -> {pressButton(binding.b11,1,1)}
            R.id.b12 -> {pressButton(binding.b12,1,2)}
            R.id.b13 -> {pressButton(binding.b13,1,3)}
            R.id.b14 -> {pressButton(binding.b14,1,4)}

            R.id.b20 -> {pressButton(binding.b20,2,0)}
            R.id.b21 -> {pressButton(binding.b21,2,1)}
            R.id.b22 -> {pressButton(binding.b22,2,2)}
            R.id.b23 -> {pressButton(binding.b23,2,3)}
            R.id.b24 -> {pressButton(binding.b24,2,4)}

            R.id.b30 -> {pressButton(binding.b30,3,0)}
            R.id.b31 -> {pressButton(binding.b31,3,1)}
            R.id.b32 -> {pressButton(binding.b32,3,2)}
            R.id.b33 -> {pressButton(binding.b33,3,3)}
            R.id.b34 -> {pressButton(binding.b34,3,4)}

            R.id.b40 -> {pressButton(binding.b40,4,0)}
            R.id.b41 -> {pressButton(binding.b41,4,1)}
            R.id.b42 -> {pressButton(binding.b42,4,2)}
            R.id.b43 -> {pressButton(binding.b43,4,3)}
            R.id.b44 -> {pressButton(binding.b44,4,4)}

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
        if (move == 25) winNobody()
    }

    private fun checkWin(): Boolean {
        for (i in 0..4) {
            if ((tab[i][0] == tab[i][1]) && (tab[i][1] == tab[i][2]) && (tab[i][2] == tab[i][3]) && (tab[i][3] == tab[i][4]) && tab[i][0] != 0)
                return true
        }
        for (i in 0..4) {
            if ((tab[0][i] == tab[1][i]) && (tab[1][i] == tab[2][i]) && (tab[2][i] == tab[3][i]) && (tab[3][i] == tab[4][i]) && tab[0][i] != 0)
                return true
        }
        if ((tab[0][0] == tab[1][1]) && (tab[1][1] == tab[2][2]) && (tab[2][2] == tab[3][3]) && (tab[3][3] == tab[4][4]) &&  tab[0][0] != 0)
            return true
        else if ((tab[0][4] == tab[1][3]) && (tab[1][3] == tab[2][2]) && (tab[2][2] == tab[3][1]) && (tab[3][1] == tab[4][0]) && tab[0][4] != 0)
            return true
        return false
    }

}