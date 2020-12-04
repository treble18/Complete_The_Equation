package com.example.completetheequation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread(Runnable {
            level1.setOnClickListener() {
                Thread(Runnable {
                    intent = Intent(this, LevelEasyActivity::class.java)
                    startActivity(intent)
                }).start()
            }

            level2.setOnClickListener() {
                Thread(Runnable {
                    intent = Intent(this, LevelMediumActivity::class.java)
                    startActivity(intent)
                }).start()
            }

            level3.setOnClickListener() {
                Thread(Runnable {
                    intent = Intent(this, LevelHardActivity::class.java)
                    startActivity(intent)
                }).start()
            }
        }).start()
    }
}