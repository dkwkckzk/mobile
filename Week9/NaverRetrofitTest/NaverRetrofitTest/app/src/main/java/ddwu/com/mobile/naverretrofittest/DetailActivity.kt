package ddwu.com.mobile.naverretrofittest

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import ddwu.com.mobile.naverretrofittest.databinding.ActivityDetailBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailActivity : AppCompatActivity() {
    val detailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(detailBinding.root)

        val url = intent.getStringExtra("url")

        // '저장' 버튼을 누르면 Glide를 사용하여 이미지를 비트맵으로 로드하고, 내부 저장소에 파일로 저장합니다.
        detailBinding.btnSave.setOnClickListener {
            Glide.with(this)
                .asBitmap()
                .load(url)
                .into(object : CustomTarget<Bitmap>(350, 350) {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA).format(
                            Date()
                        )
                        val fileName = "${timestamp}.jpg"  // 파일 이름 정의
                        openFileOutput(fileName, Context.MODE_PRIVATE).use {
                            resource.compress(Bitmap.CompressFormat.JPEG, 100, it)
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // 이미지 로드가 취소되거나 실패한 경우
                    }
                })
        }

        // '불러오기' 버튼을 누르면 내부 저장소에서 이미지 파일을 불러와 ImageView에 표시합니다.
        detailBinding.btnRead.setOnClickListener {
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA).format(Date())
            val fileName = "${timestamp}.jpg"  // 삭제할 파일 이름 정의
            Glide.with(this)
                .load(File(filesDir, fileName).path)
                .into(detailBinding.imgBookCover)  // ImageView 지정
        }

        // '초기화' 버튼을 누르면 ImageView를 초기 이미지로 설정합니다.
        detailBinding.btnInit.setOnClickListener {
            detailBinding.imgBookCover.setImageResource(R.mipmap.ic_launcher)
        }

        // '삭제' 버튼을 누르면 내부 저장소에서 이미지 파일을 삭제합니다.
        detailBinding.btnRemove.setOnClickListener {
            val fileName = "savedImage.jpg"  // 삭제할 파일 이름 정의
            val file = File(filesDir, fileName)
            if (file.exists()) {
                file.delete()
            }
        }
    }
}