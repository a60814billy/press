package me.saket.wysiwyg.parser.highlighters

import me.saket.wysiwyg.parser.SpanWriter
import me.saket.wysiwyg.parser.node.Heading
import me.saket.wysiwyg.parser.node.endOffset
import me.saket.wysiwyg.parser.node.headingLevel
import me.saket.wysiwyg.parser.node.isAtxHeading
import me.saket.wysiwyg.parser.node.openingMarker
import me.saket.wysiwyg.parser.node.startOffset

@Suppress("SpellCheckingInspection")
class HeadingVisitor : SyntaxHighlighter<Heading> {

  override fun visitor(node: Heading): NodeVisitor<Heading>? {
    // Setext styles aren't supported. Setext-style headers are "underlined" using "="
    // (for first-level headers) and dashes (for second-level headers). For example:
    // This is an H1
    // =============
    //
    // This is an H2
    // -------------
    return when {
      node.isAtxHeading -> headingVisitor()
      else -> null
    }
  }

  private fun headingVisitor() = object : NodeVisitor<Heading> {
    override fun visit(
      node: Heading,
      writer: SpanWriter
    ) {
      writer.addHeading(
          level = node.headingLevel,
          from = node.startOffset,
          to = node.endOffset
      )
      writer.addForegroundColor(
          color = writer.style.syntaxColor,
          from = node.startOffset,
          to = node.startOffset + node.openingMarker.length
      )
      writer.addForegroundColor(
          color = writer.style.heading.textColor,
          from = node.startOffset + node.openingMarker.length,
          to = node.endOffset
      )
    }
  }
}
