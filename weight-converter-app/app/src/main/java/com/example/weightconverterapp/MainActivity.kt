package com.example.weightconverterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var etInput: EditText
    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var tvResult: TextView
    private lateinit var btnConvert: Button
    private lateinit var btnClear: Button

    // Units
    private val units = listOf(
        "Kilograms (kg)",
        "Pounds (lbs)",
        "Stone",
        "Grams (g)"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etInput = findViewById(R.id.etInput)
        spinnerFrom = findViewById(R.id.spinnerFrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        tvResult = findViewById(R.id.tvResult)
        btnConvert = findViewById(R.id.btnConvert)
        btnClear = findViewById(R.id.btnClear)

        // Adapter for the spinners
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            units
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        btnConvert.setOnClickListener {
            convertWeight()
        }

        btnClear.setOnClickListener {
            clearAll()
        }
    }

    private fun convertWeight() {
        val inputText = etInput.text.toString().trim()
        if (inputText.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show()
            return
        }

        val value = inputText.toDoubleOrNull()
        if (value == null) {
            Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show()
            return
        }

        val fromUnit = spinnerFrom.selectedItem.toString()
        val toUnit = spinnerTo.selectedItem.toString()

        // 1) Convert from fromUnit to kilograms
        val valueInKg = when (fromUnit) {
            "Kilograms (kg)" -> value
            "Pounds (lbs)" -> value / 2.20462
            "Stone" -> value * 6.35029
            "Grams (g)" -> value / 1000.0
            else -> value
        }

        // 2) Convert from kilograms to toUnit
        val result = when (toUnit) {
            "Kilograms (kg)" -> valueInKg
            "Pounds (lbs)" -> valueInKg * 2.20462
            "Stone" -> valueInKg / 6.35029
            "Grams (g)" -> valueInKg * 1000.0
            else -> valueInKg
        }

        tvResult.text = String.format("%.4f %s", result, unitShortName(toUnit))
    }

    private fun unitShortName(fullName: String): String {
        return when (fullName) {
            "Kilograms (kg)" -> "kg"
            "Pounds (lbs)" -> "lbs"
            "Stone" -> "st"
            "Grams (g)" -> "g"
            else -> ""
        }
    }

    private fun clearAll() {
        etInput.text.clear()
        tvResult.text = "--"
        spinnerFrom.setSelection(0)
        spinnerTo.setSelection(0)
    }
}
