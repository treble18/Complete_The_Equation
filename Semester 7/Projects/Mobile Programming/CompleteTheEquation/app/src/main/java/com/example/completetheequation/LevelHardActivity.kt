package com.example.completetheequation

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.TypedValue
import android.view.DragEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.activity_level_hard.*
import org.jetbrains.anko.textColor

class LevelHardActivity : AppCompatActivity() {

    lateinit var countdown_timer: CountDownTimer
    var isRunning: Boolean = false;
    var time_in_milli_seconds = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_hard)

        startHard.setOnClickListener {
            if(isRunning == false) {
                time_in_milli_seconds = 60000L
                startTimer(time_in_milli_seconds)
                updateEquation(0)
            }
        }
    }

    private fun startTimer(time_in_seconds: Long) {
        countdown_timer = object : CountDownTimer(time_in_seconds, 1000) {
            override fun onFinish() {
                isRunning = false
                timerHard.text = "0"

                num1Hard.visibility = View.INVISIBLE
                num1_2Hard.visibility = View.INVISIBLE
                num2Hard.visibility = View.INVISIBLE
                num2_2Hard.visibility = View.INVISIBLE
                num3Hard.visibility = View.INVISIBLE
                num3_2Hard.visibility = View.INVISIBLE
                num3_3Hard.visibility = View.INVISIBLE
                num3_4Hard.visibility = View.INVISIBLE
                operatorHard.visibility = View.INVISIBLE

                zeroHard.setClickable(false)
                oneHard.setClickable(false)
                twoHard.setClickable(false)
                threeHard.setClickable(false)
                fourHard.setClickable(false)
                fiveHard.setClickable(false)
                sixHard.setClickable(false)
                sevenHard.setClickable(false)
                eightHard.setClickable(false)
                nineHard.setClickable(false)
                addHard.setClickable(false)
                subtractHard.setClickable(false)
                multiplyHard.setClickable(false)
                divideHard.setClickable(false)
            }

            override fun onTick(p0: Long) {
                time_in_milli_seconds = p0
                val timeLeft = time_in_milli_seconds / 1000

                timerHard.text = "$timeLeft"
            }
        }
        countdown_timer.start()

        isRunning = true
    }

    fun updateEquation(score: Int) {
        if(isRunning) {
            val num1 = findViewById<TextView>(R.id.num1Hard)
            val num1_2 = findViewById<TextView>(R.id.num1_2Hard)
            val num2 = findViewById<TextView>(R.id.num2Hard)
            val num2_2 = findViewById<TextView>(R.id.num2_2Hard)
            val num3 = findViewById<TextView>(R.id.num3Hard)
            val num3_2 = findViewById<TextView>(R.id.num3_2Hard)
            val num3_3 = findViewById<TextView>(R.id.num3_3Hard)
            val num3_4 = findViewById<TextView>(R.id.num3_4Hard)
            val operator = findViewById<TextView>(R.id.operatorHard)
            val submit = findViewById<Button>(R.id.submitHard)
            val randOperator = (1..4).random()
            var randnum1 = 0
            var randnum2 = 0
            var result = 0
            var operators = ""

            var score2 = score
            val scoreValue = findViewById<TextView>(R.id.scoreValueHard)
            scoreValue.text = score2.toString()

            val highScoreValue = findViewById<TextView>(R.id.highScoreValueHard)
            val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
            highScoreValue.text = sharedPref.getInt("key", 0).toString()

            //Operator
            when (randOperator) {
                //Subtract
                2 -> {
                    operators = "-"
                    randnum1 = (1..99).random()
                    do {
                        randnum2 = (1..99).random()
                    } while (randnum1 <= randnum2)
                    result = randnum1 - randnum2
                }
                //Divide
                4 -> {
                    operators = "/"
                    randnum1 = (1..99).random()
                    do {
                        randnum2 = (1..99).random()
                    } while (randnum1 % randnum2 != 0)
                    result = randnum1 / randnum2
                }
                //Add
                1 -> {
                    operators = "+"
                    do {
                        randnum1 = (1..99).random()
                        randnum2 = (1..99).random()
                        result = randnum1 + randnum2
                    } while (result >= 10000)
                }
                //Multiply
                3 -> {
                    operators = "x"
                    do {
                        randnum1 = (1..99).random()
                        randnum2 = (1..99).random()
                        result = randnum1 * randnum2
                    } while (result >= 10000)
                }
            }

            val blankArea = (1..4).random()
            when (blankArea) {
                1 -> {
                    llnum1Hard.setOnDragListener(dragListener)
                    llnum1_2Hard.setOnDragListener(dragListener)
                    num1.text = (randnum1 / 10).toString()
                    num1_2.text = (randnum1 % 10).toString()
                    operator.text = operators
                    num2.text = (randnum2 / 10).toString()
                    num2_2.text = (randnum2 % 10).toString()
                    num3.text = (result / 1000).toString()
                    num3_2.text = ((result % 1000) / 100).toString()
                    num3_3.text = ((result % 100) / 10).toString()
                    num3_4.text = (result % 10).toString()

                    //Set Visible
                    operator.visibility = View.VISIBLE
                    num2.visibility = View.VISIBLE
                    num2_2.visibility = View.VISIBLE
                    num3.visibility = View.VISIBLE
                    num3_2.visibility = View.VISIBLE
                    num3_3.visibility = View.VISIBLE
                    num3_4.visibility = View.VISIBLE
                }
                2 -> {
                    llCenterHard.setOnDragListener(dragListener)
                    num1.text = (randnum1 / 10).toString()
                    num1_2.text = (randnum1 % 10).toString()
                    operator.text = operators
                    num2.text = (randnum2 / 10).toString()
                    num2_2.text = (randnum2 % 10).toString()
                    num3.text = (result / 1000).toString()
                    num3_2.text = ((result % 1000) / 100).toString()
                    num3_3.text = ((result % 100) / 10).toString()
                    num3_4.text = (result % 10).toString()

                    //Set Visible
                    num1.visibility = View.VISIBLE
                    num1_2.visibility = View.VISIBLE
                    num2.visibility = View.VISIBLE
                    num2_2.visibility = View.VISIBLE
                    num3.visibility = View.VISIBLE
                    num3_2.visibility = View.VISIBLE
                    num3_3.visibility = View.VISIBLE
                    num3_4.visibility = View.VISIBLE
                }
                3 -> {
                    llnum2Hard.setOnDragListener(dragListener)
                    llnum2_2Hard.setOnDragListener(dragListener)
                    num1.text = (randnum1 / 10).toString()
                    num1_2.text = (randnum1 % 10).toString()
                    operator.text = operators
                    num2.text = (randnum2 / 10).toString()
                    num2_2.text = (randnum2 % 10).toString()
                    num3.text = (result / 1000).toString()
                    num3_2.text = ((result % 1000) / 100).toString()
                    num3_3.text = ((result % 100) / 10).toString()
                    num3_4.text = (result % 10).toString()

                    //Set Visible
                    num1.visibility = View.VISIBLE
                    num1_2.visibility = View.VISIBLE
                    operator.visibility = View.VISIBLE
                    num3.visibility = View.VISIBLE
                    num3_2.visibility = View.VISIBLE
                    num3_3.visibility = View.VISIBLE
                    num3_4.visibility = View.VISIBLE
                }
                4 -> {
                    llnum3Hard.setOnDragListener(dragListener)
                    llnum3_2Hard.setOnDragListener(dragListener)
                    llnum3_3Hard.setOnDragListener(dragListener)
                    llnum3_4Hard.setOnDragListener(dragListener)
                    num1.text = (randnum1 / 10).toString()
                    num1_2.text = (randnum1 % 10).toString()
                    operator.text = operators
                    num2.text = (randnum2 / 10).toString()
                    num2_2.text = (randnum2 % 10).toString()
                    num3.text = (result / 1000).toString()
                    num3_2.text = ((result % 1000) / 100).toString()
                    num3_3.text = ((result % 100) / 10).toString()
                    num3_4.text = (result % 10).toString()

                    //Set Visible
                    num1.visibility = View.VISIBLE
                    num1_2.visibility = View.VISIBLE
                    operator.visibility = View.VISIBLE
                    num2.visibility = View.VISIBLE
                    num2_2.visibility = View.VISIBLE
                }
            }

            llBottomHard.setOnDragListener(dragListener)

            submit.setOnClickListener {
                when (blankArea) {
                    1 -> {
                        if (llnum1Hard.getChildAt(1) != null && llnum1_2Hard.getChildAt(1) != null) {
                            val tempID = llnum1Hard.getChildAt(1).id.toString()
                            val tempID2 = llnum1_2Hard.getChildAt(1).id.toString()
                            val temp = findViewById<TextView>(
                                resources.getIdentifier(
                                    tempID,
                                    "id",
                                    this.getPackageName()
                                )
                            )
                            val temp2 = findViewById<TextView>(
                                resources.getIdentifier(
                                    tempID2,
                                    "id",
                                    this.getPackageName()
                                )
                            )
                            if (num1.text == temp.text && num1_2.text == temp2.text) {
                                score2++
                                llnum1Hard.removeView(llnum1Hard.getChildAt(1))
                                llnum1_2Hard.removeView(llnum1_2Hard.getChildAt(1))
                                //                            llBottomHard.addView(temp)
                                //                            llBottomHard.addView(temp2)
                                llBottomHard.removeAllViews()
                                llBottomHard.addView(zeroHard)
                                llBottomHard.addView(oneHard)
                                llBottomHard.addView(twoHard)
                                llBottomHard.addView(threeHard)
                                llBottomHard.addView(fourHard)
                                llBottomHard.addView(fiveHard)
                                llBottomHard.addView(sixHard)
                                llBottomHard.addView(sevenHard)
                                llBottomHard.addView(eightHard)
                                llBottomHard.addView(nineHard)
                                llBottomHard.addView(addHard)
                                llBottomHard.addView(subtractHard)
                                llBottomHard.addView(multiplyHard)
                                llBottomHard.addView(divideHard)

                                num1.visibility = View.INVISIBLE
                                num1_2.visibility = View.INVISIBLE
                                num2.visibility = View.INVISIBLE
                                num2_2.visibility = View.INVISIBLE
                                num3.visibility = View.INVISIBLE
                                num3_2.visibility = View.INVISIBLE
                                num3_3.visibility = View.INVISIBLE
                                num3_4.visibility = View.INVISIBLE
                                operator.visibility = View.INVISIBLE
                                if (score2 > sharedPref.getInt("key", 0)) {
                                    with(sharedPref.edit()) {
                                        putInt("key", score2)
                                        apply()
                                    }
                                }
                                updateEquation(score2)
                                Toast.makeText(this, "Correct Answer", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show()
                        }
                    }
                    2 -> {
                        if (llCenterHard.getChildAt(1) != null) {
                            val tempID = llCenterHard.getChildAt(1).id.toString()
                            val temp = findViewById<TextView>(
                                resources.getIdentifier(
                                    tempID,
                                    "id",
                                    this.getPackageName()
                                )
                            )
                            if (operator.text == temp.text) {
                                score2++
                                llCenterHard.removeView(llCenterHard.getChildAt(1))
                                //                            llBottomHard.addView(temp)
                                llBottomHard.removeAllViews()
                                llBottomHard.addView(zeroHard)
                                llBottomHard.addView(oneHard)
                                llBottomHard.addView(twoHard)
                                llBottomHard.addView(threeHard)
                                llBottomHard.addView(fourHard)
                                llBottomHard.addView(fiveHard)
                                llBottomHard.addView(sixHard)
                                llBottomHard.addView(sevenHard)
                                llBottomHard.addView(eightHard)
                                llBottomHard.addView(nineHard)
                                llBottomHard.addView(addHard)
                                llBottomHard.addView(subtractHard)
                                llBottomHard.addView(multiplyHard)
                                llBottomHard.addView(divideHard)

                                num1.visibility = View.INVISIBLE
                                num1_2.visibility = View.INVISIBLE
                                num2.visibility = View.INVISIBLE
                                num2_2.visibility = View.INVISIBLE
                                num3.visibility = View.INVISIBLE
                                num3_2.visibility = View.INVISIBLE
                                num3_3.visibility = View.INVISIBLE
                                num3_4.visibility = View.INVISIBLE
                                operator.visibility = View.INVISIBLE
                                if (score2 > sharedPref.getInt("key", 0)) {
                                    with(sharedPref.edit()) {
                                        putInt("key", score2)
                                        apply()
                                    }
                                }
                                updateEquation(score2)
                                Toast.makeText(this, "Correct Answer", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show()
                        }
                    }
                    3 -> {
                        if (llnum2Hard.getChildAt(1) != null && llnum2_2Hard.getChildAt(1) != null) {
                            val tempID = llnum2Hard.getChildAt(1).id.toString()
                            val tempID2 = llnum2_2Hard.getChildAt(1).id.toString()
                            val temp = findViewById<TextView>(
                                resources.getIdentifier(
                                    tempID,
                                    "id",
                                    this.getPackageName()
                                )
                            )
                            val temp2 = findViewById<TextView>(
                                resources.getIdentifier(
                                    tempID2,
                                    "id",
                                    this.getPackageName()
                                )
                            )
                            if (num2.text == temp.text && num2_2.text == temp2.text) {
                                score2++
                                llnum2Hard.removeView(llnum2Hard.getChildAt(1))
                                llnum2_2Hard.removeView(llnum2_2Hard.getChildAt(1))
                                //                            llBottomHard.addView(temp)
                                //                            llBottomHard.addView(temp2)
                                llBottomHard.removeAllViews()
                                llBottomHard.addView(zeroHard)
                                llBottomHard.addView(oneHard)
                                llBottomHard.addView(twoHard)
                                llBottomHard.addView(threeHard)
                                llBottomHard.addView(fourHard)
                                llBottomHard.addView(fiveHard)
                                llBottomHard.addView(sixHard)
                                llBottomHard.addView(sevenHard)
                                llBottomHard.addView(eightHard)
                                llBottomHard.addView(nineHard)
                                llBottomHard.addView(addHard)
                                llBottomHard.addView(subtractHard)
                                llBottomHard.addView(multiplyHard)
                                llBottomHard.addView(divideHard)

                                num1.visibility = View.INVISIBLE
                                num1_2.visibility = View.INVISIBLE
                                num2.visibility = View.INVISIBLE
                                num2_2.visibility = View.INVISIBLE
                                num3.visibility = View.INVISIBLE
                                num3_2.visibility = View.INVISIBLE
                                num3_3.visibility = View.INVISIBLE
                                num3_4.visibility = View.INVISIBLE
                                operator.visibility = View.INVISIBLE
                                if (score2 > sharedPref.getInt("key", 0)) {
                                    with(sharedPref.edit()) {
                                        putInt("key", score2)
                                        apply()
                                    }
                                }
                                updateEquation(score2)
                                Toast.makeText(this, "Correct Answer", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show()
                        }
                    }
                    4 -> {
                        if (llnum3Hard.getChildAt(1) != null && llnum3_2Hard.getChildAt(1) != null && llnum3_3Hard.getChildAt(
                                1
                            ) != null && llnum3_4Hard.getChildAt(1) != null
                        ) {
                            val tempID = llnum3Hard.getChildAt(1).id.toString()
                            val tempID2 = llnum3_2Hard.getChildAt(1).id.toString()
                            val tempID3 = llnum3_3Hard.getChildAt(1).id.toString()
                            val tempID4 = llnum3_4Hard.getChildAt(1).id.toString()
                            val temp = findViewById<TextView>(
                                resources.getIdentifier(
                                    tempID,
                                    "id",
                                    this.getPackageName()
                                )
                            )
                            val temp2 = findViewById<TextView>(
                                resources.getIdentifier(
                                    tempID2,
                                    "id",
                                    this.getPackageName()
                                )
                            )
                            val temp3 = findViewById<TextView>(
                                resources.getIdentifier(
                                    tempID3,
                                    "id",
                                    this.getPackageName()
                                )
                            )
                            val temp4 = findViewById<TextView>(
                                resources.getIdentifier(
                                    tempID4,
                                    "id",
                                    this.getPackageName()
                                )
                            )
                            if (num3.text == temp.text && num3_2.text == temp2.text && num3_3.text == temp3.text && num3_4.text == temp4.text) {
                                score2++
                                llnum3Hard.removeView(llnum3Hard.getChildAt(1))
                                llnum3_2Hard.removeView(llnum3_2Hard.getChildAt(1))
                                llnum3_3Hard.removeView(llnum3_3Hard.getChildAt(1))
                                llnum3_4Hard.removeView(llnum3_4Hard.getChildAt(1))
                                //                            llBottomHard.addView(temp)
                                //                            llBottomHard.addView(temp2)
                                //                            llBottomHard.addView(temp3)
                                llBottomHard.removeAllViews()
                                llBottomHard.addView(zeroHard)
                                llBottomHard.addView(oneHard)
                                llBottomHard.addView(twoHard)
                                llBottomHard.addView(threeHard)
                                llBottomHard.addView(fourHard)
                                llBottomHard.addView(fiveHard)
                                llBottomHard.addView(sixHard)
                                llBottomHard.addView(sevenHard)
                                llBottomHard.addView(eightHard)
                                llBottomHard.addView(nineHard)
                                llBottomHard.addView(addHard)
                                llBottomHard.addView(subtractHard)
                                llBottomHard.addView(multiplyHard)
                                llBottomHard.addView(divideHard)

                                num1.visibility = View.INVISIBLE
                                num1_2.visibility = View.INVISIBLE
                                num2.visibility = View.INVISIBLE
                                num2_2.visibility = View.INVISIBLE
                                num3.visibility = View.INVISIBLE
                                num3_2.visibility = View.INVISIBLE
                                num3_3.visibility = View.INVISIBLE
                                num3_4.visibility = View.INVISIBLE
                                operator.visibility = View.INVISIBLE
                                if (score2 > sharedPref.getInt("key", 0)) {
                                    with(sharedPref.edit()) {
                                        putInt("key", score2)
                                        apply()
                                    }
                                }
                                updateEquation(score2)
                                Toast.makeText(this, "Correct Answer", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            //Set Long CLick Listeners
            zeroHard.setOnLongClickListener {
                val clipText = "You added Zero"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            oneHard.setOnLongClickListener {
                val clipText = "You added One"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            twoHard.setOnLongClickListener {
                val clipText = "You added Two"

                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            threeHard.setOnLongClickListener {
                val clipText = "You added Three"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            fourHard.setOnLongClickListener {
                val clipText = "You added Four"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            fiveHard.setOnLongClickListener {
                val clipText = "You added Five"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            sixHard.setOnLongClickListener {
                val clipText = "You added Six"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            sevenHard.setOnLongClickListener {
                val clipText = "You added Seven"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            eightHard.setOnLongClickListener {
                val clipText = "You added Eight"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            nineHard.setOnLongClickListener {
                val clipText = "You added Nine"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            addHard.setOnLongClickListener {
                val clipText = "You added Add"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            subtractHard.setOnLongClickListener {
                val clipText = "You added Subtract"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            multiplyHard.setOnLongClickListener {
                val clipText = "You added Multiply"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            divideHard.setOnLongClickListener {
                val clipText = "You added Divide"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
        }
    }

    val dragListener = View.OnDragListener { view, event ->
        when(event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                view.invalidate()
                true
            }
            DragEvent.ACTION_DRAG_LOCATION -> true
            DragEvent.ACTION_DRAG_EXITED -> {
                view.invalidate()
                true
            }
            DragEvent.ACTION_DROP -> {
                val item = event.clipData.getItemAt(0)
                val dragData = item.text
                // Toast.makeText(this, dragData, Toast.LENGTH_SHORT).show()

                view.invalidate()

                val v = event.localState as TextView
                val owner = v.parent as ConstraintLayout

                owner.removeView(v)

                val destination = view as ConstraintLayout

                if(owner == llBottomHard && destination != llBottomHard) {
                    val newView = TextView(this)
                    newView.setId(View.generateViewId())
                    newView.text = v.text
                    newView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30F)
                    newView.textColor = Color.parseColor("#ffffff")

                    newView.width = 90
                    newView.height = 120
                    newView.typeface = v.typeface
                    newView.background = v.background
                    newView.textAlignment = v.textAlignment

                    newView.setOnLongClickListener {
                        val clipText = "You added Seven"
                        val item = ClipData.Item(clipText)
                        val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                        val data = ClipData(clipText, mimeTypes, item)

                        val dragShadowBuilder = View.DragShadowBuilder(it)
                        it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                        it.visibility = View.INVISIBLE
                        true
                    }
                    owner.addView(newView)

                    val constraintSet = ConstraintSet()
                    constraintSet.connect(newView.id, ConstraintSet.LEFT, owner.id, ConstraintSet.LEFT)
                    constraintSet.connect(newView.id, ConstraintSet.RIGHT, owner.id, ConstraintSet.RIGHT)
                    constraintSet.setHorizontalBias(newView.id, 0.041f)

                    constraintSet.connect(newView.id, ConstraintSet.TOP, owner.id, ConstraintSet.TOP)
                    constraintSet.connect(newView.id, ConstraintSet.BOTTOM, owner.id, ConstraintSet.BOTTOM)
                    constraintSet.setVerticalBias(newView.id, 0.542f)

                    constraintSet.constrainWidth(newView.id, 90)
                    constraintSet.constrainHeight(newView.id, 120)

                    constraintSet.applyTo(owner)

                    newView.visibility = View.VISIBLE
                }

                destination.addView(v)

                v.visibility = View.VISIBLE

                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                val v = event.localState as View
                v.visibility = View.VISIBLE
                view.invalidate()
                true
            }

            else -> false
        }
    }
}