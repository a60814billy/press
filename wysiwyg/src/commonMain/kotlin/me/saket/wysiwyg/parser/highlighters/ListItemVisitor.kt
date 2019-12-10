package me.saket.wysiwyg.parser.highlighters

import me.saket.wysiwyg.parser.RealSpanWriter
import me.saket.wysiwyg.parser.node.BulletListItem
import me.saket.wysiwyg.parser.node.ListItem
import me.saket.wysiwyg.parser.node.OrderedListItem
import me.saket.wysiwyg.parser.node.startOffset
import me.saket.wysiwyg.spans.SpanPool
import me.saket.wysiwyg.spans.foregroundColor

class OrderedListItemVisitor : ListItemVisitor<OrderedListItem>()
class BulletListItemVisitor : ListItemVisitor<BulletListItem>()

abstract class ListItemVisitor<T : ListItem> : NodeVisitor<T> {

  override fun visit(
    node: T,
    pool: SpanPool,
    writer: RealSpanWriter
  ) {
    writer.add(pool.foregroundColor(pool.style.syntaxColor), node.startOffset, node.startOffset + 1)
  }
}
