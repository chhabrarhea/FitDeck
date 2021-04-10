package com.example.todo.fragments

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.*
import android.util.Log
import android.view.*
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.todo.R
import com.example.todo.databinding.FragmentRecordAudioBinding
import com.github.squti.androidwaverecorder.WaveRecorder
import java.io.File
import java.io.FileInputStream
import java.io.IOException


class RecordAudioFragment : Fragment(),MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener{
    private var isRecording = false
    private val sharedViewModel:SharedViewModel by viewModels()
    private var isStarted = false
    lateinit var binding: FragmentRecordAudioBinding
    lateinit var recorder: WaveRecorder
    private var audioFilePath = ""
    private lateinit var file: File
    private var mediaPlayer : MediaPlayer?=MediaPlayer()
    private lateinit var runnable: Runnable
    private var handler: Handler = Handler(Looper.getMainLooper())
    private var pause: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecordAudioBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        if (!hasMicrophone()) {
            binding.stopButton.isEnabled = false
            binding.recordButton.isEnabled = false;
            Toast.makeText(requireContext(), "No microphone found!", Toast.LENGTH_SHORT).show();
        } else {
            binding.stopButton.setEnabled(false)
            file = File(requireContext().getExternalFilesDir(null)?.absolutePath, "DoIt!")
            if (!file.exists()) file.mkdir()
            audioFilePath =
                file.path + "/audio" + requireArguments().getString("audioFilePath", "") + ".wav"
            mediaPlayer!!.setOnCompletionListener(this) }
        binding.seekBar.setOnSeekBarChangeListener(this)
        binding.recordButton.setOnClickListener { recordAudio() }
        binding.stopButton.setOnClickListener { stopRecording() }
        binding.playButton.setOnClickListener { playAndPauseAudio() }
        binding.stopBtn.setOnClickListener { stopAudio() }
        setHasOptionsMenu(true)
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar)
        return binding.root
    }

    private fun stopAudio() {
        if(mediaPlayer!=null && (mediaPlayer!!.isPlaying || pause.equals(true))){
            Log.i("stopAudio", "called")
            pause = true
            mediaPlayer!!.stop()
            mediaPlayer!!.reset()
            isStarted=false
            handler.removeCallbacks(runnable)
            binding.playButton.isEnabled = true
            binding.playButton.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_play
                )
            )
            binding.stopBtn.isEnabled = false
            binding.tvPass.text = "0 secs"
            binding.tvDue.text = "${binding.seekBar.max} secs"
            binding.seekBar.progress=0
        }
    }


    private fun hasMicrophone(): Boolean {
        val packageManager: PackageManager = requireActivity().packageManager
        return packageManager.hasSystemFeature(
            PackageManager.FEATURE_MICROPHONE
        )
    }

    @Throws(IOException::class)
    fun recordAudio() {
        if(mediaPlayer!=null && (mediaPlayer!!.isPlaying || pause)){
            mediaPlayer!!.stop()
            mediaPlayer!!.reset()
            isStarted=false
            binding.playButton.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_play
                )
            )
            binding.playButton.isEnabled=false
            binding.stopBtn.isEnabled=false
            binding.seekBar.progress=0
            binding.tvDue.text=""
            binding.tvPass.text=""
            binding.seekBar.isEnabled=false
            handler.removeCallbacks(runnable)
        }
        isRecording = true
        binding.stopButton.isEnabled = true
        binding.recordButton.isEnabled = false
        try {
            binding.timer.base = SystemClock.elapsedRealtime()
            recorder = WaveRecorder(audioFilePath)
            recorder.startRecording()
            binding.timer.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopRecording() {
        if (isRecording) {
            Log.i("stopRecording", "called")
            binding.timer.stop()
            recorder.stopRecording()
            binding.recordButton.isEnabled = true
            isRecording = false
            binding.mediaPlayer.visibility=View.VISIBLE
            binding.stopButton.isEnabled = false
            binding.playButton.isEnabled=true
            binding.seekBar.isEnabled=true
            binding.stopBtn.isEnabled=true

        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.record_audio_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_save) {
           sharedViewModel.setRecordAudio(audioFilePath)
            requireActivity().supportFragmentManager.popBackStack()

        }
        return super.onOptionsItemSelected(item)
    }




    private fun playAndPauseAudio() {
        //initialize mediaPlayer
//       mediaPlayer goes to idle state after calling reset
        Log.i("play", "$")
        if (mediaPlayer==null)
            return
        if (!isStarted) {
            val file = File(audioFilePath)
            file.setReadable(true, false)
            val inputStream = FileInputStream(file)
            mediaPlayer!!.setDataSource(inputStream.getFD())
            inputStream.close()
            mediaPlayer!!.prepare()
            mediaPlayer!!.setOnPreparedListener { mp: MediaPlayer? ->
                initializeSeekBar()
                mediaPlayer!!.start()
                binding.playButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_pause
                    )
                )
                isStarted = true
                binding.stopBtn.isEnabled=true
            }
        }
        //Pause playing audio
        else if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.pause()
            pause = true
            binding.playButton.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_play
                )
            )
        }
        //Play paused audio
        else  {
            binding.playButton.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_pause
                )
            )
            mediaPlayer!!.start()
            initializeSeekBar()
        }
    }


    private fun initializeSeekBar() {
        if (mediaPlayer==null)
        binding.seekBar.max = mediaPlayer!!.duration/1000
        binding.tvDue.text = "${binding.seekBar.max} secs"
        runnable = Runnable {
            binding.seekBar.progress = mediaPlayer!!.currentPosition/1000
            binding.tvPass.text = "${mediaPlayer!!.currentPosition/1000} secs"

            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

    //automatically starts from beginning if start() is called
    override fun onCompletion(p0: MediaPlayer?) {
        Log.i("onCompletion", "called")
        binding.playButton.isEnabled = true
        pause=true
        binding.playButton.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_play
            )
        )
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        if (p2) {
            if (mediaPlayer!=null)
            mediaPlayer!!.seekTo(p1 * 1000)
        }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {

    }

    override fun onStopTrackingTouch(p0: SeekBar?) {

    }

    override fun onStop() {
        super.onStop()
        if(mediaPlayer!=null){
        if (mediaPlayer!!.isPlaying || pause){
        mediaPlayer!!.stop()}
        mediaPlayer!!.reset()
        mediaPlayer!!.release()
        mediaPlayer=null}
    }


}