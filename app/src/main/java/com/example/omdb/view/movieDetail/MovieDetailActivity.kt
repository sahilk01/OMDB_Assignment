package com.example.omdb.view.movieDetail

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
import com.example.omdb.databinding.ActivityMovieDetailBinding
import com.example.omdb.model.dto.movieDetail.MovieDetail
import com.example.omdb.util.setImageUrl
import com.example.omdb.util.showError
import com.example.omdb.viewModel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding

    private val movieDetailViewModel by viewModels<MovieDetailViewModel>()

    private var movieId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        binding.movieDetailViewModel = movieDetailViewModel
        movieId = intent.getStringExtra(MOVIE_ID)
        collectData()
        getMovie()
    }

    private fun collectData() {
        collectMovieDetail()
        collectNetworkFailures()
    }

    private fun collectMovieDetail() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieDetailViewModel.movieDetail.collectLatest { movieDetail ->
                    movieDetail?.let {
                        bindData(it)
                    }
                }
            }
        }
    }

    private fun bindData(movieDetail: MovieDetail) {
        hideLoader()
        with(binding) {
            poster.setImageUrl(movieDetail.Poster)
            title.text = movieDetail.Title
            year.text = movieDetail.Year
        }
    }

    private fun collectNetworkFailures() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieDetailViewModel.networkFailure.collectLatest {
                    hideLoader()
                    showError(it)
                }
            }
        }
    }

    private fun showLoader() {
        binding.progress.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        binding.progress.visibility = View.GONE
    }

    private fun getMovie() {
        movieId?.let {
            showLoader()
            movieDetailViewModel.getMovieById(it)
        } ?: showError(getString(R.string.something_went_wrong))
    }

    companion object {
        const val MOVIE_ID = "movie_id"
        fun open(context: Context, movieId: String) {
            Intent(context, MovieDetailActivity::class.java).also {
                it.putExtra(MOVIE_ID, movieId)
                context.startActivity(it)
            }
        }
    }
}