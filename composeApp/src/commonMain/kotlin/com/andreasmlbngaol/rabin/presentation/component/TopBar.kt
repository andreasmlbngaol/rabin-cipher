package com.andreasmlbngaol.rabin.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.andreasmlbngaol.rabin.presentation.navigation.Navigator
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
@Preview
fun TopBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    type: TopBarType = TopBarType.Default,
    navigator: Navigator = koinInject()
) {
    @Composable
    fun navigationIcon() {
        AnimatedVisibility(navigator.canGoBack) {
            FilledIconButton(
                onClick = navigator::goBack,
                shapes = IconButtonDefaults.shapes()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    }

    val colors = TopAppBarDefaults.topAppBarColors().copy(
//        containerColor = MaterialTheme.colorScheme.primaryContainer,
        scrolledContainerColor = Color.Transparent,
        navigationIconContentColor = MaterialTheme.colorScheme.secondary,
//        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        actionIconContentColor = MaterialTheme.colorScheme.primary
    )

    when (type) {
        TopBarType.Centered -> {
            CenterAlignedTopAppBar(
                title = { Text(title) },
                navigationIcon = { navigationIcon() },
                scrollBehavior = scrollBehavior,
                colors = colors
            )
        }
        TopBarType.Default -> {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = { navigationIcon() },
                scrollBehavior = scrollBehavior,
                colors = colors
            )
        }
    }
}

enum class TopBarType {
    Centered,
    Default
}
