package com.example.workoutapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.R
import com.example.workoutapp.database.models.Workout
import com.example.workoutapp.databinding.ItemRunBinding
import com.example.workoutapp.other.TrackingUtility
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RunViewHolder>() {

    inner class RunViewHolder(val binding: ItemRunBinding, val context:Context) : RecyclerView.ViewHolder(binding.root){
        fun bind(workout: Workout){
             when(workout.category){
                0->binding.routineNameTv.text="Running"
                1->binding.routineNameTv.text="Fat Burning"
                2->binding.routineNameTv.text="Legs"
                3->binding.routineNameTv.text="Obliques"
                 4->binding.routineNameTv.text="Relaxing Yoga"
            }
            if(workout.category==0)
                binding.icon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_runner_history_icon))
            else
                binding.icon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_exercise_history_icon))
            binding.caloriesTv.text=workout.caloriesBurned.toString()
            binding.durationTv.text=TrackingUtility.getFormattedStopWatchTime(workout.timeInMillis*60*1000)
            val dateFormat = SimpleDateFormat("MMM d, hh:mm aaa", Locale.getDefault())
                binding.timeStampTv.text = dateFormat.format(workout.timestamp)
        }
    }

        private val diffCallback = object : DiffUtil.ItemCallback<Workout>() {
            override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }

        private val differ = AsyncListDiffer(this, diffCallback)

        fun submitList(list: List<Workout>) = differ.submitList(list)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
            return RunViewHolder(ItemRunBinding.inflate(LayoutInflater.from(parent.context)),parent.context)
        }

        override fun getItemCount(): Int {
            return differ.currentList.size
        }

        override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
            val workout = differ.currentList[position]
            holder.bind(workout)
        }
    }






