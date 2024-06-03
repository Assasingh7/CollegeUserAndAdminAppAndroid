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
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.collegeadminapp.R
import com.google.collegeadminapp.itemview.BannerItemView
import com.google.collegeadminapp.itemview.FacultyItemView
import com.google.collegeadminapp.itemview.NoticeItemView
import com.google.collegeadminapp.navigation.Routes
import com.google.collegeadminapp.ui.theme.Purple80
import com.google.collegeadminapp.viewmodel.BannerViewModel
import com.google.collegeadminapp.viewmodel.FacultyViewModel
import com.google.collegeadminapp.viewmodel.NoticeViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun ManageFaculty(navController: NavController) {
    val context = LocalContext.current
    val facultyViewModel: FacultyViewModel = viewModel()
    val isUploaded by facultyViewModel.isPosted.observeAsState(false)
    val isDeleted by facultyViewModel.isDeleted.observeAsState(false)
    val categoryList by facultyViewModel.categoryList.observeAsState(emptyList())
    val option = arrayListOf<String>()
    facultyViewModel.getCategory()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isCategory by remember { mutableStateOf(false) }
    var mExtended by remember { mutableStateOf(false) }
    var isTeacher by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { imageUri = it }

    LaunchedEffect(isUploaded) {
        if (isUploaded) {
            Toast.makeText(context, "Data Uploaded", Toast.LENGTH_SHORT).show()
            imageUri = null
            isCategory = false
            isTeacher = false
            category = ""
            name = ""
            email = ""
            position = ""
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
                title = { Text(text = "Manage Faculty") },
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
                            isTeacher = false
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
                            isTeacher = true
                            isCategory = false
                        }
                ) {
                    Text(
                        text = "Add Teacher", fontWeight = FontWeight.Bold, fontSize = 18.sp,
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
                        Row {
                            Button(
                                onClick = {
                                    if (category.isEmpty()) {
                                        Toast.makeText(context, "Please provide category", Toast.LENGTH_SHORT).show()
                                    } else {
                                        facultyViewModel.uploadCategory(category)
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(4.dp)
                            ) {
                                Text(text = "Add Category")
                            }
                            OutlinedButton(
                                onClick = {
                                    imageUri = null
                                    isCategory = false
                                    isTeacher = false
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

            if (isTeacher)
                ElevatedCard(modifier = Modifier.padding(8.dp)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(5.dp))
                        Image(
                            painter = if (imageUri == null) painterResource(id = R.drawable.placeholder) else rememberAsyncImagePainter(model = imageUri),
                            contentDescription = "faculty image",
                            modifier = Modifier
                                .height(120.dp)
                                .width(120.dp)
                                .clip(CircleShape)
                                .clickable { launcher.launch("image/*") },
                            contentScale = ContentScale.Crop
                        )
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            placeholder = { Text(text = "Teacher Name...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        )
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            placeholder = { Text(text = "Teacher Email...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        )
                        OutlinedTextField(
                            value = position,
                            onValueChange = { position = it },
                            placeholder = { Text(text = "Position...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        )
                        Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                            OutlinedTextField(
                                value = category,
                                onValueChange = { category = it },
                                readOnly = true,
                                placeholder = { Text(text = "Select your department") },
                                label = { Text(text = "Department Name") },
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
                                option.clear()
                                categoryList.forEach { option.add(it) }
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
                                        name.isEmpty() || email.isEmpty() || position.isEmpty() || category.isEmpty() -> {
                                            Toast.makeText(context, "Please provide all fields", Toast.LENGTH_SHORT).show()
                                        }
                                        else -> {
                                            facultyViewModel.saveFaculty(imageUri!!, name, email, position, category)
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
                                    isTeacher = false
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
                items(categoryList) { category ->
                    FacultyItemView(category, delete = { docId ->
                        facultyViewModel.deleteCategory(docId)
                    }, onClick = {categoryName->
                        val routes= Routes.FacultyDetailScreen.route.replace("{catName}",category)
                        navController.navigate(routes)
                    })
                }
            }
        }
    }
}






