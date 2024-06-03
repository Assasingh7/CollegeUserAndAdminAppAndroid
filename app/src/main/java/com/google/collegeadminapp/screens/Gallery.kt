package com.google.collegeadminapp.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.collegeadminapp.itemview.GalleryItemView
import com.google.collegeadminapp.viewmodel.GalleryViewModel

@Composable
fun Gallery() {
    val galleryViewModel: GalleryViewModel = viewModel()
    val galleryList by galleryViewModel.galleryList.observeAsState(emptyList())
    galleryViewModel.getGallery()
    LazyColumn {
        items(galleryList) {
            GalleryItemView(it, delete = { docId ->
                galleryViewModel.deleteGallery(docId)
            }, deleteImage = {cat, imageUrl ->
                galleryViewModel.deleteImage(cat,imageUrl)
            })
        }
    }
}