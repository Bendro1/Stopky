package com.example.stopky

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Chronometer.OnChronometerTickListener
import com.example.stopky.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var zaciatokVSekundach : Long = 0;
    private var stopkyBezia = false
    private var progress = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.stopky.format = "%s"
        binding.stopky.base = SystemClock.elapsedRealtime()
        binding.stopky.onChronometerTickListener = OnChronometerTickListener {
            chronometer -> progress = (binding.stopky.text [3].toString() + binding.stopky.text [4].toString()).toFloat()*5/3f
            updateProgressBar()
            if ((SystemClock.elapsedRealtime()-chronometer.base)>=3599000){
                chronometer.base=SystemClock.elapsedRealtime()
            }
        }
        binding.spustitStopkyBtn.setOnClickListener {
            if (!stopkyBezia){
                binding.stopky.base=SystemClock.elapsedRealtime() - zaciatokVSekundach
                binding.stopky.start()
                stopkyBezia = true
                binding.spustitStopkyBtn.text = "PAUSE"
            }
            else {
                binding.stopky.stop()
                zaciatokVSekundach = SystemClock.elapsedRealtime() - binding.stopky.base
                stopkyBezia = false
                binding.spustitStopkyBtn.text = "START"
            }
        }
        binding.resetStopkyBtn.setOnClickListener {
            resetStopky()
        }
    }

    private fun resetStopky (){
        binding.stopky.stop()
        stopkyBezia = false
        binding.spustitStopkyBtn.text = "START"
        binding.stopky.base = SystemClock.elapsedRealtime()
        zaciatokVSekundach = 0
        progress = 0f
        updateProgressBar()
    }

    private fun updateProgressBar (){
        binding.progressBar.progress = progress.toInt()
    }
}