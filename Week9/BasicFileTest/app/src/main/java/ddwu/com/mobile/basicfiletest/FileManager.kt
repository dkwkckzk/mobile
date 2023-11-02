package ddwu.com.mobile.basicfiletest

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class FileManager(val context: Context) {
    val TAG = "FileManager"

    fun writeText(fileName: String, data: String) {
        context.openFileOutput("test.txt", AppCompatActivity.MODE_PRIVATE).use { // 다양한 모드가 있음
            it.write(data.toByteArray(), )
        }
    }


    fun readText(fileName: String) : String? {
        val result = StringBuffer()
        context.openFileInput("test.txt").bufferedReader().useLines {
            for (line in it) {
                result.append(line)
            }
        }
        return null
    }

    fun writeImage(fileName: String, imageUrl: String) {
        Glide.with(context)
            .asBitmap()
            .load(imageUrl)
            .into( object: CustomTarget<Bitmap>(350, 350){

        }


            )
    }


    fun readInternetImage(url: String, view: ImageView) {
//        Glide.with(context)
//            .load(url)
//            .into(view)

//        Glide.with(context)
//            .asBitmap()
//            .load(url)
//            .into(
//
//            )
    }


    fun readImage(fileName: String, view: ImageView) {
        val imageFile = File(context.filesDir.toString(),"snoopy_spoon.png")
        val bitmap = BitmapFactory.decodeFile(imageFile.path)
    }



    // Checks if a volume containing external storage is available
    // for read and write.
    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    // Checks if a volume containing external storage is available to at least read.
    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    fun getImageFileName(path: String) : String {
        val fileName = path.slice(IntRange( path.lastIndexOf("/")+1, path.length-1))
        return fileName
    }

    fun getCurrentTime() : String {
        return SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    }
}