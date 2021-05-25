package com.example.workoutapp.ui.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.workoutapp.R
import com.example.workoutapp.databinding.FragmentSettingsBinding
import com.example.workoutapp.databinding.HwDialogBinding
import java.text.SimpleDateFormat
import java.util.*


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val newDate: Calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding= FragmentSettingsBinding.inflate(inflater,container,false)
        binding.dobText.setOnClickListener { openDobDialog() }
        binding.wText.setOnClickListener { openDialog() }
        binding.hText.setOnClickListener { openDialog() }
        binding.cont.setOnClickListener { findNavController().navigate(R.id.action_settingsFragment_to_homeFragment) }
        return binding.root
    }

    private fun openDialog() {
        val binding1=HwDialogBinding.inflate(layoutInflater,null,false)
        val builder= androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setView(binding1.root)
        val alert= builder.create()
        binding1.cancel.setOnClickListener { alert.dismiss() }
        binding1.ok.setOnClickListener {
            var wt: String
            if(binding1.weight.text.isNotEmpty()){
            wt="${binding1.weight.text} ${resources.getString(R.string.kg)}"
            binding.wText.text = wt}
            if(binding1.height.text.isNotEmpty()){
            wt="${binding1.weight.text} ${resources.getString(R.string.cm)}"
            binding.hText.text = wt}
            alert.dismiss()
        }
        alert.show()
    }



    @SuppressLint("SimpleDateFormat")
    private fun openDobDialog() {
        val newCalender = Calendar.getInstance()
        val datePicker= DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    run {
                       newDate.set(year,month,day)
                        val df=SimpleDateFormat("yyyy-MM-dd")
                        val dateString=df.format(newDate.time)
                        binding.dobText.text = dateString
                                    }}, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(
                Calendar.DAY_OF_MONTH
        )
        )
        datePicker.show()

    }


}