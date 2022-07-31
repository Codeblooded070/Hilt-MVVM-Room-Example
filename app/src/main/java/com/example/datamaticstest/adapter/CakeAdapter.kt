package com.example.datamaticstest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datamaticstest.databinding.RowItemCakeListBinding
import com.example.datamaticstest.model.CakeResponse
import com.example.datamaticstest.utils.OnActionPerformed

class CakeAdapter(
    private val context: Context?,
    private val dataList: List<CakeResponse.CakeResponseItem>,
    private val onActionPerformed: OnActionPerformed?,
) : RecyclerView.Adapter<CakeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            RowItemCakeListBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val provider = dataList[position]
        holder.mBinding.tvTitle.text = provider.name
        val toppingCount = "Topping Count : ${provider.topping.size}"
        holder.mBinding.tvToppingCount.text = toppingCount

        holder.mBinding.cvParent.setOnClickListener {
            onActionPerformed?.onSelect(provider.id, provider.name, provider.topping)
        }

    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    class ViewHolder(binding: RowItemCakeListBinding) : RecyclerView.ViewHolder(binding.root) {
        val mBinding: RowItemCakeListBinding = binding
    }



}