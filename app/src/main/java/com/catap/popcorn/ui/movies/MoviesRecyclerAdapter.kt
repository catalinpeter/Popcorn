package com.catap.popcorn.ui.movies

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.catap.popcorn.Helper
import com.catap.popcorn.Helper.Companion.MOVIE_COMPARATOR
import com.catap.popcorn.R
import com.catap.popcorn.data.TmdbMovie
import com.catap.popcorn.databinding.MovieListCardViewBinding
import com.catap.popcorn.networkapi.TmdbApi.Companion.IMAGE_URL_185
import com.catap.popcorn.networkapi.TmdbApi.Companion.IMAGE_URL_92

class MoviesRecyclerAdapter(private val listener: OnItemClickListene) :
    PagingDataAdapter<TmdbMovie, MoviesRecyclerAdapter.MovieViewHolder>(MOVIE_COMPARATOR) {

    inner class MovieViewHolder(private val binding: MovieListCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        //bind movie data to layout views
        fun bind(movie: TmdbMovie) {
            binding.apply {

                //load poster image to imageView
                Glide.with(itemView)
                    .load(IMAGE_URL_185 + movie.poster_path)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageCardViewMlcv)

                titleMlc.text = movie.original_title


                if (movie.release_date != null) yearMlc.text =
                    movie.release_date.substringBefore("-")

                //Make only a part of textview  bold
                val text = movie.vote_average + "/10"
                val spannableString = SpannableString(text)
                val boldSpan = StyleSpan(Typeface.BOLD)
                spannableString.setSpan(
                    boldSpan,
                    0,
                    movie.vote_average.length + 1,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                ratingUpperMlc.text = Helper.makeSubstringBold(
                    movie.vote_average,
                    "/10",
                    movie.vote_average.length + 1
                )

                ratingLowerMlc.text = movie.popularity

                val mpaaRating = if (movie.adult.toBoolean()) "NC-17" else "PG-13"
                shortDescriptionMlc.text =
                    "$mpaaRating | ${movie.release_date} | language=${movie.original_language} "

            }
        }

    }

    //Interfacce dat handle onClick listener over an item from the list
    interface OnItemClickListene {
        fun onItemClick(movie: TmdbMovie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            MovieListCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val curretntItem = getItem(position)
        if (curretntItem != null) {
            holder.bind(curretntItem)
        }

    }


}