package com.example.wisielec

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wisielec.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnClickListener   {
    private lateinit var binding: ActivityMainBinding
    private var wordNumber = 0;
    private var badAnswers = 0;
    private var wordLength = 0;
    lateinit var wordsArray: Array<String>
    lateinit var charWord: MutableList<Char>
    lateinit var mutedWord: MutableList<Char>
    var buttonsArray = ArrayList<Button>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        wordsArray = resources.getStringArray(R.array.words)
        setContentView(view)

        binding.q.setOnClickListener(this)
        binding.w.setOnClickListener(this)
        binding.e.setOnClickListener(this)
        binding.r.setOnClickListener(this)
        binding.t.setOnClickListener(this)
        binding.y.setOnClickListener(this)
        binding.u.setOnClickListener(this)
        binding.i.setOnClickListener(this)
        binding.o.setOnClickListener(this)
        binding.p.setOnClickListener(this)

        binding.a.setOnClickListener(this)
        binding.s.setOnClickListener(this)
        binding.d.setOnClickListener(this)
        binding.f.setOnClickListener(this)
        binding.g.setOnClickListener(this)
        binding.h.setOnClickListener(this)
        binding.j.setOnClickListener(this)
        binding.k.setOnClickListener(this)
        binding.l.setOnClickListener(this)

        binding.z.setOnClickListener(this)
        binding.x.setOnClickListener(this)
        binding.c.setOnClickListener(this)
        binding.v.setOnClickListener(this)
        binding.b.setOnClickListener(this)
        binding.n.setOnClickListener(this)
        binding.m.setOnClickListener(this)

        startGame()
    }


    private fun startGame(){
        badAnswers=0
        wordNumber  = Random.nextInt(20);
        clearButtons()
        val word = wordsArray[wordNumber]
        wordLength = word.length

        charWord = MutableList(word.length){
            word[it]
        }

        mutedWord = MutableList(word.length){
            '_'
        }

        showWord(mutedWord)


        binding.image.setImageResource(R.drawable.wisielec4)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.q -> {pressButton(binding.q,'q')}
            R.id.w -> {pressButton(binding.w,'w')}
            R.id.e -> {pressButton(binding.e,'e')}
            R.id.r -> {pressButton(binding.r,'r')}
            R.id.t -> {pressButton(binding.t,'t')}
            R.id.y -> {pressButton(binding.y,'y')}
            R.id.u -> {pressButton(binding.u,'u')}
            R.id.i -> {pressButton(binding.i,'i')}
            R.id.o -> {pressButton(binding.o,'o')}
            R.id.p -> {pressButton(binding.p,'p')}

            R.id.a -> {pressButton(binding.a,'a')}
            R.id.s -> {pressButton(binding.s,'s')}
            R.id.d -> {pressButton(binding.d,'d')}
            R.id.f -> {pressButton(binding.f,'f')}
            R.id.g -> {pressButton(binding.g,'g')}
            R.id.h -> {pressButton(binding.h,'h')}
            R.id.j -> {pressButton(binding.j,'j')}
            R.id.k -> {pressButton(binding.k,'k')}
            R.id.l -> {pressButton(binding.l,'l')}

            R.id.z -> {pressButton(binding.z,'z')}
            R.id.x -> {pressButton(binding.x,'x')}
            R.id.c -> {pressButton(binding.c,'c')}
            R.id.v -> {pressButton(binding.v,'v')}
            R.id.b -> {pressButton(binding.b,'b')}
            R.id.n -> {pressButton(binding.n,'n')}
            R.id.m -> {pressButton(binding.m,'m')}


        }
    }

    private fun pressButton(button: Button, letter: Char) {
        var isLetter = false
        for ((index, char) in charWord.withIndex()) {
            if (char == letter) {
                mutedWord[index] = letter
                wordLength--
                isLetter = true
            }
            button.setEnabled(false);
            button.setBackgroundColor(Color.parseColor("#B8ADAB"))
            buttonsArray.add(button)
        }
        showWord(mutedWord)

        if(!isLetter){
            badAnswers++
            seiImage(badAnswers)
            Toast.makeText(this, "Bad letter!", Toast.LENGTH_SHORT).show();
        }
        if(badAnswers == 5){
            Toast.makeText(this, "You lost!", Toast.LENGTH_SHORT).show();
            showWord(charWord)
        }
        if (wordLength == 0) {
            Toast.makeText(this, "You won!", Toast.LENGTH_SHORT).show();
            seiImage(0)
        }
    }


    private fun clearButtons(){
        for( button in buttonsArray){
            button.setEnabled(true)
            button.setBackgroundColor(Color.parseColor("#AACDCA"))
        }
    }

    private fun seiImage(x:Int){
        when (x) {
            0 -> binding.image.setImageResource(R.drawable.wisielec_win)
            1 -> binding.image.setImageResource(R.drawable.wisielec6)
            2 -> binding.image.setImageResource(R.drawable.wisielec7)
            3 -> binding.image.setImageResource(R.drawable.wisielec8)
            4 -> binding.image.setImageResource(R.drawable.wisielec9)
            5 -> binding.image.setImageResource(R.drawable.wisielec10)
        }
    }

    private fun showWord (list: MutableList<Char>){
        list.toList()
        val word = list.joinToString(" ")
        binding.phrase.text = word
    }

    fun restart(view: View){
        startGame()
    }

}