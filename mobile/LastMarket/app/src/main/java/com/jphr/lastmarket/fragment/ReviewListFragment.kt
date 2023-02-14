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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jphr.lastmarket.R
import com.jphr.lastmarket.activity.MainActivity
import com.jphr.lastmarket.adapter.LikeListAdapter
import com.jphr.lastmarket.adapter.ReviewAdapter
import com.jphr.lastmarket.databinding.FragmentCreateProductBinding
import com.jphr.lastmarket.databinding.FragmentReviewListBinding
import com.jphr.lastmarket.dto.LikeListProductDTO
import com.jphr.lastmarket.dto.ReviewListDTO
import com.jphr.lastmarket.service.MyPageService
import com.jphr.lastmarket.util.RecyclerViewDecoration
import com.jphr.lastmarket.util.RetrofitCallback

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReviewListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "ReviewListFragment"
class ReviewListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentReviewListBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var callback: OnBackPressedCallback

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
        binding= FragmentReviewListBinding.inflate(inflater, container, false)
        reviewAdapter= ReviewAdapter(mainActivity)
        
        var pref=mainActivity.getSharedPreferences("user_info", AppCompatActivity.MODE_PRIVATE)
        var token= pref?.getString("token","null").toString()

        MyPageService().getReviewList(ReviewCallback())

        return binding.root
    }
    inner class ReviewCallback: RetrofitCallback<MutableList<ReviewListDTO>> {
        override fun onSuccess(code: Int, responseData: MutableList<ReviewListDTO>, issearch:Boolean, word:String?, category:String?) {
            if(responseData!=null) {

                binding.recyclerview.apply {
                    reviewAdapter.list=responseData
                    var linearLayoutManager = LinearLayoutManager(context)
                    linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    setLayoutManager(linearLayoutManager)
                    adapter = reviewAdapter
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