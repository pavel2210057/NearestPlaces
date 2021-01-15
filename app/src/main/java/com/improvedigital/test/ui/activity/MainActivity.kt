package com.improvedigital.test.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.improvedigital.test.R
import com.improvedigital.test.model.BriefTheaterDescModel
import com.improvedigital.test.model.BriefTheatersDescModel
import com.improvedigital.test.presenter.MainPresenter
import com.improvedigital.test.ui.adapter.TheaterRecyclerViewAdapter
import com.improvedigital.test.view.MainView

class MainActivity : AppCompatActivity(), MainView {
    private val presenter = MainPresenter(this, this)

    private lateinit var theaterRecyclerView: RecyclerView
    private lateinit var serviceLayout: LinearLayoutCompat
    private lateinit var serviceImage: ImageView
    private lateinit var serviceText: TextView
    private lateinit var progressBar: ProgressBar

    private lateinit var lastAcceptedModel: BriefTheatersDescModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermissions()
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
        else
            init()
    }

    private fun init() {
        bindViews()
        initGui()
        initTheaters()
    }

    private fun bindViews() {
        this.theaterRecyclerView = findViewById(R.id.theater_recycler_view)
        this.serviceLayout = findViewById(R.id.service_message_layout)
        this.serviceImage = findViewById(R.id.service_image)
        this.serviceText = findViewById(R.id.service_text)
        this.progressBar = findViewById(R.id.progress_bar)
    }

    private fun initGui() {
        this.theaterRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity,
                LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun initTheaters() {
        this.presenter.proceedLoadData()
    }

    private fun onUpdateRequire() {
        this.progressBar.visibility = View.VISIBLE
        this.presenter.proceedUpdateData(this.lastAcceptedModel)
    }

    private fun onItemSelected(model: BriefTheaterDescModel) {
        startActivity(
            Intent(this, TheaterActivity::class.java)
                    .putExtra(TheaterActivity.BRIEF_THEATER_DESC_MODEL, model)
        )
    }

    override fun initTheatersAdapter(model: BriefTheatersDescModel) {
        runOnUiThread {
            this.lastAcceptedModel = model

            if (model.theatersList.isEmpty())
                showServiceMessage(R.drawable.ic_list, R.string.list_is_empty)
            else
                this.theaterRecyclerView.adapter = TheaterRecyclerViewAdapter(
                    model.theatersList, this::onUpdateRequire, this::onItemSelected)
        }
    }

    override fun addTheatersData(model: BriefTheatersDescModel) {
        runOnUiThread {
            this.lastAcceptedModel = model
            (this.theaterRecyclerView.adapter as TheaterRecyclerViewAdapter)
                .addItems(model.theatersList)
        }
    }

    override fun showProgressBar() {
        runOnUiThread { this.progressBar.visibility = View.VISIBLE }
    }

    override fun hideProgressBar() {
        runOnUiThread { this.progressBar.visibility = View.GONE }
    }

    override fun showServiceMessage(drawableResId: Int, textResId: Int) {
        this.theaterRecyclerView.visibility = View.INVISIBLE
        this.serviceLayout.visibility = View.VISIBLE

        this.serviceImage.setImageResource(drawableResId)
        this.serviceText.text = getString(textResId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.favoriteList ->
                startActivity(
                    Intent(this, FavoriteActivity::class.java)
                )
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults.first() == PERMISSION_GRANTED)
            init()
    }
}