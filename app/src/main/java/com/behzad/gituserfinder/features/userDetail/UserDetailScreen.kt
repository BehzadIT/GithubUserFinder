package com.behzad.gituserfinder.features.userDetail

import androidx.activity.compose.ReportDrawn
import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.behzad.gituserfinder.features.userSearch.data.GithubUser
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import androidx.compose.ui.Alignment.Companion as Alignment1


@Composable
fun UserDetailScreen(
    modifier: Modifier = Modifier,
    username: String,
    viewModel: UserDetailViewModel = getViewModel(
        parameters = { parametersOf(username) }
    ),
    navController: NavController

) {
    val userDetail by viewModel.userDetail.collectAsState()
    Column {
        AsyncImage(
            model = userDetail.data?.avatarUrl,
            contentDescription = null,
        )
    }
}

@Composable
fun UserDetailScreen(
    modifier: Modifier = Modifier,
    users: List<GithubUser>,
    onItemClick: (item: GithubUser) -> Unit,
) {
    if (users.isEmpty()) {
        EmptySearchQuery(modifier)
    } else {
        GithubUserList(users, modifier, onItemClick)
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
        }
    }
}

@Composable
private fun EmptySearchQuery(modifier: Modifier = Modifier) {
    // Calls reportFullyDrawn when this composable is composed.
    ReportDrawn()

    Column(
        modifier,
        horizontalAlignment = Alignment1.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Search for users", style = MaterialTheme.typography.headlineSmall
        )
    }
}