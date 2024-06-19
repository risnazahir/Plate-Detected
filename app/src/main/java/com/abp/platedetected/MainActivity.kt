package com.abp.platedetected

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abp.platedetected.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.searchButton.setOnClickListener {
            val searchVehicleNumber: String = binding.searchVechicleNumber.text.toString()
            if (searchVehicleNumber.isNotEmpty()) {
                readData(searchVehicleNumber)
            } else {
                Toast.makeText(this, "Please enter the vehicle number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readData(vechicleNumber: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
        databaseReference.child(vechicleNumber).get().addOnSuccessListener {
            if (it.exists()) {
                val ownerName = it.child("ownerName").value
                val vechicleBrand = it.child("vechicleBrand").value
                val vechicleRTO = it.child("vechicleRTO").value

                Toast.makeText(this, "Result Found", Toast.LENGTH_SHORT).show()
                binding.searchVechicleNumber.text.clear()
                binding.readOwnerName.text = ownerName.toString()
                binding.readVechicleBrand.text = vechicleBrand.toString()
                binding.readVechicelRTO.text = vechicleRTO.toString()
            } else {
                Toast.makeText(this, "Vehicle Number does not exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}
