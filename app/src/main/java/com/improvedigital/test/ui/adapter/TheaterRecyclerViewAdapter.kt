package com.improvedigital.test.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.improvedigital.test.R
import com.improvedigital.test.model.BriefTheaterDescModel

class TheaterRecyclerViewAdapter(
    private val theaterList: MutableList<BriefTheaterDescModel>,
    private val onUpdateRequire: (() -> Unit)?,
    private val onItemClick: (BriefTheaterDescModel) -> Unit
) :
    RecyclerView.Adapter<TheaterRecyclerViewAdapter.TheaterRecyclerViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TheaterRecyclerViewHolder = TheaterRecyclerViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.theater_item, parent, false)
    )

    override fun onBindViewHolder(holder: TheaterRecyclerViewHolder, position: Int) {
        if (position == itemCount - 1)
            onUpdateRequire?.invoke()

        val model = this.theaterList[position]

        holder.apply {
            ratingValueView.text = model.rating.toString()
            establishmentTitleView.text = model.name
            establishmentvicinityView.text = model.vicinity
            establishmentDistanceView.text = model.distance.toString()

            itemView.setOnClickListener {
                this@TheaterRecyclerViewAdapter.onItemClick(model)
            }
        }
    }

    override fun getItemCount(): Int = this.theaterList.size

    fun addItems(briefTheaterDescModels: List<BriefTheaterDescModel>) {
        this.theaterList.addAll(briefTheaterDescModels)
        notifyItemRangeInserted(itemCount - briefTheaterDescModels.size,
            briefTheaterDescModels.size)
    }

    class TheaterRecyclerViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val ratingValueView: TextView = item.findViewById(R.id.rating_value)
        val establishmentTitleView: TextView = item.findViewById(R.id.establishment_title)
        val establishmentvicinityView: TextView = item.findViewById(R.id.establishment_vicinity)
        val establishmentDistanceView: TextView = item.findViewById(R.id.establishment_distance)
    }
}