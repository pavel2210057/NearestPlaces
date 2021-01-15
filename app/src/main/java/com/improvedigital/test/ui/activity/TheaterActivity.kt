package com.improvedigital.test.ui.activity

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.improvedigital.test.R
import com.improvedigital.test.model.BriefTheaterDescModel
import com.improvedigital.test.model.TheaterDescModel
import com.improvedigital.test.presenter.TheaterPresenter
import com.improvedigital.test.view.TheaterView

class TheaterActivity : AppCompatActivity(), TheaterView {
    companion object Extras {
        private const val BASE = "com.improvedigital.test.ui.activity"

        const val BRIEF_THEATER_DESC_MODEL = "$BASE.EXTRA_BRIEF_THEATER_DESC_MODEL"
    }

    private lateinit var theaterPresenter: TheaterPresenter

    private lateinit var establishmentImageView: AppCompatImageView
    private lateinit var ratingTextView: TextView
    private lateinit var addToFavoriteButton: FloatingActionButton
    private lateinit var vicinityTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var distanceTextView: TextView
    private lateinit var workingHoursTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theater)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bindViews()
        initGui()
    }

    private fun bindViews() {
        this.establishmentImageView = findViewById(R.id.establishment_image)
        this.ratingTextView = findViewById(R.id.rating_text)
        this.addToFavoriteButton = findViewById(R.id.add_to_favorite_button)
        this.vicinityTextView = findViewById(R.id.vicinity_text)
        this.phoneTextView = findViewById(R.id.phone_text)
        this.distanceTextView = findViewById(R.id.distance_text)
        this.workingHoursTextView = findViewById(R.id.working_hours)
    }

    @SuppressLint("SetTextI18n")
    private fun initGui() {
        val briefModel = intent.getSerializableExtra(BRIEF_THEATER_DESC_MODEL)
                as BriefTheaterDescModel

        val model = TheaterDescModel(
                placeId = briefModel.placeId,
                name = briefModel.name,
                distance = briefModel.distance,
                rating = briefModel.rating,
                vicinity = briefModel.vicinity,
                photoReference = briefModel.photos?.first()?.photoReference
        )

        this.theaterPresenter = TheaterPresenter(this, this, model)
        this.theaterPresenter.loadPlaceInfo()

        supportActionBar?.title = briefModel.name

        this.distanceTextView.text = "Distance: ${briefModel.distance} m"
        this.ratingTextView.text = briefModel.rating.toString()
        this.vicinityTextView.text = briefModel.vicinity

        this.addToFavoriteButton.apply {
            updateFloatButtonColor()
            setOnClickListener {
                theaterPresenter.toggleFavorite()
                updateFloatButtonColor()
            }
        }
    }

    private fun updateFloatButtonColor() {
        this.addToFavoriteButton.apply {
            if (this@TheaterActivity.theaterPresenter.isCached())
                setColorFilter(R.color.pink)
            else
                setColorFilter(R.color.black)
        }
    }

    override fun displayTheaterData(model: TheaterDescModel) {
        runOnUiThread {
            this.phoneTextView.text = model.phone
            this.workingHoursTextView.text =
                if (model.workingHours.isNotEmpty())
                    model.workingHours.joinToString("\n")
                else
                    "График не указан"
        }
    }

    override fun displayTheaterImage(bitmap: Bitmap) {
        runOnUiThread {
            this.establishmentImageView.setImageBitmap(bitmap)
        }
    }

    override fun printToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT)
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}