package com.abdullah996.education.repo


import com.abdullah996.education.models.QuizListModel
import com.abdullah996.education.models.SubjectsModel
import com.abdullah996.education.models.TeachersModel
import com.google.firebase.firestore.*


import java.lang.Exception


class FirebaseRepository(private val onFirestoreTaskComplete: OnFirestoreTaskComplete) {
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    // we cant order the data without delete whereEqualTo function until now
    //so  if we want to sort the data we have to remove this function
    private val subjectRef = firebaseFirestore.collection("subjects")

    fun getQuizListData(string: String,string1: String) {
        subjectRef.document(string).collection("Teachers").document(string1).collection("Quiz_list").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onFirestoreTaskComplete.quizDataAdded(
                    task.result!!.toObjects(
                        QuizListModel::class.java
                    )
                )
            } else {
                onFirestoreTaskComplete.onError(task.exception)
            }
        }
    }

    fun getTeachersData(string: String) {
        subjectRef.document(string).collection("Teachers").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onFirestoreTaskComplete.teachersDataAdded(
                    task.result!!.toObjects(
                        TeachersModel::class.java
                    )
                )
            } else {
                onFirestoreTaskComplete.onError(task.exception)
            }
        }
    }
    fun getSubjectsData() {
        subjectRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onFirestoreTaskComplete.quizListDataAdded(
                    task.result!!.toObjects(
                        SubjectsModel::class.java
                    )
                )
            } else {
                onFirestoreTaskComplete.onError(task.exception)
            }
        }
    }

    interface OnFirestoreTaskComplete {
        fun quizListDataAdded(subjects: List<SubjectsModel?>?)
        fun onError(e: Exception?)
        fun teachersDataAdded(teachers:List<TeachersModel>)
        fun quizDataAdded(quizs:List<QuizListModel>)
    }

}