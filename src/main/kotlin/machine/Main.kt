package machine

fun main(args: Array<String>) {
    val waterPerCup = 200
    val milkPerCup = 50
    val beansPerCup = 15

    println("Write how many ml of water the coffee machine has: > ")
    val currentWater: Int = readLine()?.toIntOrNull() ?: 0
    println("Write how many ml of milk the coffee machine has: > ")
    val currentMilk: Int = readLine()?.toIntOrNull() ?: 0
    println("Write how many grams of coffee beans the coffee machine has: > ")
    val currentBeans: Int = readLine()?.toIntOrNull() ?: 0
    println("Write how many cups of coffee you will need: > ")
    val neededCups: Int = readLine()?.toIntOrNull() ?: 0

    var possibleCups: Int = currentWater / waterPerCup
    if (possibleCups > currentMilk / milkPerCup) {
        possibleCups = currentMilk / milkPerCup
    } else if (possibleCups > currentBeans / beansPerCup) {
        possibleCups = currentBeans / beansPerCup
    }

    if (possibleCups < neededCups) {
        println("No, I can make only $possibleCups cups of coffee")
    } else if (possibleCups == neededCups) {
        println("Yes, I can make that amount of coffee")
    } else {
        println("Yes, I can make that amount of coffee (and even ${possibleCups - neededCups} more than that)")
    }
}
