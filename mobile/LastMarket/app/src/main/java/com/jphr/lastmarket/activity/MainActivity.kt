package com.jphr.lastmarket.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.jphr.lastmarket.R
import com.jphr.lastmarket.fragment.ChatFragment
import com.jphr.lastmarket.fragment.MainFragment
import com.jphr.lastmarket.fragment.MypageFragment
import com.jphr.lastmarket.fragment.SearchFragment
import com.jphr.lastmarket.service.UserInfoService


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    lateinit  var drawerLayout:DrawerLayout
    var categoryList = MutableLiveData<MutableList<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, MainFragment())
        transaction.commit()

        drawerLayout=findViewById<DrawerLayout>(R.id.drawer_layout)
        var navigationView=findViewById<NavigationView>(R.id.navigation_view)
        var toolbar=findViewById<MaterialToolbar>(R.id.topAppBar)
        var menu=navigationView.menu

        categoryList = UserInfoService().getCategory()
        categoryList.observe(this, Observer{
            var i=0
            var groupId=1
            for (i in 0 until it.size) {
                menu.add(groupId,i,i,it[i])
                val drawable = resources.getDrawable(
                    R.drawable.circle
                )
                menu.getItem(i).icon=drawable
                if(i%3==0) groupId++
            }
        })


        setSupportActionBar(toolbar)
        toolbar.setOnClickListener {
            changeFragment(1)
        }



        toolbar.setNavigationOnClickListener {
            drawerLayout.open()
        }
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                android.R.id.home->{//메뉴버튼을 눌렀을 때
                    drawerLayout.openDrawer(GravityCompat.START);
                    true
                }
                R.id.search->{//검색버튼
                    true
                }
                R.id.chat->{//채팅버튼
                    changeFragment(3)
                    true
                }
                R.id.mypage->{//마이페이지
                    changeFragment(2)
                    true
                }
                else ->false

            }
        }
        navigationView.setNavigationItemSelectedListener {menuItem->
            when(menuItem.itemId){
                android.R.id.home->{//메뉴버튼을 눌렀을 때
                    menuItem.isChecked = true
                    drawerLayout.close()
                    true
                }
                R.id.search->{//검색버튼
                    menuItem.isChecked = true
                    drawerLayout.close()
                    true
                }
                R.id.chat->{//채팅버튼
                    menuItem.isChecked = true
//                    changeFragment(3)
                    drawerLayout.close()
                    true
                }
                R.id.mypage->{//마이페이지
                    menuItem.isChecked = true
//                    changeFragment(2)
                    drawerLayout.close()
                    true
                }

                else ->{
                    menuItem.isChecked = true
                    drawerLayout.close()
                    false
                }
            }
        }


    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            android.R.id.home->{//메뉴버튼을 눌렀을 때
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//            R.id.search->{//검색버튼
//                Log.d(TAG, "onOptionsItemSelected: ")
//
//            }
//            R.id.chat->{//채팅버튼
//
//            }
//            R.id.mypage->{//마이페이지
//
//            }
//
//        }
//       return super.onOptionsItemSelected(item)
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return super.onCreateOptionsMenu(menu)
    }
    fun changeFragment(index :Int){
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        when(index){
            1->{
                transaction.replace(R.id.fragmentContainer, MainFragment()).commit()
            }
            2->{
                transaction.replace(R.id.fragmentContainer, MypageFragment()).commit()
            }
            3->{
                transaction.replace(R.id.fragmentContainer, ChatFragment()).commit()
            }
            4->{
                transaction.replace(R.id.nestedview,SearchFragment()).commit()
            }
        }
    }

}