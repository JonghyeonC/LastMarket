package com.jphr.lastmarket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jphr.lastmarket.R
import com.jphr.lastmarket.dto.Product
import com.jphr.lastmarket.dto.ProductDTO
import com.jphr.lastmarket.dto.ProductX
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "LatestOrderAdapter_싸피"
class ProductListAdapter(val context: Context) :RecyclerView.Adapter<ProductListAdapter.ProductListHolder>(){
    var list : MutableList<ProductX>? =null

    inner class ProductListHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.iv)
        val title: TextView = itemView.findViewById(R.id.title)
        val price: TextView = itemView.findViewById(R.id.price)
        val liveImage = itemView.findViewById<ImageView>(R.id.live_image)
        val liveText=itemView.findViewById<TextView>(R.id.live_text)
        fun bindInfo(product: ProductX){
            //TODO:image삽입하기
            Glide.with(itemView)
                .load("${product.imgURI}")
                .into(image)
            title.text=product.title
            price.text=product.instantPrice.toString()
            if(product.liveTime!=null){
                liveImage.visibility=View.VISIBLE
                liveText.visibility=View.VISIBLE
            }else{
                liveImage.visibility=View.GONE
                liveText.visibility=View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)

        return ProductListHolder(view)
    }

    override fun onBindViewHolder(holder: ProductListHolder, position: Int) {
//        holder.bind()

        holder.apply {
            list?.get(position)?.let { bindInfo(it) }
            //클릭연결
            itemView.setOnClickListener{
                itemClickListner.onClick(it, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return 10.coerceAtMost(list!!.size)
    }

    //클릭 인터페이스 정의 사용하는 곳에서 만들어준다.
    interface ItemClickListener {
        fun onClick(view: View,  position: Int)
    }
    //클릭리스너 선언
    private lateinit var itemClickListner: ItemClickListener
    //클릭리스너 등록 매소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }

}