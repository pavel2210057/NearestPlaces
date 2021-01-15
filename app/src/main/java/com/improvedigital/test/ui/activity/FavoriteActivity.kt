package com.improvedigital.test.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.improvedigital.test.R
import com.improvedigital.test.model.BriefTheaterDescModel
import com.improvedigital.test.presenter.FavoritePresenter
import com.improvedigital.test.ui.adapter.TheaterRecyclerViewAdapter
import com.improvedigital.test.view.FavoriteView

class FavoriteActivity : AppCompatActivity(), FavoriteView {
    private val favoritePresenter = FavoritePresenter(this)

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bindViews()
        this.favoritePresenter.loadFavorites()
    }

    private fun bindViews() {
        this.recyclerView = findViewById(R.id.recycler_view)
    }

    override fun setTheaterData(theaterDescModels: List<BriefTheaterDescModel>) {
        this.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity,
                LinearLayoutManager.VERTICAL, false)
            adapter = TheaterRecyclerViewAdapter(theaterDescModels.toMutableList(),
                null, this@FavoriteActivity::onItemSelected)
        }
    }

    private fun onItemSelected(theaterDescModel: BriefTheaterDescModel) {
        startActivity(
            Intent(this, TheaterActivity::class.java)
                .putExtra(TheaterActivity.BRIEF_THEATER_DESC_MODEL, theaterDescModel)
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}