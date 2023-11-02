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
            val writeData = mainBinding.etText.text.toString()
            fileManager.writeText("test.txt",writeData)

        }

        mainBinding.btnRead.setOnClickListener { // 읽기
            val result = fileManager.readText("test.txt")
            mainBinding.etText.setText(result.toString())
        }


        mainBinding.btnReadInternet.setOnClickListener {
            fileManager.readInternetImage("https://danonline.kr/snoopym/images/snoopy_spoon.png?crc=3980034464")

        }

        mainBinding.btnWriteImage.setOnClickListener {

        }

        mainBinding.btnReadImageFile.setOnClickListener {
            val imageFile = File(filesDir.toString(),"snoopy_spoon.png")
            val bitmap = BitmapFactory.decodeFile(imageFile.path)
            mainBinding.imageView.setImageBitmap(bitmap)

        }

    }


}