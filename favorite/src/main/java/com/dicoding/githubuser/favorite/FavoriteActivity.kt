package com.dicoding.githubuser.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.R
import com.dicoding.githubuser.core.ui.UserAdapter
import com.dicoding.githubuser.detail.DetailActivity
import com.dicoding.githubuser.favorite.databinding.ActivityFavoriteBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.favorites)

        loadKoinModules(favoriteModule)

        val userAdapter = UserAdapter()
        userAdapter.onItemClick = { selectedData ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_LOGIN, selectedData.login)
            startActivity(intent)
        }

        favoriteViewModel.favoriteUser.observe(this) { dataUser ->
            userAdapter.setData(dataUser)
            if (dataUser.isEmpty()){
                binding.viewEmpty.root.visibility = View.VISIBLE
                binding.viewEmpty.tvError.text = getString(R.string.no_data)
            } else {
                binding.viewEmpty.root.visibility = View.GONE
            }
        }

        with(binding.rvFavorite) {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(true)
            adapter = userAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return true
    }
}