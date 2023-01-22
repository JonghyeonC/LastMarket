package com.jphr.lastmarket.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.jphr.lastmarket.R

class ProductCardView (context: Context, attrs: AttributeSet): LinearLayout(context,attrs) {
    //커스텀 뷰 안에 들어가는 아이템들
//     var layout : MaterialCardView
     var image : ImageView
     var title: TextView
     var secondText : TextView
//     var supportText: TextView
//     var button1:MaterialButton
//     var button2:MaterialButton

    init {
        val v = View.inflate(context, R.layout.product_card, this)

//        layout = findViewById(R.id.card)
        image =findViewById(R.id.iv)
        title=findViewById(R.id.title)
        secondText=findViewById(R.id.second)
//        supportText=findViewById(R.id.support)
//        button1=findViewById(R.id.button1)
//        button2=findViewById(R.id.button2)

        context.theme?.obtainStyledAttributes(
            attrs,
            R.styleable.CardView,
            0, 0)?.apply {
            try {
                setImage(getString(R.styleable.CardView_image))
                setTitleText(getString(R.styleable.CardView_title))
                setSecondText(getString(R.styleable.CardView_second_text))
                setSupportText(getString(R.styleable.CardView_second_text))
//                setButtonText(getString(R.styleable.CardView_button_text1))
//                setButton2Text(getString(R.styleable.CardView_button_text2))

            } finally {
                recycle()
            }
        }

    }

    fun setImage(url: String?){
        Glide.with(context).load(url).into(image)
    }
    fun setTitleText(text: String?){
        title.text = text
        onRefresh()
    }

    fun setSecondText(text: String?){
        secondText.text = text
        onRefresh()
    }

    fun setSupportText(text: String?){
        secondText.text = text
        onRefresh()
    }
//    fun setButtonText(text: String?){
//        button1.text = text
//        onRefresh()
//    }
//    fun setButton2Text(text: String?){
//        button2.text = text
//        onRefresh()
//    }

    private fun onRefresh(){
        invalidate()
        requestLayout()
    }
}