package com.jphr.lastmarket.activity

import android.Manifest
import android.R.array
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.gms.location.*
import com.jphr.lastmarket.databinding.ActivityUserInfoBinding
import com.jphr.lastmarket.dto.UserInfoDTO
import com.jphr.lastmarket.service.UserInfoService
import com.jphr.lastmarket.util.RetrofitCallback
import java.io.IOException
import java.util.*


private const val TAG = "UserInfoActivity"

class UserInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserInfoBinding
    lateinit var userName: String
    lateinit var userJob: String
    lateinit var userCategory: String
    lateinit var userAddress: String

    val MY_PERMISSION_ACCESS_ALL = 100
    var categoryList = MutableLiveData<MutableList<String>>()
    var jobList = MutableLiveData<MutableList<String>>()

    private var mFusedLocationProviderClient: FusedLocationProviderClient? =
        null // 현재 위치를 가져오기 위한 변수

    //    lateinit var mLastLocation: Location // 위치 값을 가지고 있는 객체
    internal lateinit var mLocationRequest: LocationRequest // 위치 정보 요청의 매개변수를 저장하는
    private val REQUEST_PERMISSION_LOCATION = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "onCreate: 시작------")

        categoryList = UserInfoService().getCategory()
        jobList = UserInfoService().getJob()

        categoryList.observe(this, Observer {
            binding.userCategory.adapter = ArrayAdapter(
                this@UserInfoActivity,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                it
            )
            Log.d(TAG, "onCreate: $it")

        })
        jobList.observe(this, Observer {
            binding.userJob.adapter = ArrayAdapter(
                this@UserInfoActivity,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                it
            )
            Log.d(TAG, "onCreate: $it")
        })


        binding.search.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.d(TAG, "onCreate:if문")
                var permissions = arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
                ActivityCompat.requestPermissions(this, permissions, MY_PERMISSION_ACCESS_ALL)

            } else {
                if (checkPermissionForLocation(this)) {
                    Log.d(TAG, "onCreate: if2  ")
                    startLocationUpdates()
                }
            }
        }

        binding.save.setOnClickListener {
            userName = binding.userName.text.toString()
            userJob = binding.userJob.selectedItem as String
            userCategory = binding.userCategory.selectedItem as String
            userAddress= binding.address.text as String
            var userinfo=UserInfoDTO(userAddress,userCategory,userJob,userName)
            Log.d(TAG, "onCreate: $userinfo")
            UserInfoService().insertUserInfo(userinfo)
            var intent=Intent(this@UserInfoActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }

    fun getAddress(mContext: Context?, lat: Double, lng: Double) {//지오코더를 사용하여 위경도를 주소로 변환
        Log.d(TAG, "getAddress: $lat $lng")
        var nowAddr = "dfdsaf"
        val geocoder = Geocoder(mContext, Locale.KOREA)
        val address: List<Address>?
        try {
            if (geocoder != null) {
                address = geocoder.getFromLocation(lat, lng, 3)
                Log.d(TAG, "getAddress: $address")
                if (address != null && address.size > 0) {
                    nowAddr = address[0].getAddressLine(0).toString()
                    var arr = nowAddr.split(" ")
                    var i = 0
                    var add: String = ""
                    for (i in 1 until 4) {
                        add += arr[i];
                        Log.d(TAG, "getAddress: ${arr[i]}")
                    }
                    binding.address.let {
                        it.visibility = View.VISIBLE
                        it.text = add
                    }
                    binding.search.visibility = View.GONE
                } else {
                    Log.d(TAG, "getAddress: else 1")
                }
            } else {
                Log.d(TAG, "getAddress: else 문")
            }
        } catch (e: IOException) {
            Toast.makeText(mContext, "주소를 가져 올 수 없습니다.", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }

    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        Log.d(TAG, "startLocationUpdates: ")
        mLocationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 5 * 10000
        }
        //FusedLocationProviderClient의 인스턴스를 생성.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // 기기의 위치에 관한 정기 업데이트를 요청하는 메서드 실행
        // 지정한 루퍼 스레드(Looper.myLooper())에서 콜백(mLocationCallback)으로 위치 업데이트를 요청

        mFusedLocationProviderClient!!.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.getMainLooper()
        )

//        binding.address.let {
//            it.visibility= View.VISIBLE
//            it.text=nowAddr
//        }
//        binding.search.visibility=View.GONE
    }

    // 시스템으로 부터 위치 정보를 콜백으로 받음
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            Log.d(TAG, "onLocationResult: ")
            // 시스템에서 받은 location 정보를 getAddress에 전달
            locationResult.lastLocation
            getAddress(
                this@UserInfoActivity,
                locationResult.lastLocation!!.latitude,
                locationResult.lastLocation!!.longitude
            )
        }
    }

    // 위치 권한이 있는지 확인하는 메서드
    private fun checkPermissionForLocation(context: Context): Boolean {
        // Android 6.0 Marshmallow 이상에서는 위치 권한에 추가 런타임 권한이 필요
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                // 권한이 없으므로 권한 요청 알림 보내기
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_LOCATION
                )
                false
            }
        } else {
            true
        }
    }

    // 사용자에게 권한 요청 후 결과에 대한 처리 로직
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: ")
                startLocationUpdates()

            } else {
                Log.d("ttt", "onRequestPermissionsResult() _ 권한 허용 거부")
                Toast.makeText(this, "권한이 없어 해당 기능을 실행할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun removeLocationUpdate() {
        mFusedLocationProviderClient?.removeLocationUpdates(mLocationCallback)
    }

    override fun finish() {
        removeLocationUpdate()
        Log.d(TAG, "finish: ")
        super.finish()
    }

//    inner class JoinCallback: RetrofitCallback<Boolean> {
//
//        override fun onSuccess(code: Int, responseData: Boolean) {
//            if (responseData) {
//                Log.d(TAG, "onSuccess: ")
//                var intent=Intent(this@UserInfoActivity, UserInfoActivity::class.java)
//                startActivity(intent)
//
//            } else {
//
//                Toast.makeText(this@UserInfoActivity, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        override fun onFailure(code: Int) {
//            Log.d(TAG, "onResponse: Error Code $code")
//        }
//
//        override fun onError(t: Throwable) {
//            Log.d(TAG, t.message ?: "유저 정보 불러오는 중 통신오류")
//        }
//    }
}