package com.abdullah996.education.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.abdullah996.education.R
import com.abdullah996.education.databinding.FragmentSignInBinding
import com.abdullah996.education.util.Constants
import com.abdullah996.education.util.PreferenceManger
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var preferenceManger: PreferenceManger

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSignInBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferenceManger= PreferenceManger(requireContext())
        if (preferenceManger.getBoolean(Constants.KEY_IS_SIGNED_IN)){
                findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
        }

        setListeners()
    }
    private fun setListeners(){
        binding.logIn.setOnClickListener {
            if (checkData()){
                logIn()
            }
        }
        binding.signIp.setOnClickListener {
            val action=SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            findNavController().navigate(action)
        }
    }
    private fun logIn(){
        loading(true)
        makeToast("hey")
        val database=FirebaseFirestore.getInstance()
        GlobalScope.launch(Dispatchers.IO) {
            database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL, binding.Email.text.toString())
                .whereEqualTo(Constants.KEY_PASSWORD, binding.password.text.toString())
                .get()
                .addOnCompleteListener { it ->
                    makeToast("hey2")
                    if (it.isSuccessful&&it.result!=null&&it.result!!.documents.size>0){
                        val documentSnapshot=it.result!!.documents[0]
                        preferenceManger.putBoolean(Constants.KEY_IS_SIGNED_IN,true)
                        preferenceManger.putString(Constants.KEY_USER_ID,documentSnapshot.id)
                        documentSnapshot.getString(Constants.KEY_NAME)?.let {name->
                            preferenceManger.putString(Constants.KEY_NAME,name)

                        }

                        findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                       //here we can go to home fragment
                    }else{
                        loading(false)
                        makeToast("unable to sign in ")
                    }
                }.addOnFailureListener {
                    loading(false)
                    makeToast(it.toString())
                }.await()
        }
    }
    private fun checkData():Boolean{
        return if (binding.Email.text.toString().trim().isNotEmpty()&&binding.password.text.toString().trim().isNotEmpty()) {
            true
        } else{
            makeToast("please fill all the fields first ")
            false
        }

    }

    private fun makeToast(string: String){
        Toast.makeText(context,string, Toast.LENGTH_SHORT).show()
    }
    private fun loading(isLoading :Boolean){
        if (isLoading){
            binding.progressBar.visibility=View.VISIBLE
            binding.logIn.visibility=View.INVISIBLE
        }else{
            binding.logIn.visibility=View.VISIBLE
            binding.progressBar.visibility=View.INVISIBLE
        }


    }


}