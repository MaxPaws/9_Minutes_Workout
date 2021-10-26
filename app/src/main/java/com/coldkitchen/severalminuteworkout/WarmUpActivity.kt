package com.coldkitchen.severalminuteworkout

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.activity_warm_up.*
import kotlinx.android.synthetic.main.custom_dialog_back_button.*
import java.util.*

class WarmUpActivity : AppCompatActivity() {

    private var imageTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warm_up)

        setSupportActionBar(tbar_warmup_activity)
        val actionBar = supportActionBar
        if(actionBar != null){
           actionBar.setDisplayHomeAsUpEnabled(true)}

        tbar_warmup_activity.setNavigationOnClickListener {
            finish()
        }

        btnWarmedUp.setOnClickListener {
            finish()
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        catWarmingUpStart()
    }

    private fun catWarmingUpStart(){
        val imagesArray = arrayOf(R.drawable.ic_warmup1, R.drawable.ic_warmup2,
                                    R.drawable.ic_warmup3, R.drawable.ic_warmup4)
        var i = 0

        imageTimer = object: CountDownTimer(30000, 500){
            override fun onTick(millisUntilFinished: Long) {

                ivWarmUpImage.setImageResource(imagesArray[i])
                if(i < imagesArray.size-1)
                    i++
                else
                    i = 0

            }

            override fun onFinish() {

            }
        }.start()
    }
}