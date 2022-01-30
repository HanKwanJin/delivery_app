package com.han.delivery_app.presentation.trackingitems

import android.view.LayoutInflater
import android.view.ViewGroup
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.han.delivery_app.R
import com.han.delivery_app.data.entity.Level
import com.han.delivery_app.data.entity.ShippingCompany
import com.han.delivery_app.data.entity.TrackingInformation
import com.han.delivery_app.data.entity.TrackingItem
import com.han.delivery_app.databinding.ItemTrackingBinding
import com.han.delivery_app.extension.setTextColorRes
import com.han.delivery_app.extension.toReadableDateString
import java.util.*

class TrackingItemsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data: List<Pair<TrackingItem, TrackingInformation>> = emptyList()
    inner class ViewHolder(private val binding: ItemTrackingBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(company: ShippingCompany, information: TrackingInformation){
            binding.updatedAtTextView.text = Date(information.lastDetail?.time ?: System.currentTimeMillis()).toReadableDateString()
            binding.levelLabelTextView.text = information.level?.label
            when(information.level){
                Level.COMPLETE -> {
                    binding.levelLabelTextView.setTextColor(androidx.appcompat.R.attr.colorPrimary)
                    binding.root.alpha = 0.5f
                }
                Level.PREPARE -> {
                    binding.levelLabelTextView.setTextColorRes(R.color.orange)
                    binding.root.alpha = 1f
                }
                else -> {
                    binding.levelLabelTextView.setTextColorRes(R.color.green)
                    binding.root.alpha = 1f
                }
            }

            binding.invoiceTextView.text = information.invoiceNo

            if(information.itemName.isNullOrBlank()){
                binding.itemNameTextView.text = "이름 없음"
                binding.itemNameTextView.setTextColorRes(R.color.gray)
            } else {
                binding.itemNameTextView.text = information.itemName
                binding.itemNameTextView.setTextColorRes(R.color.black)
            }

            binding.lastStateTextView.text = information.lastDetail?.let { it.kind + " @${it.where}" }

            binding.companyNameTextView.text = company.name
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemTrackingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val (item, trackingInformation) = data[position]
        (holder as ViewHolder).bind(item.company, trackingInformation)
    }

    override fun getItemCount() = data.size


}