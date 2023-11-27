package ddwu.com.mobile.photomemo

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import ddwu.com.mobile.photomemo.data.MemoDao
import ddwu.com.mobile.photomemo.data.MemoDatabase
import ddwu.com.mobile.photomemo.data.MemoDto
import ddwu.com.mobile.photomemo.databinding.ActivityAddMemoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.jvm.Throws

class AddMemoActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1

    val addMemoBinding by lazy {
        ActivityAddMemoBinding.inflate(layoutInflater)
    }

    val memoDB: MemoDatabase by lazy {
        MemoDatabase.getDatabase(this)
    }

    val memoDao: MemoDao by lazy {
        memoDB.memoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(addMemoBinding.root)

        /*DB 에 memoDto 저장*/
        addMemoBinding.btnAdd.setOnClickListener {
            if (currentPhotoFileName != null) {
                val memo = addMemoBinding.tvAddMemo.text.toString()

                CoroutineScope(Dispatchers.IO).launch {
                    memoDao.insertMemo(MemoDto(0, currentPhotoFileName!!, memo))
                }

                Toast.makeText(this@AddMemoActivity, "New memo is added!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // 카메라 앱을 실행하는 기능 구현
        addMemoBinding.ivAddPhoto.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if(takePictureIntent.resolveActivity(packageManager) != null) {
                val photoFile: File? = try {
                    createImageFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                    null
                }
                if(photoFile != null) {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this, "ddwu.com.mobile.photomemo.fileprovider", photoFile
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
//            Toast.makeText(this@AddMemoActivity, "Click! Implement call taking a picture", Toast.LENGTH_SHORT).show()
        }

        addMemoBinding.btnCancel.setOnClickListener {
            finish()
        }
    }

    lateinit var currentPhotoPath: String   // 현재 이미지 파일의 경로 저장
    var currentPhotoFileName: String? = null    // 현재 이미지 파일명

    /*카메라 앱 호출 관련 기능 구현*/
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        val file = File("${storageDir?.path}/${timeStamp}.jpg")

        currentPhotoFileName = file.name
        currentPhotoPath = file.absolutePath
        return file
    }

    override fun onActivityResult(requestCode: Int, resulteCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resulteCode, data)
        when (requestCode) {
            REQUEST_T
        }
    }
}

