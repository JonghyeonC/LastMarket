package com.jphr.lastmarket.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jphr.lastmarket.databinding.ActivityUserInfoBinding
import com.jphr.lastmarket.service.UserInfoService

private const val TAG = "UserInfoActivity"
class UserInfoActivity : AppCompatActivity() {
    lateinit var binding:ActivityUserInfoBinding
    lateinit var userName :String
    lateinit var userJob :String
    lateinit var userCategory :String
    var categoryList= MutableLiveData<MutableList<String>>()
    var jobList= MutableLiveData<MutableList<String>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "onCreate: 시작------")

        categoryList=UserInfoService().getCategory()
        jobList=UserInfoService().getJob()

        categoryList.observe(this, Observer {
            binding.userCategory.adapter = ArrayAdapter(this@UserInfoActivity, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, it)
            Log.d(TAG, "onCreate: $it")

        })
        jobList.observe(this, Observer {
            binding.userJob.adapter = ArrayAdapter(this@UserInfoActivity, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, it)
            Log.d(TAG, "onCreate: $it")
        })





        binding.save.setOnClickListener{
            userName= binding.userName.text.toString()
            userJob= binding.userJob.selectedItem as String
            userCategory=binding.userCategory.selectedItem as String
        }

    }

}