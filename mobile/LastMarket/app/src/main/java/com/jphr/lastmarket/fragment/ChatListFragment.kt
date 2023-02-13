package com.jphr.lastmarket.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.jphr.lastmarket.activity.MainActivity
import com.jphr.lastmarket.adapter.ChatListAdapter
import com.jphr.lastmarket.databinding.FragmentChatListBinding
import com.jphr.lastmarket.dto.ChatDTO
import com.jphr.lastmarket.dto.ChatListDTO
import com.jphr.lastmarket.service.ChatService
import com.jphr.lastmarket.util.RecyclerViewDecoration
import com.jphr.lastmarket.util.RetrofitCallback
import com.jphr.lastmarket.viewmodel.MainViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "ChatListFragment"
class ChatListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var userId=0L
    private lateinit var binding: FragmentChatListBinding
    private lateinit var chatListAdapter:ChatListAdapter
    private lateinit var mainActivity:MainActivity
    private val mainViewModel by activityViewModels<MainViewModel>()

    var chatList= mutableListOf<ChatListDTO>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity=context as MainActivity
        var prefs=requireActivity().getSharedPreferences("user_info", AppCompatActivity.MODE_PRIVATE)
        userId = prefs.getLong("user_id", 0)

        chatListAdapter=ChatListAdapter(mainActivity)

        chatListAdapter.setItemClickListener(object : ChatListAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int,item:ChatListDTO) {
                Log.d(TAG, "onClick: $item")
                chatListAdapter.list?.get(position)?.productId
                    ?.let {
                        val chatDTO = ChatDTO(
                            "FINISH_BROADCAST",
                            item.otherId,
                            userId.toString(),
                            "",
                            item.productId,
                            userId.toString()
                        )
                        mainViewModel.setChatDTO(chatDTO)
                        mainActivity.changeFragment(8)
                    }

            }
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentChatListBinding.inflate(inflater,container,false)

        val pref: SharedPreferences = requireActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE)
        userId = pref.getLong("user_id", 0)
        var token = pref.getString("token", "null")
        ChatService().getChatList(token!!,chatCallback())
        return binding.root
    }
    inner class chatCallback: RetrofitCallback<MutableList<ChatListDTO>> {
        override fun onSuccess(code: Int, responseData: MutableList<ChatListDTO>, issearch:Boolean, word:String?, category:String?) {
            if(responseData!=null) {
                //취미별 vffzxcvvcc
                chatList=responseData

                binding.recyclerview.apply {
                    chatListAdapter.list=responseData
                    var linearLayoutManager= LinearLayoutManager(context)
                    linearLayoutManager.orientation= LinearLayoutManager.VERTICAL
                    setLayoutManager(linearLayoutManager)
                    adapter=chatListAdapter
                    addItemDecoration(DividerItemDecoration(requireContext(),VERTICAL))
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
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChatListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}