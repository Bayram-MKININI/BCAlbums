package fr.leboncoin.bcalbums.common.util

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun ViewGroup.inflate(
    layoutRes: Int,
    attachToRoot: Boolean
): View = LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun <T> Flow<T>.collectLifecycleAware(
    owner: LifecycleOwner,
    action: suspend (value: T) -> Unit
) {
    owner.lifecycleScope.launch {
        this@collectLifecycleAware.flowWithLifecycle(owner.lifecycle).collectLatest {
            action.invoke(it)
        }
    }
}

fun Context?.toast(
    text: CharSequence,
    duration: Int = Toast.LENGTH_LONG
) = this?.let {
    Toast.makeText(it, text, duration).show()
}

fun View.measureWrapContent() {
    measure(
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    )
}

fun View.layoutToTopLeft(left: Int, top: Int) {
    val right = left + measuredWidth
    val bottom = top + measuredHeight
    layout(left, top, right, bottom)
}

fun View.layoutToTopRight(right: Int, top: Int) {
    val left = right - measuredWidth
    val bottom = top + measuredHeight
    layout(left, top, right, bottom)
}

fun View.layoutToBottomLeft(left: Int, bottom: Int) {
    val right = left + measuredWidth
    val top = bottom - measuredHeight
    layout(left, top, right, bottom)
}

fun View.layoutToBottomRight(right: Int, bottom: Int) {
    val left = right - measuredWidth
    val top = bottom - measuredHeight
    layout(left, top, right, bottom)
}

fun View.convertDpToPx(dpValue: Int): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dpValue.toFloat(),
    context.resources.displayMetrics
).toInt()