package com.google.collegeadminapp.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.collegeadminapp.R
import com.google.collegeadminapp.models.BottomNavItem
import com.google.collegeadminapp.models.NavItem
import com.google.collegeadminapp.navigation.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNav(navController: NavController) {
    val navController1= rememberNavController()
    val scope = rememberCoroutineScope()
    val context= LocalContext.current
    val drawerState= rememberDrawerState(initialValue = DrawerValue.Closed)
    val selectedItemIndex by remember{
        mutableIntStateOf(0)
    }


    val list= listOf(
        NavItem("Website", R.drawable.globe),
        NavItem("Website", R.drawable.notice),
        NavItem("Website", R.drawable.info),
        NavItem("Website", R.drawable.phone)




    )
    ModalNavigationDrawer(drawerState = drawerState,
        drawerContent = {
        ModalDrawerSheet {

            Image(painter = painterResource(id = R.drawable.image), contentDescription =null, modifier = Modifier.height(220.dp), contentScale = ContentScale.Crop )
Divider()
        Text(text = "")
        list.forEachIndexed{index, items ->
        NavigationDrawerItem(label = { Text(text = items.title) }, selected =index==selectedItemIndex , onClick = {
            Toast.makeText(
                context,
                items.title,
                Toast.LENGTH_SHORT
            ).show()
        scope.launch { drawerState.close() }}, icon = { Icon(painter = painterResource(id = items.icon), contentDescription =null , modifier = Modifier.size(24.dp))})
            
        }}
    }, content = {
        Scaffold(topBar = {
            TopAppBar(title = { Text(text = "College App")},
                navigationIcon = {
                    IconButton(onClick = {scope.launch { drawerState.open() } }) {
                        Icon(painter = painterResource(id = R.drawable.menu), contentDescription =null )
                    }
                })
        }, bottomBar = {
            MyBottomNav(navController =  navController1)
        }) {
            padding ->
            NavHost(navController = navController1, startDestination = Routes.Home.route, modifier = Modifier.padding(padding) ){
                composable(route = Routes.Home.route){
                    Home()
                }
                composable(route = Routes.Gallery.route){
                    Gallery()
                }
                composable(route = Routes.AboutUs.route){
                    About()
                }
                composable(route = Routes.Faculty.route){
                    Faculty(navController = navController)
                }
            }
        }
        })
}
@Composable
fun MyBottomNav(navController: NavController){
    val backStackEntry=navController.currentBackStackEntryAsState()
    val list= listOf(
        BottomNavItem("Home",R.drawable.home, Routes.Home.route),
        BottomNavItem("Faculty",R.drawable.graduated, Routes.Faculty.route),
        BottomNavItem("Gallery",R.drawable.imagegallery, Routes.Gallery.route),
        BottomNavItem("About Us",R.drawable.info, Routes.AboutUs.route),
    )
    BottomAppBar {
        list.forEach{
            val curRoute=it.route
            val otherRoute=try{backStackEntry.value!!.destination.route} catch (e:Exception){
                Routes.Home.route
            }
            val selected=curRoute==otherRoute
            NavigationBarItem(selected = selected, onClick = { navController.navigate(it.route){
                popUpTo(navController.graph.findStartDestination().id){
                    saveState=true
                }
                launchSingleTop=true
            } }, icon = { Icon(painterResource(id = it.icon), contentDescription =it.title , modifier = Modifier.size(24.dp))})
        }
    }
}

