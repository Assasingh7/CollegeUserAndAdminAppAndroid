package com.google.collegeadminapp.navigation

sealed class Routes(val route:String) {
    object Home:Routes("home")
    object Faculty:Routes("faculty")
    object Gallery:Routes("gallery")
    object AboutUs:Routes("about_us")
    object BottomNav:Routes("bottom_nav")
    object AdminDashboard:Routes("admin_dashboard")
    object MannerFaculty:Routes("manage_faculty")
    object MannerBanner:Routes("manage_banner")
    object MannerGallery:Routes("manage_gallery")
    object MannerCollegeInfo:Routes("college_info")
    object MannerNotice:Routes("manage_notice")
    object FacultyDetailScreen:Routes("faculty_details/{catName}")
}