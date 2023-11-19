package ddwu.com.mobile.alarmtest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import kotlin.coroutines.coroutineContext

class MyBroadcastReceiver: BroadcastReceiver() { // 여기에 noti를 띄우는 코드로 변경하면 됨
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "휴식중...", Toast.LENGTH_SHORT).show()
        val notiId = intent?.getIntExtra("NOTI_ID", 0)
        Log.d("AlertBroadcastReceiver", "Notification ID: ${notiId}")
    }

}

// 채널 등록 코드 필요
// 알림만 띄우면 됨
// 터치해서 띄우기 필요없음
// 알림만 필요!!
// 권한 설정 해주기!!
// 두번째꺼도 해보기!!!꼭!!!!!!!!!!
// 기획서 검토하는 시간을 가져야 함. 이번주 일요일까지 기획서 작성해서 스마트 클래스에 제출
// A4 용지에 대충 화면이랑, 기능. 지도, openAPI, DB는 필수 주제는 자유!!!