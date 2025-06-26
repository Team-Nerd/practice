package com.example.practice

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kakaomobility.knsdk.guidance.KNGuideStateDelegate
import com.kakaomobility.knsdk.guidance.KNRouteGuideDelegate

import com.example.practice

class NaviActivity : AppCompatActivity(), KNGuidance_GuideStateDelegate {

    lateinit var naviView: KNNaviView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navi)

        naviView = findViewById(R.id.navi_view)

        // status bar 영역까지 사용하기 위한 옵션
        window?.apply {
            statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        requestRoute()
    }
    /**
     * 주행 경로를 요청합니다.
     */
    fun requestRoute() {
        Thread {
            // 출발지와 목적지를 설정합니다.
            val startPoi = KNPOI("현위치",309840,552483,"현위치")
            val goalPoi = KNPOI("목적지",321497,532896,"목적지")

            KNApplication.knsdk.makeTripWithStart(
                aStart = startPoi,
                aGoal = goalPoi,
                aVias = null
            ) { aError, aTrip ->

                // 경로 요청이 성공하면 aError는 Null이 됩니다.
                if (aError == null) {

                }
            }
        }.start()
    }

    fun startGuide(trip: KNTrip?) {
        KNApplication.knsdk.sharedGuidance()?.apply {
            // guidance delegate 등록
            guideStateDelegate = this@NaviActivity
            locationGuideDelegate = this@NaviActivity
            routeGuideDelegate = this@NaviActivity
            safetyGuideDelegate = this@NaviActivity
            voiceGuideDelegate = this@NaviActivity
            citsGuideDelegate = this@NaviActivity

            naviView.initWithGuidance(
                this,
                trip,
                KNRoutePriority.KNRoutePriority_Recommand,
                0
            )
        }
    }


}
