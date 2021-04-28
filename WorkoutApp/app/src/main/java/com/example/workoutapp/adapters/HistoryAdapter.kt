package com.example.workoutapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workoutapp.R
import com.example.workoutapp.database.models.Workout
import com.example.workoutapp.other.TrackingUtility
import kotlinx.android.synthetic.main.item_run.view.*
import java.text.SimpleDateFormat
import java.util.*

public class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RunViewHolder>() {

        inner class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

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
            return RunViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_run,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int {
            return differ.currentList.size
        }

        override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
            val run = differ.currentList[position]
            holder.itemView.apply {
                Glide.with(this).load(run.img).into(ivRunImage)

                val calendar = Calendar.getInstance().apply {
                    timeInMillis = run.timestamp
                }
                val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
                tvDate.text = dateFormat.format(calendar.time)

                val avgSpeed = "${run.avgSpeedInKMH}km/h"
                tvAvgSpeed.text = avgSpeed

                val distanceInKm = "${run.distanceInMeters / 1000f}km"
                tvDistance.text = distanceInKm

                tvTime.text = TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)

                val caloriesBurned = "${run.caloriesBurned}kcal"
                tvCalories.text = caloriesBurned
            }
        }
    }






