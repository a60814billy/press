package press.editor

import android.text.InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
import android.text.InputType.TYPE_TEXT_FLAG_CAP_WORDS
import android.text.Spannable
import android.text.Spannable.SPAN_INCLUSIVE_INCLUSIVE
import android.widget.EditText
import androidx.core.text.getSpans
import me.saket.wysiwyg.spans.HeadingSpan
import me.saket.wysiwyg.widgets.AfterTextChange
import me.saket.wysiwyg.widgets.addTextChangedListener
import press.widgets.OnSpanAdded
import java.lang.Character.isWhitespace

/**
 * Uses [TYPE_TEXT_FLAG_CAP_WORDS] for the first letter after '# ' so that the
 * heading starts with a capital letter and [TYPE_TEXT_FLAG_CAP_SENTENCES] for
 * the rest of the content.
 */
object CapitalizeOnHeadingStart {

  fun capitalize(editText: EditText) {
    val originalInputType = editText.inputType

    val textChangeListener = { text: Spannable ->
      val isSelectingText = editText.selectionStart != editText.selectionEnd
      val headingSyntax = "#"

      val forceCapitalize = if (isSelectingText.not() && text.length >= headingSyntax.length) {
        val cursorAt = editText.selectionEnd
        val headingsUnderSyntax = text.getSpans<HeadingSpan>(
            start = cursorAt - headingSyntax.length,
            end = cursorAt
        )
        headingsUnderSyntax.isNotEmpty()
            && ((cursorAt >= 2 && text[cursorAt - 2] == '#' && isWhitespace(text[cursorAt - 1]))
            || text[cursorAt - 1] == '#')

      } else {
        false
      }

      val newInputType = when {
        forceCapitalize -> originalInputType
            .xor(TYPE_TEXT_FLAG_CAP_SENTENCES)
            .or(TYPE_TEXT_FLAG_CAP_WORDS)
        else -> originalInputType
      }

      if (newInputType != editText.inputType) {
        editText.inputType = newInputType
      }
    }

    // Wysiwyg inserts spans without changing the text so the text changer
    // will not get called if a heading span was added after the cursor moved.
    val spanWatcher = OnSpanAdded { text, span ->
      if (span is HeadingSpan) {
        textChangeListener(text)
      }
    }
    editText.addTextChangedListener(AfterTextChange { text ->
      text.setSpan(spanWatcher, 0, text.length, SPAN_INCLUSIVE_INCLUSIVE)
    })
  }
}
