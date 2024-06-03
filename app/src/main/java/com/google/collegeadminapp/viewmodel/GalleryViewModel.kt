package com.google.collegeadminapp.viewmodel

import android.annotation.SuppressLint
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.collegeadminapp.models.FacultyModel
import com.google.collegeadminapp.models.GalleryModel
import com.google.collegeadminapp.utils.Constant.FACULTY
import com.google.collegeadminapp.utils.Constant.GALLERY
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import java.util.UUID

class GalleryViewModel : ViewModel() {
    private val galleryRef = FirebaseFirestore.getInstance().collection(GALLERY)
    private val storageRef = FirebaseStorage.getInstance().reference
    private val _isPosted = MutableLiveData<Boolean>()
    val isPosted: LiveData<Boolean> = _isPosted
    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted: LiveData<Boolean> = _isDeleted
    private val _galleryList = MutableLiveData<List<GalleryModel>>()
    val galleryList: LiveData<List<GalleryModel>> = _galleryList



    fun saveGalleryImage(uri: Uri, category: String,isCategory:Boolean) {
        _isPosted.postValue(false)
        val randomUid = UUID.randomUUID().toString()
        val imageRef = storageRef.child("$GALLERY/$randomUid.jpg")
        val uploadTask = imageRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                if(isCategory)
                uploadCategory(it.toString(), category)
                else updateImage(it.toString(),category)

            }
        }
    }



  @SuppressLint("SuspiciousIndentation")
  private fun uploadCategory(image:String, category: String) {
        val map = mutableMapOf<String, Any>()
        map["category"] = category
      map["images"]=FieldValue.arrayUnion(image)

        galleryRef.document(category).set(map)
            .addOnSuccessListener {
                _isPosted.postValue(true)
            }
            .addOnFailureListener {
                _isPosted.postValue(false)
            }
    }
    private fun updateImage(image:String, category: String) {


        galleryRef.document(category).update("images",FieldValue.arrayUnion(image))
            .addOnSuccessListener {
                _isPosted.postValue(true)
            }
            .addOnFailureListener {
                _isPosted.postValue(false)
            }
    }

    fun getGallery() {
        galleryRef.get().addOnSuccessListener {
            val list = mutableListOf<GalleryModel>()
            for (doc in it) {
                val category = doc.id
                galleryRef.document(category).collection("teacher").get()
                    .addOnSuccessListener { teacherDocs ->
                        for (teacherDoc in teacherDocs) {
                            list.add(teacherDoc.toObject(GalleryModel::class.java))
                        }
                        _galleryList.postValue(list)
                    }
            }
        }
    }



    fun deleteGallery(galleryModel: GalleryModel) {
        galleryModel.images!!.forEach {
            Firebase.storage.getReferenceFromUrl(it).delete()
        }
        galleryRef.document(galleryModel.category!!).delete()
                    .addOnSuccessListener {

                        _isDeleted.postValue(true)
                        }
            .addOnFailureListener {
                _isDeleted.postValue(false)
            }

                    }

    fun deleteImage(category: String,image: String) {
        galleryRef.document(category).update("images",FieldValue.arrayRemove(image))
            .addOnSuccessListener {
                Firebase.storage.getReferenceFromUrl(image).delete()
                _isDeleted.postValue(true)
            }
            .addOnFailureListener {
                _isDeleted.postValue(false)
            }
    }
            }





