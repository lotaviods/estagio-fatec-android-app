package com.github.lotaviods.linkfatec.ui.components.text

import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.widget.ScrollView
import android.widget.TextView
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.text.util.LinkifyCompat
import com.github.lotaviods.linkfatec.R

@Composable
fun BodyCustomText(modifier: Modifier = Modifier, text: String?) {
    val context = LocalContext.current
    val customLinkifyTextView = remember {
        TextView(context)
    }

    Box(modifier) {
        AndroidView(factory = { customLinkifyTextView }) { textView ->
            textView.text = text ?: ""
            textView.setTextColor(ContextCompat.getColor(context, R.color.black))
            LinkifyCompat.addLinks(textView, Linkify.WEB_URLS)
            textView.movementMethod = LinkMovementMethod.getInstance()
            val attrs = intArrayOf(android.R.attr.scrollbarStyle)
            val typedArray = context.obtainStyledAttributes(attrs)
            val defaultScrollBarStyle = typedArray.getResourceId(0, 0)
            typedArray.recycle()
            textView.scrollBarStyle = defaultScrollBarStyle
            textView.isVerticalScrollBarEnabled = true
        }
    }
}