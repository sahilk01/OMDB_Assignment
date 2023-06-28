package com.example.omdb.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
const val NotThere = "N/A"
const val NoImageAvailable = "https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg?20200913095930"

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    val imageUrl = if (url == NotThere) NoImageAvailable else url
    Glide
        .with(this)
        .load(imageUrl)
        .into(this)
}