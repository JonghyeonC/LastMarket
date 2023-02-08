package com.jphr.lastmarket.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.jphr.lastmarket.activity.MainActivity
import com.jphr.lastmarket.adapter.ChatAdapter
import com.jphr.lastmarket.adapter.ChatSocketAdapter
import com.jphr.lastmarket.databinding.FragmentChatBinding
import com.jphr.lastmarket.dto.ChatDTO
import com.jphr.lastmarket.dto.ChatListDTO
import com.jphr.lastmarket.service.ChatService
import com.jphr.lastmarket.util.RecyclerViewDecoration
import com.jphr.lastmarket.util.RetrofitCallback
import com.jphr.lastmarket.viewmodel.MainViewModel
import io.reactivex.functions.Consumer
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader
import ua.naiksoftware.stomp.dto.StompMessage
import java.util.concurrent.atomic.AtomicBoolean

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "ChatFragment"
class ChatFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentChatBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatSocketAdapter: ChatSocketAdapter
    private val wsServerUrl = "ws://i8d206.p.ssafy.io/api/ws/websocket"
    private var token=""
    private var userId =0L

    private var stompClient: StompClient? = null
    private var headerList= ArrayList<StompHeader>()

    private val mainViewModel by activityViewModels<MainViewModel>()
    private lateinit var chatList:ChatListDTO
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment'

        binding=FragmentChatBinding.inflate(inflater,container,false)
        var prefs=requireActivity().getSharedPreferences("user_info", AppCompatActivity.MODE_PRIVATE)

        token =prefs.getString("token","")!!
        userId=prefs.getLong("user_id",0)

        initAdapter(userId)

        var chatDTO=mainViewModel.getChatDTO()
        if(chatDTO?.seller==userId.toString()){//내가 seller 일 때
            binding.nickname.text=chatDTO.buyer
        }else{
            binding.nickname.text=chatDTO?.seller
        }
        var roomId=chatDTO?.seller+"-"+chatDTO?.buyer+"-"+chatDTO?.roomKey

        initStomp()
        stompClient?.topic("/exchange/chat.exchange/room.${chatDTO?.roomKey}")
            ?.subscribe(Consumer { topicMessage: StompMessage ->
                val str = topicMessage.payload
                val jsonObject = JSONObject(str) as ChatDTO

                var chatDTO=ChatDTO(jsonObject.chatType,jsonObject.buyer
                        ,jsonObject.seller,jsonObject.message,jsonObject.roomKey,jsonObject.sender)

                chatSocketAdapter.list.add(chatDTO)
                chatSocketAdapter.notifyDataSetChanged()

            })



        ChatService().getChatDetail(roomId,chatCallback())

        binding.send.setOnClickListener {//클릭하면 send
            val dto = ChatDTO(
                "TRADE_CHAT",
                chatDTO?.buyer.toString(),
                chatDTO?.seller.toString(),
                binding.chatText.text.toString(),
                chatDTO?.roomKey.toString(),
                userId.toString()
            )

            val mapper = ObjectMapper()
            try {
                val jsonString = mapper.writeValueAsString(dto)
                stompClient!!.send("/send/room.${chatDTO?.roomKey}", jsonString).subscribe()
                Log.d(TAG, "onClick: send OK$jsonString")
            } catch (e: JsonProcessingException) {
                e.printStackTrace()
            }

        }




        return binding.root
    }
    fun initStomp() {
        val isUnexpectedClosed = AtomicBoolean(false)

        //stomp client 생성
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, wsServerUrl)

        stompClient!!.lifecycle().subscribe(Consumer { lifecycleEvent: LifecycleEvent ->
            when(lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> Log.d(TAG, "Stomp connection opened")
                LifecycleEvent.Type.ERROR -> {
                    Log.e(TAG, "initStomp:")
                    Log.e(TAG, "Error", lifecycleEvent.exception)
                    if (lifecycleEvent.exception.message!!.contains("EOF")) {
                        isUnexpectedClosed.set(true)
                    }
                }
                LifecycleEvent.Type.CLOSED -> {
                    Log.d(TAG, "Stomp connection closed")
                    if (isUnexpectedClosed.get()) {
                        /**
                         * EOF Error
                         */
                        /**
                         * EOF Error
                         */
                        initStomp()
                        isUnexpectedClosed.set(false)
                    }
                }
                else ->{
                    Log.d(TAG, "initStomp: else")
                }
            }
        })

        // add Header
        headerList!!.add(StompHeader("Authorization", token))
        stompClient!!.connect(headerList)
    }
    fun initAdapter(userId:Long){
        chatAdapter=ChatAdapter(mainActivity)
        chatSocketAdapter= ChatSocketAdapter(mainActivity)

        chatSocketAdapter.myId=userId
        chatAdapter.myId=userId

    }
    inner class chatCallback: RetrofitCallback<ChatListDTO> {
        override fun onSuccess(code: Int, responseData: ChatListDTO, issearch:Boolean, word:String?, category:String?) {
            if(responseData!=null) {
                //취미별
                chatList=responseData

                binding.recyclerview.apply {
                    chatAdapter.list=responseData
                    var linearLayoutManager= LinearLayoutManager(context)
                    linearLayoutManager.orientation= LinearLayoutManager.HORIZONTAL
                    setLayoutManager(linearLayoutManager)
                    adapter=chatAdapter
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
         * @return A new instance of fragment ChatFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}