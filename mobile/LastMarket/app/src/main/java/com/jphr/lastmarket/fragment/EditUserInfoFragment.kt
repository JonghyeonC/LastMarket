package com.jphr.lastmarket.fragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
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
private const val TAG = "EditUserInfoFragment"
class EditUserInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentEditUserInfoBinding

    var imageUriList = mutableListOf<Uri>()
    var imageMultipartList = mutableListOf<MultipartBody.Part>()

    private lateinit var mainActivity: MainActivity
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
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

        verifyStoragePermissions(requireActivity())     //권한

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

                            imageMultipartList.add(body)
                            imageUriList.add(imageUri)

                        }
                    } else {
                        var clipData = data.clipData
                        if (clipData?.itemCount!! > 10) {
                            Toast.makeText(
                                requireContext(),
                                "사진은 10장까지 선택 가능합니다.",
                                Toast.LENGTH_LONG
                            ).show();
                        } else {

                            for (i in 0 until clipData.itemCount) {
                                val imageUri = clipData.getItemAt(i).uri // 선택한 이미지들의 uri를 가져온다.
                                val file =
                                    File(absolutelyPath(imageUri, requireContext()))  //절대경로로 바꿈
                                val requestFile =
                                    RequestBody.create("image/*".toMediaTypeOrNull(), file)
                                val body = MultipartBody.Part.createFormData(
                                    "imgs",
                                    file.name,
                                    requestFile
                                )

                                try {
                                    imageUriList.add(imageUri) //uri를 list에 담는다.
                                    imageMultipartList.add(body)        //멀티파트 리스트에 담는다.
                                    binding.imageView.setImageURI(imageUriList[0])

                                } catch (e: Exception) {
                                    Log.e(TAG, "File select error", e)
                                }
                            }
                            Log.d(TAG, "onCreateView: ${imageUriList}")

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
            Log.d(TAG, "onCreateView: -------before")

            launcher.launch(intent)
            Log.d(TAG, "onCreateView: -------after")
            binding.imageView.visibility=View.VISIBLE

        }

        binding.save.setOnClickListener {
            var prefs =
                requireActivity().getSharedPreferences("user_info", AppCompatActivity.MODE_PRIVATE)
            var token = prefs.getString("token", "")!!
            Log.d("TAG", "onCreateView: $imageMultipartList")
            MyPageService().insertUserProfile(token, imageMultipartList[0])
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
    fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
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