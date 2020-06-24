package id.fiqridhan.movieapps.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.fiqridhan.movieapps.R
import id.fiqridhan.movieapps.model.Movie
import id.fiqridhan.movieapps.view.DetailMovieActivity
import java.util.*

class FavoriteMovieAdapter(val context: Context) : RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteViewHolder?>() {
    private val listMovieFavorite: ArrayList<Movie> = ArrayList<Movie>()
    private val movieFavorite: ArrayList<Movie>
        private get() = listMovieFavorite

    fun setListFavoriteMovie(listFavorite: ArrayList<Movie>) {
        if (listFavorite.size > 0) listMovieFavorite.clear()
        listMovieFavorite.addAll(listFavorite)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_movie, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: FavoriteViewHolder,
        position: Int
    ) {
        holder.tvName.text = listMovieFavorite[position].getmName()
        holder.tvRelease.text = listMovieFavorite[position].getmRelease()
        holder.tvDesc.text = listMovieFavorite[position].getmDesc()
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500/" + movieFavorite[position].getmPhoto())
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_image_black_24dp)
            )
            .into(holder.imageMovie)
    }

    inner class FavoriteViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val tvName: TextView = itemView.findViewById(R.id.text_item_name)
        val tvRelease: TextView = itemView.findViewById(R.id.text_item_release)
        val tvDesc: TextView = itemView.findViewById(R.id.text_item_desc)
        val imageMovie: ImageView = itemView.findViewById(R.id.image_item_photo)
        override fun onClick(v: View) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val movieIntent = Intent(context, DetailMovieActivity::class.java)
                movieIntent.putExtra(
                    DetailMovieActivity.EXTRA_MOVIE,
                    movieFavorite[position]
                )
                context.startActivity(movieIntent)
            }
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    override fun getItemCount(): Int {
       return listMovieFavorite.size
    }

}