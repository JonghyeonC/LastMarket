package com.jphr.lastmarket.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import com.auth0.android.jwt.JWT
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import com.jphr.lastmarket.R
import com.jphr.lastmarket.dto.CategoryDTO
import com.jphr.lastmarket.dto.ChatDTO
import com.jphr.lastmarket.dto.ListDTO
import com.jphr.lastmarket.fragment.*
import com.jphr.lastmarket.service.ProductService
import com.jphr.lastmarket.service.UserInfoService
import com.jphr.lastmarket.util.RetrofitCallback
import com.jphr.lastmarket.viewmodel.MainViewModel


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    lateinit  var drawerLayout:DrawerLayout
    lateinit var searchBar: SearchBar
    lateinit var searchView:SearchView
    lateinit var menu: Menu
    var categoryList = mutableListOf<String>()
    var SearchFragment=SearchFragment()
    var ProductListFragment= ProductListFragment()
    lateinit var actionButton:FloatingActionButton
    private val mainViewModel: MainViewModel by viewModels()
    var cityData=""
    var city=""
    var lifestyle=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionButton=findViewById(R.id.floating_action_button)
        var isFromLive=intent.getStringExtra("isFromLive")
        Log.d(TAG, "onCreate: $isFromLive")
        actionButton.visibility=View.VISIBLE

        if(isFromLive.equals("true")) {
            Log.d(TAG, "onCreate: isLivetrue")
            actionButton.visibility=View.GONE
         var chatDTO=intent.getSerializableExtra("chatDTO") as ChatDTO
            mainViewModel.setChatDTO(chatDTO)   //데이터 set
            changeFragment(8)
        }else{
            actionButton.visibility=View.VISIBLE
            Log.d(TAG, "onCreate: isLivefalse")
            val transaction = supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, MainFragment())
            transaction.commit()
            //sharedPreference 작업 viewmodel로 옮기기

            var pref=getSharedPreferences("user_info",MODE_PRIVATE)
            city= pref?.getString("city","null").toString()
            cityData= pref?.getString("city_data","null").toString()
            lifestyle= pref?.getString("lifestyle","null").toString()

            var prefs = getSharedPreferences("user_info", MODE_PRIVATE)
            var editor = prefs?.edit()
            var token=prefs?.getString("token", "")
            Log.d(TAG, "onCreate: $token")
            var jwt= token?.let { JWT(it) }
            val issuer = jwt!!.issuer //get registered claims

            val claim = jwt.getClaim("id").asString() //get custom claims

            val isExpired = jwt.isExpired(10)

            Log.d(TAG, "onCreate: $claim $issuer $isExpired")
            editor?.putLong("user_id",claim!!.toLong())
            editor?.commit()


        }

        drawerLayout=findViewById<DrawerLayout>(R.id.drawer_layout)
        var navigationView=findViewById<NavigationView>(R.id.navigation_view)
        var toolbar=findViewById<MaterialToolbar>(R.id.topAppBar)
        menu=navigationView.menu
        var floatingActionButton=findViewById<FloatingActionButton>(R.id.floating_action_button)
        searchBar =findViewById(R.id.search_bar)
        searchView=findViewById<SearchView>(R.id.search_view)






        floatingActionButton.setOnClickListener {
            changeFragment(6)
        }


        searchBar.visibility=View.GONE


        UserInfoService().getCategory(CategoryCallback())


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
                    if(searchBar.isVisible){
                        searchBar.visibility=View.GONE
                    }else  searchBar.visibility= View.VISIBLE
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
            var title=""
            when(menuItem.itemId){
                0->{
                    menuItem.isChecked = true
                    title= menuItem.title as String
                    ProductService().getProductWithSort("BOOK",null,cityData,"favoriteCnt","DEFAULT","0",ProductCallback(),false,null)
                    drawerLayout.close()
                    true
                }
                1->{
                    menuItem.isChecked = true
                    title= menuItem.title as String
                    ProductService().getProductWithSort("CAMPING",null,cityData,"favoriteCnt","DEFAULT","0",ProductCallback(),false,null)
                    drawerLayout.close()
                    true
                }
                2->{
                    menuItem.isChecked = true
                    title= menuItem.title as String
                    ProductService().getProductWithSort("BOOK",null,cityData,"favoriteCnt","DEFAULT","0",ProductCallback(),false,null)
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

    override fun onResume() {
        super.onResume()
        searchBar.visibility=View.GONE
        searchView
            .editText
            .setOnEditorActionListener { v, actionId, event ->
                searchBar.text = searchView.text
                searchView.hide()
                ProductService().getProductWithSort("",null,cityData,"favoriteCnt","DEFAULT","1",ProductCallback(),true,searchView.text.toString())
                searchView.editText.text=null
                false
            }
    }


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
                transaction.replace(R.id.fragmentContainer, ChatListFragment()).commit()
            }
            4->{
                transaction.replace(R.id.fragmentContainer,SearchFragment).commit()
            }
            5->{
                transaction.replace(R.id.fragmentContainer,ProductListFragment).commit()
            }
            6->{
                transaction.replace(R.id.fragmentContainer,CreateProductFragment()).commit()
            }
            7->{
                transaction.replace(R.id.fragmentContainer,DetailFragment()).commit()
            }
            8->{
                transaction.replace(R.id.fragmentContainer,ChatFragment()).commit()
            }
            9->{
                transaction.replace(R.id.fragmentContainer,LikeListFragment()).commit()
            }
            10->{
                transaction.replace(R.id.fragmentContainer,EditUserInfoFragment()).commit()
            }
            11->{
                transaction.replace(R.id.fragmentContainer,ReviewListFragment()).commit()
            }
            12->{
                transaction.replace(R.id.fragmentContainer,SellListFragment()).commit()
            }
            13->{
                transaction.replace(R.id.fragmentContainer,BuyListFragment()).commit()
            }
        }
    }

    inner class ProductCallback: RetrofitCallback<ListDTO> {
        override fun onSuccess(code: Int,responseData: ListDTO, issearch:Boolean,word:String?,category:String?) {
            if(responseData!=null) {
                if(issearch){
                    Log.d(TAG, "initData: ${responseData}")
                    mainViewModel.setProduct(responseData.content)
                    if (word != null) {
                        mainViewModel.setWord(word)
                    }
                    changeFragment(4)
                }
                else {
                    mainViewModel.setProduct(responseData.content)
                    if (category != null) {
                        mainViewModel.setCategory(category)
                    }
                    changeFragment(5)
                }
            }
        }

        override fun onError(t: Throwable) {
            Log.d(TAG, t.message ?: "물품 정보 받아오는 중 통신오류")
        }

        override fun onFailure(code: Int) {
            Log.d(TAG, "onResponse: Error Code $code")
        }

    }
    inner class CategoryCallback: RetrofitCallback<CategoryDTO> {
        override fun onSuccess(code: Int,responseData: CategoryDTO, issearch:Boolean,word:String?,category:String?) {
            if(responseData!=null) {
                categoryList=responseData.categories

                var i=0
                var groupId=1
                for (i in 0 until categoryList.size) {
                    menu.add(groupId,i,i,categoryList[i])
                    val drawable = resources.getDrawable(
                        R.drawable.circle
                    )
                    menu.getItem(i).icon=drawable
                    if(i%3==0) groupId++
                }

            }
        }

        override fun onError(t: Throwable) {
            Log.d(TAG, t.message ?: "물품 정보 받아오는 중 통신오류")
        }

        override fun onFailure(code: Int) {
            Log.d(TAG, "onResponse: Error Code $code")
        }

    }




}