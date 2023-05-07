package com.example.submissionawalgithub

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.example.submissionawalgithub.databinding.ActivityDetailBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        DetailViewModel.Factory(DatabaseModule(this))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.title = "Detail Users GitHub"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getParcelableExtra<Response.Item>("item")
        val username = item?.login ?: ""
        viewModel.getDetailUser(username)

        viewModel.detailUser.observe(this) {
            when (it) {
                is HasilRespon.Sukses<*> -> {
                    val user = it.data as DetailUser
                    binding.detailImage.load(user.avatar_url) {
                        transformations(CircleCropTransformation())
                    }
                    binding.detailNamaUser.text = user.name
                    binding.detailUsername.text = user.login
                    binding.texters.text = user.followers.toString()
                    binding.texting.text = user.following.toString()
                }
                is HasilRespon.Loading -> {
                    binding.loading.isVisible = it.L
                }
                is HasilRespon.Error -> {
                    Toast.makeText(this, it.E.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

        val fragments = mutableListOf<Fragment>(
            FragmentFollowers_ing.newInstance(FragmentFollowers_ing.ERS),
            FragmentFollowers_ing.newInstance(FragmentFollowers_ing.ING)
        )

        val judul = mutableListOf(getString(R.string.ers), getString(R.string.ing))
        val adapter = DetailAdapter(this, fragments)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.choose, binding.viewPager) { tab, posisi ->
            tab.text = judul[posisi]
        }.attach()

        binding.choose.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    viewModel.getFollowers(username)
                } else {
                    viewModel.getFollowing(username)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        viewModel.getFollowers(username)

        viewModel.resultSuksesfav.observe(this) {
            binding.btnFav.changeIconColor(R.color.Red)
        }
        viewModel.resultDeleteFav.observe(this) {
            binding.btnFav.changeIconColor(R.color.white)
        }

        binding.btnFav.setOnClickListener {
            viewModel.favUser(item)
        }

        viewModel.findfav(item?.id ?: 0) {
            binding.btnFav.changeIconColor(R.color.Red)
        }
    }

}

fun FloatingActionButton.changeIconColor(@ColorRes color: Int) {
    imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
}


