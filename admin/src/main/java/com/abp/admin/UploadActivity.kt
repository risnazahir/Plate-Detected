package com.abp.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.abp.admin.databinding.ActivityUploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SaveButton.setOnClickListener {
            val ownerName = binding.UploadOwnerName.text.toString()
            val vehicleBrand = binding.UploadVechicleBrand.text.toString()
            val vehicleRTO = binding.UploadVechicleRTO.text.toString()
            val vehicleNumber = binding.UploadVechicleNumber.text.toString()

            if (ownerName.isNotEmpty() && vehicleBrand.isNotEmpty() && vehicleRTO.isNotEmpty() && vehicleNumber.isNotEmpty()) {
                saveVehicleData(ownerName, vehicleBrand, vehicleRTO, vehicleNumber)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveVehicleData(ownerName: String, vehicleBrand: String, vehicleRTO: String, vehicleNumber: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
        val vehicleData = VehicleData(ownerName, vehicleBrand, vehicleRTO, vehicleNumber)
        databaseReference.child(vehicleNumber).setValue(vehicleData)
            .addOnSuccessListener {
                clearInputFields()
                Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show()
                navigateToMainActivity()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
            }
    }

    private fun clearInputFields() {
        binding.UploadOwnerName.text.clear()
        binding.UploadVechicleBrand.text.clear()
        binding.UploadVechicleRTO.text.clear()
        binding.UploadVechicleNumber.text.clear()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this@UploadActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
