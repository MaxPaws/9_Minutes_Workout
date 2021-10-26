package com.coldkitchen.severalminuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import com.coldkitchen.severalminuteworkout.ExerciseActivity.Companion.soundCanBePlayed
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.activity_finish.*
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity(), TextToSpeech.OnInitListener  {

    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        tts = TextToSpeech(this, this)


        setSupportActionBar(tbar_excersize_activity)
        val actionBar = supportActionBar
        //Display Back button on the top (action bar)
        if(actionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)}

        tbar_finish_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnFinish.setOnClickListener {
            // Finish it and go back
            finish()
        }
        addDateToDB()

    }

    private fun addDateToDB(){
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time
        val dbHandler = SqliteOpenHelper(this, null)

        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val formattedDate = sdf.format(dateTime)

        var canAdd = true

        // Add new date to DB only if it's not already there
        for(s in dbHandler.getAllCompletedDatesList()){
            if(s.startsWith(formattedDate))
                canAdd = false
        }

        //Adding formatted current date to my db
        if(canAdd)
            dbHandler.addDate(formattedDate)
    }

    private fun speakOut(speech: String){
       if(soundCanBePlayed)
            tts!!.speak(speech, TextToSpeech.QUEUE_FLUSH, null, "")
       else tts!!.stop()
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                Log.e("TTS", "Initialization failed!")
        }
        speakOut("Congratulations! you have finished today workout! " +
                "Stay healthy. And see you tomorrow!")
    }

    override fun onDestroy() {
        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}