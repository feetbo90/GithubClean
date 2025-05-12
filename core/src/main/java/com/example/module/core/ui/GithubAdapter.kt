package com.example.module.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.module.core.databinding.CardItemBinding
import com.example.module.core.domain.model.UserGithub
import com.example.module.core.utils.UserImageLoader

class GithubAdapter(private val listUser: List<UserGithub>, private val imageLoader: UserImageLoader) : RecyclerView.Adapter<GithubAdapter.ListViewHolder>() {
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
            imageLoader.loadImage(holder.itemView.context, users.avatarUrl.toString(), imageProfile)
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
        fun onItemClicked(data: UserGithub)
    }
}
