package com.google.collegeadminapp.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.collegeadminapp.models.BannerModel
import com.google.collegeadminapp.utils.Constant.BANNER
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.storage
import java.util.UUID

class BannerViewModel: ViewModel() {
    private val bannerRef=Firebase.firestore.collection(BANNER)
    private val storageRef=Firebase.storage.reference
    private val _isPosted=MutableLiveData<Boolean>()
    val isPosted:LiveData<Boolean> =_isPosted
    private val _isDeleted=MutableLiveData<Boolean>()
    val isDeleted:LiveData<Boolean> =_isDeleted
    private val _bannerList=MutableLiveData<List<BannerModel>>()
    val bannerList:LiveData<List<BannerModel>> =_bannerList
    fun saveImage(uri: Uri){
        _isPosted.postValue(false)
        val randomUid=UUID.randomUUID().toString()
        val imageRef=storageRef.child("$BANNER/${randomUid}.jpg")
        val uploadTask=imageRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                  uploadImage(it.toString(),randomUid)
            }
        }
    }

    private fun uploadImage(imageUrl: String, docId: String) {
val map=mutableMapOf<String,String>()
        map["url"]=imageUrl
        map["docId"]=docId
        bannerRef.document(docId).set(map).addOnSuccessListener { _isPosted.postValue(true) }
    }
    fun getBanner(){
        bannerRef.get().addOnSuccessListener {
            val list= mutableListOf<BannerModel>()
            for(doc in it){
                list.add(doc.toObject(BannerModel::class.java))
            }
            _bannerList.postValue(list)
        }
    }
    fun deleteBanner(bannerModel:  BannerModel){

        bannerRef.document(bannerModel.docId!!).delete().addOnSuccessListener {
            bannerModel.url?.let { it1 -> Firebase.storage.getReferenceFromUrl(it1).delete() }
            _isDeleted.postValue(true)
        }.addOnFailureListener{
            _isDeleted.postValue(false)
        }
    }

}