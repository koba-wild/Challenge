interface Element {
    fun render(builder: StringBuilder)
}

class TextElement(val text: String) : Element {
    override fun render(builder: StringBuilder) {
        builder.append("$text\n")
    }
}

@DslMarker
annotation class MarkdownTagMarker

@MarkdownTagMarker
abstract class Tag(val name: String) : Element {
    val children = arrayListOf<Element>()

    protected fun <T : Element> initTag(tag: T, init: T.() -> Unit): T {
        tag.init()
        children.add(tag)
        return tag
    }

    override fun render(builder: StringBuilder) {

    }

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder)
        return builder.toString()
    }
}

abstract class TagWithText(name: String) : Tag(name) {
    operator fun String.unaryPlus() {
        children.add(TextElement(this))
    }
}

class MARKDOWN : TagWithText("markdown") {
    fun h1(init: H1.() -> Unit) = initTag(H1(), init)
    fun h2(init: H2.() -> Unit) = initTag(H2(), init)
    fun p(init: P.() -> Unit) = initTag(P(), init)
    fun blockquotes(init: Blockquotes.() -> Unit) = initTag(Blockquotes(), init)
    fun orderedList(init: OrderedList.() -> Unit) = initTag(OrderedList(), init)

}

class H1 : TagWithText("h1")
class H2 : TagWithText("h2")
class Blockquotes : TagWithText("blockquotes")
class OrderedList : TagWithText("orderedList")


abstract class PTag(name: String) : TagWithText(name) {
    fun bold(init: Bold.() -> Unit) = initTag(Bold(), init)
    fun italic(init: Italic.() -> Unit) = initTag(Italic(), init)
    fun br(init: Br.() -> Unit) = initTag(Br(), init)
}

class P : PTag("p")
class Bold : PTag("bold")
class Italic : PTag("italic")
class Br : PTag("br")

fun markdown(init: MARKDOWN.() -> Unit): MARKDOWN {
    val markdown = MARKDOWN()
    markdown.init()
    return markdown
}
