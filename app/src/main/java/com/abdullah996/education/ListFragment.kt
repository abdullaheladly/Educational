package com.abdullah996.education

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullah996.education.adapters.QuizListAdapter
import com.abdullah996.education.ui.QuizListViewModel
import com.abdullah996.education.adapters.TeachersAdapter
import com.abdullah996.education.databinding.FragmentListBinding
import com.abdullah996.education.models.QuizListModel
import com.abdullah996.education.models.TeachersModel
import com.abdullah996.education.ui.TeachersViewModel


class ListFragment : Fragment(),TeachersAdapter.OnTeachersListItemClicked,QuizListAdapter.OnQuizListItemClicked {
    private lateinit var teachersViewModel: TeachersViewModel
    private lateinit var quizViewModel: QuizListViewModel
    private lateinit var binding:FragmentListBinding
    private lateinit var teachersAdapter: TeachersAdapter
    private lateinit var quizAdapter: QuizListAdapter
    private  var sub_id:String?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         sub_id= arguments?.let { ListFragmentArgs.fromBundle(it).subject }
        val year= arguments?.let { ListFragmentArgs.fromBundle(it).year }
        Log.d("app_log", "postion :$year and $sub_id")


        teachersAdapter= TeachersAdapter(this)
        quizAdapter= QuizListAdapter(this)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.teachersRv.setLayoutManager(layoutManager)
        binding.teachersRv.setHasFixedSize(true)
        binding.teachersRv.setAdapter(teachersAdapter)


        binding.quizListRv.setLayoutManager(LinearLayoutManager(context))
        binding.quizListRv.setHasFixedSize(true)
        binding.quizListRv.setAdapter(quizAdapter)




    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        teachersViewModel = ViewModelProvider(requireActivity())[TeachersViewModel::class.java]
        teachersViewModel.getTeachers(sub_id!!)
        teachersViewModel.teachersModelData().observe(viewLifecycleOwner,
            Observer<List<TeachersModel?>?> { teachersModels ->
                teachersAdapter.setTeachersListModels(teachersModels as List<TeachersModel>?)
                teachersAdapter.notifyDataSetChanged()
            })

        quizViewModel = ViewModelProvider(requireActivity())[QuizListViewModel::class.java]
        quizViewModel.getQuizList(sub_id!!,"0p8xKssOSomEAjhcRYnN")
        quizViewModel.quizListModelData().observe(viewLifecycleOwner,
            Observer<List<QuizListModel?>?> { quizModels ->
                quizAdapter.setQuizListModels(quizModels as List<QuizListModel>?)
                quizAdapter.notifyDataSetChanged()
            })

    }
    private fun setupQuizesRv() {
        TODO("Not yet implemented")
    }
    private fun setupTeacherRv() {
        TODO("Not yet implemented")
    }
    override fun onItemClicked(subject: String) {
        TODO("Not yet implemented")
    }


}