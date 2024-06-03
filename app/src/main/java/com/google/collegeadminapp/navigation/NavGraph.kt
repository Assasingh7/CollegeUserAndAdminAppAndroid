package com.google.collegeadminapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.google.collegeadminapp.admin.screens.AdminDashBoard
import com.google.collegeadminapp.admin.screens.FacultyDetailScreen
import com.google.collegeadminapp.admin.screens.ManageBanner
import com.google.collegeadminapp.admin.screens.ManageCollegeInfo
import com.google.collegeadminapp.admin.screens.ManageFaculty
import com.google.collegeadminapp.admin.screens.ManageGallery
import com.google.collegeadminapp.admin.screens.ManageNotice
import com.google.collegeadminapp.screens.About
import com.google.collegeadminapp.screens.BottomNav
import com.google.collegeadminapp.screens.Faculty
import com.google.collegeadminapp.screens.Gallery
import com.google.collegeadminapp.screens.Home
import com.google.collegeadminapp.utils.Constant.isAdmin
import java.security.AccessController

@Composable
fun NavGraph(navController: NavHostController) {
//    val isAdmin=true
    NavHost(navController = navController,startDestination=if(isAdmin) Routes.AdminDashboard.route else Routes.BottomNav.route){
composable(Routes.BottomNav.route){
BottomNav(navController)
}
        composable(Routes.Home.route){
            Home()
        }
        composable(Routes.Gallery.route){
          Gallery()
        }
        composable(Routes.Faculty.route){
            Faculty(navController)
        }
        composable(Routes.AboutUs.route){
            About()
        }
        composable(Routes.AdminDashboard.route){
            AdminDashBoard(navController)
        }
        composable(Routes.MannerFaculty.route){
            ManageFaculty(navController)
        }
        composable(Routes.MannerBanner.route){
            ManageBanner(navController)
        }
        composable(Routes.MannerCollegeInfo.route){
            ManageCollegeInfo(navController)
        }
        composable(Routes.MannerGallery.route){
            ManageGallery(navController)
        }
        composable(Routes.MannerNotice.route){
            ManageNotice(navController = navController)
        }
        composable(Routes.FacultyDetailScreen.route){
            val data=it.arguments!!.getString("catName")
            FacultyDetailScreen(navController = navController,data!!)
        }
    }

}