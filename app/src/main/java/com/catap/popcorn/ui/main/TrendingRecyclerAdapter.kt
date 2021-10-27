package com.catap.popcorn.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.catap.popcorn.Helper.Companion.MOVIE_COMPARATOR
import com.catap.popcorn.R
import com.catap.popcorn.data.TmdbMovie
import com.catap.popcorn.databinding.TrendingListCardViewBinding
import com.catap.popcorn.networkapi.TmdbApi.Companion.IMAGE_URL_500

class TrendingRecyclerAdapter :
    PagingDataAdapter<TmdbMovie, TrendingRecyclerAdapter.TrendingViewHolder>(MOVIE_COMPARATOR) {

    class TrendingViewHolder(private val binding: TrendingListCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: TmdbMovie) {
            binding.apply {
                Glide.with(itemView)
                    .load(IMAGE_URL_500 + movie.poster_path)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(cardViewImage)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val binding =
            TrendingListCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrendingViewHolder(binding)

    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }

    }

}