// Show the exercises and the rest view with countdown timers that "draw" bars progress,
// by hiding and reveal UIs of the exercise or of the rest view.
// Separate timer for changing the pictures that showing how to do an exercise.
// Speak out the speech in certain intervals of countdown timers.
// Implemented functions for fake pause and resume timers.
// All exercises are in ArrayList that is from ExerciseConstants made by ExerciseModel pattern.

package com.coldkitchen.severalminuteworkout

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.custom_dialog_back_button.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    companion object{
        var soundCanBePlayed = true
    }

    private val EXERCISE_TIME = 30000L
    private val REST_TIME = 15000L

    private var restTimer: CountDownTimer? = null
    private var exTimer: CountDownTimer? = null
    private var imageTimer: CountDownTimer? = null
    private var exerciseCountdown = EXERCISE_TIME
    private var restCountdown = REST_TIME
    private var restProgress = 0
    private var exProgress = 0
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePos = -1
    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null
    private var exerciseAdapter: ExerciseStatusAdapter? = null
    private var paused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        tts = TextToSpeech(this, this)

        setSupportActionBar(tbar_excersize_activity)
        val actionBar = supportActionBar
        if(actionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)}
        tbar_excersize_activity.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        exerciseList = ExercisesConstants.defaultExerciseList()
        setupRestView()
        setupExerciseStatusRecyclerView()

        fl_restTimer.setOnClickListener {
            if(paused)
                resumeRestTimer()
            else
                pauseRestTimer()
        }

        fl_exTimer.setOnClickListener {
            if (paused)
                resumeExTimer()
            else
                pauseExTimer()
        }


        /**
        * VOLUME SETUP VARIATIONS

        // This feature is muting volume of the phone, not the app
        // also its gets current image and compares it to the needed one, to know what action to do
        // now:

         iv_soundOnOff.setOnClickListener {
            val imageSrc = iv_soundOnOff.drawable.constantState
            val imageSoundOn =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_volume_up_24, null)
                    ?.constantState
            if(imageSrc == imageSoundOn){
                iv_soundOnOff.setImageResource(R.drawable.ic_baseline_volume_off_24)
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_MUTE,0)
            }else{
                iv_soundOnOff.setImageResource(R.drawable.ic_baseline_volume_up_24)
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_UNMUTE,0)
            }
        }

        // And this is allow/not allow the app to play its sounds by the boolean var
        // also it change current image to needed one depending on the boolean var status
         */
        iv_soundOnOff.setOnClickListener {
            soundCanBePlayed = if(soundCanBePlayed){
                iv_soundOnOff.setImageResource(R.drawable.ic_baseline_volume_off_24)
                iv_exSoundOnOff.setImageResource(R.drawable.ic_baseline_volume_off_24)
                tts!!.stop()
                false
            }else{
                iv_soundOnOff.setImageResource(R.drawable.ic_baseline_volume_up_24)
                iv_exSoundOnOff.setImageResource(R.drawable.ic_baseline_volume_up_24)
                true
            }
        }

        iv_exSoundOnOff.setOnClickListener {
            soundCanBePlayed = if(soundCanBePlayed){
                iv_soundOnOff.setImageResource(R.drawable.ic_baseline_volume_off_24)
                iv_exSoundOnOff.setImageResource(R.drawable.ic_baseline_volume_off_24)
                tts!!.stop()
                false
            }else{
                iv_soundOnOff.setImageResource(R.drawable.ic_baseline_volume_up_24)
                iv_exSoundOnOff.setImageResource(R.drawable.ic_baseline_volume_up_24)
                true
            }
        }
    }

    override fun onBackPressed() {
        customDialogForBackButton()
    }

    private fun setRestProgressBar(){
        var progressBarNumber = 15
        //  At the last Rest View we will need longer countdown and bar number:
        if(currentExercisePos == 11){
            progressBarNumber = 45
            restCountdown = 45000L
        }


        restTimer = object: CountDownTimer(restCountdown, 1000){

            override fun onTick(millisUntilFinished: Long) {
                // count current progress every second, for digits in progress Bar, and
                // for Pause/Resume feature
                restProgress++

                progressExcersizeBar.progress = progressBarNumber - restProgress

                tvTimer.text = (progressBarNumber - restProgress).toString()

                when(currentExercisePos){
                    -1 -> when(millisUntilFinished){
                        in 8000..9000 -> speakOut("Get ready! Next exercise is "
                                + exerciseList!![currentExercisePos+1].getName()
                                + ". Remember, that you can adapt your exercises and do it slower "
                                + "or faster, if you need it.")
                    }
                }
            }

            override fun onFinish() {
                if(currentExercisePos<exerciseList!!.size - 1){
                    currentExercisePos++
                    exerciseList!![currentExercisePos].setIsSelected(true)
                    // UPDATING THE ADAPTER
                    exerciseAdapter!!.notifyDataSetChanged()

                    setupExView()
                }else{
                    finish()
                    val intent = Intent(this@ExerciseActivity,
                        FinishActivity::class.java)

                    startActivity(intent)
                }
            }
        }.start()

    }

    private fun setExProgressBar(){
        var i = 0
        val imagesArray = exerciseList!![currentExercisePos].getImagesArray()

        exTimer = object: CountDownTimer(exerciseCountdown, 1000){

            override fun onTick(millisUntilFinished: Long) {
                // count current progress every second, for digits in  progress Bar, and
                // for Pause/Resume feature
                exProgress++

                progressExcersizeSecondBar.progress = 30 - exProgress
                tvSecondTimer.text = (30 - exProgress).toString()

                when(millisUntilFinished){
                    in 15000..16000 -> speakOut("15 seconds left.")
                    in 5000..6000 -> speakOut("5.")
                    in 4000..5000 -> speakOut("4.")
                    in 3000..4000 -> speakOut("3.")
                    in 2000..3000 -> speakOut("2.")
                    in 1000..2000 -> speakOut("1.")
                }
            }

            override fun onFinish() {
                if(currentExercisePos<exerciseList!!.size - 1){

                    exerciseList!![currentExercisePos + 1].setIsSelected(true)
                    exerciseList!![currentExercisePos].setIsSelected(false)
                    exerciseList!![currentExercisePos].setIsComplited(true)
                    exerciseAdapter!!.notifyDataSetChanged()


                }

                setupRestView()
            }
        }.start()

        var imageCountDown = 1000L
        when(currentExercisePos){
            3 -> imageCountDown = 700L
            4,5,10,6 -> imageCountDown = 850L
            2 -> imageCountDown = 500L
            8,0 -> imageCountDown = 250L
            11 -> imageCountDown = 15000
        }

        imageTimer = object: CountDownTimer(exerciseCountdown, imageCountDown){

            override fun onTick(millisUntilFinished: Long) {
                ivExerImage.setImageResource(imagesArray[i])
                if(i < imagesArray.size-1)
                    i++
                else
                    i = 0

                if(millisUntilFinished in 22000..23000){
                    when(currentExercisePos){
                        0 -> speakOut("Be sure to not going too fast. Watch your heart rate.")
                        1 -> speakOut("Try to hold your knees at an angle of 90 degrees.")
                        2 -> speakOut("Do it in a measured way.")
                        3 -> speakOut("Lift your body by your abdominals, not with your loins.")
                        4 -> speakOut("Remember to change your legs each step.")
                        5 -> speakOut("Keep your hands on your belt or in front of you, and control your speed.")
                        6 -> speakOut("Try it with a straighten or with a crossed legs.")
                        7 -> speakOut("Hold your elbows at an angle of 90 degrees, and keep your body straight.")
                        9 -> speakOut("At the lowest position, your knees must be at an angle of 90 degrees.")
                        10 -> speakOut("Change a side of rotation each push-up, and be sure your hand is straight and pointed straight up.")
                        11 -> speakOut("Change the side on the halfway through the exercise.")
                    }
                }
            }
            override fun onFinish() {}
        }.start()
    }

    private fun setupRestView(){
        paused = false
        try {
            if(soundCanBePlayed){
                player = MediaPlayer.create(applicationContext, R.raw.press_start)
                player!!.isLooping = false
                player!!.start()
            }else if(player != null){
                player!!.stop()
            }
        }catch(e: Exception){
            e.printStackTrace()
        }

        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
            restCountdown = REST_TIME
        }
        ll_restTimer.visibility = View.VISIBLE
        ll_exTimer.visibility = View.GONE

        if(currentExercisePos < exerciseList!!.size - 1) {
            val nextExName = exerciseList!![currentExercisePos + 1].getName()
            tvRestExercName.text = nextExName
            speakOut("Ok, it's the rest time! Next exercise is $nextExName.")
        }else{
            tv_getReady.text = "WELL DONE"
            tv_upcomingExercise.text = "TAKE SOME TIME\nTO WALK AROUND\n TO SLOW YOUR HEARTBEAT " +
                    "\nAND PREPARE YOUR BODY TO REST"
            tvRestExercName.visibility = View.GONE
            speakOut(getString(R.string.final_rest_speech))
        }

        setRestProgressBar()
    }

    private fun setupExView(){
        paused = false

        if(exTimer != null){
            exTimer!!.cancel()
            exProgress = 0
            exerciseCountdown = EXERCISE_TIME
        }
        ll_restTimer.visibility = View.GONE
        ll_exTimer.visibility = View.VISIBLE

        ivExerImage.setImageResource(exerciseList!![currentExercisePos].getImagesArray()[0])
        tv_exerciseName.text = exerciseList!![currentExercisePos].getName()

        speakOut(exerciseList!![currentExercisePos].getName() + "! Let's go!")

        setExProgressBar()
    }

    private fun setupExerciseStatusRecyclerView(){
        //Creating a LinearLayout for rec.view to display the adapter with a statuses:
        rvExerciseStatus.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)
        //Passing list and context to the adapter, creating it:
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)
        //Setting adapter to my layout:
        rvExerciseStatus.adapter = exerciseAdapter
    }

    // On View Closed
    override fun onDestroy() {
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        if(exTimer != null){
            exTimer!!.cancel()
            exProgress = 0
        }

        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }

        if(player != null){
            player!!.stop()
        }

        super.onDestroy()
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
    }

    private fun customDialogForBackButton(){
        if(ll_restTimer.visibility == View.VISIBLE)
            pauseRestTimer()
        else
            pauseExTimer()

        val customDialog = Dialog(this)
        customDialog.setContentView(R.layout.custom_dialog_back_button)

        customDialog.btn_Yes.setOnClickListener {
            finish()
            customDialog.dismiss()
        }

        customDialog.btn_No.setOnClickListener {
            if(ll_restTimer.visibility == View.VISIBLE)
                resumeRestTimer()
            else
                resumeExTimer()
            customDialog.dismiss()
        }
        customDialog.show()
    }

    // "Pause" the exercise timer and image changing timer
    private fun pauseExTimer(){
        if (exTimer != null) {
            exTimer!!.cancel()
        }
        if (imageTimer != null) {
            imageTimer!!.cancel()
        }
        paused = true
        ll_exPauseText.visibility = View.VISIBLE
    }

    // "Resume" the exercise timer and image changing timer by start it again in setExProgressBar
    // function
    private fun resumeExTimer(){
        exerciseCountdown = EXERCISE_TIME - exProgress * 1000
        setExProgressBar()
        paused = false
        ll_exPauseText.visibility = View.GONE
    }

    private fun pauseRestTimer(){
        if (restTimer != null) {
            restTimer!!.cancel()
        }
        paused = true
        ll_restPauseText.visibility = View.VISIBLE
    }

    private fun resumeRestTimer(){
        restCountdown = REST_TIME - restProgress * 1000
        setRestProgressBar()
        paused = false
        ll_restPauseText.visibility = View.GONE
    }
}