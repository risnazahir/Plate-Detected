package com.abp.admin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abp.admin.databinding.ActivityUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)  // Set the content view using the binding root

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")

        // Set click listener for the update button
        binding.updateButton.setOnClickListener {
            val referenceVehicleNumber = binding.referenceVehicleNumber.text.toString()
            val updateOwnerName = binding.updateOwnerName.text.toString()
            val updateVehicleBrand = binding.updateVehicleBrand.text.toString()
            val updateVehicleRTO = binding.updateVehicleRTO.text.toString()

            // Call the update function with the provided inputs
            update(referenceVehicleNumber, updateOwnerName, updateVehicleBrand, updateVehicleRTO)
        }
    }

    // Function to update vehicle information in the Firebase database
    private fun update(vechicleNumber: String, ownerName: String, vechicleBrand: String, vechicleRTO: String) {
        // Check if the vehicle number is not empty
        if (vechicleNumber.isEmpty()) {
            Toast.makeText(this, "Please enter a vehicle number", Toast.LENGTH_SHORT).show()
            return
        }

        // Create a map of the updated values
        val vehicleData = mapOf(
            "ownerName" to ownerName,
            "vechicleBrand" to vechicleBrand,
            "vechicleRTO" to vechicleRTO
        )

        // Update the values in the Firebase database
        databaseReference.child(vechicleNumber).updateChildren(vehicleData).addOnSuccessListener {
            // Clear the input fields on success
            binding.referenceVehicleNumber.text.clear()
            binding.updateOwnerName.text.clear()
            binding.updateVehicleBrand.text.clear()
            binding.updateVehicleRTO.text.clear()
            Toast.makeText(this, "Data updated", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to update data", Toast.LENGTH_SHORT).show()
        }
    }
}
