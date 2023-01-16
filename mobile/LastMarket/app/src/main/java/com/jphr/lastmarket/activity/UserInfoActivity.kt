package com.jphr.lastmarket.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.jphr.lastmarket.api.UserInfoAPI
import com.jphr.lastmarket.databinding.ActivityUserInfoBinding
import com.jphr.lastmarket.service.UserInfoService
import com.jphr.lastmarket.viewmodel.UserInfoViewModel
import kotlinx.coroutines.launch

private const val TAG = "UserInfoActivity"
class UserInfoActivity : AppCompatActivity() {
    lateinit var binding:ActivityUserInfoBinding
    lateinit var userName :String
    lateinit var userJob :String
    lateinit var userCategory :String
    private val userInfoViewModel: UserInfoViewModel by viewModels()
    var categoryList= mutableListOf<String>()
    var jobList= mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        categoryList=userInfoViewModel.getcategory()

        lifecycleScope.launch(){
            jobList= UserInfoService().getJob()
            binding.userJob.adapter = ArrayAdapter(this@UserInfoActivity, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, jobList)
            binding.userCategory.adapter = ArrayAdapter(this@UserInfoActivity, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categoryList)
            Log.d(TAG, "onCreate: $categoryList")

        }




//        Log.d(TAG, "onCreate: $jobList")




        binding.save.setOnClickListener{
            userName= binding.userName.text.toString()
            userJob= binding.userJob.selectedItem as String
            userCategory=binding.userCategory.selectedItem as String
        }

    }



}