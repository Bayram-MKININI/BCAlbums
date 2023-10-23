package fr.leboncoin.bcalbums.feature_albums.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import fr.leboncoin.bcalbums.R
import fr.leboncoin.bcalbums.common.util.inflate
import fr.leboncoin.bcalbums.feature_albums.domain.model.Album
import fr.leboncoin.bcalbums.feature_albums.presentation.views.AlbumItemView
import fr.leboncoin.bcalbums.feature_albums.presentation.views.AlbumItemView.*

class AlbumsRecyclerAdapter(
    private val onItemClicked: ((Album) -> Unit)? = null
) : ListAdapter<Album, AlbumItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumItemViewHolder {
        val inflatedView = parent.inflate(R.layout.album_item_layout, false)
        return AlbumItemViewHolder(inflatedView as AlbumItemView)
    }

    override fun onBindViewHolder(
        holder: AlbumItemViewHolder,
        position: Int
    ) {
        holder.albumItemView.setOnClickListener {
            onItemClicked?.invoke(getItem(position))
        }
        val album = getItem(position)
        holder.albumItemView.fillViewWithData(
            AlbumItemViewAdapter().apply {
                thumbUrl = album.url
                title = album.title
            }
        )
    }

    class DiffCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(
            oldItem: Album,
            newItem: Album
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Album,
            newItem: Album
        ) = oldItem == newItem
    }
}