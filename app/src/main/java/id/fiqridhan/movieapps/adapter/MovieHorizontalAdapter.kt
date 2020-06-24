package id.fiqridhan.movieapps.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import id.fiqridhan.movieapps.R
import id.fiqridhan.movieapps.model.Movie
import id.fiqridhan.movieapps.view.DetailMovieActivity
import java.util.*

class MovieHorizontalAdapter(private val context: Context) :
    RecyclerView.Adapter<MovieHorizontalAdapter.MovieViewHolder>() {
    private var listMovie: ArrayList<Movie>
    private fun getListMovie(): ArrayList<Movie> {
        return listMovie
    }

    fun setListMovie(listMovie: ArrayList<Movie>) {
        this.listMovie = listMovie
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemRow: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_movie_horizontal, parent, false)
        return MovieViewHolder(itemRow)
    }


    override fun getItemCount(): Int {
        return getListMovie().size
    }

    inner class MovieViewHolder(itemView: View) :
        ViewHolder(itemView), View.OnClickListener {
        val imageMovie: ImageView = itemView.findViewById(R.id.image_item_photo)
        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val movieIntent = Intent(context, DetailMovieActivity::class.java)
                movieIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, getListMovie()[position])
                context.startActivity(movieIntent)
            }
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    init {
        listMovie = ArrayList<Movie>()
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500/" + getListMovie()[position].getmPhoto())
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .transform(RoundedCorners(16))
            )
            .into(holder.imageMovie)
    }
}