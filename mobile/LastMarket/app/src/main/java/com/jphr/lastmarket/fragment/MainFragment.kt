package com.jphr.lastmarket.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.jphr.lastmarket.activity.LiveBuyActivity
import com.jphr.lastmarket.activity.MainActivity
import com.jphr.lastmarket.adapter.ProductListAdapter
import com.jphr.lastmarket.databinding.FragmentMainBinding
import com.jphr.lastmarket.util.RecyclerViewDecoration

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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentMainBinding.inflate(inflater,container,false)


        //취미별
        productListAdapter= ProductListAdapter(mainActivity)
        binding.recyclerviewCategory.apply {
            productListAdapter.list=productDTO
            var linearLayoutManager= LinearLayoutManager(context)
            linearLayoutManager.orientation=LinearLayoutManager.VERTICAL
            setLayoutManager(linearLayoutManager)
            adapter=productListAdapter
            addItemDecoration(RecyclerViewDecoration(60,0))
        }
        binding.city.text=city
        binding.city2.text=city
        binding.city3.text=city


        //라이브 중인것
        productListAdapter= ProductListAdapter(mainActivity)
        binding.recyclerviewCategory.apply {
            productListAdapter.list=productDTO
            var linearLayoutManager= LinearLayoutManager(context)
            linearLayoutManager.orientation=LinearLayoutManager.VERTICAL
            setLayoutManager(linearLayoutManager)
            adapter=productListAdapter
            addItemDecoration(RecyclerViewDecoration(60,0))
        }

        //새로운 것
        productListAdapter= ProductListAdapter(mainActivity)
        binding.recyclerviewCategory.apply {
            productListAdapter.list=productDTO
            var linearLayoutManager= LinearLayoutManager(context)
            linearLayoutManager.orientation=LinearLayoutManager.VERTICAL
            setLayoutManager(linearLayoutManager)
            adapter=productListAdapter
            addItemDecoration(RecyclerViewDecoration(60,0))
        }


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
}