package com.coldkitchen.severalminuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_bmiactivity.*
import kotlinx.android.synthetic.main.activity_hstory.*
import ru.cleverpumpkin.calendar.CalendarView
import java.util.*

class HstoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hstory)

        setSupportActionBar(toolbar_History)
        val actionBar = supportActionBar
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "HISTORY"
        }
        toolbar_History.setNavigationOnClickListener {
            onBackPressed()
        }

        getAllCompletedDates()
    }

    private fun getAllCompletedDates(){
        val dbHandler = SqliteOpenHelper(this,null)
        val completedDatesList = dbHandler.getAllCompletedDatesListCalendarDates()

        if(completedDatesList.size > 0){
            tv_noData.visibility = View.GONE
            calendar_view.visibility = View.VISIBLE


            // Setting dates to the Pumpkin calendar widget
            calendar_view.setupCalendar(selectionMode = CalendarView.SelectionMode.MULTIPLE,
                selectedDates = completedDatesList)
            calendar_view.dateSelectionFilter = { date ->
                date.dayOfWeek != Calendar.SATURDAY && date.dayOfWeek != Calendar.SUNDAY
                        && date.dayOfWeek != Calendar.MONDAY
                        && date.dayOfWeek != Calendar.TUESDAY
                        && date.dayOfWeek != Calendar.WEDNESDAY
                        && date.dayOfWeek != Calendar.THURSDAY
                        && date.dayOfWeek != Calendar.FRIDAY
            }

        } else {
            calendar_view.visibility = View.GONE
            tv_noData.visibility = View.VISIBLE
        }
    }

}