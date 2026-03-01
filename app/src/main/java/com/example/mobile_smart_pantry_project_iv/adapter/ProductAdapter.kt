package com.example.mobile_smart_pantry_project_iv.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.mobile_smart_pantry_project_iv.databinding.ItemProductBinding
import com.example.mobile_smart_pantry_project_iv.model.Product

class ProductAdapter(context: Context, private val products: List<Product>) :
    ArrayAdapter<Product>(context, 0, products) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = if (convertView == null) {
            ItemProductBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            ItemProductBinding.bind(convertView)
        }

        val p = products[position]
        binding.tvName.text = p.name
        binding.tvCategory.text = p.category
        binding.tvQuantity.text = p.quantity.toString()

        if (p.quantity < 5) {
            binding.itemRoot.setBackgroundColor(Color.parseColor("#B71C1C"))
        } else {
            binding.itemRoot.setBackgroundColor(Color.TRANSPARENT)
        }

        val resId = context.resources.getIdentifier(p.imageRef, "drawable", context.packageName)
        binding.imgProduct.setImageResource(if (resId != 0) resId else android.R.drawable.ic_menu_help)

        return binding.root
    }
}