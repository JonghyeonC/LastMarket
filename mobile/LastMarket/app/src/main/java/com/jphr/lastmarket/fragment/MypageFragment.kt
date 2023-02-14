package com.jphr.lastmarket.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.jphr.lastmarket.R
import com.jphr.lastmarket.activity.MainActivity
import com.jphr.lastmarket.databinding.FragmentMypageBinding

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MypageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MypageFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentMypageBinding
    private lateinit var mainActivity: MainActivity
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
        binding=FragmentMypageBinding.inflate(inflater,container,false)
//
//        binding.sellerProfile.setImageURI()

        binding.edit.setOnClickListener {
            mainActivity.changeFragment(10)
        }
        binding.likeList.setOnClickListener {
            mainActivity.changeFragment(9)
        }
        binding.reviewList.setOnClickListener {
            mainActivity.changeFragment(11)
        }
        binding.sellList.setOnClickListener {
            mainActivity.changeFragment(12)
        }
        binding.buyList.setOnClickListener {
            mainActivity.changeFragment(13)
        }
        return binding.root
    }
    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

}