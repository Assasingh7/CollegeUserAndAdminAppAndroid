package com.google.collegeadminapp.admin.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.collegeadminapp.R
import com.google.collegeadminapp.itemview.FacultyItemView
import com.google.collegeadminapp.itemview.GalleryItemView
import com.google.collegeadminapp.navigation.Routes
import com.google.collegeadminapp.ui.theme.Purple80
//import com.google.collegeadminapp.viewmodel.galleryViewModel
import com.google.collegeadminapp.viewmodel.GalleryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageGallery(navController:NavController){
    val context = LocalContext.current
    val galleryViewModel: GalleryViewModel = viewModel()
    val isUploaded by galleryViewModel.isPosted.observeAsState(false)
    val isDeleted by galleryViewModel.isDeleted.observeAsState(false)
    val galleryList by galleryViewModel.galleryList.observeAsState(emptyList())
    val option = arrayListOf<String>()
    galleryViewModel.getGallery()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isCategory by remember { mutableStateOf(false) }
    var mExtended by remember { mutableStateOf(false) }
    var isImage by remember { mutableStateOf(false) }

    var category by remember { mutableStateOf("") }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { imageUri = it }

    LaunchedEffect(isUploaded) {
        if (isUploaded) {
            Toast.makeText(context, "Data Uploaded", Toast.LENGTH_SHORT).show()
            imageUri = null
            isCategory = false

            category = ""

        }
    }

    LaunchedEffect(isDeleted) {
        if (isDeleted) {
            Toast.makeText(context, "Data Deleted", Toast.LENGTH_SHORT).show()
            imageUri = null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Manage Gallery") },
                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Purple80),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                }
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Row(modifier = Modifier.padding(8.dp)) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .clickable {
                            isCategory = true
                            isImage = false
                        }
                ) {
                    Text(
                        text = "Add Category", fontWeight = FontWeight.Bold, fontSize = 18.sp,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(), textAlign = TextAlign.Center
                    )
                }

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .clickable {
                            isImage = true
                            isCategory = false
                        }
                ) {
                    Text(
                        text = "Add Image", fontWeight = FontWeight.Bold, fontSize = 18.sp,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(), textAlign = TextAlign.Center
                    )
                }
            }

            if (isCategory)
                ElevatedCard {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        OutlinedTextField(
                            value = category,
                            onValueChange = { category = it },
                            placeholder = { Text(text = "Category...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        )


                        Image(painter =if(imageUri==null) painterResource(id = R.drawable.placeholder) else rememberAsyncImagePainter(model = imageUri), contentDescription = "banner image", modifier = Modifier
                            .height(220.dp)
                            .fillMaxWidth()
                            .clickable {
                                launcher.launch("image/*")
                            }, contentScale = ContentScale.Crop)
                        Row {
                            Button(
                                onClick = {
                                    if (category.isEmpty() && imageUri==null) {
                                        Toast.makeText(context, "Please provide all fields", Toast.LENGTH_SHORT).show()
                                    } else {
                                        galleryViewModel.saveGalleryImage(imageUri!!,category,true)
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(4.dp)
                            ) {
                                Text(text = "Add Image")
                            }
                            OutlinedButton(
                                onClick = {
                                    imageUri = null
                                    isCategory = false
                                    isImage = false
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(4.dp)
                            ) {
                                Text(text = "Cancel")
                            }
                        }
                    }
                }

            if (isImage)
                ElevatedCard(modifier = Modifier.padding(8.dp)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(5.dp))
                        Image(painter =if(imageUri==null) painterResource(id = R.drawable.placeholder) else rememberAsyncImagePainter(model = imageUri), contentDescription = "banner image", modifier = Modifier
                            .height(220.dp)
                            .fillMaxWidth()
                            .clickable {
                                launcher.launch("image/*")
                            }, contentScale = ContentScale.Crop)

                        Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                            OutlinedTextField(
                                value = category,
                                onValueChange = { category = it },
                                readOnly = true,
                                placeholder = { Text(text = "Select your Gallery") },
                                label = { Text(text = "Department Gallery") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = mExtended)
                                }
                            )
                            DropdownMenu(
                                expanded = mExtended,
                                onDismissRequest = { mExtended = false }
                            ) {
                               if(galleryList!=null && galleryList!!.isNotEmpty()){
                                   option.clear()
                                   for(data in galleryList!!){
                                       option.add(data.category!!)
                                   }
                               }
                                option.forEach { selectedOption ->
                                    DropdownMenuItem(
                                        text = { Text(selectedOption) },
                                        onClick = {
                                            category = selectedOption
                                            mExtended = false
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                            Spacer(
                                modifier = Modifier
                                    .matchParentSize()
                                    .padding(10.dp)
                                    .clickable { mExtended = !mExtended }
                            )
                        }
                        Row {
                            Button(
                                onClick = {
                                    when {
                                        imageUri == null -> {
                                            Toast.makeText(context, "Please provide image", Toast.LENGTH_SHORT).show()
                                        }
                                        category.isEmpty() -> {
                                            Toast.makeText(context, "Please provide category", Toast.LENGTH_SHORT).show()
                                        }
                                        else -> {
                                            galleryViewModel.saveGalleryImage(imageUri!!, category,false)
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(4.dp)
                            ) {
                                Text(text = "Add Teacher")
                            }
                            OutlinedButton(
                                onClick = {
                                    imageUri = null
                                    isCategory = false
                                    isImage = false
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(4.dp)
                            ) {
                                Text(text = "Cancel")
                            }
                        }
                    }
                }

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
    }
}






