package com.google.collegeadminapp.itemview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.google.collegeadminapp.R
import com.google.collegeadminapp.models.BannerModel
import com.google.collegeadminapp.viewmodel.BannerViewModel

@Composable
fun BannerItemView(bannerModel: BannerModel,delete:(docId:BannerModel)->Unit) {
    OutlinedCard (modifier = Modifier.padding(4.dp)){
ConstraintLayout {
     val (image,delete)=createRefs()
    Image(painter = rememberAsyncImagePainter(model = bannerModel.url), contentDescription = "", modifier = Modifier
        .height(220.dp)
        .fillMaxWidth(), contentScale = ContentScale.Crop)
    Card(modifier = Modifier.constrainAs(delete){
        top.linkTo(parent.top)
        end.linkTo(parent.end)
    }.padding(4.dp).clickable { delete(bannerModel) }) {
        Image(painter = painterResource(id = R.drawable.baseline_delete_24), contentDescription = "", modifier = Modifier.padding(8.dp))
    }
}
    }
}
@Preview(showSystemUi = true)
@Composable
fun BannerView(){
//    BannerItemView(bannerModel = BannerModel())
}