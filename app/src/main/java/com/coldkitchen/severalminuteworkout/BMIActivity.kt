package com.coldkitchen.severalminuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bmiactivity.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
    val US_UNITS_VIEW = "US_UNIT_VIEW"

    var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiactivity)

        setSupportActionBar(toolbar_BMI)
        val actionBar = supportActionBar
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "CALCULATE BMI"
        }
        toolbar_BMI.setNavigationOnClickListener {
            onBackPressed()
        }

        btn_BMI_Calculate.setOnClickListener {
           if(currentVisibleView == METRIC_UNITS_VIEW){
               if(validateCorrectMetricBMIUnits()){
                   val heightValue: Float = et_BMI_MetricHeight.text.toString().toFloat() / 100
                   val weightValue: Float = et_BMI_MetricWeight.text.toString().toFloat()
                   val BMI = weightValue / (heightValue * heightValue)
                   displayBMI(BMI)
               }else(
                       Toast.makeText(this@BMIActivity, "Please enter valid values.",
                           Toast.LENGTH_SHORT).show()
                       )
           } else {
               if(validateCorrectUSBMIUnits()){
                   val heightValue1: String = et_BMI_USHeightFeet.text.toString()
                   val heightValue2: String = et_BMI_USHeightInch.text.toString()
                   val finalHeight: Float = heightValue2.toFloat() + heightValue1.toFloat() * 12
                   val weightValue: Float = et_BMI_USWeight.text.toString().toFloat()
                   val BMI = 703 * (weightValue / (finalHeight * finalHeight))
                   displayBMI(BMI)
               }else(
                       Toast.makeText(this@BMIActivity, "Please enter valid values.",
                           Toast.LENGTH_SHORT).show()
                       )
           }
        }

        makeVisibleMetricView()

        rg_Units.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.rb_Metric)
                makeVisibleMetricView()
            else makeVisibleUSView()
        }
    }

    private fun validateCorrectMetricBMIUnits(): Boolean{
        var isCorrect = false

        if(et_BMI_MetricWeight.text.toString().isNotEmpty() && et_BMI_MetricHeight.text.toString().isNotEmpty())
            isCorrect = true

        return isCorrect
    }

    private fun validateCorrectUSBMIUnits(): Boolean{
        var isCorrect = false

        if(et_BMI_USWeight.text.toString().isNotEmpty() &&
            et_BMI_USHeightFeet.text.toString().isNotEmpty() &&
            et_BMI_USHeightInch.text.toString().isNotEmpty())
            isCorrect = true

        return isCorrect
    }

    private fun displayBMI(bmi: Float){
        val BMILabel: String
        val BMIDescritpion: String
        when(bmi){
            in Float.MIN_VALUE .. 15f -> {
                BMILabel = "Very severely underweight"
                BMIDescritpion = "You really need to take better care of yourself. Eat more!"
            }
            in 15f .. 16f -> {
                BMILabel = "Severely underweight"
                BMIDescritpion = "You really need to take better care of yourself. Eat more!"
            }
            in 16.1f .. 18.5f -> {
                BMILabel = "Underweight"
                BMIDescritpion = "You need to take better care of yourself. Eat more!"
            }
            in 18.6f .. 25f -> {
                BMILabel = "Good"
                BMIDescritpion = "Congratulations! You are in a good shape!"
            }
            in 25.1f .. 30f -> {
                BMILabel = "Overweight"
                BMIDescritpion = "You need to take better care of yourself. Workout!"
            }
            in 30.1f .. 35f -> {
                BMILabel = "Obese Class | (Moderately obese)"
                BMIDescritpion = "You need to take better care of yourself. Workout!"
            }
            in 35.1f .. 40f -> {
                BMILabel = "Obese Class || (Severely obese)"
                BMIDescritpion = "You are in a dangerous condition! Act now!"
            }
            in 35.1f .. 40f -> {
                BMILabel = "Obese Class || (Severely obese)"
                BMIDescritpion = "You are in a dangerous condition! Act now!"
            }
            else -> {
                BMILabel = "Obese Class ||| (Very Severely obese)"
                BMIDescritpion = "You are in a very dangerous condition! Act now!"
            }
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        ll_BMI_Result.visibility = View.VISIBLE

        tv_ValueBMI.text = bmiValue
        tv_TypeBMI.text = BMILabel
        tv_DescriptionBMI.text = BMIDescritpion
    }

    private fun makeVisibleMetricView(){
        currentVisibleView = METRIC_UNITS_VIEW
        til_BMI_MetricWeight.visibility = View.VISIBLE
        til_BMI_MetricHeight.visibility = View.VISIBLE
        til_BMI_USWeight.visibility = View.GONE
        ll_BMI_USUnitsHeight.visibility = View.GONE
        ll_BMI_Result.visibility = View.INVISIBLE

        et_BMI_MetricWeight.text!!.clear()
        et_BMI_MetricHeight.text!!.clear()
    }

    private fun makeVisibleUSView(){
        currentVisibleView = US_UNITS_VIEW
        til_BMI_MetricWeight.visibility = View.GONE
        til_BMI_MetricHeight.visibility = View.GONE
        til_BMI_USWeight.visibility = View.VISIBLE
        ll_BMI_USUnitsHeight.visibility = View.VISIBLE
        ll_BMI_Result.visibility = View.INVISIBLE

        et_BMI_USWeight.text!!.clear()
        et_BMI_USHeightFeet.text!!.clear()
        et_BMI_USHeightInch.text!!.clear()
    }

}