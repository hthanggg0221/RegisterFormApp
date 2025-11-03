package com.example.registerformapp

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.registerformapp.databinding.ActivityMainBinding
import java.util.Calendar

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
            showDatePickerDialog()
        }

        binding.btnRegister.setOnClickListener {
            if (validateAllInputs()) {
                Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                binding.etBirthday.setText(selectedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
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