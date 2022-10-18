package com.dicoding.githubuser.detail

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.core.data.Resource
import com.dicoding.githubuser.databinding.ActivityDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_LOGIN = "extra_login"
        private val TAG = DetailActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        when(resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                with(binding) {
                    iconCompany.setImageResource(R.drawable.ic_baseline_apartment_white_24)
                    iconFollow.setImageResource(R.drawable.ic_baseline_person_white_24)
                    iconLocation.setImageResource(R.drawable.ic_baseline_location_on_white_24)
                    iconRepository.setImageResource(R.drawable.ic_baseline_book_white_24)
                }
            }
        }

        val login = intent.getStringExtra(EXTRA_LOGIN).toString()
        Log.d(TAG, "login: $login")

        detailViewModel.getUser(login).observe(this@DetailActivity) { user ->
            when (user) {
                is Resource.Loading -> {
                    binding.progressBarDetail.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBarDetail.visibility = View.GONE
                    user.data?.let {
                        with(binding) {
                            val userGet = it[0]
                            Glide.with(this@DetailActivity)
                                .load(userGet.avatarUrl)
                                .error(R.drawable.github_mark)
                                .into(imgDetailAvatar)

                            tvDetailName.text = userGet.name
                            tvDetailUsername.text = userGet.login
                            tvDetailCompany.text = userGet.company
                            tvDetailLocation.text = userGet.location
                            tvDetailRepository.text = userGet.publicRepos.toString()
                            tvDetailFollowers.text = userGet.followers.toString()
                            tvDetailFollowing.text = userGet.following.toString()

                            detailViewModel.checkFavoriteUser(userGet.login)
                            detailViewModel.isFavorite.observe(this@DetailActivity) {isFavorite ->
                                if (isFavorite) buttonFavorite.text = getString(R.string.remove_from_favorite)
                                else buttonFavorite.text = getString(R.string.add_to_favorite)
                                buttonFavorite.setOnClickListener {
                                    if (isFavorite) {
                                        Toast.makeText(this@DetailActivity, "Removed from Favorite", Toast.LENGTH_SHORT)
                                            .show()
                                        detailViewModel.deleteFavoriteUser(userGet)
                                    } else {
                                        Toast.makeText(this@DetailActivity, "Added to Favorite", Toast.LENGTH_SHORT)
                                            .show()
                                        detailViewModel.insertFavoriteUser(userGet)
                                    }
                                    detailViewModel.checkFavoriteUser(userGet.login)
                                }
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    binding.progressBarDetail.visibility = View.GONE
                    Toast.makeText(this@DetailActivity, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }
}