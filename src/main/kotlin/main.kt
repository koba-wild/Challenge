fun result() =
    markdown {
        h1 {+"I am heading level 1"}
        h2 {+"I am heading level 2"}
        p {
            +"First line of paragraphs"
            br(){} //line break
            bold {+"I am bold text"}
            italic {+"I am italic text"}
            blockquotes{
                +"blockqoutes"
                this@p.blockquotes{+"nested blockqoutes"}
            }
            orderedList {
                +"First item"
                +"Second item"
                this@p.orderedList {
                    +"Indented first item"
                    +"Indented second item"
                }
            }
        }

    }
fun main() {
    result()
}