package fr.leboncoin.bcalbums.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.ViewSizeResolver
import fr.leboncoin.bcalbums.R
import fr.leboncoin.bcalbums.utils.convertDpToPx
import fr.leboncoin.bcalbums.utils.layoutToTopLeft

class AlbumItemView(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private lateinit var thumbImageView: ImageView
    private lateinit var titleTextView: TextView

    class AlbumItemViewHolder(val albumItemView: AlbumItemView) :
        RecyclerView.ViewHolder(albumItemView)

    class AlbumItemViewAdapter {
        var thumbUrl: String = ""
        var miniThumbUrl: String = ""
        var title: String = ""
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initView()
    }

    private fun initView() {
        thumbImageView = findViewById(R.id.thumb_image_view)
        titleTextView = findViewById(R.id.title_text_view)
    }

    fun fillViewWithData(albumItemViewAdapter: AlbumItemViewAdapter) {

        thumbImageView.load(albumItemViewAdapter.thumbUrl) {
            crossfade(true)
            placeholder(R.drawable.rectangle_placeholder)
            size(ViewSizeResolver(thumbImageView))
            listener(
                onError = { _, _ ->
                    /*
                    This where I would notify a monitoring service about a missing thumb
                    Likewise the monitoring service would be useful to notify about any mission media resource in backend

                    Example call of the monitoring service:
                    MonitoringManager.sendMonitoringMessage(MonitoringActionType.IMAGE_UNREACHABLE_ERROR, albumItemViewAdapter.thumbUrl.toString())
                     */
                }
            )
        }

        titleTextView.text = albumItemViewAdapter.title
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        var viewHeight = MeasureSpec.getSize(heightMeasureSpec)

        thumbImageView.measure(
            MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY)
        )

        titleTextView.measure(
            MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.AT_MOST),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )

        viewHeight = thumbImageView.measuredHeight + convertDpToPx(5) + titleTextView.measuredHeight

        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY)
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val viewWidth = right - left
        val viewHeight = bottom - top

        thumbImageView.layoutToTopLeft(0, 0)
        titleTextView.layoutToTopLeft(
            (viewWidth - titleTextView.measuredWidth) / 2,
            thumbImageView.bottom + convertDpToPx(5)
        )
    }
}