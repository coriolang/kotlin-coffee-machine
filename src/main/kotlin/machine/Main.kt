package machine

class CoffeeMachine(
    private var _money: Int = 550,
    private var _water: Int = 400,
    private var _milk: Int = 540,
    private var _beans: Int = 120,
    private var _cups: Int = 9
) {

    private val espresso: Coffee = Coffee(250, 0, 16, 4)
    private val latte: Coffee = Coffee(350, 75, 20, 7)
    private val cappuccino: Coffee = Coffee(200, 100, 12, 6)

    val money: Int
        get() = _money

    fun ingredientsInfo() {
        println("The coffee machine has:")
        println("$_water of water")
        println("$_milk of milk")
        println("$_beans of coffee beans")
        println("$_cups of disposable cups")
        println("$_money of money")
    }

    fun makeCoffee(selectedCoffee: Int) {
        when (selectedCoffee) {
            1 -> makeCoffee(espresso)
            2 -> makeCoffee(latte)
            3 -> makeCoffee(cappuccino)
        }
    }

    private fun makeCoffee(selectedCoffee: Coffee) {
        if (_water < selectedCoffee.waterPerCup
            || _milk < selectedCoffee.milkPerCup
            || _beans < selectedCoffee.beansPerCup
            || _cups < 1) {

            return
        }

        _money += selectedCoffee.moneyPerCup
        _water -= selectedCoffee.waterPerCup
        _milk -= selectedCoffee.milkPerCup
        _beans -= selectedCoffee.beansPerCup
        _cups--
    }

    fun fillMachine(water: Int, milk: Int, beans: Int, cups: Int) {
        _water += water
        _milk += milk
        _beans += beans
        _cups += cups
    }

    fun takeMoney() {
        _money = 0
    }
}

data class Coffee(
    val waterPerCup: Int,
    val milkPerCup: Int,
    val beansPerCup: Int,
    val moneyPerCup: Int
)

fun main(args: Array<String>) {
    val coffeeMachine = CoffeeMachine()

    coffeeMachine.ingredientsInfo()

    println("Write action (buy, fill, take): > ")
    val usersInput = readLine() ?: ""

    when (usersInput) {
        "buy" -> {
            println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: > 3")
            val selectedCoffee = readLine()?.toIntOrNull() ?: 0
            coffeeMachine.makeCoffee(selectedCoffee)
        }
        "fill" -> {
            println("\nWrite how many ml of water do you want to add: > ")
            val water = readLine()?.toIntOrNull() ?: 0
            println("Write how many ml of milk do you want to add: > ")
            val milk = readLine()?.toIntOrNull() ?: 0
            println("Write how many grams of coffee beans do you want to add: > ")
            val beans = readLine()?.toIntOrNull() ?: 0
            println("Write how many disposable cups of coffee do you want to add: > ")
            val cups = readLine()?.toIntOrNull() ?: 0

            coffeeMachine.fillMachine(water, milk, beans, cups)
        }
        "take" -> {
            println("\nI gave you \$${coffeeMachine.money}")
            coffeeMachine.takeMoney()
        }
    }

    coffeeMachine.ingredientsInfo()
}
