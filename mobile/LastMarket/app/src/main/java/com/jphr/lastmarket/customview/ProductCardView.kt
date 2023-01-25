package com.jphr.lastmarket.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jphr.lastmarket.R

class ProductCardView (context: Context, attrs: AttributeSet): LinearLayout(context,attrs) {

     var image : ImageView
     var title: TextView
     var price : TextView


    init {
        val v = View.inflate(context, R.layout.product_card, this)

        image =findViewById(R.id.iv)
        title=findViewById(R.id.title)
        price=findViewById(R.id.price)


        context.theme?.obtainStyledAttributes(
            attrs,
            R.styleable.CardView,
            0, 0)?.apply {
            try {
                setImage(getString(R.styleable.CardView_image))
                setTitle(getString(R.styleable.CardView_title))
                setPrice(getString(R.styleable.CardView_price))

            } finally {
                recycle()
            }
        }

    }

    fun setImage(url: String?){
        Glide.with(context).load(url).into(image)
    }
    fun setTitle(text: String?){
        title.text = text
        onRefresh()
    }

    fun setPrice(text: String?){
        price.text = text
        onRefresh()
    }

    private fun onRefresh(){
        invalidate()
        requestLayout()
    }
}