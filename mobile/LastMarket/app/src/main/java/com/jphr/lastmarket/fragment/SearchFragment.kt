package com.jphr.lastmarket.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.jphr.lastmarket.R
import com.jphr.lastmarket.activity.MainActivity
import com.jphr.lastmarket.adapter.ProductListAdapter
import com.jphr.lastmarket.databinding.FragmentProductListBinding
import com.jphr.lastmarket.databinding.FragmentSearchBinding
import com.jphr.lastmarket.dto.ProductDTO
import com.jphr.lastmarket.util.RecyclerViewDecoration
import java.time.Clock
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "SearchFragment"
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var productDTO: ProductDTO? = null
    private var word: String? = null
    private lateinit var binding:FragmentSearchBinding
    private lateinit var productListAdapter: ProductListAdapter
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity=context as MainActivity
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productDTO = it.getSerializable("products") as ProductDTO?
            word = it.getString("word")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSearchBinding.inflate(inflater,container,false)

        productListAdapter= ProductListAdapter(mainActivity)
        binding.recyclerview.apply {
            productListAdapter.list=productDTO
            layoutManager= GridLayoutManager(context,3)
            adapter=productListAdapter
            addItemDecoration(RecyclerViewDecoration(60,0))
        }
        binding.resultText.text="${word}에 대한 검색결과"
        return binding.root

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}