package com.catap.popcorn

import android.graphics.Color
import android.graphics.Typeface
import android.text.*
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.recyclerview.widget.DiffUtil
import com.catap.popcorn.data.TmdbMovie


class Helper {

    companion object {

        //used in calculation of list diferences for recycleViewss updates, required as PagingDataAdapter constructor paramenter
        //it will be used in main screen's recyleView and in SearchList recycleView
        val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<TmdbMovie>() {
            override fun areItemsTheSame(oldItem: TmdbMovie, newItem: TmdbMovie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TmdbMovie, newItem: TmdbMovie): Boolean {
                return oldItem == newItem
            }

        }

        // change text or part of the text color
        fun changeTextColor(
            textToBeChanged: String,
            colorCode: Int,
            substringLength: Int
        ): CharSequence? {
            val spannableString = SpannableString(textToBeChanged.substringBefore(" "))
            val colorSpan = ForegroundColorSpan(colorCode)
            spannableString.setSpan(
                colorSpan,
                0,
                substringLength,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return TextUtils.concat(
                spannableString, " ",
                SpannableString(textToBeChanged.substringAfter(" "))
            )
        }


        //make string bold
        fun makeSubstringBold(
            textToBeChanged: String,
            stringToBeConcat: String,
            length: Int
        ): SpannableString {
            val spannableString = SpannableString(textToBeChanged + stringToBeConcat)
            val boldSpan = StyleSpan(Typeface.BOLD)
            spannableString.setSpan(
                boldSpan,
                0,
                length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return spannableString
        }

    }
}