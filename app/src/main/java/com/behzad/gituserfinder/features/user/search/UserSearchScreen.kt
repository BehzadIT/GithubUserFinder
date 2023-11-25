package com.behzad.gituserfinder.features.user.search

import androidx.activity.compose.ReportDrawn
import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.behzad.gituserfinder.R
import com.behzad.gituserfinder.features.shared.LoadableData
import com.behzad.gituserfinder.features.shared.getErrorMessage
import com.behzad.gituserfinder.features.user.data.GithubUser
import com.behzad.gituserfinder.features.user.detail.ToastMessage
import org.koin.androidx.compose.koinViewModel


@Composable
fun UserSearchScreen(
    modifier: Modifier = Modifier,
    viewModel: UserSearchViewModel = koinViewModel(),
    navController: NavController

) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(label = { Text(text = stringResource(id = R.string.search_input_hint)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = searchQuery,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = "Search"
                )
            },
            onValueChange = { text ->
                viewModel.searchForUsers(text)
            })

        if (searchQuery.isBlank()) EmptySearchQuery(modifier)
        else ResultsForValidSearchQuery(
            viewModel, navController, modifier.align(CenterHorizontally)
        )
    }
}

@Composable
private fun ResultsForValidSearchQuery(
    viewModel: UserSearchViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val users by viewModel.searchResults.collectAsState()

    when (val result = users) {
        is LoadableData.Failed -> {
            val errorMessage = result.exception.getErrorMessage(LocalContext.current)
            ToastMessage(errorMessage)
        }

        is LoadableData.Loaded -> {
            if (result.data.isEmpty()) {
                //no result found
            } else {
                GithubUserList(result.data, modifier) {
                    navController.navigate("userDetail?username=${it.login}&avatar=${it.avatarUrl}")
                }
            }
        }

        is LoadableData.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(64.dp)
                    .padding(top = 64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }

        LoadableData.NotLoaded -> {}
    }
}

@Composable
private fun GithubUserList(
    users: List<GithubUser>,
    modifier: Modifier = Modifier,
    onItemClick: (item: GithubUser) -> Unit,
) {
    val gridState = rememberLazyListState()
    ReportDrawnWhen { gridState.layoutInfo.totalItemsCount > 0 }
    LazyColumn {
        items(users) { user ->
            GithubUserRow(user, modifier, onItemClick)
        }
    }
}

@Composable
private fun EmptySearchQuery(modifier: Modifier = Modifier) {
    // Calls reportFullyDrawn when this composable is composed.
    ReportDrawn()

    Column(
        modifier, horizontalAlignment = CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.empty_search_results_placeholder),
            style = MaterialTheme.typography.titleLarge
        )
    }
}