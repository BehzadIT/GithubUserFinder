package com.behzad.gituserfinder.features.user.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.behzad.gituserfinder.features.shared.ToastMessage
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
        horizontalAlignment = CenterHorizontally
    ) {
        SearchBox(searchQuery = searchQuery, onSearchTextChanged = {
            viewModel.searchForUsers(it)
        })

        //search query is valid, show the results
        if (searchQuery.isValidSearchQuery()) {
            ResultsForValidSearchQuery(viewModel, navController, modifier.align(CenterHorizontally))
        }
        //search query is too short, how the hint
        else {
            InitialSearchHint()
        }
    }
}

@Composable
private fun SearchBox(searchQuery: String, onSearchTextChanged: (query: String) -> Unit) {
    TextField(
        label = { Text(text = stringResource(id = R.string.search_input_hint)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        value = searchQuery,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = "Search"
            )
        },
        onValueChange = onSearchTextChanged
    )
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
            //search result is empty
            if (result.data.isEmpty()) {
                EmptyResults()
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
    LazyColumn {
        items(users) { user ->
            GithubUserRow(user, modifier, onItemClick)
        }
    }
}

@Composable
private fun InitialSearchHint() {
    Text(
        text = stringResource(id = R.string.initial_search_screen_placeholder),
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun EmptyResults() {
    Text(
        text = stringResource(id = R.string.empty_search_results_placeholder),
        style = MaterialTheme.typography.titleLarge
    )
}
