package ddwu.com.mobile.basicfiletest

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import ddwu.com.mobile.basicfiletest.databinding.ActivityMainBinding
import java.io.File
import java.lang.System.load

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    val mainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val fileManager: FileManager by lazy {
        FileManager(applicationContext)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        Log.d(TAG, "Internal filesDir: ${filesDir}")
        Log.d(TAG, "Internal cacheDir: ${cacheDir}")
        Log.d(TAG, "External filesDir: ${getExternalFilesDir(null).toString()}")
        Log.d(TAG, "External cacheDir: ${externalCacheDir}")

        Log.d(TAG, fileManager.getImageFileName(resources.getString(R.string.image_url)))
        Log.d(TAG, "file name: ${fileManager.getCurrentTime()}.jpg")


        mainBinding.btnWrite.setOnClickListener { // 쓰기(생성)
            val data = mainBinding.etText.text.toString()
            fileManager.writeText("test.txt",data)
        }

        mainBinding.btnRead.setOnClickListener { // 읽기
            val data = fileManager.readText("test.txt")
            mainBinding.etText.setText(data)
        }


        mainBinding.btnReadInternet.setOnClickListener {
            //fileManager.readInternetImage("https://danonline.kr/snoopym/images/snoopy_spoon.png?crc=3980034464")

        }

        mainBinding.btnWriteImage.setOnClickListener {
            fileManager.writeImage("image.jpg",
                resources.getString(R.string.image_url))
        }

        mainBinding.btnReadImageFile.setOnClickListener {
            fileManager.readImage("image.jpg",
                mainBinding.imageView)
        }

    }


}