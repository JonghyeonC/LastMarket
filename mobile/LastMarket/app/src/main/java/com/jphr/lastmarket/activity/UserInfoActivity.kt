package com.jphr.lastmarket.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.jphr.lastmarket.api.UserInfoAPI
import com.jphr.lastmarket.databinding.ActivityUserInfoBinding
import com.jphr.lastmarket.service.UserInfoService

class UserInfoActivity : AppCompatActivity() {
    lateinit var binding:ActivityUserInfoBinding
    lateinit var userName :String
    lateinit var userJob :String
    lateinit var userCategory :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //카테고리 받아오기
        var categoryList=UserInfoService().getCategory()
        //직업 받아오기
        var jobList=UserInfoService().getJob()


        binding.userJob.adapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, jobList)
        binding.userCategory.adapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categoryList)

        binding.save.setOnClickListener{
            userName= binding.userName.text.toString()
            userJob= binding.userJob.selectedItem as String
            userCategory=binding.userCategory.selectedItem as String
        }

    }
}