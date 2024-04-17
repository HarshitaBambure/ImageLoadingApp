package com.example.imageloadingapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.imageloadingapp.databinding.ActivityMainBinding
import com.example.imageloadingapp.ui.adapter.ImageLoaderAdapter
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imageloadingapp.data.api.BaseResponse
import com.example.imageloadingapp.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {
    val adapter = ImageLoaderAdapter()
    private val viewModel by viewModels<MainViewModel>()
    lateinit var binding: ActivityMainBinding
    var isLoading: Boolean = false
    var isLastPage: Boolean = false
    var page = 0
    var pageSize = 30
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvImages.adapter = adapter
        binding.rvImages.layoutManager = GridLayoutManager(
            this, 2
        )
        observeData()
        getImageList()
        binding.rvImages.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = (binding.rvImages.layoutManager as GridLayoutManager)
                val visibleCount = manager.childCount
                val totalItem = manager.itemCount
                val firstVisibleItem = manager.findFirstVisibleItemPosition()
                if (!isLoading && !isLastPage) {
                    if ((visibleCount + firstVisibleItem >= totalItem) &&
                        firstVisibleItem >= 0 && totalItem >= pageSize
                    ) {
                        page++
                        getImageList()
                    }
                }
            }
        })
    }

    private fun getImageList() {
        viewModel.getImageList(page, pageSize)
    }

    fun setProgressBarVisible(isVisible: Boolean) {
        isLoading=isVisible
        binding.progressbar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun observeData() {
        viewModel.listData.observe(this) {
            runOnUiThread {
                when (it) {
                    is BaseResponse.Loading -> {
                        setProgressBarVisible(true)
                    }
                    is BaseResponse.Success -> {
                        setProgressBarVisible(false)
                        isLoading = false
                        if (it.data?.size!! > 0) {
                            isLastPage = it.data.size < pageSize
                        } else {
                            isLastPage = true
                        }
                        it.data?.let { it1 -> adapter.updateData(it1) }
                    }

                    is BaseResponse.Failed -> {
                        setProgressBarVisible(false)
                        Toast.makeText(this@MainActivity, "Api Failed" + it.msg, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }
}