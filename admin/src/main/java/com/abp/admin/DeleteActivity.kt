package com.abp.admin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.abp.admin.databinding.ActivityDeleteBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteBinding
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.deleteButton.setOnClickListener{
            val vechicleNumber = binding.deletevehicleNumber.text.toString()
            if(vechicleNumber.isNotEmpty()){
                deleteData(vechicleNumber)
            }else{
                Toast.makeText(this, "Please Enter the Vehicle Number", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun deleteData(vechicleNumber: String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
        databaseReference.child(vechicleNumber).removeValue().addOnSuccessListener {
            binding.deletevehicleNumber.text.clear()
            Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show()


        }.addOnFailureListener {
            Toast.makeText(this, "Unable to Delete", Toast.LENGTH_SHORT).show()
        }
    }
}