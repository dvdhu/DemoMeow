package com.gunaya.demo.demomeow.presentation.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import com.gunaya.demo.demomeow.R
import com.gunaya.demo.demomeow.presentation.detail.DetailActivity
import com.gunaya.demo.demomeow.presentation.main.adapter.CatAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

const val NUMBER_OF_COLUMN = 3

class MainActivity : AppCompatActivity() {

    // Instantiate viewModel with Koin
    val viewModel: MainViewModel2 by viewModel()
    private lateinit var catAdapter: CatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // The click listener for displaying a cat image in detail activity
        val onCatClicked: (imageUrl: String) -> Unit = { imageUrl ->
            viewModel.catClicked(imageUrl)
        }
        // Instantiate our custom Adapter with the click listener
        catAdapter = CatAdapter(onCatClicked)
        catsRecyclerView.apply {
            // Displaying data in a Grid design
            layoutManager = GridLayoutManager(
                this@MainActivity,
                NUMBER_OF_COLUMN
            )
            adapter = catAdapter
        }
        // Initiate the observers on viewModel fields and then starts the API request
        initViewModel()
    }

    private fun initViewModel() {
        // Observe catsList and update our adapter if we get new one from API
        viewModel.catsList.observe(this, Observer { newCatsList ->
            catAdapter.updateData(newCatsList!!)
        })
        // Observe showLoading value and display or hide our activity's progressBar
        viewModel.showLoading.observe(this, Observer { showLoading ->
            mainProgressBar.visibility = if (showLoading!!) View.VISIBLE else View.GONE
        })
        // Observe showError value and display the error message as a Toast
        viewModel.showError.observe(this, Observer { showError ->
            Toast.makeText(this, showError, Toast.LENGTH_SHORT).show()
        })
        // Navigate to detail view with the image's url to display
        viewModel.navigateToDetail.observe(this, Observer { imageUrl ->
            if (imageUrl != null) startActivity(DetailActivity.getStartIntent(this, imageUrl))
        })
    }
}
