package com.google.collegeadminapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.collegeadminapp.itemview.NoticeItemView
import com.google.collegeadminapp.viewmodel.BannerViewModel
import com.google.collegeadminapp.viewmodel.CollegeInfoViewModel
import com.google.collegeadminapp.viewmodel.NoticeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Home() {
    val bannerViewModel: BannerViewModel = viewModel()
    val bannerList by bannerViewModel.bannerList.observeAsState()
    bannerViewModel.getBanner()

    val collegeInfoViewModel: CollegeInfoViewModel = viewModel()
    val collegeInfo by collegeInfoViewModel.collegeInfo.observeAsState(null)
    collegeInfoViewModel.getCollegeInfo()

    val noticeViewModel: NoticeViewModel = viewModel()
    val noticeList by noticeViewModel.noticeList.observeAsState()
    noticeViewModel.getNotice()
val pagerState= rememberPagerState(initialPage = 0)
    val imageSlider=ArrayList<AsyncImagePainter>()
    bannerList?.forEach {
imageSlider.add(rememberAsyncImagePainter(model = it.url))
    }
    LaunchedEffect(Unit ){
        while (true){
            yield()
            delay(2000)
            pagerState.animateScrollToPage(page = (pagerState.currentPage+1)%pagerState.pageCount)
        }
    }
    LazyColumn(Modifier.padding(8.dp)) {
        item {
            HorizontalPager(count = imageSlider.size, state = pagerState) { pager ->
                Card(modifier = Modifier.height(250.dp)) {
                    Image(
                        imageSlider[pager], contentDescription = "banner",
                        Modifier
                            .height(250.dp)
                            .fillMaxWidth(), contentScale = ContentScale.Crop
                    )
                }

            }
        }
        item {
            Row(horizontalArrangement = Arrangement.Center) {
                HorizontalPagerIndicator(pagerState = pagerState, Modifier.padding(8.dp))
            }
        }
        item {
            if (collegeInfo != null) {
                Text(
                    text = collegeInfo!!.name!!,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = collegeInfo!!.desc!!, color = Color.White, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = collegeInfo!!.address!!, color = Color.White, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = collegeInfo!!.websiteLink!!, color = Color.White, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Notices",
                    color = Color.White,
                    fontSize = 16.sp,
            
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        items(noticeList ?: emptyList()) {
            NoticeItemView(it, delete = { docId ->
                noticeViewModel.deleteNotice(docId)

            })
        }
    }
}