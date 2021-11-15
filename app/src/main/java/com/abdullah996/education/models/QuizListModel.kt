package com.abdullah996.education.models

import com.google.firebase.firestore.DocumentId

data class QuizListModel(
    @DocumentId
    val quiz_id: String?=null,
    val name:String?=null,
    val desc:String?=null,
    val image:String?=null,
    val level: String?=null,

)
