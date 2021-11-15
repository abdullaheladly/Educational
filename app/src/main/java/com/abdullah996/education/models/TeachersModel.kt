package com.abdullah996.education.models

import com.google.firebase.firestore.DocumentId

data class TeachersModel(
    @DocumentId
    val Teacher_id: String?=null,
    val Teacher_name:String?=null,
    val Teacher_image:String?=null,
    val visibility: String?=null,
)
