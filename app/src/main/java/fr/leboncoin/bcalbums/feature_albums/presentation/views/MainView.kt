package fr.leboncoin.bcalbums.feature_albums.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.leboncoin.bcalbums.R
import fr.leboncoin.bcalbums.common.util.GRID
import fr.leboncoin.bcalbums.common.util.MarginItemDecoration
import fr.leboncoin.bcalbums.common.util.convertDpToPx
import fr.leboncoin.bcalbums.common.util.layoutToTopLeft
import fr.leboncoin.bcalbums.feature_albums.presentation.adapters.AlbumsRecyclerAdapter

class MainView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyle: Int = 0
) : ViewGroup(context, attrs, defStyle) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    var albumRecyclerAdapter
        get() = recyclerView.adapter as AlbumsRecyclerAdapter
        set(adapter) {
            recyclerView.adapter = adapter
        }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initView()
    }

    private fun initView() {

        recyclerView = findViewById(R.id.recycler_view)
        progressBar = findViewById(R.id.progress_bar)

        recyclerView.also {
            it.layoutManager = GridLayoutManager(
                context,
                context.resources.getInteger(R.integer.number_of_columns_for_thumbs)
            )
            val spacing = convertDpToPx(5)
            it.setPadding(spacing, spacing, spacing, spacing)
            it.clipToPadding = false
            it.clipChildren = false
            it.addItemDecoration(MarginItemDecoration(spacing, GRID))
        }
    }

    fun setProgressVisible(inProgress: Boolean) {
        if (inProgress) {
            progressBar.visibility = VISIBLE
            recyclerView.visibility = GONE
        } else {
            progressBar.visibility = GONE
            recyclerView.visibility = VISIBLE
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        val viewHeight = MeasureSpec.getSize(heightMeasureSpec)

        recyclerView.measure(
            MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY)
        )

        if (progressBar.visibility == VISIBLE)
            progressBar.measure(
                MeasureSpec.makeMeasureSpec(convertDpToPx(45), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(convertDpToPx(45), MeasureSpec.EXACTLY)
            )

        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY)
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val viewWidth = right - left
        val viewHeight = bottom - top

        recyclerView.layoutToTopLeft(0, 0)

        if (progressBar.visibility == VISIBLE)
            progressBar.layoutToTopLeft(
                (viewWidth - progressBar.measuredWidth) / 2,
                (viewHeight - progressBar.measuredHeight) / 2
            )
    }
}