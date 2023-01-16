package com.jphr.lastmarket.activity

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jphr.lastmarket.databinding.ActivityUserInfoBinding
import com.jphr.lastmarket.service.UserInfoService
import java.io.IOException
import java.util.*


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
    fun getAddress(mContext: Context?, lat: Double, lng: Double): String? {//지오코더를 사용하여 위경도를 주소로 변환
        var nowAddr = "현재 위치를 확인 할 수 없습니다."
        val geocoder = Geocoder(mContext, Locale.KOREA)
        val address: List<Address>?
        try {
            if (geocoder != null) {
                address = geocoder.getFromLocation(lat, lng, 1)
                if (address != null && address.isNotEmpty()) {
                    nowAddr = address[0].getAddressLine(0).toString()
                }
            }
        } catch (e: IOException) {
            Toast.makeText(mContext, "주소를 가져 올 수 없습니다.", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
        return nowAddr
    }

}