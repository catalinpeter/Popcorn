package com.catap.popcorn.ui.movies

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.catap.popcorn.R
import com.catap.popcorn.data.TmdbMovie
import com.catap.popcorn.data.TmdbPagingSource
import com.catap.popcorn.databinding.MovieListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.movie_list_fragment),
    MoviesRecyclerAdapter.OnItemClickListene, TmdbPagingSource.EmptyListCallback {

    private val viewModel by viewModels<MoviesViewModel>()
    private val args by navArgs<MoviesFragmentArgs>()

    private var _binding: MovieListFragmentBinding? = null
    private val binding get() = _binding!!
    private val actionThisToMain = MoviesFragmentDirections.actionMoviesFragmentToMainFragment()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //search from movie passed from first fragment
        val adapter = MoviesRecyclerAdapter(this)


        //in case the user press view all button i display the trending response
        if (args.searchInputMain.trending) {
            viewModel.trandingMovies.observe(viewLifecycleOwner) {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        } else
            viewModel.searchMovies(args.searchInputMain.movie_query, this)

        _binding = MovieListFragmentBinding.bind(view)


        //bind RecycleView and Title from movie_list_fragment
        binding.apply {
            //set list adapter
            movieRecyclerFml.adapter = adapter
            //set list title based on query
            movieTitleFml.text = args.searchInputMain.movie_query.toUpperCase()
        }


        //obsderve the movie liveData and get notifide when the livedata changes when execute a search request
        //passed view lifcycle as argument
        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }


        //Setting navigation for back button (movies->main)
        binding.backBtnFml.setOnClickListener {
            findNavController().navigate(actionThisToMain)
        }
    }

    //this is call when a item is clcik in the recicle view (opens the movie details page)
    override fun onItemClick(movie: TmdbMovie) {
        val action = MoviesFragmentDirections.actionMoviesFragmentToDetailesFragment(movie)
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        //release reference when viewBinding on the view is detroied
        _binding = null
    }


    //callback that return true and query in case that movie search have no elements
    override fun checkResultStatus(query: String, isResultEmpty: Boolean) {

        if (isResultEmpty) {
            val builder = AlertDialog.Builder(this.requireContext())
            builder.setTitle(R.string.alert)
            builder.setMessage(R.string.empty_api_call_response)
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                findNavController().navigate(actionThisToMain)
            }
            builder.show()
        } else {
            viewModel.handleLastTenSearches(query)
        }

    }


}