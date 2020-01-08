package com.derrick.park.recyclerviewdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_sw_list_item.view.*

class SWAdapter(private val swChars: Array<SWChar>) :
    RecyclerView.Adapter<SWAdapter.SWViewHolder>() {

    var onItemClick: ((SWChar) -> Unit)? = null

    // inflates a layout and return the viewHolder object
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SWViewHolder {
        return SWViewHolder.from(parent)
    }

    // tells the recyclerView how many total items we have to display
    override fun getItemCount() = swChars.size

    // binds data with viewHolder
    override fun onBindViewHolder(holder: SWViewHolder, position: Int) {
        holder.bind(swChars[position])
        holder.itemView.setOnClickListener {
            Toast.makeText(it?.context, "Tapped Cell ${swChars[position].name}", Toast.LENGTH_SHORT)
                .show()
            onItemClick?.invoke(swChars[position])
        }
    }

    // ViewHolder
    class SWViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.nameTextView
        private val dataTextView: TextView = itemView.dataTextView

        companion object {
            fun from(parent: ViewGroup): SWViewHolder {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_sw_list_item, parent, false)
                return SWViewHolder(itemView)
            }
        }

        fun bind(item: SWChar) {
            nameTextView.text = item.name
            dataTextView.text = "Height: ${item.height}, Mass: ${item.mass}"
            itemView.setOnClickListener {

            }
        }
    }
}