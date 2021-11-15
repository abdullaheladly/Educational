package com.abdullah996.education.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullah996.education.R
import com.abdullah996.education.adapters.SubjectsAdapter
import com.abdullah996.education.databinding.FragmentHomeBinding
import com.abdullah996.education.models.SubjectsModel
import com.abdullah996.education.ui.SubjectsViewModel


class HomeFragment : Fragment(),SubjectsAdapter.OnQuizListItemClicked {
    private lateinit var subjectsViewModel: SubjectsViewModel
    private lateinit var binding:FragmentHomeBinding
    private lateinit var adapter: SubjectsAdapter
    private var fadeInAnim: Animation?=null
    private var fadeOutAnim: Animation?=null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter= SubjectsAdapter(this)

        binding.listView.setLayoutManager(LinearLayoutManager(context))
        binding.listView.setHasFixedSize(true)
        binding.listView.setAdapter(adapter)
        fadeInAnim= AnimationUtils.loadAnimation(context, R.anim.fade_in)
        fadeOutAnim= AnimationUtils.loadAnimation(context, R.anim.fade_out)
        binding.aboutUs.setOnClickListener {
          /*  val actionAboutUs=ListFragmentDirections.actionListFragmentToAboutUsFragment()
            findNavController().navigate(actionAboutUs)*/
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subjectsViewModel = ViewModelProvider(requireActivity())[SubjectsViewModel::class.java]
        subjectsViewModel.getQuizListModelData().observe(viewLifecycleOwner,
            Observer<List<SubjectsModel?>?> { quizListModels ->
               binding.listView.startAnimation(fadeInAnim)
                binding.listProgress.startAnimation(fadeOutAnim)
                adapter.setQuizListModels(quizListModels as List<SubjectsModel>?)
                adapter.notifyDataSetChanged()
            })
    }

    override fun onItemClicked(subject: String) {
        /** go to each subject fragment with the year known
         * for the student and to show all teacher for this subject */
        val mydialog= AlertDialog.Builder(requireContext())
        mydialog.setTitle("please enter year Number ")
        val editText= EditText(requireContext())
        editText.inputType= InputType.TYPE_CLASS_NUMBER
        mydialog.setView(editText)
        mydialog.setPositiveButton("ok", DialogInterface.OnClickListener { dialog, which ->
            val  text=editText.text.toString()
              val actions =HomeFragmentDirections.actionHomeFragmentToListFragment(subject,text)

          findNavController().navigate(actions)

        })
        mydialog.show()

    }


}