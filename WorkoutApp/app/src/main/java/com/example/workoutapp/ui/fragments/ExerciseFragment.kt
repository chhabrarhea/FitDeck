package com.example.workoutapp.ui.fragments

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.workoutapp.R
import com.example.workoutapp.database.models.Exercise
import com.example.workoutapp.database.models.Routine
import com.example.workoutapp.databinding.FragmentExerciseBinding
import com.example.workoutapp.other.Timer
import timber.log.Timber
import java.util.*


class ExerciseFragment : Fragment(), TextToSpeech.OnInitListener {

    private lateinit var binding: FragmentExerciseBinding

    private var currentExercise = 0
    private lateinit var routine: Routine
    private lateinit var exercises: List<Exercise>
    private lateinit var exerciseTimer: Timer
    private lateinit var restTimer: Timer
    private var tts:TextToSpeech?=null
    private var mediaPlayer:MediaPlayer?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExerciseBinding.inflate(layoutInflater, null, false)
        routine = arguments?.getParcelable("routine")!!
        exercises = routine.exercises
        exerciseTimer=Timer({item:Long->updateExerciseTimerUi(item)},{onExerciseFinished()})
        restTimer=Timer({item:Long->updateRestTimerUi(item)},{onRestFinished()})
        tts= TextToSpeech(requireContext(),this)
        mediaPlayer= MediaPlayer.create(requireContext(), R.raw.finish_sound)
        mediaPlayer!!.isLooping=false
        initializeUI()
        return binding.root
    }

    private fun initializeRest() {
        speakOut("Next is ${exercises[currentExercise].name}")
        binding.gif.setImageResource(exercises[currentExercise].image)
        binding.exercise = exercises[currentExercise]
        binding.restRoot.visibility = View.VISIBLE
        val max = 10
        binding.progressBar.max = max
        binding.progressBar.progress = 0
        binding.timerTv.text = max.toString()
        restTimer.initializeTimer(max.toLong()*1000)
    }

    private fun initializeExercise() {
        speakOut("${exercises[currentExercise].name} ${exercises[currentExercise].duration} seconds")
        val duration = "/${exercises[currentExercise].duration}''"
        val max = exercises[currentExercise].duration*1000+1000
        binding.exerciseProgressBar.progress = 0
        binding.exerciseRoot.visibility = View.VISIBLE
        binding.totalDurationTv.text = duration
        binding.secondsPassedTv.text = "0''"
        binding.exerciseProgressBar.max = max
        exerciseTimer.initializeTimer(max.toLong())}

    private fun speakOut(s: String) {
        tts!!.speak(s, TextToSpeech.QUEUE_FLUSH, null, "")
    }


    private fun initializeUI() {
        binding.secondsPassedTv.setTextColor(Color.parseColor(routine.color))
        binding.header.setTextColor(Color.parseColor(routine.color))
        binding.exerciseProgressBar.setIndicatorColor(Color.parseColor(routine.color))
        binding.progressBar.setIndicatorColor(Color.parseColor(routine.color))
        binding.pauseExerciseBtn.setOnClickListener { exerciseTimer.pauseTimer() }
        initializeRest()
    }

    private fun updateExerciseTimerUi(millisUntilFinished: Long) {
        val max=exercises[currentExercise].duration*1000+1000
        var left=millisUntilFinished.toInt()
        if(left in 1001..1999)
            mediaPlayer!!.start()
        if(left in max/2-1000..max/2)
            speakOut("half time")
         left= max-left
        val passed = "${left/1000}''"
        binding.secondsPassedTv.text = passed
        binding.exerciseProgressBar.progress= (binding.exerciseProgressBar.max-millisUntilFinished).toInt()
    }

    private fun onExerciseFinished() {
        binding.exerciseRoot.visibility = View.GONE
        currentExercise++
        initializeRest()
    }
    private fun updateRestTimerUi(millisUntilFinished: Long){
        val seconds = millisUntilFinished / 1000
        binding.progressBar.incrementProgressBy(1)
        binding.timerTv.text = seconds.toString()
    }
    private fun onRestFinished(){
        binding.restRoot.visibility = View.GONE
        initializeExercise()
    }

    override fun onPause() {
        super.onPause()
        restTimer.pauseTimer()
        exerciseTimer.pauseTimer()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS)
            tts!!.language = Locale.US
    }

    override fun onDestroy() {
        super.onDestroy()
        if (tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if(mediaPlayer != null){
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
        }
    }


}