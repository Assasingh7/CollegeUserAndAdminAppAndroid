package com.google.collegeadminapp.admin.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.collegeadminapp.models.DashboardItemModel
import com.google.collegeadminapp.navigation.Routes
import com.google.collegeadminapp.ui.theme.Purple80
import com.google.collegeadminapp.ui.theme.TEXT_SIZE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashBoard(navController: NavController) {
    val list= listOf(
        DashboardItemModel("Manage Banner",Routes.MannerBanner.route),
        DashboardItemModel("Manage Notice",Routes.MannerNotice.route),
        DashboardItemModel("Manage Gallery",Routes.MannerGallery.route),
        DashboardItemModel("Manage Faculty",Routes.MannerFaculty.route),
        DashboardItemModel("College Info",Routes.MannerCollegeInfo.route),
        )
Scaffold(topBar ={ TopAppBar(title = { Text(text = "Admin Dashboard") }, colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Purple80))}, content = {padding->

    LazyColumn (modifier = Modifier.padding(padding)){
        items(items = list, itemContent = {
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    navController.navigate(it.route)

                }) {
                Text(text = it.title, modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp), fontWeight = FontWeight.Bold, fontSize = TEXT_SIZE)
            }
        })
    }
})
}

//@Preview(showSystemUi = true)
//@Composable
//fun AdminDashboardPreview(){
//    AdminDashBoard(navController = rememberNavController() )
//}