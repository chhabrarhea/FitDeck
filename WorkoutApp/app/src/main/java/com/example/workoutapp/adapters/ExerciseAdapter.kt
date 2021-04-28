package com.example.workoutapp.adapters

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.database.models.Exercise
import com.example.workoutapp.databinding.ExerciseCardBinding

class ExerciseAdapter(val context: Context, private val list:List<Exercise>,private val clickListener: (Exercise)->Unit): RecyclerView.Adapter<ExerciseAdapter.VH>() {
    class VH(private val binding:ExerciseCardBinding): RecyclerView.ViewHolder(binding.root) {
       fun bind(exercise: Exercise,clickListener: (Exercise) -> Unit){
           binding.exerciseGif.setImageResource(exercise.image)
           binding.duration.text="${exercise.duration}"
           binding.exerciseName.text=exercise.name
           binding.root.setOnClickListener { clickListener(exercise) }
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
         val binding=ExerciseCardBinding.inflate(LayoutInflater.from(context),null,false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list[position],clickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}