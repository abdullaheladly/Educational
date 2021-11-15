package com.abdullah996.education.models


import com.google.firebase.firestore.DocumentId

data class SubjectsModel(
    @DocumentId
    val subject_id: String?=null,
    val name:String?=null,
    val desc:String?=null,
    val image:String?=null,
    val visibility: String?=null,
)
