package ru.skillbranch.devintensive

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var benderImage:ImageView
    private lateinit var textTextView:TextView
    private lateinit var messageEditT:EditText
    private lateinit var sendBtn:ImageView
    private lateinit var benderObj: Bender
    private lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity = this

        benderImage = iv_bender
        textTextView = tv_text
        messageEditT = et_message
        sendBtn = iv_send

        val status = savedInstanceState?.getString("STATUS") ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString("QUESTION") ?: Bender.Question.NAME.name
        benderObj = Bender(Bender.Status.valueOf(status), Bender.Question.valueOf(question))
        val (r,g,b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)

        Log.d("M_MainActivity", "onCreate  ${benderObj.status.name} ${benderObj.question.name}")

        textTextView.text = benderObj.askQuestion()
        sendBtn.setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()
        Log.d("M_MainActivity", "onStart")

    }

    override fun onResume() {
        super.onResume()
        Log.d("M_MainActivity", "onResume")

    }

    override fun onRestart() {
        super.onRestart()
        Log.d("M_MainActivity", "onRestart")

    }

    override fun onPause() {
        super.onPause()
        Log.d("M_MainActivity", "onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.d("M_MainActivity", "onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("M_MainActivity", "onDestroy")

    }

    override fun onClick(v: View?) {
        if (v?.id==R.id.iv_send){
            activity.hideKeyboard()
            val (phrase, color) = benderObj.listenAnswer(messageEditT.text.toString().trim())
            val (r, g, b) = color
            messageEditT.setText("")
            textTextView.text = phrase
            benderImage.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("STATUS", benderObj.status.name)
        outState?.putString("QUESTION", benderObj.question.name)
        Log.d("M_MainActivity","onSaveInstanceState: ${benderObj.status.name} ${benderObj.question.name}")
    }
}

