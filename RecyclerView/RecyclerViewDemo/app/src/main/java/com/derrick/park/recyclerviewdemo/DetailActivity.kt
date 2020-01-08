package com.derrick.park.recyclerviewdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        nameTextView.text = intent.getStringExtra(MainActivity.NAME_EXTRA)
        heightTextView.text = "Height: ${intent.getIntExtra(MainActivity.HEIGHT_EXTRA, 0)}"
        massTextView.text = "Mass: ${intent.getIntExtra(MainActivity.MASS_EXTRA, 0)}"
        eyeColorTextView.text = "Eye Color: ${intent.getStringExtra(MainActivity.EYE_EXTRA)}"
        genderTextView.text = "Gender: ${intent.getStringExtra(MainActivity.GENDER_EXTRA)}"

    }
}
