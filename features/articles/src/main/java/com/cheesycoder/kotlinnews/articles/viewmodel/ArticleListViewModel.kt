package com.cheesycoder.kotlinnews.articles.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheesycoder.kotlinnews.common.model.Result
import com.cheesycoder.kotlinnews.common.model.ViewEffects
import com.cheesycoder.kotlinnews.domain.model.RedditArticleListItem
import com.cheesycoder.kotlinnews.domain.usecase.AfterValueExtractorUseCase
import com.cheesycoder.kotlinnews.domain.usecase.SubRedditListFetchUseCase
import com.cheesycoder.kotlinnews.domain.usecase.SubRedditListMergerUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class ArticleListViewModel(
    private val fetchUseCase: SubRedditListFetchUseCase,
    private val mergerUseCase: SubRedditListMergerUseCase,
    private val extracterUseCase: AfterValueExtractorUseCase,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private var _itemList = listOf<RedditArticleListItem>()
        set(value) {
            field = value
            _itemListLiveData.value = field
        }
    private val _itemListLiveData = MutableLiveData<List<RedditArticleListItem>>()
    private val _errorMessage = MutableLiveData<ViewEffects<String>>()
    private val _isLoading = MutableLiveData<Boolean>()
    private val _navigateToDetail = MutableLiveData<ViewEffects<RedditArticleListItem.Article>>()
    private var name: String by Delegates.notNull()

    val itemList: LiveData<List<RedditArticleListItem>> = _itemListLiveData
    val errorMessage: LiveData<ViewEffects<String>> = _errorMessage
    val isLoading: LiveData<Boolean> = _isLoading
    val navigateToDetail: LiveData<ViewEffects<RedditArticleListItem.Article>> = _navigateToDetail

    @VisibleForTesting
    var isLoadedInitially = false

    @VisibleForTesting
    var isLoadingNextBatch = false

    fun start(subredditName: String) = viewModelScope.launch(coroutineDispatcher) {
        name = subredditName
        if (isLoadedInitially) return@launch
        _isLoading.value = true
        when (val result = fetchUseCase.execute(subredditName, null)) {
            is Result.Success -> {
                _itemList = result.data
                isLoadedInitially = true
            }
            is Result.Error ->
                _errorMessage.value =
                    ViewEffects("${result.reason}(${result.cause})")
        }
        _isLoading.value = false
    }

    fun loadNext() = viewModelScope.launch(coroutineDispatcher) {
        if (!isLoadedInitially) return@launch
        if (isLoadingNextBatch) return@launch
        isLoadingNextBatch = true
        val afterValue = extracterUseCase.extract(_itemList)
        if (afterValue == null) {
            _errorMessage.value = ViewEffects("Cannot load next page!")
            isLoadingNextBatch = false
            return@launch
        }
        val newList = when (val result = fetchUseCase.execute(name, afterValue)) {
            is Result.Success -> mergerUseCase.mergeOnSuccess(result.data, _itemList)
            is Result.Error -> mergerUseCase.mergeOnFailure(_itemList)
        }
        _itemList = newList
        isLoadingNextBatch = false
    }

    fun navigateWith(item: RedditArticleListItem) {
        when (item) {
            is RedditArticleListItem.Article -> _navigateToDetail.value = ViewEffects(item)
            is RedditArticleListItem.TryAgain -> loadNext()
        }
    }
}
