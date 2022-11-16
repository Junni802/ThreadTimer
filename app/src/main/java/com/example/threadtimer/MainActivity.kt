package com.example.threadtimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.example.threadtimer.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
	val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
	var total = 0	// 전체 시간을 저장할 변수
	var started = false	// 시작 여부를 체크할 변수
	var mili = 0
	var stop = "break"
	var conti = "continue"

	var handler = object : Handler(Looper.getMainLooper()){
		override fun handleMessage(msg: Message) {
			val minute = String.format("%02d", total / 60)
			val second = String.format("%02d", total % 60)
			val milisecond = String.format("%d", mili % 10)
			binding.txtTimer.text = "$minute:$second.$milisecond"
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		binding.btnStart.setOnClickListener {
			if(started == true){
				started = false
				binding.btnStart.text = "계속"
			}else {
				started = true
				binding.btnStart.text = "멈춤"
			}
			thread(start = true){

				while (started){
					Thread.sleep(100)
					mili += 1
						if (mili % 10 == 9) {
							total += 1
							// handler를 호출하는 함수로 값은 큰 의미가 없음

						}
					handler?.sendEmptyMessage(0)
					}
				}
			}

			/*
				for(i in 0.. 5){
					for(j in 0..9){
						if(i == 2 && j == 4){
							break
						}
						for(k in 0..5){
							for(l in 0..9){
								Thread.sleep(1000)
								Log.d("timer", "${i}${j}:${k}${l}")
							}
						}
					}
				}
			 */

		binding.btnStop.setOnClickListener {
			binding.txtTimer.text="00:00.0"
			binding.btnStart.text = "시작"
			started = false
			total = 0
		}
	}
}
