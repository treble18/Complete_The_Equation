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
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.activity_level_easy.*
import kotlinx.android.synthetic.main.activity_level_medium.*
import org.jetbrains.anko.textColor

class LevelEasyActivity : AppCompatActivity() {

    lateinit var countdown_timer: CountDownTimer
    var isRunning: Boolean = false;
    var time_in_milli_seconds = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_easy)

        start.setOnClickListener {
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
                timer.text = "0"

                num1.visibility = View.INVISIBLE
                num2.visibility = View.INVISIBLE
                num3.visibility = View.INVISIBLE
                num3_2.visibility = View.INVISIBLE
                operator.visibility = View.INVISIBLE

                zero.setClickable(false)
                one.setClickable(false)
                two.setClickable(false)
                three.setClickable(false)
                four.setClickable(false)
                five.setClickable(false)
                six.setClickable(false)
                seven.setClickable(false)
                eight.setClickable(false)
                nine.setClickable(false)
                add.setClickable(false)
                subtract.setClickable(false)
                multiply.setClickable(false)
                divide.setClickable(false)
            }

            override fun onTick(p0: Long) {
                time_in_milli_seconds = p0
                val timeLeft = time_in_milli_seconds / 1000

                timer.text = "$timeLeft"
            }
        }
        countdown_timer.start()

        isRunning = true
    }

    fun updateEquation(score: Int) {
        if(isRunning) {
            val num1 = findViewById<TextView>(R.id.num1)
            val num2 = findViewById<TextView>(R.id.num2)
            val num3 = findViewById<TextView>(R.id.num3)
            val operator = findViewById<TextView>(R.id.operator)
            val submit = findViewById<Button>(R.id.submit)
            val randOperator = (1..4).random()
            var randnum1 = 0
            var randnum2 = 0
            var result = 0
            var operators = ""

            var score2 = score
            val scoreValue = findViewById<TextView>(R.id.scoreValue)
            scoreValue.text = score2.toString()

            val highScoreValue = findViewById<TextView>(R.id.highScoreValue)
            val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
            highScoreValue.text = sharedPref.getInt("key", 0).toString()

            //Operator
            when (randOperator) {
                //Subtract
                2 -> {
                    operators = "-"
                    randnum1 = (1..9).random()
                    do {
                        randnum2 = (1..9).random()
                    } while (randnum1 <= randnum2)
                    result = randnum1 - randnum2
                }
                //Divide
                4 -> {
                    operators = "/"
                    randnum1 = (1..9).random()
                    do {
                        randnum2 = (1..9).random()
                    } while (randnum1 % randnum2 != 0)
                    result = randnum1 / randnum2
                }
                //Add
                1 -> {
                    operators = "+"
                    do {
                        randnum1 = (1..9).random()
                        randnum2 = (1..9).random()
                        result = randnum1 + randnum2
                    } while (result >= 100)
                }
                //Multiply
                3 -> {
                    operators = "x"
                    do {
                        randnum1 = (1..9).random()
                        randnum2 = (1..9).random()
                        result = randnum1 * randnum2
                    } while (result >= 100)
                }
            }

            val blankArea = (1..4).random()
            when (blankArea) {
                1 -> {
                    llnum1.setOnDragListener(dragListener)
                    num1.text = randnum1.toString()
                    operator.text = operators
                    num2.text = randnum2.toString()
                    num3.text = (result / 10).toString()
                    num3_2.text = (result % 10).toString()

                    //Set Visible
                    operator.visibility = View.VISIBLE
                    num2.visibility = View.VISIBLE
                    num3.visibility = View.VISIBLE
                    num3_2.visibility = View.VISIBLE
                }
                2 -> {
                    llCenter.setOnDragListener(dragListener)
                    num1.text = randnum1.toString()
                    operator.text = operators
                    num2.text = randnum2.toString()
                    num3.text = (result / 10).toString()
                    num3_2.text = (result % 10).toString()

                    //Set Visible
                    num1.visibility = View.VISIBLE
                    num2.visibility = View.VISIBLE
                    num3.visibility = View.VISIBLE
                    num3_2.visibility = View.VISIBLE
                }
                3 -> {
                    llnum2.setOnDragListener(dragListener)
                    num1.text = randnum1.toString()
                    operator.text = operators
                    num2.text = randnum2.toString()
                    num3.text = (result / 10).toString()
                    num3_2.text = (result % 10).toString()

                    //Set Visible
                    num1.visibility = View.VISIBLE
                    operator.visibility = View.VISIBLE
                    num3.visibility = View.VISIBLE
                    num3_2.visibility = View.VISIBLE
                }
                4 -> {
                    llnum3.setOnDragListener(dragListener)
                    llnum3_2.setOnDragListener(dragListener)
                    num1.text = randnum1.toString()
                    operator.text = operators
                    num2.text = randnum2.toString()
                    num3.text = (result / 10).toString()
                    num3_2.text = (result % 10).toString()

                    //Set Visible
                    num1.visibility = View.VISIBLE
                    operator.visibility = View.VISIBLE
                    num2.visibility = View.VISIBLE
                }
            }

            llBottom.setOnDragListener(dragListener)

            submit.setOnClickListener {
                when (blankArea) {
                    1 -> {
                        if (llnum1.getChildAt(1) != null) {
                            val tempID = llnum1.getChildAt(1).id.toString()
                            val temp = findViewById<TextView>(
                                resources.getIdentifier(
                                    tempID,
                                    "id",
                                    this.getPackageName()
                                )
                            )
                            if (num1.text == temp.text) {
                                score2++
                                llnum1.removeView(llnum1.getChildAt(1))
                                //                            llBottom.addView(temp)
                                llBottom.removeAllViews()
                                llBottom.addView(zero)
                                llBottom.addView(one)
                                llBottom.addView(two)
                                llBottom.addView(three)
                                llBottom.addView(four)
                                llBottom.addView(five)
                                llBottom.addView(six)
                                llBottom.addView(seven)
                                llBottom.addView(eight)
                                llBottom.addView(nine)
                                llBottom.addView(add)
                                llBottom.addView(subtract)
                                llBottom.addView(multiply)
                                llBottom.addView(divide)

                                num1.visibility = View.INVISIBLE
                                num2.visibility = View.INVISIBLE
                                num3.visibility = View.INVISIBLE
                                num3_2.visibility = View.INVISIBLE
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
                        if (llCenter.getChildAt(1) != null) {
                            val tempID = llCenter.getChildAt(1).id.toString()
                            val temp = findViewById<TextView>(
                                resources.getIdentifier(
                                    tempID,
                                    "id",
                                    this.getPackageName()
                                )
                            )
                            if (operator.text == temp.text) {
                                score2++
                                llCenter.removeView(llCenter.getChildAt(1))
                                //                            llBottom.addView(temp)
                                llBottom.removeAllViews()
                                llBottom.addView(zero)
                                llBottom.addView(one)
                                llBottom.addView(two)
                                llBottom.addView(three)
                                llBottom.addView(four)
                                llBottom.addView(five)
                                llBottom.addView(six)
                                llBottom.addView(seven)
                                llBottom.addView(eight)
                                llBottom.addView(nine)
                                llBottom.addView(add)
                                llBottom.addView(subtract)
                                llBottom.addView(multiply)
                                llBottom.addView(divide)

                                num1.visibility = View.INVISIBLE
                                num2.visibility = View.INVISIBLE
                                num3.visibility = View.INVISIBLE
                                num3_2.visibility = View.INVISIBLE
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
                        if (llnum2.getChildAt(1) != null) {
                            val tempID = llnum2.getChildAt(1).id.toString()
                            val temp = findViewById<TextView>(
                                resources.getIdentifier(
                                    tempID,
                                    "id",
                                    this.getPackageName()
                                )
                            )
                            if (num2.text == temp.text) {
                                score2++
                                llnum2.removeView(llnum2.getChildAt(1))
                                //                            llBottom.addView(temp)
                                llBottom.removeAllViews()
                                llBottom.addView(zero)
                                llBottom.addView(one)
                                llBottom.addView(two)
                                llBottom.addView(three)
                                llBottom.addView(four)
                                llBottom.addView(five)
                                llBottom.addView(six)
                                llBottom.addView(seven)
                                llBottom.addView(eight)
                                llBottom.addView(nine)
                                llBottom.addView(add)
                                llBottom.addView(subtract)
                                llBottom.addView(multiply)
                                llBottom.addView(divide)

                                num1.visibility = View.INVISIBLE
                                num2.visibility = View.INVISIBLE
                                num3.visibility = View.INVISIBLE
                                num3_2.visibility = View.INVISIBLE
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
                        if (llnum3.getChildAt(1) != null && llnum3_2.getChildAt(1) != null) {
                            val tempID = llnum3.getChildAt(1).id.toString()
                            val tempID2 = llnum3_2.getChildAt(1).id.toString()
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
                            if (num3.text == temp.text && num3_2.text == temp2.text) {
                                score2++
                                llnum3.removeView(llnum3.getChildAt(1))
                                llnum3_2.removeView(llnum3_2.getChildAt(1))
                                //                            llBottom.addView(temp)
                                //                            llBottom.addView(temp2)
                                llBottom.removeAllViews()
                                llBottom.addView(zero)
                                llBottom.addView(one)
                                llBottom.addView(two)
                                llBottom.addView(three)
                                llBottom.addView(four)
                                llBottom.addView(five)
                                llBottom.addView(six)
                                llBottom.addView(seven)
                                llBottom.addView(eight)
                                llBottom.addView(nine)
                                llBottom.addView(add)
                                llBottom.addView(subtract)
                                llBottom.addView(multiply)
                                llBottom.addView(divide)

                                num1.visibility = View.INVISIBLE
                                num2.visibility = View.INVISIBLE
                                num3.visibility = View.INVISIBLE
                                num3_2.visibility = View.INVISIBLE
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
            zero.setOnLongClickListener {
                val clipText = "You added Zero"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            one.setOnLongClickListener {
                val clipText = "You added One"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            two.setOnLongClickListener {
                val clipText = "You added Two"

                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            three.setOnLongClickListener {
                val clipText = "You added Three"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            four.setOnLongClickListener {
                val clipText = "You added Four"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            five.setOnLongClickListener {
                val clipText = "You added Five"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            six.setOnLongClickListener {
                val clipText = "You added Six"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            seven.setOnLongClickListener {
                val clipText = "You added Seven"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            eight.setOnLongClickListener {
                val clipText = "You added Eight"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            nine.setOnLongClickListener {
                val clipText = "You added Nine"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            add.setOnLongClickListener {
                val clipText = "You added Add"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            subtract.setOnLongClickListener {
                val clipText = "You added Subtract"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            multiply.setOnLongClickListener {
                val clipText = "You added Multiply"
                val item = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, item)

                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDragAndDrop(data, dragShadowBuilder, it, 0)

                it.visibility = View.INVISIBLE
                true
            }
            divide.setOnLongClickListener {
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

    val dragListener = View.OnDragListener {view, event ->
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

                if(owner == llBottom && destination != llBottom) {
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
                val v = event.localState as TextView
                v.visibility = View.VISIBLE
                view.invalidate()
                true
            }

            else -> false
        }
    }
}