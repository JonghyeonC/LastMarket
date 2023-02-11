package com.jphr.lastmarket.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.jphr.lastmarket.R
import com.jphr.lastmarket.activity.MainActivity
import com.jphr.lastmarket.adapter.MultiImageAdapter
import com.jphr.lastmarket.databinding.FragmentEditUserInfoBinding
import com.jphr.lastmarket.service.MyPageService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditUserInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditUserInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentEditUserInfoBinding
    var imageUriList : Uri?=null
    var imageMultipartList :MultipartBody.Part?=null
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentEditUserInfoBinding.inflate(inflater, container, false)
        val launcher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data == null) {
                    Toast.makeText(requireContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show()
                } else {
                    if (data.clipData == null) {//하나만 선택
                        var imageUri = data.data
                        if (imageUri != null) {
                            val file = File(absolutelyPath(imageUri, requireContext()))  //절대경로로 바꿈
                            val requestFile =
                                RequestBody.create("image/*".toMediaTypeOrNull(), file)
                            val body =
                                MultipartBody.Part.createFormData("imgs", file.name, requestFile)

                            imageMultipartList=body
                            imageUriList=imageUri

                            binding.imageView.visibility=View.VISIBLE
                            binding.imageView.setImageURI(imageUri)
                            Glide.with(mainActivity).load(imageUri).into(binding.imageView)


                        }
                    } else {
                        var clipData = data.clipData
                        if (clipData?.itemCount!! > 1) {
                            Toast.makeText(
                                requireContext(),
                                "사진은 1장만 선택 가능합니다.",
                                Toast.LENGTH_LONG
                            ).show();
                        }
                    }
                }
            }
        }


        binding.imageUpload.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            launcher.launch(intent)
            binding.imageView.visibility=View.VISIBLE
            binding.imageView.setImageURI(imageUriList)
            Glide.with(mainActivity).load(imageUriList).into(binding.imageView)

        }

        binding.save.setOnClickListener {
            var prefs =
                requireActivity().getSharedPreferences("user_info", AppCompatActivity.MODE_PRIVATE)
            var token = prefs.getString("token", "")!!
            Log.d("TAG", "onCreateView: $imageMultipartList")
            MyPageService().insertUserProfile(token,imageMultipartList!!)
        }
        return binding.root
    }

    // 절대경로 변환
    fun absolutelyPath(path: Uri?, context: Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)

        return result!!
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditUserInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditUserInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}