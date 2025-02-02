package com.example.staticuserprofilepage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.staticuserprofilepage.ui.theme.StaticUserProfilePageTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StaticUserProfilePageTheme {
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) { innerPadding ->
                    UserProfile(
                        modifier = Modifier
                            .padding(innerPadding),
                        snackbarHostState = snackbarHostState
                    )
                }
            }
        }
    }
}

@Composable
fun UserProfile(modifier: Modifier = Modifier, snackbarHostState: SnackbarHostState) {
    val isFollowing = remember { mutableStateOf(false) }

    val snackbarMessage = if (isFollowing.value) {
        "You are now following the user."
    } else {
        "You have unfollowed the user."
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
    ) {
        Image( // profile picture default avatar
            painter = painterResource(R.drawable.avatar),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
        )

        Column(
            modifier = modifier
                .padding(20.dp)
        ) {
            Text(
                text = "username",
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "insert biography"
            )

            Button(onClick = {
                isFollowing.value = !isFollowing.value
            }) {
                Text(text = if (isFollowing.value) "Following" else "Follow")
            }

            LaunchedEffect(isFollowing.value) {
                snackbarHostState.showSnackbar(snackbarMessage)
            }
        }
    }
    LaunchedEffect(snackbarHostState) {
        snackbarHostState.showSnackbar(snackbarMessage)
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfilePreview() {
    val snackbarHostState = remember { SnackbarHostState() }
    StaticUserProfilePageTheme {
        UserProfile(snackbarHostState = snackbarHostState)
    }
}