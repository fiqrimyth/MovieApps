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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import id.fiqridhan.movieapps.R
import id.fiqridhan.movieapps.model.Television
import id.fiqridhan.movieapps.view.DetailTelevisionActivity
import java.util.*

class TvAdapter(private val context: Context) :
    RecyclerView.Adapter<TvAdapter.TvViewHolder>() {
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
    ): TvViewHolder {
        val itemRow: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_movie, parent, false)
        return TvViewHolder(itemRow)
    }

    override fun onBindViewHolder(
        holder: TvViewHolder,
        position: Int
    ) {
        holder.tvName.text = getListTelevision()[position].getmName()
        holder.tvRelease.text = getListTelevision()[position].getmRelease()
        holder.tvDesc.text = getListTelevision()[position].getmDesc()
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

    inner class TvViewHolder(itemView: View) :
        ViewHolder(itemView),
        View.OnClickListener {
        val tvName: TextView = itemView.findViewById(R.id.text_item_name)
        val tvRelease: TextView = itemView.findViewById(R.id.text_item_release)
        val tvDesc: TextView = itemView.findViewById(R.id.text_item_desc)
        val imageTv: ImageView = itemView.findViewById(R.id.image_item_photo)
        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val tvIntent = Intent(context, DetailTelevisionActivity::class.java)
                tvIntent.putExtra(
                    DetailTelevisionActivity.EXTRA_TV,
                    getListTelevision()[position]
                )
                context.startActivity(tvIntent)
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