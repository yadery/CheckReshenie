package com.example.checkresh

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.checkresh.databinding.ActivityMainBinding
import kotlin.math.abs
import kotlin.math.roundToInt
class MainActivity : AppCompatActivity() {
    private var timeTotal = 0.0
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    private fun resetTimer()
    {
        binding.chronometerOnOne.stop()
        var time =  (SystemClock.elapsedRealtime() - binding.chronometerOnOne.base).toDouble() / 1000

        timeTotal += time;
        if (time < binding.txtViewTimeMin.text.toString().toDouble()){
            binding.txtViewTimeMin.text = time.toString();
        }
        else if (binding.txtViewTimeMin.text.toString().toDouble() == 0.0){
            binding.txtViewTimeMin.text = time.toString();
        }
        if (time > binding.txtViewTimeMax.text.toString().toDouble()){
            binding.txtViewTimeMax.text = time.toString();
        }
        var avgTime = ((timeTotal / binding.txtViewTotal.text.toString().toInt()) * 100).roundToInt().toDouble() / 100
        binding.txtViewTimeAvg.text = avgTime.toString()
    }
    private fun startTimer()
    {
        binding.chronometerOnOne.base = SystemClock.elapsedRealtime();
        binding.chronometerOnOne.start()
    }
    class Equation(val equationText: String, val equationCorrectness: Boolean)
    class ResultOfEquation(val firstNumber: Int, val secondNumber: Int, val result: Double)
    fun resultGet(operand: Char): ResultOfEquation{
        var result = 0.00;
        val firstRng = (10..99).random();
        val secondRng = (10..99).random();
        when (operand){
            '/' -> result = ((firstRng.toDouble() / secondRng) * 100).roundToInt().toDouble() / 100;
            '*' -> result = (firstRng * secondRng).toDouble();
            '+' -> result = (firstRng + secondRng).toDouble();
            '-' -> result = (firstRng - secondRng).toDouble();
        }
        return ResultOfEquation(firstRng, secondRng, result)
    }
    fun equationCreation(): Equation{
        val operands = arrayOf('*','/','-','+');
        val operand = operands.random();
        val listOfResults = listOf(resultGet(operand), resultGet(operand));
        val result = listOfResults.random().result;
        val equtnR = listOfResults[0]
        var equation = "${equtnR.firstNumber} ${operand} ${equtnR.secondNumber} = ${result}";
        var correctness = result == listOfResults[0].result
        return Equation(equation, correctness)
    }


    private var equation = Equation("",false);
    fun onClickStart(view: View) {
        startTimer()
        equation = equationCreation();
        binding.txtViewEquation.setBackgroundColor(Color.WHITE);
        binding.txtViewEquation.text = equation.equationText;
        binding.btnRight.isEnabled = true;
        binding.btnWrong.isEnabled = true;
        binding.btnStart.isEnabled = false;
    }
    fun onClickRight(view: View) {
        var total = binding.txtViewTotal.text.toString().toInt();
        total++;
        binding.txtViewTotal.text = total.toString();
        var loses = binding.txtViewWrong.text.toString().toInt();
        var wins = binding.txtViewRight.text.toString().toInt();
        if (equation.equationCorrectness){
            wins++;
            binding.txtViewRight.text = wins.toString();
            binding.txtViewEquation.setBackgroundColor(Color.GREEN);
        }
        else{
            loses++;
            binding.txtViewWrong.text = loses.toString();
            binding.txtViewEquation.setBackgroundColor(Color.RED);
        }
        var percentage = ((wins.toDouble() / total) * 100).roundToInt().toDouble();
        binding.txtViewPercentage.text = percentage.toString() + "%";
        binding.btnStart.isEnabled = true;
        binding.btnRight.isEnabled = false;
        binding.btnWrong.isEnabled = false;

        resetTimer();
    }
    fun onClickWrong(view: View) {
        var total = binding.txtViewTotal.text.toString().toInt();
        total++;
        binding.txtViewTotal.text = total.toString();
        var loses = binding.txtViewWrong.text.toString().toInt();
        var wins = binding.txtViewRight.text.toString().toInt();
        if (!equation.equationCorrectness){
            wins++;
            binding.txtViewRight.text = wins.toString();
            binding.txtViewEquation.setBackgroundColor(Color.GREEN);
        }
        else{
            loses++;
            binding.txtViewWrong.text = loses.toString();
            binding.txtViewEquation.setBackgroundColor(Color.RED);
        }
        var percentage = ((wins.toDouble() / total) * 100).roundToInt().toDouble();
        binding.txtViewPercentage.text = percentage.toString() + "%";
        binding.btnStart.isEnabled = true;
        binding.btnRight.isEnabled = false;
        binding.btnWrong.isEnabled = false;

        resetTimer();
    }
    fun textMsg(s:String,c: Context){
        Toast.makeText(c,s, Toast.LENGTH_SHORT).show()
    }
}