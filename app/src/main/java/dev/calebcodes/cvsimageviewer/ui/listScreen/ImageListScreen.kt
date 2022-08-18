package dev.calebcodes.cvsimageviewer.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.calebcodes.cvsimageviewer.R
import dev.calebcodes.cvsimageviewer.navigation.Screen
import dev.calebcodes.cvsimageviewer.ui.listScreen.ImageItem
import dev.calebcodes.cvsimageviewer.viewmodel.ImageListViewModel

@Composable
fun ImageListScreen(
    imageListViewModel: ImageListViewModel = hiltViewModel(),
    navController: NavController
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier =
            Modifier
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(imageListViewModel)
            if (imageListViewModel.imageList.value.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            if (imageListViewModel.imageList.value.error.isNotBlank()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = imageListViewModel.imageList.value.error
                    )
                }
            }
            if (imageListViewModel.imageList.value.data.isNotEmpty()) {
                LazyVerticalGrid(
                    GridCells.Adaptive(minSize = 160.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    imageListViewModel.imageList.value.data.let { list ->
                        items(list) { image ->
                            ImageItem(image = image, onClick = { imageId ->
                                navController.navigate(Screen.ImageDetailScreen.route + "/$imageId")
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    imageListViewModel: ImageListViewModel
) {
    val previousQueryList = imageListViewModel.previousSearches.observeAsState()
    val query: MutableState<String> = remember { mutableStateOf("") }
    val showSearches: MutableState<Boolean> = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            Modifier
                .padding(8.dp)
                .onFocusChanged { focusState ->
                    when {
                        focusState.isFocused -> {
                            showSearches.value = true
                        }
                        !focusState.isFocused -> {
                            showSearches.value = false
                        }
                    }
                }
        ) {
            OutlinedTextField(
                value = query.value,
                onValueChange = {
                    query.value = it
                },
                enabled = true,
                singleLine = true,
                trailingIcon = { Icons.Filled.Search },
                textStyle = MaterialTheme.typography.subtitle1,
                placeholder = {
                    Text(
                        text = stringResource(R.string.placeholder_search_text),
                        color = MaterialTheme.colors.secondary
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = {
                    showSearches.value = false
                    imageListViewModel.handleQuery(query.value)
                    focusManager.clearFocus()
                })
            )
            IconButton(onClick = {
                showSearches.value = false
                imageListViewModel.handleQuery(query.value)
                focusManager.clearFocus()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_button_CD)
                )
            }
        }
        if (showSearches.value) {
            Searches(previousQueryList.value, imageListViewModel)
        }
    }
}

@Composable
fun Searches(previousSearches: ArrayDeque<String>?, imageListViewModel: ImageListViewModel) {
    val focusManager = LocalFocusManager.current
    Box() {
        previousSearches?.let { searches ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                when {
                    searches.isNotEmpty() -> {
                        Text(
                            text = stringResource(R.string.recent_searches_header),
                            style = MaterialTheme.typography.h6
                        )
                        searches.forEach { term ->
                            SearchRow(searchTerm = term) {
                                imageListViewModel.handleQuery(term)
                                focusManager.clearFocus()
                            }
                        }
                    }
                    else -> {
                        Text(
                            text = stringResource(R.string.no_recent_searches_header),
                            style = MaterialTheme.typography.h6
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchRow(searchTerm: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick() }
    ) {
        Text(searchTerm, style = MaterialTheme.typography.body1)
    }
}