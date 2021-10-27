package com.catap.popcorn.ui.detailes

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.ScrollingMovementMethod
import android.text.style.StyleSpan
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.catap.popcorn.Helper.Companion.makeSubstringBold
import com.catap.popcorn.R
import com.catap.popcorn.databinding.DetailesFragmentBinding
import com.catap.popcorn.networkapi.TmdbApi.Companion.IMAGE_URL_500
import com.catap.popcorn.networkapi.TmdbApi.Companion.IMAGE_URL_780
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailesFragment : Fragment(R.layout.detailes_fragment) {
    private val args by navArgs<DetailesFragmentArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = DetailesFragmentBinding.bind(view)

        //bind views with corresponding data
        binding.apply {
            Glide.with(view)
                .load(IMAGE_URL_780 + args.movie.poster_path)
                .centerInside()
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBarDetailes.isVisible = false
                        movieImageHeartFmd.isVisible = true
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBarDetailes.isVisible = false
                        movieImageHeartFmd.isVisible = true
                        return false
                    }
                })
                .into(movieImageFmd)

            titleFmd.text = args.movie.original_title

            //populate short description
            val mpaaRating = if (args.movie.adult.toBoolean()) "NC-17" else "PG-13"
            shortDescriptionFmd.text =
                "$mpaaRating | ${args.movie.release_date} | language=${args.movie.original_language} "

            //Make only a part of textview  bold
            ratingUpperFmd.text = makeSubstringBold(
                args.movie.vote_average,
                "/10",
                args.movie.vote_average.length + 1
            )

            ratingLowerFmd.text = args.movie.popularity
            descriptionFmd.movementMethod = ScrollingMovementMethod();
            descriptionFmd.text = args.movie.overview

        }

        binding.backButtonFmd.setOnClickListener {
            val action = DetailesFragmentDirections.actionDetailesFragmentToMainFragment()
            findNavController().navigate(action)
        }
    }


}