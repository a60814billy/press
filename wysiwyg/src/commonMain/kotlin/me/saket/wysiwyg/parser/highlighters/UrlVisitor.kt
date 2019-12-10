package me.saket.wysiwyg.parser.highlighters

import me.saket.wysiwyg.parser.RealSpanWriter
import me.saket.wysiwyg.parser.node.Url
import me.saket.wysiwyg.parser.node.endOffset
import me.saket.wysiwyg.parser.node.startOffset
import me.saket.wysiwyg.parser.node.url
import me.saket.wysiwyg.spans.SpanPool
import me.saket.wysiwyg.spans.clickableUrl
import me.saket.wysiwyg.spans.foregroundColor

class UrlVisitor : NodeVisitor<Url> {

  override fun visit(
    node: Url,
    pool: SpanPool,
    writer: RealSpanWriter
  ) {
    writer.add(
        pool.foregroundColor(pool.style.link.urlTextColor),
        node.startOffset,
        node.endOffset
    )
    writer.add(pool.clickableUrl(node.url.toString()), node.startOffset, node.endOffset)
  }
}
