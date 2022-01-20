package fr.leboncoin.bcalbums.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.leboncoin.bcalbums.R
import fr.leboncoin.bcalbums.utils.inflate
import fr.leboncoin.bcalbums.views.AlbumItemView
import fr.leboncoin.bcalbums.views.AlbumItemView.AlbumItemViewAdapter
import fr.leboncoin.bcalbums.views.AlbumItemView.AlbumItemViewHolder

class AlbumsRecyclerAdapter(private val albumItemViewAdapters: List<AlbumItemViewAdapter>) : RecyclerView.Adapter<AlbumItemViewHolder>() {

    override fun getItemCount(): Int = albumItemViewAdapters.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumItemViewHolder {
        val inflatedView = parent.inflate(R.layout.album_item_layout, false)
        return AlbumItemViewHolder(inflatedView as AlbumItemView)
    }

    override fun onBindViewHolder(holder: AlbumItemViewHolder, position: Int) = holder.albumItemView.fillViewWithData(albumItemViewAdapters[position])
}