package com.example.todolist2

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.Instant
import java.time.LocalDateTime
import java.util.*


class MainActivity : AppCompatActivity(), OnClickListener, AdapterView.OnItemSelectedListener {

    var sorts = arrayOf(
        "Sortuj po nazwie",
        "Sortuj po dacie początku",
        "Sortuj po dacie końca",
        "Sortuj po ikonie",
        "Sortuj po priorytecie",
        "Sortuj po lokalizacji",
        "Wybierz na dzisiaj",
        "Wybierz od dzisiaj"
    )


    private val newTaskActivityRequestCode = 1
    val adapter = TaskListAdapter(this)
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as TasksApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        taskViewModel.allTasks.observe(owner = this) { tasks ->
            // Update the cached copy of the words in the adapter.
            tasks.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewTaskActivity::class.java)
            startActivityForResult(intent, newTaskActivityRequestCode)
        }


        val spin = findViewById<View>(R.id.mySpinner) as Spinner
        spin.onItemSelectedListener = this

        val aa: ArrayAdapter<*> =
            ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, sorts)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spin.setAdapter(aa);


    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        var task_name: String? = null
        var task_start_date: Long? = null
        var task_end_date: Long? = null
        var task_text: String? = null
        var task_icon: Images? = null
        var task_priority: String? = null
        var task_localization: String? = null

        if (requestCode == newTaskActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringExtra("task_name")?.let { reply -> task_name = reply }
            intentData?.getLongExtra("task_start_date", 0)?.let { reply -> task_start_date = reply }
            intentData?.getLongExtra("task_end_date", 0)?.let { reply -> task_end_date = reply }
            intentData?.getStringExtra("task_text")?.let { reply -> task_text = reply }
            intentData?.getSerializableExtra("task_icon")
                ?.let { reply -> task_icon = reply as Images }
            intentData?.getStringExtra("task_priority")?.let { reply -> task_priority = reply }
            intentData?.getStringExtra("task_localization")
                ?.let { reply -> task_localization = reply }

            val task_start_date1 = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(task_start_date!!),
                TimeZone.getDefault().toZoneId()
            )
            val task_end_date1 = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(task_end_date!!),
                TimeZone.getDefault().toZoneId()
            )



            if (task_name != null) {
                val task = Task(
                    0,
                    task_name,
                    task_start_date1,
                    task_end_date1,
                    task_text,
                    task_icon,
                    task_priority,
                    task_localization
                )
                taskViewModel.insert(task)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Nie dodano taska",
                    Toast.LENGTH_LONG
                ).show()

            }
        }
    }


    override fun deleteOnLongClick(task: Task) {
        taskViewModel.deleteItem(task)
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            0 -> {
                taskViewModel.allTasks.observe(owner = this) { tasks ->
                    tasks.let {
                        adapter.submitList(
                            it
                        )
                    }
                }
            }
            1 -> {
                taskViewModel.sortDataByDate.observe(owner = this) { tasks ->
                    tasks.let {
                        adapter.submitList(
                            it
                        )
                    }
                }
            }
            2 -> {
                taskViewModel.sortDataByEndDate.observe(owner = this) { tasks ->
                    tasks.let {
                        adapter.submitList(
                            it
                        )
                    }
                }
            }
            3 -> {
                taskViewModel.sortDataByIcon.observe(owner = this) { tasks ->
                    tasks.let {
                        adapter.submitList(
                            it
                        )
                    }
                }
            }
            4 -> {
                taskViewModel.sortDataByPriority.observe(owner = this) { tasks ->
                    tasks.let {
                        adapter.submitList(
                            it
                        )
                    }
                }
            }
            5 -> {
                taskViewModel.sortDataByLocalization.observe(owner = this) { tasks ->
                    tasks.let {
                        adapter.submitList(
                            it
                        )
                    }
                }
            }
            6 -> {
                taskViewModel.sortDataByEndDate.observe(owner = this) { tasks ->
                    tasks.let {
                        adapter.submitList(
                            it.filter { task -> task.task_end_date!!.dayOfMonth == LocalDateTime.now().dayOfMonth && task.task_end_date.month == LocalDateTime.now().month && task.task_end_date.year == LocalDateTime.now().year })
                    }
                }
            }
            7 -> {
                taskViewModel.sortDataByDate.observe(owner = this) { tasks ->
                    tasks.let {
                        adapter.submitList(
                            it.filter { task -> task.task_start_date!!.dayOfMonth == LocalDateTime.now().dayOfMonth && task.task_start_date.month == LocalDateTime.now().month && task.task_start_date.year == LocalDateTime.now().year })
                    }
                }
            }
        }
    }

}