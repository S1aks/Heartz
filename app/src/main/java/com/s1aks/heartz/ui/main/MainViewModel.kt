package com.s1aks.heartz.ui.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.s1aks.heartz.data.HeartData

class MainViewModel : ViewModel() {
    private val netDb: DatabaseReference by lazy { FirebaseDatabase.getInstance("https://heartz-49e48-default-rtdb.europe-west1.firebasedatabase.app").reference }
    private val tablePoint: DatabaseReference by lazy { netDb.child("health_data") }
    var data = MutableLiveData<List<HeartData>>()
        private set

    init {
        tablePoint.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list = mutableListOf<HeartData>()
                for (noteSnapshot in dataSnapshot.children) {
                    noteSnapshot.getValue(HeartData::class.java)?.let {
                        list.add(it)
                    }
                }
                data.value = list
            }

            @SuppressLint("RestrictedApi")
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(LOG_TAG, databaseError.message)
            }
        })
    }

    fun getData() {

    }

    @SuppressLint("RestrictedApi")
    fun saveData(data: HeartData) {
        val key = tablePoint.push().key
        key?.let {
            data.id = it
            tablePoint.child(key).setValue(data).addOnFailureListener { p0 ->
                p0.localizedMessage?.let { it1 -> Log.d(LOG_TAG, it1) }
            }
        }
    }
}