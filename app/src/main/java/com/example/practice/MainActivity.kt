package com.example.practice

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
// ✅ 올바른 import 예시
import com.example.practice.KNApplication
import com.example.practice.KNLanguageType
import com.kakaomobility.knsdk.common.objects.KNError


class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var btnGuide: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGuide = findViewById(R.id.btn_guide)
        btnGuide.setOnClickListener(this)
        checkPermission()
    }

    // 버튼 클릭 이벤트
    /**
     * 버튼 클릭 이벤트
     */

    /**
     * GPS 위치 권한을 확인합니다.
     */
    private fun checkPermission() {
        when {
            checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED -> {
                // GPS 퍼미션 체크
            }

            else -> {
                // 길찾기 SDK 인증
                knsdkAuth()
            }
        }
    }
    /**
     * GPS 위치 권한을 요청합니다.
     */
    fun gpsPermissionCheck() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1234)
    }

    /**
     * GPS 위치 권한 요청의 실패 여부를 확인합니다.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1234 -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // 다시 권한 요청하는 곳으로 돌아갑니다.
                    checkPermission()
                }
            }
        }
    }
    /**
     * 길찾기 SDK 인증을 진행합니다.
     */
    private fun knsdkAuth() {
        KNApplication.knsdk.apply {
            initializeWithAppKey(
                aAppKey = "814cecf78236f15dcd224ea79577eb7d",       // 카카오디벨로퍼스에서 부여 받은 앱 키
                aClientVersion = "1.0.0",                                               // 현재 앱의 클라이언트 버전
                aUserKey = "testUser",                                                  // 사용자 id
                aLangType = KNLanguageType.KNLanguageType_KOREAN,   // 언어 타입
                aCompletion = {
                    // it이 null이 아닐 경우 에러가 발생한 경우입니다.
                    // it.message를 통해 에러 메시지를 확인할 수 있습니다.
                    // it.code를 통해 에러 코드를 확인할 수 있습니다.
                    // ex) Toast.makeText(applicationContext, "인증에 실패하였습니다.\n errorCode: ${it?.code} \n errorMessage: ${it?.message}", Toast.LENGTH_LONG).show()
                    // Toast는 UI를 갱신하는 작업이기 때문에 UIThread에서 동작되도록 해야 합니다.
                    runOnUiThread {
                        if (it != null) {
                            Toast.makeText(applicationContext, "인증에 실패하였습니다", Toast.LENGTH_LONG).show()

                        } else {
                            Toast.makeText(applicationContext, "인증 성공하였습니다", Toast.LENGTH_LONG).show()

                            var intent = Intent(this@MainActivity, NaviActivity::class.java)
                            this@MainActivity.startActivity(intent)
                        }
                    }
                })
        }
    }

    private fun initializeWithAppKey(aAppKey: String, aClientVersion: String, aUserKey: String, aLangType: KNLanguageType, aCompletion: (aError: KNError?) -> Unit) {

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }


}
