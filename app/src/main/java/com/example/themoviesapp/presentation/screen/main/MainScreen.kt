package com.example.themoviesapp.presentation.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.themoviesapp.R
import com.example.themoviesapp.presentation.screen.main.components.MovieCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showSortMenu by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()


    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            lastVisibleItemIndex >= uiState.movies.size - 5 && uiState.movies.isNotEmpty()
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            viewModel.handleIntent(MainScreenIntents.LoadNextPage)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.main_title),
                        color = MaterialTheme.colorScheme.tertiary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    actionIconContentColor = MaterialTheme.colorScheme.secondary
                ),
                actions = {
                    IconButton(onClick = { showSortMenu = true }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Sort,
                            contentDescription = stringResource(R.string.sort_description),
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                    DropdownMenu(
                        expanded = showSortMenu,
                        onDismissRequest = { showSortMenu = false },
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                    ) {
                        val popularSelected = uiState.sort == MoviesSort.ByPopular
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(R.string.sort_popular),
                                    color = if (popularSelected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary
                                )
                            },
                            onClick = {
                                viewModel.handleIntent(MainScreenIntents.ChangeSort(MoviesSort.ByPopular))
                                showSortMenu = false
                            },
                            trailingIcon = {
                                if (popularSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.tertiary
                                    )
                                }
                            }
                        )
                        val topRatedSelected = uiState.sort == MoviesSort.ByTopRated
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(R.string.sort_top_rated),
                                    color = if (topRatedSelected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary
                                )
                            },
                            onClick = {
                                viewModel.handleIntent(MainScreenIntents.ChangeSort(MoviesSort.ByTopRated))
                                showSortMenu = false
                            },
                            trailingIcon = {
                                if (topRatedSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.tertiary
                                    )
                                }
                            }
                        )
                    }
                }
            )
        }
    )
{ padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isLoading && uiState.movies.isEmpty()) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.tertiary
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState,
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(uiState.movies) { movie ->
                        MovieCard(
                            movie = movie,
                            genreNames = movie.genreIds.mapNotNull { uiState.genresMap[it] },
                            onMovieClick = onMovieClick
                        )
                    }

                    if (uiState.isPaginationLoading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(32.dp),
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                            }
                        }
                    }
                }
            }

            uiState.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
