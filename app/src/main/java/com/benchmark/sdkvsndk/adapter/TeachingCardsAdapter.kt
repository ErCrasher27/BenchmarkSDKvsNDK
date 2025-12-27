package com.benchmark.sdkvsndk.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benchmark.sdkvsndk.databinding.ContentTeachingCardsBinding
import com.benchmark.sdkvsndk.model.CardData

class TeachingCardsAdapter(private val cards: List<CardData>) :
    RecyclerView.Adapter<TeachingCardsAdapter.ViewHolder>() {

    class ViewHolder(val binding: ContentTeachingCardsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ContentTeachingCardsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cards[position]
        val context = holder.itemView.context

        holder.binding.titleInnerCard.text = context.getString(card.titleRes)
        holder.binding.contentInnerCard.text = context.getString(card.contentRes)
        holder.binding.iconInnerCard.setImageResource(card.iconRes)
    }

    override fun getItemCount() = cards.size
}
