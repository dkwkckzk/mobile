package ddwu.com.mobile.ballviewtest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.AttributeSet
import android.util.Log
import android.util.MonthDisplayHelper
import android.view.View

class BallView : View {

    val TAG = "BallView"

    constructor(context: Context) : super(context) { initialize() }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){ initialize() }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { initialize()}

    fun initialize() {
        paint.color = Color.RED
        paint.isAntiAlias = true
        isStart = true
        ballR = 100f

        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magentometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    }

    lateinit var sensorManager : SensorManager
    lateinit var accelerometer : Sensor
    lateinit var magentometer : Sensor

    val mAccmeterReading = FloatArray(3)
    val mMagnetometerReading = FloatArray(3)

    var pitch: Float = 0f
    var roll: Float = 0f



    val listener : SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {


                    invalidate() // x,y 좌표 변경
                }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }

    }

    fun startListening () {

    }


    fun stopListening() {
    }

    val paint: Paint by lazy {
        Paint()
    }

    var ballX : Float = 0f
    var ballY : Float = 0f
    var ballR : Float = 0f

    var width : Int? = 0
    var height: Int? = 0

    var isStart : Boolean = true

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (isStart) {
            width = canvas?.width
            height = canvas?.height
            ballX = width?.div(2)?.toFloat() ?: 100f
            ballY = height?.div(2)?.toFloat() ?: 100f
            isStart = false
        }

        canvas?.drawCircle(ballX, ballY, ballR, paint)
    }
}