package com.jphr.lastmarket.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.jphr.lastmarket.R

class MainActivity : AppCompatActivity() {
    lateinit  var drawerLayout:DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout=findViewById<DrawerLayout>(R.id.drawer_layout)
        var navigationView=findViewById<NavigationView>(R.id.navigation_view)
        var toolbar=findViewById<Toolbar>(R.id.topAppBar)
        setActionBar(toolbar)
        var actionbar= supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(false)
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setHomeAsUpIndicator(R.drawable.hamberger)

        toolbar.setNavigationOnClickListener {
            drawerLayout.open()
        }
        navigationView.setNavigationItemSelectedListener {menuItem->
            when(menuItem.itemId){
                android.R.id.home->{//메뉴버튼을 눌렀을 때
                    menuItem.isChecked = true
//                    drawerLayout.close()
                    true
                }
                R.id.search->{//검색버튼
                    menuItem.isChecked = true
                    drawerLayout.close()
                    true
                }
                R.id.chat->{//채팅버튼
                    menuItem.isChecked = true
                    drawerLayout.close()
                    true
                }
                R.id.mypage->{//마이페이지
                    menuItem.isChecked = true
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{//메뉴버튼을 눌렀을 때
                drawerLayout.openDrawer(GravityCompat.START);
            }
            R.id.search->{//검색버튼

            }
            R.id.chat->{//채팅버튼

            }
            R.id.mypage->{//마이페이지

            }

        }
       return super.onOptionsItemSelected(item)
    }
}