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
import id.fiqridhan.movieapps.model.Television
import id.fiqridhan.movieapps.view.DetailTelevisionActivity
import java.util.*

class TvHorizontalAdapter(private val context: Context) :
    RecyclerView.Adapter<TvHorizontalAdapter.MovieViewHolder>() {
    private var listTelevision: ArrayList<Television>
    private fun getListTelevision(): ArrayList<Television> {
        return listTelevision
    }

    fun setListTelevision(listTelevision: ArrayList<Television>) {
        this.listTelevision = listTelevision
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val itemRow: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_movie_horizontal, parent, false)
        return MovieViewHolder(itemRow)
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int
    ) {
        Glide.with(context)
            .load(
                "https://image.tmdb.org/t/p/w500/" + getListTelevision()[position].getmPhoto()
            )
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .transform(RoundedCorners(16))
            )
            .into(holder.imageTv)
    }

    override fun getItemCount(): Int {
        return getListTelevision().size
    }

    inner class MovieViewHolder(itemView: View) :
        ViewHolder(itemView), View.OnClickListener {
        val imageTv: ImageView = itemView.findViewById(R.id.image_item_photo)
        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val televisionIntent = Intent(context, DetailTelevisionActivity::class.java)
                televisionIntent.putExtra(
                    DetailTelevisionActivity.EXTRA_TV,
                    getListTelevision()[position]
                )
                context.startActivity(televisionIntent)
            }
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    init {
        listTelevision = ArrayList<Television>()
    }
}