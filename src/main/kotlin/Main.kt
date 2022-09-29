import lab1.WikipediaSearch

fun main() {
    println("Enter search:")
    val userSearch = readln()

    try {
        val searchResult = WikipediaSearch(userSearch)

        searchResult.wikipediaResults.forEachIndexed { index, item ->
            println("$index) $item")
        }

        println("Choose page ID to open")
        val id = readln().toInt()

        searchResult.openLink(id)
    } catch (e: java.lang.Exception) {
        println(e.message)
    } catch (e: java.lang.NumberFormatException) {
        println("Enter a number!")
    } catch (e: java.lang.IndexOutOfBoundsException) {
        println("Enter a valid page ID!")
    }
}