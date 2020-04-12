package ar.edu.utn.frba.mobile.clases_2020c1.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ar.edu.utn.frba.mobile.clases_2020c1.R
import ar.edu.utn.frba.mobile.clases_2020c1.core.movie.Movie

class MoviesAdapter(private val myDataset: MutableList<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {

    private val VIEWTYPE_CATEGORY: Int = 1
    private val VIEWTYPE_MOVIE: Int = 2

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MoviesAdapter.MyViewHolder {
        val view : View = if(viewType == VIEWTYPE_CATEGORY)
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_listitem_category, parent, false)
        else
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_listitem_movie, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.movie_name).text = myDataset[position].movieName
        if(myDataset[position].moviePoster != null)
            holder.view.findViewById<ImageView>(R.id.movie_poster).setImageResource(myDataset[position].moviePoster!!)
    }

    override fun getItemViewType(position: Int): Int {
        return if(myDataset[position].IsCategory)
            VIEWTYPE_CATEGORY
        else
            VIEWTYPE_MOVIE
    }

    override fun getItemCount() = myDataset.size
}
