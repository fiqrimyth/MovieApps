package id.fiqridhan.movieapps.notification

import android.R
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import id.fiqridhan.movieapps.BuildConfig
import id.fiqridhan.movieapps.view.MainActivity
import id.fiqridhan.movieapps.network.ApiClient
import id.fiqridhan.movieapps.network.ApiInterface
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MovieReleaseReceiver : BroadcastReceiver() {
    private var mContext: Context? = null
    override fun onReceive(context: Context, intent: Intent) {
        mContext = context
        releaseMovie
    }

    private fun sendNotification(
        context: Context?,
        title: String
    ) {
        val notificationManager = context!!.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, NOTIFICATION_ID, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val uriTone =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context)
//            .setSmallIcon(R.drawable.ic_movie_black_24dp)
            .setContentTitle(title)
//            .setContentText(context.resources.getString(R.string.message_release_reminder))
            .setContentIntent(pendingIntent)
            .setColor(ContextCompat.getColor(context, R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setSound(uriTone)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.YELLOW
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(
                100,
                200,
                300,
                400,
                500,
                400,
                300,
                200,
                400
            )
            builder.setChannelId(NOTIFICATION_CHANNEL_ID)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private val releaseMovie: Unit
        private get() {
            val apiInterface: ApiInterface? = ApiClient.getClient()?.create(ApiInterface::class.java)
            try {
                val dateToday =
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .format(Date())
                val authorized: Call<String?>? =
                    apiInterface?.getReleaseMovie(BuildConfig.API_KEY, dateToday, dateToday)
                authorized?.enqueue(object : Callback<String?> {
                    override fun onResponse(
                        call: Call<String?>,
                        response: Response<String?>
                    ) {
                        if (response.isSuccessful) {
                            try {
                                val responseObject = JSONObject(response.body())
                                val list = responseObject.getJSONArray("results")
                                for (i in 0 until list.length()) {
                                    val movie = list.getJSONObject(i)
                                    sendNotification(mContext, movie.getString("title"))
                                }
                            } catch (e: Exception) {
                                Log.d("Exception", e.message)
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<String?>,
                        throwable: Throwable
                    ) {
                    }
                })
            } catch (e: Exception) {
                println("error$e")
            }
        }

    fun setReleaseAlarm(context: Context) {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MovieReleaseReceiver::class.java)
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 8
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            NOTIFICATION_ID,
            intent,
            0
        )
        alarmManager?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun cancelAlarm(context: Context) {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(getPendingIntent(context))
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "channel_02"
        private const val NOTIFICATION_ID = 101
        private fun getPendingIntent(context: Context): PendingIntent {
            val intent = Intent(context, MovieReleaseReceiver::class.java)
            return PendingIntent.getBroadcast(
                context, NOTIFICATION_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }
}
