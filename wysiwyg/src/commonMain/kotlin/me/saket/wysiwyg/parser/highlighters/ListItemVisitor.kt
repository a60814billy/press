package me.saket.wysiwyg.parser.highlighters

import me.saket.wysiwyg.parser.SpanWriter
import me.saket.wysiwyg.parser.node.BulletListItem
import me.saket.wysiwyg.parser.node.ListItem
import me.saket.wysiwyg.parser.node.OrderedListItem
import me.saket.wysiwyg.parser.node.startOffset

class OrderedListItemVisitor : ListItemVisitor<OrderedListItem>()
class BulletListItemVisitor : ListItemVisitor<BulletListItem>()

abstract class ListItemVisitor<T : ListItem> : NodeVisitor<T> {

  override fun visit(
    node: T,
    writer: SpanWriter
  ) {
    writer.addForegroundColor(writer.style.syntaxColor, node.startOffset, node.startOffset + 1)
  }
}
