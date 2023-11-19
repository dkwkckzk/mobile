package ddwu.com.mobile.naverretrofittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import ddwu.com.mobile.naverretrofittest.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    val detailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(detailBinding.root)

        val url = intent.getStringExtra("url")


        detailBinding.btnSave.setOnClickListener {

        }

        detailBinding.btnRead.setOnClickListener {

        }

        detailBinding.btnInit.setOnClickListener {
            detailBinding.imgBookCover.setImageResource(R.mipmap.ic_launcher)
        }

        detailBinding.btnRemove.setOnClickListener {


        }
    }
}