package com.example.urbandictionaryapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.urbandictionaryapp.R
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandictionaryapp.model.ItemDescriptionResponse
import com.example.urbandictionaryapp.model.ItemsModel

class UrbanDictionaryAdapter : RecyclerView.Adapter<UrbanDictionaryAdapter.UrbanDictionaryViewHolder>() {

    var dataSet: ItemDescriptionResponse? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UrbanDictionaryViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout,
                parent,
                false)
        )

    override fun getItemCount() = dataSet?.list?.size?: 0



    override fun onBindViewHolder(holder: UrbanDictionaryViewHolder, position: Int) {
        dataSet?.list?.let {
            holder.onBind(it[position])
        }
    }

    class UrbanDictionaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val tvDescriptionItem: TextView = itemView.findViewById(R.id.tv_urban_description)
        private val tvThumbsUpNumber: TextView = itemView.findViewById(R.id.tv_tup_vote)
        private val tvThumbsDownNumber: TextView = itemView.findViewById(R.id.tv_tdown_vote)

        fun onBind(dataItem: ItemsModel) {
            tvDescriptionItem.text = dataItem.definition.toString()
            tvThumbsDownNumber.text = dataItem.thumbs_down.toString()
            tvThumbsUpNumber.text = dataItem.thumbs_up.toString()
        }



    }
}


