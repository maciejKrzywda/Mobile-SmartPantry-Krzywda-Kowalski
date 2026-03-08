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

        adapter = ProductAdapter(this, productList) { savePantry() }
        binding.listView.adapter = adapter

        binding.btnAddProduct.setOnClickListener {
            val dialogBinding = DialogAddProductBinding.inflate(layoutInflater)
            AlertDialog.Builder(this)
                .setTitle("Nowy Produkt")
                .setView(dialogBinding.root)
                .setPositiveButton("Dodaj") { _, _ ->
                    val name = dialogBinding.etName.text.toString()
                    val qty = dialogBinding.etQuantity.text.toString().toIntOrNull() ?: 0
                    val cat = dialogBinding.etCategory.text.toString()
                    val img = dialogBinding.etImageRef.text.toString()

                    if (name.isNotEmpty()) {
                        productList.add(Product(java.util.UUID.randomUUID().toString(), name, qty, cat, img))
                        adapter.notifyDataSetChanged()
                        savePantry()
                    }
                }
                .setNegativeButton("Anuluj", null)
                .show()
        }

        loadPantry()


    }

    private val fileName = "inventory.json"

    private fun loadPantry() {
        try {
            val file = File(filesDir, fileName)
            if (!file.exists()) {
                val inputStream = resources.openRawResource(R.raw.pantry)
                val rawJson = inputStream.bufferedReader().use { it.readText() }
                file.writeText(rawJson)
            }
            val jsonString = file.readText()
            val loadedList = Json.decodeFromString<List<Product>>(jsonString)
            productList.clear()
            productList.addAll(loadedList)
            adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun savePantry() {
        try {
            val file = File(filesDir, "inventory.json")
            val jsonString = Json.encodeToString(productList)
            file.writeText(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}