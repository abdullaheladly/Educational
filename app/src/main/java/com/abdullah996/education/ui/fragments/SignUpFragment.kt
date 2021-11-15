package com.abdullah996.education.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.abdullah996.education.util.PreferenceManger

import com.abdullah996.education.databinding.FragmentSignUpBinding
import com.abdullah996.education.util.Constants
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var preferenceManger: PreferenceManger
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSignUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferenceManger= PreferenceManger(requireContext())
        setListeners()
    }
    private fun setListeners(){
        binding.logIn.setOnClickListener {
            val action=SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
            findNavController().navigate(action)
        }
        binding.signIp.setOnClickListener {
            if (isValidData()){
                signUp()
            }

        }
    }
    private fun signUp(){
        loading(true)
        val database=FirebaseFirestore.getInstance()
        val user:HashMap<String,Any> = HashMap()
        user[Constants.KEY_EMAIL] = binding.Email.text.toString()
        user[Constants.KEY_NAME] = binding.Name.text.toString()
        user[Constants.KEY_PASSWORD] = binding.password.text.toString()
        GlobalScope.launch(Dispatchers.IO) {
            database.collection(Constants.KEY_COLLECTION_USERS)
                .add(user)
                .addOnSuccessListener {
                    loading(false)
                    preferenceManger.putBoolean(Constants.KEY_IS_SIGNED_IN, true)
                    preferenceManger.putString(Constants.KEY_USER_ID, it.id)
                    preferenceManger.putString(Constants.KEY_NAME, binding.Name.text.toString())
                    preferenceManger.putString(Constants.KEY_EMAIL,binding.Email.text.toString())
                    preferenceManger.putString(Constants.KEY_PASSWORD,binding.password.text.toString())
                    findNavController().navigate(
                        SignUpFragmentDirections.actionSignUpFragmentToCompleteProfileFragment()
                    )
                }.addOnFailureListener {
                    loading(false)
                    makeToast(it.message.toString())
                }.await()
        }

    }
   private fun isValidData():Boolean{
       return if  (binding.Name.text.toString().trim().isEmpty()
           ||binding.password.text.toString().trim().isEmpty()
           ||binding.password2.text.toString().trim().isEmpty()
           ||binding.Email.text.toString().trim().isEmpty()){
           makeToast("please fill all the data first")
           false
       }else if (binding.password.text.toString()!=binding.password2.text.toString()){
           makeToast("Please enter correct password")
           false
       }else{
           true
       }
   }



    private fun makeToast(string: String){
        Toast.makeText(context,string,Toast.LENGTH_LONG).show()
    }
    private  fun loading(isLoading:Boolean){
        if (isLoading){
            binding.signIp.visibility=View.INVISIBLE
            binding.progressBar.visibility=View.VISIBLE
        }else{
            binding.progressBar.visibility=View.INVISIBLE
            binding.signIp.visibility=View.VISIBLE
        }
    }

}