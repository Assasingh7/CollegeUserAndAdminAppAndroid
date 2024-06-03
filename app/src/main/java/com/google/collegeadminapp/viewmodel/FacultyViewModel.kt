package com.google.collegeadminapp.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.collegeadminapp.models.FacultyModel
import com.google.collegeadminapp.utils.Constant.FACULTY
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class FacultyViewModel : ViewModel() {
    private val facultyRef = FirebaseFirestore.getInstance().collection(FACULTY)
    private val storageRef = FirebaseStorage.getInstance().reference
    private val _isPosted = MutableLiveData<Boolean>()
    val isPosted: LiveData<Boolean> = _isPosted
    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted: LiveData<Boolean> = _isDeleted
    private val _facultyList = MutableLiveData<List<FacultyModel>>()
    val facultyList: LiveData<List<FacultyModel>> = _facultyList

    private val _categoryList = MutableLiveData<List<String>>()
    val categoryList: LiveData<List<String>> = _categoryList

    fun saveFaculty(uri: Uri, name: String, email: String, position: String, catName: String) {
        _isPosted.postValue(false)
        val randomUid = UUID.randomUUID().toString()
        val imageRef = storageRef.child("$FACULTY/$randomUid.jpg")
        val uploadTask = imageRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                uploadFaculty(uri.toString(), randomUid, name, email, position, catName)
            }.addOnFailureListener {
                _isPosted.postValue(false)
            }
        }.addOnFailureListener {
            _isPosted.postValue(false)
        }
    }

    private fun uploadFaculty(imageUrl: String, docId: String, name: String, email: String, position: String, catName: String) {
        val map = mutableMapOf<String, String>()
        map["imageUrl"] = imageUrl
        map["docId"] = docId
        map["name"] = name
        map["email"] = email
        map["position"] = position
        map["catName"] = catName

        facultyRef.document(catName).collection("teacher").document(docId).set(map)
            .addOnSuccessListener {
                _isPosted.postValue(true)
            }
            .addOnFailureListener {
                _isPosted.postValue(false)
            }
    }

    fun uploadCategory(category: String) {
        val map = mutableMapOf<String, String>()
        map["category"] = category

        facultyRef.document(category).set(map)
            .addOnSuccessListener {
                _isPosted.postValue(true)
            }
            .addOnFailureListener {
                _isPosted.postValue(false)
            }
    }

    fun getFaculty(catName: String) {
        facultyRef.document(catName).collection("teacher").get().addOnSuccessListener {
            val list = mutableListOf<FacultyModel>()
            for (doc in it) {
                val category = doc.id
                facultyRef.document(category).collection("teacher").get()
                    .addOnSuccessListener { teacherDocs ->
                        for (teacherDoc in teacherDocs) {
                            list.add(teacherDoc.toObject(FacultyModel::class.java))
                        }
                        _facultyList.postValue(list)
                    }
            }
        }
    }

    fun getCategory() {
        facultyRef.get().addOnSuccessListener {
            val list = mutableListOf<String>()
            for (doc in it) {
                list.add(doc.id)
            }
            _categoryList.postValue(list)
        }
    }

    fun deleteFaculty(facultyModel: FacultyModel) {
        facultyModel.docId?.let {
            facultyModel.catName?.let { it1 ->
                facultyRef.document(it1).collection("teacher").document(it).delete()
                    .addOnSuccessListener {
                        facultyModel.imageUrl?.let { imageUrl ->
                            FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl).delete()
                        }
                        _isDeleted.postValue(true)
                    }
                    .addOnFailureListener {
                        _isDeleted.postValue(false)
                    }
            }
        }
    }

    fun deleteCategory(category: String) {
        facultyRef.document(category).delete()
            .addOnSuccessListener {
                _isDeleted.postValue(true)
            }
            .addOnFailureListener {
                _isDeleted.postValue(false)
            }
    }
}
