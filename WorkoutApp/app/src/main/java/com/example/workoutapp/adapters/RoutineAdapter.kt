package com.example.workoutapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workoutapp.database.models.Routine
import com.example.workoutapp.databinding.RoutineCardBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class RoutineAdapter(private val list:List<Routine>, val context: Context, private val clickListener: (Routine) -> Unit): RecyclerView.Adapter<RoutineAdapter.VH>() {
    class VH(private val binding:RoutineCardBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(routine: Routine,context: Context,clickListener: (Routine) -> Unit){
            if (routine.caloriesBurned!=0)
            binding.calories.text= routine.caloriesBurned.toString()
            else
                binding.calories.visibility= View.GONE
            if(routine.duration!=0){
                val duration="${routine.duration} minutes"
                binding.duration.text=duration }
            else
                binding.duration.visibility=View.GONE
            binding.rootRelativeLayout.setOnClickListener { clickListener(routine) }
            binding.heading.text=routine.name
            val ref= Firebase.storage.reference.child("headers").child(routine.image)
            Glide.with(context).load(ref).into(binding.header)
    }}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding=RoutineCardBinding.inflate(LayoutInflater.from(context),null,false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list[position],context,clickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}