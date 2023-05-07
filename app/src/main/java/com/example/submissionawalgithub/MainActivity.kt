package com.example.submissionawalgithub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawalgithub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<ViewModel>{
        ViewModel.Factory(SettingPreferences(this))
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "List Users GitHub"

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getTheme().observe(this){
            if(it){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getUser(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getUser(newText.toString())
                return true
            }

        })
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        viewModel.hasilRespon.observe(this) {
            when (it) {
                is HasilRespon.Sukses<*> -> {
                    adapter.setData(it.data as MutableList<Response.Item>)
                }
                is HasilRespon.Loading -> {
                    binding.loading.isVisible = it.L
                }
                is HasilRespon.Error -> {
                    Toast.makeText(this, it.E.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.getUser()

    }

    private val adapter by lazy {
        Adapter {
            Intent(this, DetailActivity::class.java).apply {
                putExtra("item", it)
                startActivity(this)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                Intent(this, FavActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.settings ->{
                Intent(this, SettingActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}