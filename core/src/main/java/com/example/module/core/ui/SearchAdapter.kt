package com.example.module.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.module.core.R
import com.example.module.core.data.source.remote.response.SimpleUser
import com.example.module.core.databinding.CardItemBinding

class SearchAdapter(private val listUser: List<SimpleUser>) : RecyclerView.Adapter<SearchAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val users = listUser[position]
        holder.binding.apply {
            usernameText.text = users.login
            Glide
                .with(holder.itemView.context)
                .load(users.avatarUrl)
                .placeholder(R.drawable.ic_baseline_person)
                .into(imageProfile)
        }
        holder.itemView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClickCallback.onItemClicked(listUser[position])
            }
        }
    }

    override fun getItemCount(): Int = listUser.size

    class ListViewHolder(var binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: SimpleUser)
    }
}
