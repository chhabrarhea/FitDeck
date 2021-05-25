package com.example.workoutapp.ui.fragments
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.bumptech.glide.Glide
import com.example.workoutapp.R
import com.example.workoutapp.database.models.Exercise
import com.example.workoutapp.database.models.Routine
import com.example.workoutapp.databinding.FragmentExerciseBinding
import com.example.workoutapp.other.Timer
import com.example.workoutapp.ui.viewmodels.MainViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ExerciseFragment : Fragment(), TextToSpeech.OnInitListener {

    private lateinit var binding: FragmentExerciseBinding
    private val viewModel: MainViewModel by viewModels()
    private var currentExercise = 0
    private lateinit var routine: Routine
    private lateinit var exercises: List<Exercise>
    private val exerciseTimer: Timer= Timer({ item: Long -> updateExerciseTimerUi(item) }, { onExerciseFinished() })
    private val restTimer: Timer= Timer({ item: Long -> updateRestTimerUi(item) }, { onRestFinished() })
    private var tts: TextToSpeech? = null
    private var mediaPlayer: MediaPlayer? = null
    private var duration = System.currentTimeMillis()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExerciseBinding.inflate(layoutInflater, null, false)
        routine = arguments?.getParcelable("routine")!!
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        binding.routine=routine
        exercises = routine.exercises
        tts = TextToSpeech(requireContext(), this)
        initializeUI()
        return binding.root
    }

    private fun initializeRest() {
        if (currentExercise == exercises.size) {
            routineFinished()
            return
        }
        speakOut("Next is ${exercises[currentExercise].name}")
        loadGif()
        binding.exercise = exercises[currentExercise]
        binding.restRoot.visibility = View.VISIBLE
        val max = 10
        binding.progressBar.max = max
        binding.progressBar.progress = 0
        binding.timerTv.text = max.toString()
        restTimer.initializeTimer(max.toLong() * 1000)
    }

    private fun loadGif() {
        val ref = Firebase.storage.reference.child("gifs").child(exercises[currentExercise].image)
        if (exercises[currentExercise].isGif)
            Glide.with(this).asGif().load(ref).onlyRetrieveFromCache(true).into(binding.gif)
        else
            Glide.with(this).load(ref).onlyRetrieveFromCache(true).into(binding.gif)
    }

    private fun routineFinished() {
        viewModel.insertWorkout(
            routine.id,
            Date(System.currentTimeMillis()),
            System.currentTimeMillis() - duration,
            routine.caloriesBurned
        )
    }

    private fun initializeExercise() {
        binding.exerciseRoot.visibility = View.VISIBLE
        if (exercises[currentExercise].duration != 0) {
            binding.exerciseProgressBar.visibility = View.VISIBLE
            binding.pauseExerciseBtn.visibility = View.VISIBLE
            binding.repsFab.visibility = View.GONE
            binding.totalDurationTv.visibility=View.VISIBLE
            initializeCountdown()
        } else {
            binding.exerciseProgressBar.visibility = View.INVISIBLE
            binding.pauseExerciseBtn.visibility = View.GONE
            binding.repsFab.visibility = View.VISIBLE
            val number="x${exercises[currentExercise].reps}"
            binding.secondsPassedTv.text=number
            binding.totalDurationTv.visibility=View.GONE
            speakOut("${exercises[currentExercise].reps} reps")
        }
    }

    private fun initializeExerciseTimer() {
        speakOut("${exercises[currentExercise].name} ${exercises[currentExercise].duration} seconds")
        val duration = "/${exercises[currentExercise].duration}''"
        val max = exercises[currentExercise].duration * 1000 + 1000
        binding.exerciseProgressBar.progress = 0
        binding.totalDurationTv.text = duration
        binding.secondsPassedTv.text = "0''"
        binding.exerciseProgressBar.max = max
        exerciseTimer.initializeTimer(max.toLong())
    }

    private fun speakOut(s: String) {
        tts!!.speak(s, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun initializeUI() {
        binding.repsFab.setOnClickListener { onExerciseFinished() }
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.finish_sound)
        mediaPlayer!!.isLooping = false
        binding.pauseExerciseBtn.setOnClickListener { exerciseTimer.pauseTimer() }
        initializeRest()
    }

    private fun initializeCountdown() {
        binding.countdownImage.visibility=View.VISIBLE
        val animated = AnimatedVectorDrawableCompat.create(binding.countdownImage.context, R.drawable.avd_countdown)
        animated!!.setTintList(ColorStateList.valueOf(Color.parseColor(routine.color)))
        animated.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                initializeExerciseTimer()
                binding.countdownImage.visibility=View.GONE
            }

        })
        binding.countdownImage.setImageDrawable(animated)
        animated.start()
    }

    private fun updateExerciseTimerUi(millisUntilFinished: Long) {
        val max = exercises[currentExercise].duration * 1000 + 1000
        var left = millisUntilFinished.toInt()
        if (left in 1001..1999)
            mediaPlayer!!.start()
        left = max - left
        val passed = "${left / 1000}''"
        binding.secondsPassedTv.text = passed
        if (millisUntilFinished.toInt() < 1000)
            binding.exerciseProgressBar.progress = binding.exerciseProgressBar.max
        else
            binding.exerciseProgressBar.progress = left
    }

    private fun onExerciseFinished() {
        binding.exerciseRoot.visibility = View.GONE
        currentExercise++
        binding.routineProgressIndicator.incrementProgressBy(1)
        initializeRest()
    }

    private fun updateRestTimerUi(millisUntilFinished: Long) {
        val seconds = millisUntilFinished / 1000
        binding.progressBar.incrementProgressBy(1)
        binding.timerTv.text = seconds.toString()
    }

    private fun onRestFinished() {
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
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
        }
    }


}