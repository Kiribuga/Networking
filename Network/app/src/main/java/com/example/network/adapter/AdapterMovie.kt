package com.example.network.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.network.RemoteMovie
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class AdapterMovie: AsyncListDifferDelegationAdapter<RemoteMovie>(MovieDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(AdapterDelegateMovie())
    }

    class MovieDiffUtilCallback : DiffUtil.ItemCallback<RemoteMovie>() {
        override fun areItemsTheSame(oldItem: RemoteMovie, newItem: RemoteMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RemoteMovie, newItem: RemoteMovie): Boolean {
            return oldItem == newItem
        }

    }
}