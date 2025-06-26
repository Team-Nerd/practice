package com.example.practice

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var btnGuide: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGuide = findViewById(R.id.btn_guide)
        btnGuide.setOnClickListener(this)
    }

    // 버튼 클릭 이벤트
    override fun onClick(v: View?) {

    }
}
