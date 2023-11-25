package com.behzad.gituserfinder.features.user.detail

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.behzad.gituserfinder.R
import com.behzad.gituserfinder.features.shared.LoadableData
import com.behzad.gituserfinder.features.shared.getErrorMessage
import com.behzad.gituserfinder.features.user.data.GithubUserDetail
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun UserDetailScreen(
    modifier: Modifier = Modifier,
    username: String,
    avatar: String,
    viewModel: UserDetailViewModel = getViewModel(parameters = { parametersOf(username) }),
    navController: NavController

) {
    val userDetail by viewModel.userDetail.collectAsState()
    Column {
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .padding(32.dp)
                .clip(shape = CircleShape),
            model = avatar,
            contentDescription = null,
        )
        Text(
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(bottom = 16.dp),
            style = MaterialTheme.typography.titleLarge,
            text = username
        )
        when (val result = userDetail) {
            is LoadableData.Failed -> {
                val errorMessage = result.exception.getErrorMessage(LocalContext.current)
                ToastMessage(errorMessage)
            }

            is LoadableData.Loaded -> AllUserInfo(result.data)
            is LoadableData.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }

            LoadableData.NotLoaded -> {}
        }
    }
}

@Composable
fun ToastMessage(toastMessage: String) {
    val context = LocalContext.current
    LaunchedEffect(toastMessage) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun AllUserInfo(userDetail: GithubUserDetail, modifier: Modifier = Modifier) {
    userDetail.name?.let { name ->
        UserInfoRow(Icons.Default.AccountCircle, name)
    }
    UserInfoRow(
        Icons.Default.Person, stringResource(
            R.string.user_detail_follow_info, userDetail.followers, userDetail.following
        )
    )
    userDetail.bio?.let { bio ->
        UserInfoRow(Icons.Default.Info, stringResource(R.string.user_detail_bio, bio))
    }
    userDetail.company?.let { company ->
        UserInfoRow(
            Icons.Default.DateRange, stringResource(R.string.user_detail_company, company)
        )
    }
    userDetail.location?.let { location ->
        UserInfoRow(
            Icons.Default.LocationOn, stringResource(R.string.user_detail_location, location)
        )
    }
}

@Composable
fun UserInfoRow(iconIcon: ImageVector, info: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(16.dp)
    ) {
        Icon(imageVector = iconIcon, contentDescription = "Stars")
        Text(
            modifier = Modifier.padding(start = 16.dp), text = info
        )
    }
}