package ddwu.com.mobile.sensorlistenertest

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ddwu.com.mobile.sensorlistenertest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivityTag"

    val mainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) { // Sensor값 사용
            Log.d(TAG, "${event?.timestamp} : ${event?.values?.get(0)}")
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) { // 정확도 변경 시 동작
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        mainBinding.btnStart.setOnClickListener {
            lateinit var result : String

            /* 센싱 구현 */
            val sensorType = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            sensorManager.registerListener(
                sensorListener, sensorType,
                SensorManager.SENSOR_DELAY_UI
            )
            Log.d(TAG, result)
        }

        mainBinding.btnStop.setOnClickListener {
            /* 센싱 종료 */
            sensorManager.unregisterListener(sensorListener)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorListener)
    }
}