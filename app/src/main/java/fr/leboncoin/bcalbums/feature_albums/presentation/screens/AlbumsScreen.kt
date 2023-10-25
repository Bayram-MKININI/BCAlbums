package fr.leboncoin.bcalbums.feature_albums.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.leboncoin.bcalbums.R
import fr.leboncoin.bcalbums.common.util.ViewState.DataState
import fr.leboncoin.bcalbums.common.util.ViewState.LoadingState
import fr.leboncoin.bcalbums.feature_albums.domain.model.Album
import fr.leboncoin.bcalbums.feature_albums.presentation.MainFragmentViewModel

@Composable
fun AlbumsScreen(
    viewModel: MainFragmentViewModel,
    onAlbumClick: (Album) -> Unit = {}
) {

    val state by viewModel.stateFlow.collectAsState()

    when (state) {
        is LoadingState -> {
            LoadingScreen()
        }

        is DataState -> {
            val albums = (state as DataState<List<Album>>).data
            LazyVerticalGrid(
                columns = GridCells.Fixed(integerResource(id = R.integer.number_of_columns_for_thumbs)),
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                content = {
                    items(albums?.count() ?: 0) { index ->
                        val album = albums?.get(index)
                        AlbumItemScreen(album, onAlbumClick)
                    }
                }
            )
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = colorResource(id = R.color.purple_500),
            strokeWidth = 2.dp
        )
    }
}

@Composable
private fun AlbumItemScreen(
    album: Album?,
    onAlbumClick: (Album) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { album?.let { onAlbumClick(album) } }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(album?.thumbnailUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.rectangle_placeholder),
            contentDescription = album?.title.orEmpty(),
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            text = album?.title.orEmpty(),
            textAlign = TextAlign.Center
        )
    }
}