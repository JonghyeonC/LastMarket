package com.jphr.lastmarket.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.jphr.lastmarket.R
import com.jphr.lastmarket.activity.MainActivity
import com.jphr.lastmarket.adapter.LikeListAdapter
import com.jphr.lastmarket.adapter.TradeListAdapter
import com.jphr.lastmarket.databinding.FragmentBuyListBinding
import com.jphr.lastmarket.databinding.FragmentLikeListBinding
import com.jphr.lastmarket.databinding.FragmentSellListBinding
import com.jphr.lastmarket.dto.LikeListProductDTO
import com.jphr.lastmarket.dto.ProductDetailDTO
import com.jphr.lastmarket.dto.TradeDTO
import com.jphr.lastmarket.dto.tradeListDTO
import com.jphr.lastmarket.service.MyPageService
import com.jphr.lastmarket.service.ProductService
import com.jphr.lastmarket.util.RecyclerViewDecoration
import com.jphr.lastmarket.util.RetrofitCallback
import com.jphr.lastmarket.viewmodel.MainViewModel


/**
 *
 * A simple [Fragment] subclass.
 * Use the [SellListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "BuyListFragment"
class BuyListFragment : Fragment() {
    private lateinit var binding: FragmentBuyListBinding
    private lateinit var productListAdapter: TradeListAdapter
    private lateinit var mainActivity: MainActivity
    private val mainViewModel by activityViewModels<MainViewModel>()
    private var productDTO: MutableList<TradeDTO>? = null
    private lateinit var callback: OnBackPressedCallback
    private var token=""
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity=context as MainActivity
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                mainActivity.changeFragment(2)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var pref=mainActivity.getSharedPreferences("user_info", AppCompatActivity.MODE_PRIVATE)
        token= pref?.getString("token","null").toString()

        MyPageService().getBuyList(token,ProductCallback())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentBuyListBinding.inflate(inflater,container,false)

        productListAdapter= TradeListAdapter(mainActivity)


        productListAdapter.setItemClickListener(object : TradeListAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {
                productListAdapter.list?.trades?.get(position)?.productId
                    ?.let {
                        ProductService().getProductDetail(token,it,ProductDetailCallback())
                    }
            }
        })

        return binding.root
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
    inner class ProductCallback: RetrofitCallback<tradeListDTO> {
        override fun onSuccess(code: Int, responseData: tradeListDTO, issearch:Boolean, word:String?, category:String?) {
            if(responseData!=null) {

                binding.recyclerview.apply {
                    productListAdapter.list=responseData
                    layoutManager= GridLayoutManager(context,3)
                    adapter=productListAdapter
                    addItemDecoration(RecyclerViewDecoration(60,0))
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

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

}