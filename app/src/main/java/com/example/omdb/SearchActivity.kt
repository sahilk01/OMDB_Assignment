package com.example.omdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.omdb.databinding.ActivitySearchBinding
import com.example.omdb.util.ValidationResult
import com.example.omdb.util.Validator
import com.example.omdb.util.showError
import com.example.omdb.view.MovieListActivity
import com.example.omdb.viewModel.SearchViewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        binding.searchButton.setOnClickListener {
            openMovieListScreen()
        }
    }

    private fun openMovieListScreen() {
        val query = binding.searchEt.text.toString()

        when(val validationResult = Validator.isValidateQeury(this, query = query)) {
            is ValidationResult.Failure -> showError(validationResult.error)
            ValidationResult.Success -> MovieListActivity.open(this, query)
        }
    }
}