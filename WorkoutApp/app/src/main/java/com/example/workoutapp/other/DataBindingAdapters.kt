package com.example.workoutapp.other

import android.graphics.Color
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.workoutapp.R
import kotlinx.android.synthetic.main.fragment_routine_detail.view.*

class DataBindingAdapters {
    companion object{
        @BindingAdapter("android:loadImageWithGlide")
        @JvmStatic
        fun loadImage(imageView: ImageView,drawable:Int){
            val context=imageView.context
          Glide.with(context).load(ContextCompat.getDrawable(context,drawable)).into(imageView)
        }

        @BindingAdapter("android:setToolbarColor")
        @JvmStatic
        fun setToolbarColor(toolbar: Toolbar,color:String){
            toolbar.setBackgroundColor(Color.parseColor(color))
        }

        @BindingAdapter("android:setIntegerText")
        @JvmStatic
        fun setText(textView: TextView,text:Int){
            if(text==0)
                return
            var t=text.toString()
            if(textView.id== R.id.duration){
                t+=" minutes"}
            textView.text= t
        }

        @BindingAdapter("android:setButtonBackgroundColor")
        @JvmStatic
        fun setColor(button: Button,color: String){
            button.setBackgroundColor(Color.parseColor(color))
        }

    }
}