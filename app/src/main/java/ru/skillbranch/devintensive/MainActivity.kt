package ru.skillbranch.devintensive

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import ru.skillbranch.devintensive.models.User

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user1 = User("0001", "Nikola", "Mikhailov")

        val textView: TextView = findViewById(R.id.text_view)
        //textView.text()
    }
}
