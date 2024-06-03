package com.google.collegeadminapp.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun LoadingDialog(
                onDismissRequest:()->Unit){
    Dialog(onDismissRequest = { onDismissRequest() }){
        Box( modifier = Modifier
            .size(120.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))){
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Loading...", fontSize = 16.sp, fontWeight = FontWeight.Bold)

        }
    } 
}
