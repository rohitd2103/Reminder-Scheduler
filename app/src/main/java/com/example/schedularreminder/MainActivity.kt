package com.example.schedularreminder

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var reminderEditText: EditText
    private lateinit var selectedDate: String
    private lateinit var selectedTime: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reminderEditText = findViewById(R.id.reminderEditText)

        val selectDateButton: Button = findViewById(R.id.selectDateButton)
        selectDateButton.setOnClickListener {
            showDatePicker()
        }

        val selectTimeButton: Button = findViewById(R.id.selectTimeButton)
        selectTimeButton.setOnClickListener {
            showTimePicker()
        }

        val addReminderButton: Button = findViewById(R.id.addReminderButton)
        addReminderButton.setOnClickListener {
            val reminderText = reminderEditText.text.toString().trim()

            if (reminderText.isNotEmpty() && selectedDate.isNotEmpty() && selectedTime.isNotEmpty()) {
                addReminder(reminderText, selectedDate, selectedTime)
                reminderEditText.text.clear()
                selectedDate = ""
                selectedTime = ""
            }
        }

        val requestPermissionButton: Button = findViewById(R.id.requestPermissionButton)
        requestPermissionButton.setOnClickListener {
            requestNotificationPermission()
        }
    }

    private fun addReminder(reminderText: String, date: String, time: String) {
        val remindersLayout: LinearLayout = findViewById(R.id.remindersLayout)

        val reminderLayout = LinearLayout(this)
        reminderLayout.orientation = LinearLayout.HORIZONTAL
        reminderLayout.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val reminderButton = Button(this)
        reminderButton.text = "$reminderText\nDate: $date, Time: $time"
        reminderButton.layoutParams = LinearLayout.LayoutParams(
            0,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            1f
        )

        val deleteButton = Button(this)
        deleteButton.text = "Delete"
        deleteButton.setOnClickListener {
            remindersLayout.removeView(reminderLayout)
        }

        reminderLayout.addView(reminderButton)
        reminderLayout.addView(deleteButton)
        remindersLayout.addView(reminderLayout)
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            selectedDate = "$dayOfMonth-${monthOfYear + 1}-$year"
        }, year, month, dayOfMonth)
        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val selectedDateTime = Calendar.getInstance()
            selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
            selectedDateTime.set(Calendar.MINUTE, minute)
            selectedTime = timeFormat.format(selectedDateTime.time)
        }, hourOfDay, minute, true)
        timePickerDialog.show()
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            startActivity(intent)
        } else {

        }
    }
}
