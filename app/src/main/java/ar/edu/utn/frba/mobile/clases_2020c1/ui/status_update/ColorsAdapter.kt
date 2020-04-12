package ar.edu.utn.frba.mobile.clases_2020c1.ui.status_update

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.edu.utn.frba.mobile.clases_2020c1.R

class ColorsAdapter(val listener: ItemClickListener): RecyclerView.Adapter<ColorsAdapter.ViewHolder>() {

    fun getColors(position: Int): IntArray {
        val color = (Color.WHITE - position * 0xa0ff) or Color.BLACK
        val other = Color.BLACK + (color and 0xffff00 shr 8) + (color and 0xff shl 16)
        return arrayOf(color, other).toIntArray()
    }

    override fun getItemCount(): Int {
        return 409
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false), listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.backgroundDrawable.colors = getColors(position) // para crear un gradiente
    }

    class ViewHolder(itemView: View, listener: ItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val backgroundDrawable = GradientDrawable().apply {
            val metrics = itemView.context.resources.displayMetrics
            cornerRadius = metrics.density * 8 // 8dp
            setStroke(1, Color.GRAY)
            orientation = GradientDrawable.Orientation.TL_BR
            itemView.background = this
        }
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    interface ItemClickListener {
        fun onItemClick(position: Int)
    }
}