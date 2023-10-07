package com.usth.job.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.usth.job.databinding.SupCardLayoutBinding
import com.usth.job.model.Sup

class SupAdapter : RecyclerView.Adapter<SupAdapter.SupViewHolder>() {

    private val supList: MutableList<Sup> = mutableListOf()

    inner class SupViewHolder(
        private val binding: SupCardLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sup: Sup) {
            binding.ivProfileSup.load(sup.imageUri)
            binding.tvSupName.text = sup.username
            binding.tvSupEmail.text = sup.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupViewHolder {
        val binding = SupCardLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SupViewHolder, position: Int) {
        holder.bind(supList[position])
    }

    override fun getItemCount(): Int = supList.size


    fun setData(newSupList: List<Sup>) {
        supList.clear()
        supList.addAll(newSupList)
        notifyDataSetChanged()
    }

}