package com.example.todolist2


import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist2.databinding.ActivityNewTaskBinding
import java.util.*
import kotlin.properties.Delegates


class NewTaskActivity  : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var icons = arrayOf("Pozostałe", "Sprzątanie", "Kodowanie", "Szkoła", "Praca", "Ulubione")
    var priorities= arrayOf("ASAP", "WYSOKI", "ŚREDNI", "NISKI")
    var localizations= arrayOf("BRAK", "W SZKOLE", "W PRACY", "W DOMU", "NA DWORZE", "W SAMOCHODZIE")

    private lateinit var binding: ActivityNewTaskBinding



    private lateinit var editTaskView: EditText
    private lateinit var task_start_date: DatePicker
    private lateinit var task_end_date: DatePicker
    private lateinit var task_start_time: TimePicker
    private lateinit var task_end_time: TimePicker
    private lateinit var task_text: EditText
    private lateinit var task_icon: Images
    private lateinit var task_priority: NumberPicker
    private lateinit var task_localization: NumberPicker


    @RequiresApi(Build.VERSION_CODES.M)
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewTaskBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        task_localization = binding.taskLocalization
        task_localization.minValue = 1
        task_localization.maxValue = 6
        task_localization.displayedValues = localizations

        task_priority = binding.taskPriority
        task_priority.minValue = 1
        task_priority.maxValue = 4
        task_priority.displayedValues = priorities


        editTaskView = binding.editTask
        task_start_date = binding.startDate
        task_start_time = binding.startTime
        task_end_date = binding.endDate
        task_end_time = binding.endTime
        task_text = binding.taskText


        val spin = findViewById<View>(R.id.iconSpinner) as Spinner
        spin.onItemSelectedListener = this

        val aa: ArrayAdapter<*> =
            ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, icons)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spin.adapter = aa;




        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editTaskView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {

                val calendar1 = Calendar.getInstance()
                calendar1[task_start_date.year, task_start_date.month, task_start_date.dayOfMonth, task_start_time.hour,  task_start_time.minute] = 0
                val startTime = calendar1.timeInMillis

                val calendar2 = Calendar.getInstance()
                calendar2[task_end_date.year, task_end_date.month, task_end_date.dayOfMonth, task_end_time.hour,  task_end_time.minute] = 0
                val endTime = calendar2.timeInMillis



                val taskName = editTaskView.text.toString()
                val taskText = task_text.text.toString()
                val taskIcon = task_icon
                val taskPriority = task_priority.value.toString()
                val taskLocalization = task_localization.value.toString()

                if(checkIfOk(startTime, endTime, taskName, taskText)){
                    replyIntent.putExtra("task_name", taskName)
                    replyIntent.putExtra("task_start_date", startTime)
                    replyIntent.putExtra("task_end_date", endTime)
                    replyIntent.putExtra("task_text", taskText)
                    replyIntent.putExtra("task_icon", taskIcon)
                    replyIntent.putExtra("task_priority", taskPriority)
                    replyIntent.putExtra("task_localization", taskLocalization)
                    setResult(Activity.RESULT_OK, replyIntent)
                    finish()
                }
            }
        }
    }



    companion object {
        const val EXTRA_REPLY = "com.example.android.tasklistsql.REPLY"
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position){
            0 -> {task_icon = Images.CHECK}
            1 -> {task_icon = Images.CLEAN}
            2 -> {task_icon = Images.CODE}
            3 -> {task_icon = Images.SHOOL}
            4 -> {task_icon = Images.MONEY}
            5 -> {task_icon = Images.STAR}
        }
    }

    fun checkIfOk(startTime: Long, endTime: Long, taskName: String, taskText: String): Boolean {
        val timestamp: Long = System.currentTimeMillis()
        if(startTime > endTime){
            Toast.makeText(applicationContext, "Czas początku taska nie może być później niż czas końca taska", Toast.LENGTH_LONG).show()
            return false
        }
        else if (startTime < timestamp){
            Toast.makeText(applicationContext, "Czas początku taska nie może być później niż czas obecny", Toast.LENGTH_LONG).show()
            return false
        }
        else if (taskText == ""){
            Toast.makeText(applicationContext, "Opis tasku nie może być pusty", Toast.LENGTH_LONG).show()
            return false
        }
        else{
            Toast.makeText(applicationContext, "Task utworzony pomyślnie", Toast.LENGTH_LONG).show()
            return true
        }

    }


}