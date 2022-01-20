package fr.leboncoin.bcalbums.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.leboncoin.bcalbums.R
import fr.leboncoin.bcalbums.adapters.AlbumsRecyclerAdapter
import fr.leboncoin.bcalbums.utils.*
import fr.leboncoin.bcalbums.views.AlbumItemView.AlbumItemViewAdapter
import java.lang.ref.WeakReference

class MainView(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private var mainViewCallbackWeakReference: WeakReference<MainViewCallback> = WeakReference(null)

    interface MainViewCallback {
        fun onItemClickedAtIndex(index: Int)
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

            it.onItemClicked(onClick = { position, _ ->
                mainViewCallbackWeakReference.get()?.onItemClickedAtIndex(position)
            })

            val spacing = convertDpToPx(5);

            it.setPadding(spacing, spacing, spacing, spacing)
            it.clipToPadding = false
            it.clipChildren = false
            it.addItemDecoration(MarginItemDecoration(spacing, GRID))
        }
    }

    fun setMainViewCallback(mainViewCallback: MainViewCallback) {
        mainViewCallbackWeakReference = WeakReference(mainViewCallback)
    }

    fun fillViewWithData(albumItemViewAdapters: List<AlbumItemViewAdapter>) {
        recyclerView.adapter = AlbumsRecyclerAdapter(albumItemViewAdapters)
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
        val viewWidth = View.MeasureSpec.getSize(widthMeasureSpec)
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