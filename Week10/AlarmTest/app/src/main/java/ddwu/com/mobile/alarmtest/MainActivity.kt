package ddwu.com.mobile.alarmtest

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import ddwu.com.mobile.alarmtest.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    val mainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val requestId  =100
    val alarmManager by lazy {
        getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        mainBinding.btnOneShot.setOnClickListener {
            val intent = Intent(this, MyBroadcastReceiver::class.java)
            val alramIntent = PendingIntent.getBroadcast(this, 0, intent, )

        }

        mainBinding.btnRepeat.setOnClickListener {

        }

        mainBinding.btnStopAlarm.setOnClickListener {

        }

    }
}