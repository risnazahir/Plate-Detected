package com.abp.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
            val vechicleBrand = binding.UploadVechicleBrand.text.toString()
            val vechicleRTO = binding.UploadVechicleRTO.text.toString()
            val vechicleNumber = binding.UploadVechicleNumber.text.toString()

            if (ownerName.isNotEmpty() && vechicleBrand.isNotEmpty() && vechicleRTO.isNotEmpty() && vechicleNumber.isNotEmpty()) {
                saveVehicleData(ownerName, vechicleBrand, vechicleRTO, vechicleNumber)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveVehicleData(ownerName: String, vechicleBrand: String, vechicleRTO: String, vechicleNumber: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("vehicle Information")
        val vehicleData = VehicleData(ownerName, vechicleBrand, vechicleRTO, vechicleNumber)
        databaseReference.child(vechicleNumber).setValue(vehicleData)
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
