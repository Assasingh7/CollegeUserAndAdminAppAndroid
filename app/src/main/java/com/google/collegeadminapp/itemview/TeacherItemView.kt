package com.google.collegeadminapp.itemview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.google.collegeadminapp.R
import com.google.collegeadminapp.models.BannerModel
import com.google.collegeadminapp.models.FacultyModel
//import com.google.collegeadminapp.models.facultyModel
//import com.google.collegeadminapp.models.facultyModel
import com.google.collegeadminapp.ui.theme.TEXT_SIZE
import com.google.collegeadminapp.utils.Constant.isAdmin
import com.google.collegeadminapp.viewmodel.BannerViewModel

@Composable
fun TeacherItemView(facultyModel:FacultyModel,delete:(facultyModel: FacultyModel)->Unit) {
    OutlinedCard (modifier = Modifier.padding(4.dp)){
ConstraintLayout {
     val (image,delete)=createRefs()
    Column {
        Spacer(modifier = Modifier.height(5.dp))
        Image(
            painter = rememberAsyncImagePainter(model = facultyModel.imageUrl),
            contentDescription = "faculty image",
            modifier = Modifier
                .height(130.dp)
                .width(130.dp)
                .clip(CircleShape)
                ,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = facultyModel.name!!,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp), fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(text = facultyModel.email!!, modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp), fontSize = 14.sp, color = Color.Blue)
        Image(
            painter = rememberAsyncImagePainter(model = facultyModel.imageUrl),
            contentDescription = "",
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
    if(isAdmin)
    Card(modifier = Modifier
        .constrainAs(delete) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }
        .padding(4.dp)
        .clickable { delete(facultyModel) }) {
        Image(painter = painterResource(id = R.drawable.baseline_delete_24), contentDescription = "", modifier = Modifier.padding(8.dp))
    }
}
    }
}
