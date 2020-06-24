package id.fiqridhan.movieapps.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import id.fiqridhan.movieapps.R
import id.fiqridhan.movieapps.notification.MovieDailyReceiver
import id.fiqridhan.movieapps.notification.MovieReleaseReceiver
import id.fiqridhan.movieapps.utils.SettingPreference
import kotlinx.android.synthetic.main.activity_setting.*
import java.util.*

class SettingActivity : AppCompatActivity() {
    private var mMovieDailyReceiver: MovieDailyReceiver? = null
    private var mMovieReleaseReceiver: MovieReleaseReceiver? = null
    private var mSettingPreference: SettingPreference? = null

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val button =
            findViewById<Button>(R.id.button_change_language)
        if (supportActionBar != null) supportActionBar!!.setTitle("Setting")
        Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_24dp)
        mMovieDailyReceiver = MovieDailyReceiver()
        mMovieReleaseReceiver = MovieReleaseReceiver()
        mSettingPreference = SettingPreference(this)
        setSwitchRelease()
        setSwitchReminder()

        switch_daily_reminder.setOnClickListener(View.OnClickListener {
            if (switch_daily_reminder.isChecked) {
                mMovieDailyReceiver!!.setDailyAlarm(applicationContext)
                mSettingPreference?.dailysReminder(true)
                Toast.makeText(
                    applicationContext,
                    resources.getString(R.string.set_daily_reminder),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                mMovieDailyReceiver!!.cancelAlarm(applicationContext)
                mSettingPreference?.dailysReminder(false)
                Toast.makeText(
                    applicationContext,
                    resources.getString(R.string.cancel_daily_reminder),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        switch_release_today.setOnClickListener(View.OnClickListener {
            if (switch_release_today.isChecked) {
                mMovieReleaseReceiver!!.setReleaseAlarm(applicationContext)
                mSettingPreference!!.releasedReminder(true)
                Toast.makeText(
                    applicationContext,
                    resources.getString(R.string.set_release_reminder),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                mMovieReleaseReceiver!!.cancelAlarm(applicationContext)
                mSettingPreference!!.releasedReminder(false)
                Toast.makeText(
                    applicationContext,
                    resources.getString(R.string.cancel_release_reminder),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        button.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setSwitchReminder() {
        switch_daily_reminder!!.isChecked = mSettingPreference!!.dailyReminder()
    }

    private fun setSwitchRelease() {
        switch_release_today!!.isChecked = mSettingPreference!!.releasedReminder()
    }
}