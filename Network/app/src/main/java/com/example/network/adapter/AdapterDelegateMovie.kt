package com.example.network.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.network.R
import com.example.network.RemoteMovie
import com.example.network.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.*

class AdapterDelegateMovie :
    AbsListItemAdapterDelegate<RemoteMovie, RemoteMovie, AdapterDelegateMovie.MovieHolder>() {

    override fun isForViewType(
        item: RemoteMovie,
        items: MutableList<RemoteMovie>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): MovieHolder {
        return MovieHolder(parent.inflate(R.layout.item_movie))
    }

    override fun onBindViewHolder(
        item: RemoteMovie,
        holder: MovieHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    abstract class BaseHolder(
        final override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        protected fun bindMainInfo(
            idImdb: String,
            title: String,
            type: String,
            year: String,
            poster: String
        ) {
            titleMovie.text = title
            typeMovie.text = type
            yearMovie.text = year
            idIMDBMovie.text = idImdb

            Glide.with(itemView)
                .load(poster)
                .placeholder(R.drawable.ic_movies)
                .into(posterMovie)
        }
    }

    class MovieHolder(
        containerView: View
    ) : BaseHolder(containerView) {

        fun bind(movie: RemoteMovie) {
            bindMainInfo(movie.idImdb, movie.title, movie.typeMovie, movie.year, movie.poster)
        }
    }
}