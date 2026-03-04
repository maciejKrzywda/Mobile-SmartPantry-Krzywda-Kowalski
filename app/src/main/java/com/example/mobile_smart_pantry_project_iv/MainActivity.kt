package com.example.mobile_smart_pantry_project_iv

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_smart_pantry_project_iv.adapter.ProductAdapter
import com.example.mobile_smart_pantry_project_iv.databinding.ActivityMainBinding
import com.example.mobile_smart_pantry_project_iv.databinding.DialogAddProductBinding
import com.example.mobile_smart_pantry_project_iv.model.Product
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var productList = mutableListOf<Product>()
    private lateinit var adapter: ProductAdapter
    private val pantryString = File(filesDir, "raw/pantry.json")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ProductAdapter(this, productList)
        binding.listView.adapter = adapter

        loadPantry()

    }

    fun loadPantry() {
        try {
            val file = File(filesDir, "raw/pantry.json")
            var fileRead = file.readText()
            val json = Json {ignoreUnknownKeys = true}
            val loadedList = json.decodeFromString<List<Product>>(fileRead)

            adapter.notifyDataSetChanged()
        } catch (e: Error) {

        }
    }


}