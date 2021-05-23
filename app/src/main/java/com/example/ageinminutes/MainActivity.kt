package com.example.ageinminutes

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ageinminutes.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDatePicker.setOnClickListener { view -> // we get the view, then we use the view below
            clickDatePicket(view)
        }
    }

    fun clickDatePicket(view: View) {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->
                Toast.makeText(this, "The choosen year is $selectedYear, and the month is $selectedMonth, and the day is $selectedDayOfMonth", Toast.LENGTH_LONG).show()

                // All the calculation belows
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"

                binding.tvSelectedDate.setText(selectedDate)

                // format the date (Simple date format)
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.GERMANY)

                val theDate = sdf.parse(selectedDate) // <-- we converted it to a date object (parse)

                val selectedDateInMinutes = theDate!!.time / 60000

                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                val currentDateToMinutes = currentDate!!.time / 60000

                val differenceInMinutes = currentDateToMinutes - selectedDateInMinutes

                binding.tvSelectedDateInMinute.setText(differenceInMinutes.toString())
            },
            year, month, day
        )
        dpd.datePicker.setMaxDate(Date().time - 86400000) // <-- this is just the value of days in millisecond
        dpd.show()
    }
}
