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
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "LatestOrderAdapter_싸피"
class ProductListAdapter(val context: Context) :RecyclerView.Adapter<ProductListAdapter.ProductListHolder>(){
    var list : ProductDTO? =null

    inner class ProductListHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.iv)
        val title: TextView = itemView.findViewById(R.id.title)
        val price: TextView = itemView.findViewById(R.id.price)
        val liveImage = itemView.findViewById<ImageView>(R.id.live_image)
        val liveText=itemView.findViewById<TextView>(R.id.live_text)
        fun bindInfo(product: Product){
            //TODO:image삽입하기
            Glide.with(itemView)
                .load("${product.imgURI}")
                .into(image)
            title.text=product.title
            price.text=product.instantPrice
            if(product.liveTime!=null){
                liveImage.visibility=View.VISIBLE
                liveText.visibility=View.VISIBLE
            }
//            menuNames.text = if (order.orderCnt - 1 > 0) {
//                "${order.productName} 외 ${order.orderCnt - 1}"
//            } else {
//                "${order.productName}"
//            }
//
//            menuPrice.text = "${order.totalPrice}원"
//            menuDate.text = dateToString(order.orderDate)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
        return ProductListHolder(view)
    }

    override fun onBindViewHolder(holder: ProductListHolder, position: Int) {
//        holder.bind()
        holder.apply {
            bindInfo(list[position])
            //클릭연결
            itemView.setOnClickListener{
                itemClickListner.onClick(it, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return 10.coerceAtMost(list.size)
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

    fun dateToString(date: Date): String {
        val dateFormatter = SimpleDateFormat("yyyy.MM.dd").apply {
            timeZone = TimeZone.getTimeZone("Asia/Seoul")
        }
        val formattedDate = dateFormatter.format(date)
        return formattedDate.toString()
    }

}