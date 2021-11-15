package com.abdullah996.education.models

import com.google.firebase.firestore.DocumentId

data class QuestionsModel(
    @DocumentId
    val question_id: String?=null,
    val question:String?=null,
    val option_a:String?=null,
    val option_b:String?=null,
    val option_c :String?=null,
    val answer: String?=null,
    val timer:Long?=null
)
