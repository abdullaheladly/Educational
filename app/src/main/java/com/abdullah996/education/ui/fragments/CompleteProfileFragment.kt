package com.abdullah996.education.ui.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.abdullah996.education.R

import com.abdullah996.education.databinding.FragmentCompleteProfileBinding
import com.abdullah996.education.util.Constants
import com.abdullah996.education.util.PreferenceManger
import com.google.firebase.firestore.FirebaseFirestore
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException

class CompleteProfileFragment : Fragment() {
    private lateinit var binding: FragmentCompleteProfileBinding
    private lateinit var encodedImage:String
    private lateinit var preferenceManger: PreferenceManger
    private lateinit var firebase:FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentCompleteProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebase= FirebaseFirestore.getInstance()
        preferenceManger= PreferenceManger(requireContext())
        setListeners()
    }


    private fun setListeners(){

        binding.imageFrame.setOnClickListener {
            val  intent=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            pickImage.launch(intent)
        }
        binding.home.setOnClickListener {
            uploudData()
        }
    }

    private fun uploudData() {
        val userId=preferenceManger.getString(Constants.KEY_USER_ID)
        val data =HashMap<String,String>()
        data.put(Constants.KEY_USER_Image,encodedImage)
        data.put(Constants.KEY_USER_Phone,binding.mobileNumber.text.toString())
        data.put(Constants.KEY_USER_ID,userId!!)
        data.put(Constants.KEY_NAME,preferenceManger.getString(Constants.KEY_NAME)!!)
        data.put(Constants.KEY_EMAIL,preferenceManger.getString(Constants.KEY_EMAIL)!!)
        data.put(Constants.KEY_PASSWORD,preferenceManger.getString(Constants.KEY_PASSWORD)!!)
        firebase.collection(Constants.KEY_COLLECTION_USERS)
            .document(userId!!).set(data).addOnSuccessListener {
                findNavController().navigate(R.id.action_completeProfileFragment_to_homeFragment)
            }
    }

    private val pickImage: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK){
            if (result.data != null){
                val imageUri= result.data!!.data
                try {
                    val inputStream= imageUri?.let { context?.contentResolver?.openInputStream(it) }
                    val bitmap= BitmapFactory.decodeStream(inputStream)
                    binding.imageProfile.setImageBitmap(bitmap)
                    binding.textImage.visibility=View.GONE
                    encodedImage=encodedImage(bitmap)
                }catch (e: FileNotFoundException){
                    e.printStackTrace()
                }
            }
        }

    }
    private fun encodedImage(bitmap: Bitmap):String{
        val previewWidth=150
        val previewHeight:Int=bitmap.height*previewWidth/bitmap.width
        val previewBitmap= Bitmap.createScaledBitmap(bitmap,previewWidth,previewHeight,false)
        val byteArrayOutputStream= ByteArrayOutputStream()
        previewBitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream)
        val bytes=byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }



}