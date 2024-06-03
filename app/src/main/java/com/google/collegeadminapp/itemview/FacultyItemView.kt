package com.google.collegeadminapp.itemview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.RowScopeInstance.weight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.google.collegeadminapp.R
import com.google.collegeadminapp.models.BannerModel
import com.google.collegeadminapp.models.NoticeModel
import com.google.collegeadminapp.ui.theme.TEXT_SIZE
import com.google.collegeadminapp.utils.Constant.isAdmin
import com.google.collegeadminapp.viewmodel.BannerViewModel

@Composable
fun FacultyItemView(catName:String,delete:(catName:String)->Unit,onClick:(catName:String)->Unit) {
    OutlinedCard (modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth().clickable { onClick(catName) }){
ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
     val (category,delete)=createRefs()

    Text(text = catName, modifier = Modifier
        .padding(vertical = 8.dp, horizontal = 12.dp)
        .constrainAs(category) {
            start.linkTo(parent.start)
            end.linkTo(delete.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }, fontWeight = FontWeight.Bold, fontSize = 16.sp, )
    if(isAdmin)
    Card(modifier = Modifier
        .constrainAs(delete) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }
        .padding(4.dp)
        .clickable { delete(catName) }) {
        Image(painter = painterResource(id = R.drawable.baseline_delete_24), contentDescription = "", modifier = Modifier.padding(8.dp))
    }
}
    }
}

