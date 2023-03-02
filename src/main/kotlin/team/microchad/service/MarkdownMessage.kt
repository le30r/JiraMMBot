package team.microchad.service

fun markdown(init: MarkdownMessage.() -> Unit): String {
    val result = MarkdownMessage()
    result.init()
    return result.result
}

class MarkdownMessage {
    var result: String = ""

    fun italics(innerText: () -> String) {
        result += "_${innerText()}_ \n"
    }

    fun bold(msg: () -> String) {
        result += "**${msg()}**\n"
    }

    fun strikethrough(msg: () -> String) {
        result += "~~${msg()}~~\n"
    }

    fun inLineCode(msg: () -> String) {
        result += "'${msg()}'\n"
    }

    fun hyperlink(msg: () -> String) {
        result += "[hyperlink](${msg()})\n"
    }

    fun h1(msg: () -> String) {
        result += "## ${msg()}\n"
    }

    fun h2(msg: () -> String) {
        result += "### ${msg()}\n"
    }

    fun h3(msg: () -> String) {
        result += "#### ${msg()}\n"
    }

    fun table(init: Table.() -> Unit) {
        val table = Table()
        table.init()
        result += table.result
    }

    class Table {
        var result = ""

        fun row(init: Row.() -> Unit) {
            val row = Row()
            row.init()
            result += row.result + "\n"
        }

        fun headerRow(init: HeaderRow.() -> Unit) {
            val row = HeaderRow()
            row.init()
            result += row.result + "\n"
        }

        open class Row {
            open val result: String
                get() = columns.toColumns()


            private val columns = mutableListOf<String>()
            open fun column(content: () -> String) {
                columns += content()
            }
        }

        class HeaderRow : Row() {
            private val columns = mutableListOf<String>()

            override val result: String
                get() {
                    val res = StringBuilder()
                    res.append(columns.toColumns() + "\n")
                    for (i in 0 until columns.count()) {
                        res.append(
                            when (i) {
                            columns.count()-1 -> {
                                "|:----:|"
                            }
                            else -> {
                                "|:----:"
                            }
                        })
                    }
                    return res.toString()
                }

            override fun column(content: () -> String) {
                columns += content()
            }
        }
    }
}

fun List<String>.toColumns(): String {
    val res: StringBuilder = StringBuilder()
    res.append("|")
    for (i in 0 until this.count()) {
        val col = this[i]
        res.append("$col|")
    }
    return res.toString()
}
