package dev.calebcodes.cvsimageviewer.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.calebcodes.cvsimageviewer.model.FlickrImage
import dev.calebcodes.cvsimageviewer.model.FlickrImageItem
import dev.calebcodes.cvsimageviewer.network.FlickrRepository
import dev.calebcodes.cvsimageviewer.util.Resource
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.collections.ArrayDeque

@HiltViewModel
class ImageListViewModel @Inject constructor(@ApplicationContext app: Context, private val repository: FlickrRepository) : ViewModel() {
    companion object {
        private const val SHARED_PREF_NAME = "previous_searches"
        private const val SHARED_PREF_KEY = "searches"
    }

    private val sharedPrefs: SharedPreferences

    init {
        sharedPrefs = getApplication(app).getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    private val _previousSearches: ArrayDeque<String> = getStoredSearches()
    val previousSearches: LiveData<ArrayDeque<String>> = MutableLiveData(_previousSearches)

    private val _imageList: MutableState<ListState> = mutableStateOf(ListState())
    val imageList: MutableState<ListState> = _imageList

    private val editor = sharedPrefs.edit()

    fun handleQuery(query: String) {
        when {
            _previousSearches.contains(query) -> {
                getImageList(query)
                return
            }
            (_previousSearches.size == 5) && !_previousSearches.contains(query) -> {
                val oldestItem = _previousSearches.last()
                _previousSearches.remove(oldestItem)
            }
        }
        _previousSearches.addFirst(query)
        storeSearches()
        getImageList(query)
    }

    fun getImageFromIdOrEmptyImage(id: String, list: List<FlickrImageItem>): FlickrImageItem {
        return list.singleOrNull() { it.id == id } ?: FlickrImageItem()
    }

    private fun storeSearches() {
        editor.putStringSet(SHARED_PREF_KEY, _previousSearches.toSet())
        editor.apply()
    }

    private fun getStoredSearches(): ArrayDeque<String> {
        val items = sharedPrefs.getStringSet(SHARED_PREF_KEY, linkedSetOf()) ?: linkedSetOf()
        val queue: ArrayDeque<String> = ArrayDeque()
        items.map { queue.add(it) }
        return queue
    }

    private fun getImageList(query: String) = viewModelScope.launch {
        _imageList.value = ListState(isLoading = true)
        try {
            val result = repository.getFlickrImageItems(query)
            when (result) {
                is Resource.Error -> _imageList.value = ListState(error = "Error loading data")
                is Resource.Loading -> _imageList.value = ListState(isLoading = true)
                is Resource.Success -> {
                    result.data?.items?.let { imageData ->
                        val imageItems = imageData.map { FlickrImage.toImageItem(it) }
                        _imageList.value = ListState(data = imageItems)
                    } ?: emptyList<FlickrImageItem>()
                }
            }
        } catch (e: HttpException) {
            _imageList.value = ListState(error = e.localizedMessage ?: "Exception Loading Data")
        } catch (e: IOException) {
            _imageList.value = ListState(error = e.localizedMessage ?: "Could not reach server")
        }
    }
}

data class ListState(
    val isLoading: Boolean = false,
    val data: List<FlickrImageItem> = emptyList(),
    val error: String = ""
)