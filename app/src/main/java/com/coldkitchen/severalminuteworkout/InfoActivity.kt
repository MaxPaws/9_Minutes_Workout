package com.coldkitchen.severalminuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_info.*
import kotlinx.android.synthetic.main.activity_warm_up.*

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        setSupportActionBar(tbar_setup_activity)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "IMPORTANT REMARK"}

        tbar_setup_activity.setNavigationOnClickListener {
            finish()
        }

        btnInfoQuit.setOnClickListener {
            finish()
        }
    }
}