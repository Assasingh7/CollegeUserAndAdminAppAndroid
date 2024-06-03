package com.google.collegeadminapp.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.collegeadminapp.itemview.FacultyItemView
import com.google.collegeadminapp.navigation.Routes
import com.google.collegeadminapp.viewmodel.FacultyViewModel

@Composable
fun Faculty(navController:NavController) {
   val facultyViewModel: FacultyViewModel = viewModel()

   val categoryList by facultyViewModel.categoryList.observeAsState(emptyList())
   facultyViewModel.getCategory()
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