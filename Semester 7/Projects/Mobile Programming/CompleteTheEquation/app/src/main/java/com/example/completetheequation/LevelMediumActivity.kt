package com.example.completetheequation

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_level_medium.*
import org.jetbrains.anko.textColor

class LevelMediumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_medium)

        updateEquation(0)
    }

    fun updateEquation(score: Int) {
        val num1 = findViewById<TextView>(R.id.num1Medium)
        val num1_2 = findViewById<TextView>(R.id.num1_2Medium)
        val num2 = findViewById<TextView>(R.id.num2Medium)
        val num3 = findViewById<TextView>(R.id.num3Medium)
        val num3_2 = findViewById<TextView>(R.id.num3_2Medium)
        val num3_3 = findViewById<TextView>(R.id.num3_3Medium)
        val operator = findViewById<TextView>(R.id.operatorMedium)
        val submit = findViewById<Button>(R.id.submitMedium)
        val randOperator = (1..4).random()
        var randnum1 = 0
        var randnum2 = 0
        var result = 0
        var operators = ""

        var score2 = score
        val scoreValue = findViewById<TextView>(R.id.scoreValueMedium)
        scoreValue.text = score2.toString()

        val highScoreValue = findViewById<TextView>(R.id.highScoreValueMedium)
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        highScoreValue.text = sharedPref.getInt("key", 0).toString()

        //Operator
        when (randOperator) {
            //Subtract
            2 -> {
                operators ="-"
                randnum1 = (1..99).random()
                do {
                    randnum2 = (1..9).random()
                }while(randnum1 <= randnum2)
                result = randnum1 - randnum2
            }
            //Divide
            4 -> {
                operators = "/"
                randnum1 = (1..99).random()
                do {
                    randnum2 = (1..9).random()
                }while(randnum1 % randnum2 != 0)
                result = randnum1 / randnum2
            }
            //Add
            1 -> {
                operators = "+"
                do {
                    randnum1 = (1..99).random()
                    randnum2 = (1..9).random()
                    result = randnum1 + randnum2
                }while(result >= 1000)
            }
            //Multiply
            3 -> {
                operators = "x"
                do {
                    randnum1 = (1..99).random()
                    randnum2 = (1..9).random()
                    result = randnum1 * randnum2
                }while(result >= 1000)
            }
        }

        val blankArea = (1..4).random()
        when (blankArea) {
            1 -> {
                llnum1Medium.setOnDragListener(dragListener)
                llnum1_2Medium.setOnDragListener(dragListener)
                num1.text = (randnum1 / 10).toString()
                num1_2.text = (randnum1 % 10).toString()
                operator.text = operators
                num2.text = randnum2.toString()
                num3.text = (result / 100).toString()
                num3_2.text = ((result % 100) / 10).toString()
                num3_3.text = (result % 10).toString()

                //Set Visible
                operator.visibility = View.VISIBLE
                num2.visibility = View.VISIBLE
                num3.visibility = View.VISIBLE
                num3_2.visibility = View.VISIBLE
                num3_3.visibility = View.VISIBLE
            }
            2 -> {
                llCenterMedium.setOnDragListener(dragListener)
                num1.text = (randnum1 / 10).toString()
                num1_2.text = (randnum1 % 10).toString()
                operator.text = operators
                num2.text = randnum2.toString()
                num3.text = (result / 100).toString()
                num3_2.text = ((result % 100) / 10).toString()
                num3_3.text = (result % 10).toString()

                //Set Visible
                num1.visibility = View.VISIBLE
                num1_2.visibility = View.VISIBLE
                num2.visibility = View.VISIBLE
                num3.visibility = View.VISIBLE
                num3_2.visibility = View.VISIBLE
                num3_3.visibility = View.VISIBLE
            }
            3 -> {
                llnum2Medium.setOnDragListener(dragListener)
                num1.text = (randnum1 / 10).toString()
                num1_2.text = (randnum1 % 10).toString()
                operator.text = operators
                num2.text = randnum2.toString()
                num3.text = (result / 100).toString()
                num3_2.text = ((result % 100) / 10).toString()
                num3_3.text = (result % 10).toString()

                //Set Visible
                num1.visibility = View.VISIBLE
                num1_2.visibility = View.VISIBLE
                operator.visibility = View.VISIBLE
                num3.visibility = View.VISIBLE
                num3_2.visibility = View.VISIBLE
                num3_3.visibility = View.VISIBLE
            }
            4 -> {
                llnum3Medium.setOnDragListener(dragListener)
                llnum3_2Medium.setOnDragListener(dragListener)
                llnum3_3Medium.setOnDragListener(dragListener)
                num1.text = (randnum1 / 10).toString()
                num1_2.text = (randnum1 % 10).toString()
                operator.text = operators
                num2.text = randnum2.toString()
                num3.text = (result / 100).toString()
                num3_2.text = ((result % 100) / 10).toString()
                num3_3.text = (result % 10).toString()

                //Set Visible
                num1.visibility = View.VISIBLE
                num1_2.visibility = View.VISIBLE
                operator.visibility = View.VISIBLE
                num2.visibility = View.VISIBLE
            }
        }

        llBottomMedium.setOnDragListener(dragListener)

        submit.setOnClickListener {
            when (blankArea) {
                1 -> {
                    if(llnum1Medium.getChildAt(1) != null && llnum1_2Medium.getChildAt(1) != null) {
                        val tempID = llnum1Medium.getChildAt(1).id.toString()
                        val tempID2 = llnum1_2Medium.getChildAt(1).id.toString()
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
                            llnum1Medium.removeView(llnum1Medium.getChildAt(1))
                            llnum1_2Medium.removeView(llnum1_2Medium.getChildAt(1))
                            llBottomMedium.addView(temp)
                            llBottomMedium.addView(temp2)
                            num1.visibility = View.INVISIBLE
                            num1_2.visibility = View.INVISIBLE
                            num2.visibility = View.INVISIBLE
                            num3.visibility = View.INVISIBLE
                            num3_2.visibility = View.INVISIBLE
                            num3_3.visibility = View.INVISIBLE
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
                    if(llCenterMedium.getChildAt(1) != null) {
                        val tempID = llCenterMedium.getChildAt(1).id.toString()
                        val temp = findViewById<TextView>(
                            resources.getIdentifier(
                                tempID,
                                "id",
                                this.getPackageName()
                            )
                        )
                        if (operator.text == temp.text) {
                            score2++
                            llCenterMedium.removeView(llCenterMedium.getChildAt(1))
                            llBottomMedium.addView(temp)
                            num1.visibility = View.INVISIBLE
                            num1_2.visibility = View.INVISIBLE
                            num2.visibility = View.INVISIBLE
                            num3.visibility = View.INVISIBLE
                            num3_2.visibility = View.INVISIBLE
                            num3_3.visibility = View.INVISIBLE
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
                    if(llnum2Medium.getChildAt(1) != null) {
                        val tempID = llnum2Medium.getChildAt(1).id.toString()
                        val temp = findViewById<TextView>(
                            resources.getIdentifier(
                                tempID,
                                "id",
                                this.getPackageName()
                            )
                        )
                        if (num2.text == temp.text) {
                            score2++
                            llnum2Medium.removeView(llnum2Medium.getChildAt(1))
                            llBottomMedium.addView(temp)
                            num1.visibility = View.INVISIBLE
                            num1_2.visibility = View.INVISIBLE
                            num2.visibility = View.INVISIBLE
                            num3.visibility = View.INVISIBLE
                            num3_2.visibility = View.INVISIBLE
                            num3_3.visibility = View.INVISIBLE
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
                    if(llnum3Medium.getChildAt(1) != null && llnum3_2Medium.getChildAt(1) != null && llnum3_3Medium.getChildAt(1) != null) {
                        val tempID = llnum3Medium.getChildAt(1).id.toString()
                        val tempID2 = llnum3_2Medium.getChildAt(1).id.toString()
                        val tempID3 = llnum3_3Medium.getChildAt(1).id.toString()
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
                        if (num3.text == temp.text && num3_2.text == temp2.text && num3_3.text == temp3.text) {
                            score2++
                            llnum3Medium.removeView(llnum3Medium.getChildAt(1))
                            llnum3_2Medium.removeView(llnum3_2Medium.getChildAt(1))
                            llnum3_3Medium.removeView(llnum3_3Medium.getChildAt(1))
                            llBottomMedium.addView(temp)
                            llBottomMedium.addView(temp2)
                            llBottomMedium.addView(temp3)
                            num1.visibility = View.INVISIBLE
                            num1_2.visibility = View.INVISIBLE
                            num2.visibility = View.INVISIBLE
                            num3.visibility = View.INVISIBLE
                            num3_2.visibility = View.INVISIBLE
                            num3_3.visibility = View.INVISIBLE
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
        zeroMedium.setOnLongClickListener {
            val clipText = "You added Zero"
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)

            it.visibility = View.INVISIBLE
            true
        }
        oneMedium.setOnLongClickListener {
            val clipText = "You added One"
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)

            it.visibility = View.INVISIBLE
            true
        }
        twoMedium.setOnLongClickListener {
            val clipText = "You added Two"

            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)

            it.visibility = View.INVISIBLE
            true
        }
        threeMedium.setOnLongClickListener {
            val clipText = "You added Three"
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)

            it.visibility = View.INVISIBLE
            true
        }
        fourMedium.setOnLongClickListener {
            val clipText = "You added Four"
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)

            it.visibility = View.INVISIBLE
            true
        }
        fiveMedium.setOnLongClickListener {
            val clipText = "You added Five"
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)

            it.visibility = View.INVISIBLE
            true
        }
        sixMedium.setOnLongClickListener {
            val clipText = "You added Six"
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)

            it.visibility = View.INVISIBLE
            true
        }
        sevenMedium.setOnLongClickListener {
            val clipText = "You added Seven"
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)

            it.visibility = View.INVISIBLE
            true
        }
        eightMedium.setOnLongClickListener {
            val clipText = "You added Eight"
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)

            it.visibility = View.INVISIBLE
            true
        }
        nineMedium.setOnLongClickListener {
            val clipText = "You added Nine"
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)

            it.visibility = View.INVISIBLE
            true
        }
        addMedium.setOnLongClickListener {
            val clipText = "You added Add"
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)

            it.visibility = View.INVISIBLE
            true
        }
        subtractMedium.setOnLongClickListener {
            val clipText = "You added Subtract"
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)

            it.visibility = View.INVISIBLE
            true
        }
        multiplyMedium.setOnLongClickListener {
            val clipText = "You added Multiply"
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)

            it.visibility = View.INVISIBLE
            true
        }
        divideMedium.setOnLongClickListener {
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
                val owner = v.parent as ViewGroup

                val newView = TextView(this)
                newView.setId(View.generateViewId())
                newView.text = v.text
                newView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30F)
                newView.textColor = Color.parseColor("#ffffff")
                newView.layoutParams = v.layoutParams
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

                owner.removeView(v)

                val destination = view as ConstraintLayout
                destination.addView(v)
                owner.addView(newView)

                newView.visibility = View.VISIBLE
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