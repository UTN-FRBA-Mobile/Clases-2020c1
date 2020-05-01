package ar.edu.utn.frba.mobile.clases_2020c1.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ar.edu.utn.frba.mobile.clases_2020c1.R
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.utils.ThumbnailItem
import kotlinx.android.synthetic.main.thumbnail_list_item.view.*

class ThumbnailsAdapter(
    private val mContext: Context,
    private val thumbnailItemList: List<ThumbnailItem>,
    private val listener: ThumbnailsAdapterListener
) : RecyclerView.Adapter<ThumbnailsAdapter.MyViewHolder>() {
    private var selectedIndex = 0

    override fun getItemCount() : Int {
        return thumbnailItemList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var thumbnail: ImageView = view.thumbnail
        internal var filterName: TextView = view.filter_name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.thumbnail_list_item, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val thumbnailItem = thumbnailItemList[position]

        holder.thumbnail.setImageBitmap(thumbnailItem.image)

        holder.thumbnail.setOnClickListener {
            listener.onFilterSelected(thumbnailItem.filter)
            selectedIndex = position
            notifyDataSetChanged()
        }

        holder.filterName.text = thumbnailItem.filterName

        if (selectedIndex == position) {
            holder.filterName.setTextColor(ContextCompat.getColor(mContext, R.color.filter_label_selected))
        } else {
            holder.filterName.setTextColor(ContextCompat.getColor(mContext, R.color.filter_label_normal))
        }
    }

    interface ThumbnailsAdapterListener {
        fun onFilterSelected(filter: Filter)
    }
}
