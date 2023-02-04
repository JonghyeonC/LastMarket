package com.jphr.lastmarket.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jphr.lastmarket.activity.LiveBuyActivity
import com.jphr.lastmarket.activity.MainActivity
import com.jphr.lastmarket.adapter.ProductListAdapter
import com.jphr.lastmarket.databinding.FragmentMainBinding
import com.jphr.lastmarket.dto.ListDTO
import com.jphr.lastmarket.dto.ProductDTO
import com.jphr.lastmarket.dto.ProductDetailDTO
import com.jphr.lastmarket.dto.ProductX
import com.jphr.lastmarket.service.ProductService
import com.jphr.lastmarket.util.RecyclerViewDecoration
import com.jphr.lastmarket.util.RetrofitCallback
import com.jphr.lastmarket.viewmodel.MainViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "MainFragment"

class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentMainBinding
    private lateinit var productListAdapter:ProductListAdapter
    private lateinit var mainActivity: MainActivity
    private val mainViewModel by activityViewModels<MainViewModel>()

    val PREFERENCES_NAME = "user_info"
    var productList1 : MutableList<ProductX>?=null
    var productList2 : MutableList<ProductX>? =null
    var productList3 : MutableList<ProductX>? =null

    private fun getPreferences(context: Context): SharedPreferences? {
        return context.getSharedPreferences(PREFERENCES_NAME, AppCompatActivity.MODE_PRIVATE)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity=context as MainActivity
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
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
        binding=FragmentMainBinding.inflate(inflater,container,false)

        var pref:SharedPreferences?= getPreferences(requireContext())
        var city= pref?.getString("city","null")
        var cityData=pref?.getString("city_data","null")

        var lifestyle= pref?.getString("lifestyle","null")


        ProductService().getProductWithSort("",lifestyle,cityData,"favoriteCnt","DEFAULT","1",ProductSortCallback1(),false,null)
        ProductService().getProductWithSort("",lifestyle,cityData,"favoriteCnt","DEFAULT","1",ProductSortCallback2(),false,null)
        ProductService().getProductWithSort("",lifestyle,cityData,"favoriteCnt","DEFAULT","1",ProductSortCallback3(),false,null)



        binding.city.text=city
        binding.city2.text=city
        binding.city3.text=city
        binding.lifestyle.text=lifestyle
        binding.lifestyle2.text=lifestyle

        binding.button.setOnClickListener{
            Log.d(TAG, "onCreateView: ")
            var intent = Intent(activity,LiveBuyActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    inner class ProductSortCallback1: RetrofitCallback<ListDTO> {
        override fun onSuccess(code: Int, responseData: ListDTO, issearch:Boolean, word:String?, category:String?) {
            if(responseData!=null) {
                //취미별
                productList1=responseData.content

                binding.recyclerviewCategory.apply {
                    productListAdapter.list=responseData.content as MutableList<ProductX>
                    var linearLayoutManager= LinearLayoutManager(context)
                    linearLayoutManager.orientation=LinearLayoutManager.HORIZONTAL
                    setLayoutManager(linearLayoutManager)
                    adapter=productListAdapter
                    addItemDecoration(RecyclerViewDecoration(20,20))
                }
            }else Log.d(TAG, "onSuccess: data is null")
        }

        override fun onError(t: Throwable) {
            Log.d(TAG, t.message ?: "물품 정보 받아오는 중 통신오류")
        }

        override fun onFailure(code: Int) {
            Log.d(TAG, "onResponse: Error Code $code")
        }

    }
    inner class ProductSortCallback2: RetrofitCallback<ListDTO> {
        override fun onSuccess(code: Int, responseData: ListDTO, issearch:Boolean, word:String?, category:String?) {
            if(responseData!=null) {
                //라이브 중인것
                productList2=responseData.content

                binding.recyclerviewLive.apply {
                    productListAdapter.list=responseData.content as MutableList<ProductX>
                    var linearLayoutManager= LinearLayoutManager(context)
                    linearLayoutManager.orientation=LinearLayoutManager.HORIZONTAL
                    setLayoutManager(linearLayoutManager)
                    adapter=productListAdapter
                    addItemDecoration(RecyclerViewDecoration(20,20))
                }
            }else Log.d(TAG, "onSuccess: data is null")
        }

        override fun onError(t: Throwable) {
            Log.d(TAG, t.message ?: "물품 정보 받아오는 중 통신오류")
        }

        override fun onFailure(code: Int) {
            Log.d(TAG, "onResponse: Error Code $code")
        }


    }
    inner class ProductSortCallback3: RetrofitCallback<ListDTO> {
        override fun onSuccess(code: Int, responseData: ListDTO, issearch:Boolean, word:String?, category:String?) {
            if(responseData!=null) {
                //새로운 것
                productList3=responseData.content

                binding.recyclerviewNew.apply {
                    productListAdapter.list=responseData.content as MutableList<ProductX>
                    var linearLayoutManager= LinearLayoutManager(context)
                    linearLayoutManager.orientation=LinearLayoutManager.HORIZONTAL
                    layoutManager = linearLayoutManager
                    adapter=productListAdapter
                    addItemDecoration(RecyclerViewDecoration(20,20))
                }

            }else Log.d(TAG, "onSuccess: data is null")
        }

        override fun onError(t: Throwable) {
            Log.d(TAG, t.message ?: "물품 정보 받아오는 중 통신오류")
        }

        override fun onFailure(code: Int) {
            Log.d(TAG, "onResponse: Error Code $code")
        }

    }

    fun initAdapter() {
        productListAdapter= ProductListAdapter(mainActivity)


        productListAdapter.setItemClickListener(object : ProductListAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {
                productListAdapter.list?.get(position)?.productId
                    ?.let {
                        ProductService().getProductDetail(it,ProductDetailCallback())
                    }

            }
        })

    }
    inner class ProductDetailCallback: RetrofitCallback<ProductDetailDTO> {

        override fun onSuccess(
            code: Int,
            responseData: ProductDetailDTO,
            option: Boolean,
            word: String?,
            category: String?
        ) {

            mainViewModel.setProductDetail(responseData)
            mainActivity!!.changeFragment(7)
        }

        override fun onError(t: Throwable) {
            Log.d(TAG, t.message ?: "물품 정보 받아오는 중 통신오류")
        }

        override fun onFailure(code: Int) {
            Log.d(TAG, "onResponse: Error Code $code")
        }


    }
}