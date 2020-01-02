package io.owuor91.news.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import io.owuor91.news.R
import io.owuor91.news.di.newsModule
import io.owuor91.news.viewmodel.NewsViewModel
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules

private val loadNewsModule by lazy {
    loadKoinModules(newsModule)
}

private fun injectNewsModule() = loadNewsModule

class NewsActivity : AppCompatActivity() {
    private val newsViewModel: NewsViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        injectNewsModule()
        newsViewModel.getNewsArticles("bbc-news", "9384b4f8947f497e86f8409466ae66d7")
    }

    override fun onResume() {
        super.onResume()
        observeNewsLiveData()
    }

    fun observeNewsLiveData() {
        newsViewModel.getNewsResponse().observe(this, Observer { articles ->
            Toast.makeText(baseContext, articles[0].content, Toast.LENGTH_LONG).show()
        })

        newsViewModel.getNewsError().observe(this, Observer { error ->
            Toast.makeText(baseContext, error, Toast.LENGTH_LONG).show()
        })
    }
}
