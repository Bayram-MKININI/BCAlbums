package fr.leboncoin.bcalbums.feature_albums.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import fr.leboncoin.bcalbums.common.util.UIEvent.ShowError
import fr.leboncoin.bcalbums.common.util.collectAsEffect
import fr.leboncoin.bcalbums.feature_albums.domain.model.Album
import fr.leboncoin.bcalbums.feature_albums.presentation.screens.AlbumsScreen
import fr.leboncoin.bcalbums.feature_albums.presentation.screens.AlertToast
import java.util.Timer
import kotlin.concurrent.schedule

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val viewModel = viewModel<MainFragmentViewModel>()
                DisplayErrorMessageIfAny(viewModel)
                HomeContent(viewModel)
            }
        }
    }

    @Composable
    private fun DisplayErrorMessageIfAny(
        viewModel: MainFragmentViewModel
    ) {
        val context = LocalContext.current
        viewModel.eventFlow.collectAsEffect(block = { uiEvent ->
            when (uiEvent) {
                is ShowError -> {
                    Toast.makeText(context, uiEvent.errorStrRes, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    @Composable
    private fun HomeContent(
        viewModel: MainFragmentViewModel
    ) {
        var selectedAlbum by remember {
            mutableStateOf<Album?>(null)
        }
        var isToastVisible by remember {
            mutableStateOf(false)
        }
        val onAlbumClickAction: (Boolean) -> Unit = { selected ->
            isToastVisible = selected
            if (isToastVisible) {
                Timer().schedule(delay = 3000) {
                    isToastVisible = false
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            AlbumsScreen(viewModel) { album ->
                selectedAlbum = album
                if (!isToastVisible) {
                    onAlbumClickAction.invoke(true)
                }
            }
            BoxWithConstraints(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                AlertToast(
                    modifier = Modifier.widthIn(min = 0.dp, max = maxWidth * 0.9f),
                    visible = isToastVisible,
                    text = selectedAlbum?.title.orEmpty()
                )
            }
        }
    }
}