package com.abdullah996.education.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdullah996.education.models.QuizListModel
import com.abdullah996.education.models.SubjectsModel
import com.abdullah996.education.models.TeachersModel
import com.abdullah996.education.repo.FirebaseRepository
import java.lang.Exception

class QuizListViewModel: ViewModel(), FirebaseRepository.OnFirestoreTaskComplete {
    private val quizListModelData = MutableLiveData<List<QuizListModel>>()
    fun quizListModelData(): LiveData<List<QuizListModel>> {
        return quizListModelData
    }

    private val firebaseRepository = FirebaseRepository(this)


    override fun quizListDataAdded(subjects: List<SubjectsModel?>?) {


    }

    override fun onError(e: Exception?) {}
    override fun teachersDataAdded(teachers: List<TeachersModel>) {
        TODO("Not yet implemented")
    }



    override fun quizDataAdded(quizs: List<QuizListModel>) {
        quizListModelData.value = quizs as List<QuizListModel>?
    }

    fun getQuizList(string: String,string1:String) {
        firebaseRepository.getQuizListData(string,string1)
    }
}

