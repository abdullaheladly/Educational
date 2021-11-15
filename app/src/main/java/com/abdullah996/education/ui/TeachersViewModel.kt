package com.abdullah996.education.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdullah996.education.models.QuizListModel
import com.abdullah996.education.models.SubjectsModel
import com.abdullah996.education.models.TeachersModel
import com.abdullah996.education.repo.FirebaseRepository
import java.lang.Exception

class TeachersViewModel() : ViewModel(), FirebaseRepository.OnFirestoreTaskComplete {
    private val teachersModelData = MutableLiveData<List<TeachersModel>>()
    fun teachersModelData(): LiveData<List<TeachersModel>> {
        return teachersModelData
    }

    private val firebaseRepository = FirebaseRepository(this)


    override fun quizListDataAdded(subjects: List<SubjectsModel?>?) {


    }

    override fun onError(e: Exception?) {}
    override fun teachersDataAdded(teachers: List<TeachersModel>) {
        teachersModelData.value = teachers as List<TeachersModel>?
    }

    override fun quizDataAdded(quizs: List<QuizListModel>) {

    }

    fun getTeachers(string: String) {
        firebaseRepository.getTeachersData(string)
    }
}