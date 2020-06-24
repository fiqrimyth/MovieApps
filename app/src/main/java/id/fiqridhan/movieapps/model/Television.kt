package id.fiqridhan.movieapps.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject
import java.text.SimpleDateFormat

class Television : Parcelable {
    private var mId = 0
    private var mType: String? = null
    private var mName: String? = null
    private var mRelease: String? = null
    private var mDesc: String? = null
    private var mPhoto: String? = null
    private var mPopularity: String? = null
    private var mVote: String? = null
    fun getmId(): Int {
        return mId
    }

    fun setmId(mId: Int) {
        this.mId = mId
    }

    fun getmType(): String? {
        return mType
    }

    fun setmType(mType: String?) {
        this.mType = mType
    }

    fun getmName(): String? {
        return mName
    }

    fun setmName(mName: String?) {
        this.mName = mName
    }

    fun getmRelease(): String? {
        return mRelease
    }

    fun setmRelease(mRelease: String?) {
        this.mRelease = mRelease
    }

    fun getmDesc(): String? {
        return mDesc
    }

    fun setmDesc(mDesc: String?) {
        this.mDesc = mDesc
    }

    fun getmPhoto(): String? {
        return mPhoto
    }

    fun setmPhoto(mPhoto: String?) {
        this.mPhoto = mPhoto
    }

    fun getmPopularity(): String? {
        return mPopularity
    }

    fun setmPopularity(mPopularity: String?) {
        this.mPopularity = mPopularity
    }

    fun getmVote(): String? {
        return mVote
    }

    fun setmVote(mVote: String?) {
        this.mVote = mVote
    }

    constructor() {}

    // Object Parcelable
    internal constructor(`object`: JSONObject) {
        try {
            @SuppressLint("SimpleDateFormat") val formatter =
                SimpleDateFormat("EEE, dd, mm, yyyy")
            @SuppressLint("SimpleDateFormat") val dateFormat =
                SimpleDateFormat("yyyy-mm-dd")
            val id = `object`.getInt("id")
            val description = `object`.getString("overview")
            val popularity = `object`.getString("popularity")
            val release = `object`.getString("first_air_date")
            val title = `object`.getString("name")
            val url_image = `object`.getString("poster_path")
            val vote = `object`.getString("vote_average")
            mId = id
            mDesc = description
            mPopularity = popularity
            mRelease = formatter.format(dateFormat.parse(release))
            mName = title
            mPhoto = url_image
            mVote = vote
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(mId)
        dest.writeString(mType)
        dest.writeString(mName)
        dest.writeString(mRelease)
        dest.writeString(mDesc)
        dest.writeString(mPhoto)
        dest.writeString(mPopularity)
        dest.writeString(mVote)
    }

    private constructor(`in`: Parcel) {
        mId = `in`.readInt()
        mType = `in`.readString()
        mName = `in`.readString()
        mRelease = `in`.readString()
        mDesc = `in`.readString()
        mPhoto = `in`.readString()
        mPopularity = `in`.readString()
        mVote = `in`.readString()
    }

    companion object {
        val CREATOR: Parcelable.Creator<Television?> = object : Parcelable.Creator<Television?> {
            override fun createFromParcel(source: Parcel): Television? {
                return Television(source)
            }

            override fun newArray(size: Int): Array<Television?> {
                return arrayOfNulls(size)
            }
        }
    }
}
