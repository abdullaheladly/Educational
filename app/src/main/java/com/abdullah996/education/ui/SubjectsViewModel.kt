package com.abdullah996.education.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdullah996.education.models.QuizListModel
import com.abdullah996.education.models.SubjectsModel
import com.abdullah996.education.models.TeachersModel
import com.abdullah996.education.repo.FirebaseRepository

import java.lang.Exception

class SubjectsViewModel : ViewModel(), FirebaseRepository.OnFirestoreTaskComplete {
    private val quizListModelData = MutableLiveData<List<SubjectsModel>>()
    fun getQuizListModelData(): LiveData<List<SubjectsModel>> {
        return quizListModelData
    }

    private val firebaseRepository = FirebaseRepository(this)


    override fun quizListDataAdded(subjects: List<SubjectsModel?>?) {
        quizListModelData.value = subjects as List<SubjectsModel>?

    }

    override fun onError(e: Exception?) {}
    override fun teachersDataAdded(teachers: List<TeachersModel>) {

    }

    override fun quizDataAdded(quizs: List<QuizListModel>) {

    }

    init {
        firebaseRepository.getSubjectsData()
    }
}