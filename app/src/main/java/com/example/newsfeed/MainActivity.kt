package com.example.newsfeed

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsfeed.databinding.ActivityMainBinding
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.NewsApiClient.ArticlesResponseCallback
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse

class MainActivity : AppCompatActivity(),
    View.OnClickListener,
    NewsRecyclerAdapter.Listener {
    private lateinit var binding: ActivityMainBinding
    private var articleList: MutableList<Article> = ArrayList()
    private var adapter: NewsRecyclerAdapter? = null
    private val favoriteList: MutableList<Favorites> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btn1.setOnClickListener(this@MainActivity)
            btn2.setOnClickListener(this@MainActivity)
            btn3.setOnClickListener(this@MainActivity)
            btn4.setOnClickListener(this@MainActivity)
            btn5.setOnClickListener(this@MainActivity)
            btn6.setOnClickListener(this@MainActivity)
            btn7.setOnClickListener(this@MainActivity)
            searchVeiw.setOnQueryTextListener(object : OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    getNews("GENERAL", query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
            favoritesRedirection.setOnClickListener {
                val intent = Intent(this@MainActivity, FavoritesActivity::class.java)
                val favoritesAll = FavoritesAll(favoriteList)
                intent.putExtra(FavoritesActivity.KEY_FAVORITE, favoritesAll)
                startActivity(intent)
            }
        }

        setupRecyclerView()
        getNews("GENERAL",null)
    }

    private fun setupRecyclerView() = with(binding) {
        newsRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = NewsRecyclerAdapter(articleList, this@MainActivity)
        newsRecyclerView.adapter = adapter
    }

    private fun changeInProgress(show: Boolean) {
        binding.apply {
            if (show) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun getNews(category: String, query: String?) {
        changeInProgress(true)
        val newsApiClient = NewsApiClient("dc259abd56ca463baa08cdacd9fd1942")
        newsApiClient.getTopHeadlines(
            TopHeadlinesRequest.Builder()
                .language("en")
                .category(category)
                .q(query)
                .build(),
            object : ArticlesResponseCallback {
                override fun onSuccess(response: ArticleResponse?) {

                    runOnUiThread {
                        changeInProgress(false)
                        articleList = response?.articles!!
                        val layoutManager = binding.newsRecyclerView.layoutManager
                        layoutManager?.smoothScrollToPosition(binding.newsRecyclerView, null, 0)
                        adapter?.updateData(articleList)
                        adapter?.notifyDataSetChanged()
                    }

                }

                override fun onFailure(throwable: Throwable?) {
                    Log.i("GOT RESPONSE", throwable.toString())
                }
            }
        )
    }

    override fun onClick(v: View?) {
        val btn = v as Button
        val category = btn.text.toString()
        getNews(category, null)
    }

    override fun onClickFavorite(favorites: Favorites) {
        favoriteList.add(favorites)
    }
}