package com.dicoding.githubuser.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.R
import com.dicoding.githubuser.core.data.Resource
import com.dicoding.githubuser.core.ui.UserAdapter
import com.dicoding.githubuser.databinding.ActivityMainBinding
import com.dicoding.githubuser.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter()
        userAdapter.onItemClick = { selectedData ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_LOGIN, selectedData.login)
            startActivity(intent)
        }

        with(binding.rvUser) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = userAdapter
        }

        getAllUser()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.toString().isEmpty()) {
                    getAllUser()
                } else {
                    Log.d("MainActivity", "query: $query")
                    mainViewModel.getSearchedUser(query.toString()).observe(this@MainActivity) { users ->
                        if (users != null) {
                            when (users) {
                                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                                is Resource.Success -> {
                                    binding.progressBar.visibility = View.GONE
                                    userAdapter.setData(users.data)
                                }
                                is Resource.Error -> {
                                    binding.progressBar.visibility = View.GONE
                                    binding.viewError.tvError.visibility = View.VISIBLE
                                    binding.viewError.tvError.text =
                                        users.message ?: getString(R.string.something_wrong)
                                }
                            }
                        }
                    }
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.toString().isEmpty()) {
                    getAllUser()
                }
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                val uri = Uri.parse("githubuser://favorites")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
        return true
    }

    private fun getAllUser() {
        mainViewModel.user.observe(this) { users ->
            if (users != null) {
                when (users) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        userAdapter.setData(users.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text =
                            users.message ?: getString(R.string.something_wrong)
                    }
                }
            }
        }
    }
}