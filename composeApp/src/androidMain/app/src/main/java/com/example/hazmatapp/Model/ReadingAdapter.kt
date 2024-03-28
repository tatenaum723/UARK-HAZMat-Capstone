package com.example.hazmatapp.Model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hazmatapp.R

class ReadingAdapter(private val readingsList: List<Reading>, private val listener: OnReadingClickListener): RecyclerView.Adapter<ReadingAdapter.ReadingHolder>() {

    interface OnReadingClickListener {
        fun onReadingClick(position: Int)
    }

    class ReadingHolder(itemView: View, private val listener: OnReadingClickListener) : RecyclerView.ViewHolder(itemView) {
        val rName: TextView = itemView.findViewById(R.id.name) // Task name in each item in recycler
        val rLocation: TextView = itemView.findViewById(R.id.location)
        val rDate: TextView = itemView.findViewById(R.id.date)
        val rTime: TextView = itemView.findViewById(R.id.time)

        init {
            itemView.setOnClickListener { // Sets the onClickListener for the items
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onReadingClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadingHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.reading_card,parent,false)
        return ReadingHolder(itemView, listener)
    }

    override fun getItemCount(): Int {
        return readingsList.size
    }

    override fun onBindViewHolder(holder: ReadingHolder, position: Int) {
        val currentReading = readingsList[position]
        holder.rName.text = currentReading.name
        holder.rLocation.text = currentReading.location
        holder.rDate.text = currentReading.date
        holder.rTime.text = currentReading.time
    }
}

