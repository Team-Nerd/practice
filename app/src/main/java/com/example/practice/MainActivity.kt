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
import android.util.Log


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
        Log.d("MainActivity", "checkPermission() 진입")
        when {
            checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED -> {
                Log.d("MainActivity", "위치 권한 없음 → 권한 요청")
                gpsPermissionCheck()
            }

            else -> {
                // 길찾기 SDK 인증
                Log.d("MainActivity", "위치 권한 있음 → knsdkAuth() 호출")
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
        Log.d("MainActivity", "knsdkAuth() 진입")

        KNApplication.knsdk.initializeWithAppKey(
            aAppKey = "31d8cf5f175c769c9ab8ed569571621b",
            aClientVersion = "1.0.0",
            aUserKey = "testUser",
            aLangType = com.kakaomobility.knsdk.KNLanguageType.KNLanguageType_KOREAN
        ) { error ->
            Log.d("MainActivity", "initializeWithAppKey() 콜백 진입")

            runOnUiThread {
                when (error) {
                    null -> {
                        Log.d("MainActivity", "인증 성공")
                        Toast.makeText(applicationContext, "인증 성공하였습니다", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, NaviActivity::class.java))
                    }

                    else -> {
                        Log.e("MainActivity", "인증 실패 - code: ${error.code}, message: ${error.toString()}")
                        Toast.makeText(applicationContext, "인증 실패: ${error.toString()}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        Log.d("MainActivity", "initializeWithAppKey() 호출 완료")

        Log.d("MainActivity", "knsdkAuth() 종료")
    }

    fun initializeWithAppKey(aAppKey: String, aClientVersion: String, aUserKey: String, aLangType: KNLanguageType, aCompletion: (aError: KNError?) -> Unit) {

    }

    override fun onClick(v: View?) {
        Log.d("MainActivity", "onClick 호출됨")
        when (v?.id) {
            R.id.btn_guide -> {
                Log.d("MainActivity", "btn_guide 눌림")
                checkPermission()
            }
        }
    }


}
