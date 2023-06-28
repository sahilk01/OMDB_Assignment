package com.example.omdb.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.omdb.R
import com.example.omdb.databinding.ActivityMovieListBinding
import com.example.omdb.model.dto.Movie
import com.example.omdb.util.showError
import com.example.omdb.view.adapter.MovieListAdapter
import com.example.omdb.view.movieDetail.MovieDetailActivity
import com.example.omdb.viewModel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListActivity : AppCompatActivity() {
    private val movieListViewModel by viewModels<MovieListViewModel>()
    private var movieListAdapter: MovieListAdapter? = null
    private var query: String? = null
    private lateinit var binding: ActivityMovieListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list)
        query = intent.getStringExtra(SEARCH_QUERY)
        movieListAdapter = MovieListAdapter(
            onItemClick = { movie ->
                navigateToMovieDetail(movie)
            }
        )
        binding.movieListRv.adapter = movieListAdapter
        attachObservers()
        searchMovie()
    }

    private fun navigateToMovieDetail(movie: Movie) {
        MovieDetailActivity.open(context = this, movieId = movie.imdbID)
    }

    private fun searchMovie() {
        showLoader()
        query?.let {
            movieListViewModel.searchMovie(it)
        } ?: showError(getString(R.string.no_search_query))
    }

    private fun showLoader() {
        binding.progress.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        binding.progress.visibility = View.GONE
    }

    private fun attachObservers() {
        collectMovieList()
        collectNetworkFailures()
    }

    private fun collectNetworkFailures() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieListViewModel.networkError.collectLatest { error ->
                    hideLoader()
                    showError(error)
                }
            }
        }
    }

    private fun collectMovieList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieListViewModel.movieList.collect { movies ->
                    hideLoader()
                    movieListAdapter?.submitList(movies)
                }
            }
        }
    }

    companion object {
        const val SEARCH_QUERY = "search_query"
        fun open(context: Context, searchQuery: String) {
            Intent(context, MovieListActivity::class.java).also {
                it.putExtra(SEARCH_QUERY, searchQuery)
                context.startActivity(it)
            }
        }
    }
}