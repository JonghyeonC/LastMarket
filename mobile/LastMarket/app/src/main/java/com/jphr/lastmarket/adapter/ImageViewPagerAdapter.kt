package com.jphr.lastmarket.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.jphr.lastmarket.R


class ImageViewPagerAdapter(context: Context, sliderImage: MutableList<String>) :
    RecyclerView.Adapter<ImageViewPagerAdapter.MyViewHolder>() {
    private val context: Context
    private val sliderImage: MutableList<String>

    init {
        this.context = context
        this.sliderImage = sliderImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_pager_image, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindSliderImage(sliderImage[position])
    }

    override fun getItemCount(): Int {
        return sliderImage.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mImageView: ImageView

        init {
            mImageView = itemView.findViewById(R.id.image)
        }

        fun bindSliderImage(imageURL: String?) {
            Glide.with(context)
                .load(imageURL)
                .into(mImageView)
        }
    }
}