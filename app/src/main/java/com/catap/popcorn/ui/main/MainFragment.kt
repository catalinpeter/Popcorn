package com.catap.popcorn.ui.main

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.catap.popcorn.Helper.Companion.changeTextColor
import com.catap.popcorn.Prefs
import com.catap.popcorn.R
import com.catap.popcorn.data.SearchType
import com.catap.popcorn.databinding.MainFragmentBinding
import com.github.guilhe.sharedprefs.gson.get
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {
    private val viewModel by viewModels<MainViewModel>()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = MainFragmentBinding.bind(view)
        val adapter = TrendingRecyclerAdapter()


        binding.apply {

            if (trandingRecyclerMain != null) {
                trandingRecyclerMain.adapter = adapter
            }

        }


        //change Finde substring of hint into black
        binding.entryText.hint = changeTextColor(
            getString(R.string.hint),
            Color.BLACK,
            getString(R.string.hint).substringBefore(" ").length
        )

        //onClick listener for serchButton
        binding.searchBtnMain.setOnClickListener {
            var mainInputText = binding.entryText?.text.toString()

            if (mainInputText.isNotEmpty()) {
                //hide keyboard
                val imm =
                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)

                //navigate to movies screen
                val action =
                    MainFragmentDirections.actionMainFragmentToMoviesFragment(
                        SearchType(
                            false,
                            mainInputText
                        )
                    )
                findNavController().navigate(action)
            } else {
                //if the search text is empty an alert will be display
                val builder = AlertDialog.Builder(this.requireContext())
                builder.setTitle(R.string.alert)
                builder.setMessage(R.string.empty_search)

                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                }

                builder.show()
            }
        }


        //set input text a long click listener that will show the list of last 10 searches
        binding.entryText.setOnLongClickListener {
            // setup the alert builder
            val builder = AlertDialog.Builder(this.requireContext())
            builder.setTitle(R.string.choose)
            // add a list
            val animals = Prefs.get(
                R.string.lastTenMoviesKey.toString(),
                object : TypeToken<List<String>>() {},
                mutableListOf()
            ).toTypedArray()
            builder.setItems(animals) { dialog, which ->
                //navigate to movies screen
                val action =
                    MainFragmentDirections.actionMainFragmentToMoviesFragment(
                        SearchType(
                            false,
                            animals[which]
                        )
                    )
                findNavController().navigate(action)
                return@setItems
            }
            // create and show the alert dialog
            val dialog = builder.create()
            dialog.show()
            true
        }

        //onClick listener for view all tarnding movies
        binding.viewAllBtnMain.setOnClickListener {
            //navigate to movies screen
            val action =
                MainFragmentDirections.actionMainFragmentToMoviesFragment(
                    SearchType(
                        true,
                        getString(R.string.trending)
                    )
                )
            findNavController().navigate(action)
        }

        //trandingMovies Livedata observer
        viewModel.trandingMovies.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        //release reference when viewBinding on the view is detroied
        _binding = null
    }


}




