package com.jphr.lastmarket.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.jphr.lastmarket.R
import com.jphr.lastmarket.activity.MainActivity
import com.jphr.lastmarket.databinding.FragmentDetailBinding
import com.jphr.lastmarket.dto.Product
import com.jphr.lastmarket.dto.ProductDTO
import com.jphr.lastmarket.dto.ProductDetailDTO
import com.jphr.lastmarket.service.ProductService
import com.jphr.lastmarket.util.RetrofitCallback
import com.jphr.lastmarket.viewmodel.MainViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "DetailFragment"
class DetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val mainViewModel by activityViewModels<MainViewModel>()
    lateinit var binding:FragmentDetailBinding
    private lateinit var mainActivity: MainActivity
    var productId: Long=0

    init {
        productId= mainViewModel.getProductId()!!
        ProductService().getProductDetail(productId,ProductDetailCallback())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity=context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        Log.d(TAG, "onCreate:$productId ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var data=mainViewModel.getProductDetail()
        var state= data?.dealState

        //공통기능 img, title, lifestyle, content,sellerinfos
        binding=FragmentDetailBinding.inflate(inflater,container,false)

        binding.title.text=data?.title
        binding.lifestyle.text=data?.lifestyle
        binding.content.text=data?.content


        if(state=="default"){// 라이브 O 아직 시작안함

        }else if(state=="onbroadcast"){ // 라이브 중

        }else if(state=="afterbroadcast"){//라이브 후 낙찰 안됨 & 라이브 X인 경우

        }else if(state=="reservation") {//라이브 후 낙찰 시

        }else if(state=="finish"){  //완전히 거래가 완료된 상태

        }

        // Inflate the layout for this fragment
        binding.instantPrice.text=data?.instantPrice.toString()
        binding.startPrice.text=data?.startingPrice.toString()


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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
        }

        override fun onError(t: Throwable) {
            Log.d(TAG, t.message ?: "물품 정보 받아오는 중 통신오류")
        }

        override fun onFailure(code: Int) {
            Log.d(TAG, "onResponse: Error Code $code")
        }


    }
}