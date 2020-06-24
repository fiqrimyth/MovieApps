package id.fiqridhan.movieapps.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.fiqridhan.movieapps.R
import id.fiqridhan.movieapps.model.Television
import id.fiqridhan.movieapps.view.DetailTelevisionActivity
import java.util.*

class FavoriteTelevisionAdapter(val context: Context) :
    RecyclerView.Adapter<FavoriteTelevisionAdapter.FavoriteViewHolder>() {
    private val listTelevisionFavorite: ArrayList<Television> = ArrayList<Television>()
    private val getTelevisionFavorite: ArrayList<Television>
        private get() = listTelevisionFavorite

    fun setListFavoriteTelevision(listFavoriteTelevision: ArrayList<Television>) {
        if (listFavoriteTelevision.size > 0) this.listTelevisionFavorite.clear()
        this.listTelevisionFavorite.addAll(listTelevisionFavorite)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_movie, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.tvName.text = listTelevisionFavorite[position].getmName()
        holder.tvRelease.text = listTelevisionFavorite[position].getmRelease()
        holder.tvDesc.text = listTelevisionFavorite[position].getmDesc()
        Glide.with(context)
            .load(
                "https://image.tmdb.org/t/p/w500/" + getTelevisionFavorite[position]
                    .getmPhoto()
            )
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_image_black_24dp)
            )
            .into(holder.imageTv)
    }

    override fun getItemCount(): Int {
        return listTelevisionFavorite.size
    }

    inner class FavoriteViewHolder(itemView: View) :
        ViewHolder(itemView),
        View.OnClickListener {
        val tvName: TextView = itemView.findViewById(R.id.text_item_name)
        val tvRelease: TextView = itemView.findViewById(R.id.text_item_release)
        val tvDesc: TextView = itemView.findViewById(R.id.text_item_desc)
        val imageTv: ImageView = itemView.findViewById(R.id.image_item_photo)
        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val tvIntent = Intent(context, DetailTelevisionActivity::class.java).also {
                    it.putExtra(
                        DetailTelevisionActivity.EXTRA_TV,
                        getTelevisionFavorite[position]
                    )
                }
                context.startActivity(tvIntent)
            }
        }

        init {
            itemView.setOnClickListener(this)
        }
    }
}