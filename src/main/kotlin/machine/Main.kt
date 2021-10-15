package machine

fun main(args: Array<String>) {
    println("Write how many cups of coffee you will need: > ")
    val cupsNumber: Int = readLine()?.toIntOrNull() ?: 0

    val water = cupsNumber * 200
    val milk = cupsNumber * 50
    val beans = cupsNumber * 15

    println("For $cupsNumber cups of coffee you will need:")
    println("$water ml of water")
    println("$milk ml of milk")
    println("$beans g of coffee beans")
}
