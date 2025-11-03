package com.example.registerformapp

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.registerformapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var defaultEditTextBackground: Drawable? = null
    private var defaultTextColor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        defaultEditTextBackground = binding.etFirstName.background
        defaultTextColor = binding.cbAgree.currentTextColor

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnSelectDate.setOnClickListener {
            binding.calendarView.isVisible = !binding.calendarView.isVisible
        }

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            binding.etBirthday.setText(selectedDate)
            binding.calendarView.isVisible = false
        }

        binding.btnRegister.setOnClickListener {
            if (validateAllInputs()) {
                Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateAllInputs(): Boolean {
        var isValid = true

        isValid = validateEditText(binding.etFirstName) && isValid
        isValid = validateEditText(binding.etLastName) && isValid
        isValid = validateEditText(binding.etBirthday) && isValid
        isValid = validateEditText(binding.etAddress) && isValid
        isValid = validateEditText(binding.etEmail) && isValid

        if (binding.rgGender.checkedRadioButtonId == -1) {
            isValid = false
            binding.rbMale.setTextColor(Color.RED)
            binding.rbFemale.setTextColor(Color.RED)
        } else {
            binding.rbMale.setTextColor(defaultTextColor)
            binding.rbFemale.setTextColor(defaultTextColor)
        }

        if (!binding.cbAgree.isChecked) {
            isValid = false
            binding.cbAgree.setTextColor(Color.RED)
        } else {
            binding.cbAgree.setTextColor(defaultTextColor)
        }

        return isValid
    }

    private fun validateEditText(editText: EditText): Boolean {
        if (editText.text.toString().trim().isEmpty()) {
            editText.setBackgroundResource(R.drawable.edit_text_error_bg)
            return false
        } else {
            editText.background = defaultEditTextBackground
            return true
        }
    }
}