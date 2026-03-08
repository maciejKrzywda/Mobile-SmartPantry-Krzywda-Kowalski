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

            val inputStream = resources.openRawResource(R.raw.pantry)
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            println("JSON: $jsonString")

            val json = Json { ignoreUnknownKeys = true }
            val loadedList = json.decodeFromString<List<Product>>(jsonString)

            println("SIZE: ${loadedList.size}")

            productList.clear()
            productList.addAll(loadedList)

            adapter.notifyDataSetChanged()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}