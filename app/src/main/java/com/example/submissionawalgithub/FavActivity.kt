package com.example.submissionawalgithub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawalgithub.databinding.ActivityFavBinding

class FavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "List Users Favorite"
        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rvFav.layoutManager = LinearLayoutManager(this)
        binding.rvFav.adapter = adapter

//        viewModel.getUserFav().observe(this){
//            adapter.setData(it)
//        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserFav().observe(this) {
            adapter.setData(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val adapter by lazy {
        Adapter {
            Intent(this, DetailActivity::class.java).apply {
                putExtra("item", it)
                startActivity(this)
            }
        }
    }

    private val viewModel by viewModels<FavViewModel> {
        FavViewModel.Factory(DatabaseModule(this))
    }
}