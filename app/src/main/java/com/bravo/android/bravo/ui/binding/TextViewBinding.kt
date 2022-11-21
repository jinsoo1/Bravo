package com.bravo.android.bravo.ui.binding

import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter(value = ["htmlText"])
fun TextView.bindHtmlText(htmlText: String?) {
    if (htmlText == null) return

    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(htmlText.replace("\n", "<br>"), Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(htmlText.replace("\n", "<br>"))
    }
}

@BindingAdapter("underLineText")
fun TextView.bindUnderLineText(str: String) {
    val spannableStr = SpannableString(str)
    spannableStr.setSpan(UnderlineSpan(), 0, str.length, 0)
    text = spannableStr
}

@BindingAdapter("dateText")
fun TextView.formatAndBindDateText(date: Date) {
    val dateFormat = SimpleDateFormat("MM.dd", Locale.KOREA)
    text = dateFormat.format(date)
}


@BindingAdapter("bindTextTags")
fun TextView.bindTextTags(tags: List<String>?){
    tags?.run {
        val tagText = tags.joinToString(" ") { "#$it" }
        this@bindTextTags.text = tagText
    }
}