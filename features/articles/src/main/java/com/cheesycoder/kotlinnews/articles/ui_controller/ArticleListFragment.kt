package com.cheesycoder.kotlinnews.articles.ui_controller

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cheesycoder.kotlinnews.articles.BuildConfig
import com.cheesycoder.kotlinnews.articles.R
import com.cheesycoder.kotlinnews.articles.adapter.ArticleAdapter
import com.cheesycoder.kotlinnews.articles.common.NavArgument
import com.cheesycoder.kotlinnews.articles.viewmodel.ArticleListViewModel
import kotlinx.android.synthetic.main.fragment_article_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ArticleListFragment : Fragment(R.layout.fragment_article_list) {

    private val viewModel by sharedViewModel<ArticleListViewModel>()
    private var adapter: ArticleAdapter? = null
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            adapter?.let { adapter ->
                val layoutManager = article_list.layoutManager as? LinearLayoutManager
                layoutManager?.let {
                    if (it.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                        viewModel.loadNext()
                        article_list.removeOnScrollListener(this)
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = ArticleAdapter {
            viewModel.navigateWith(it)
        }
        article_list.adapter = adapter
        article_list.layoutManager = LinearLayoutManager(requireContext())
        viewModel.itemList.observe(viewLifecycleOwner) {
            it?.let {
                adapter?.submitList(it)
                article_list.addOnScrollListener(scrollListener)
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            progress_bar.isVisible = it
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            it?.observeData?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        viewModel.navigateToDetail.observe(viewLifecycleOwner) {
            it?.observeData?.let {
                val argBundle = Bundle().apply {
                    putParcelable(NavArgument.articleDetailArgumentKey, it)
                }
                findNavController().navigate(
                    R.id.action_articleListFragment_to_articleDetailFragment,
                    argBundle
                )
            }
        }
        viewModel.start(BuildConfig.SUBREDDIT_NAME)
    }

    override fun onDestroyView() {
        article_list.removeOnScrollListener(scrollListener)
        article_list.adapter = null
        adapter = null
        super.onDestroyView()
    }
}
