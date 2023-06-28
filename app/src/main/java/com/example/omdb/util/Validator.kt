package com.example.omdb.util

import android.content.Context
import com.example.omdb.R

object Validator {
    fun isValidateQeury(context: Context, query: String): ValidationResult {
        if (query.isEmpty()) {
            return ValidationResult.Failure(context.getString(R.string.please_enter_movie_name))
        }
        if (query.length < 3) {
            return ValidationResult.Failure(context.getString(R.string.please_enter_more_than_2_words))
        }
        return ValidationResult.Success
    }
}

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Failure(
        val error: String
    ) : ValidationResult()
}