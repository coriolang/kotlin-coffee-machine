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
        println("\nThe coffee machine has:")
        println("$_water of water")
        println("$_milk of milk")
        println("$_beans of coffee beans")
        println("$_cups of disposable cups")
        println("$_money of money")
    }

    fun hasEnoughIngredients(selectedCoffee: Int): Boolean {
        val hasEnoughIngredients: Boolean = when (selectedCoffee) {
            1 -> hasEnoughIngredients(espresso)
            2 -> hasEnoughIngredients(latte)
            else -> hasEnoughIngredients(cappuccino)
        }

        return hasEnoughIngredients
    }

    private fun hasEnoughIngredients(selectedCoffee: Coffee): Boolean {
        return !(_water < selectedCoffee.waterPerCup
                || _milk < selectedCoffee.milkPerCup
                || _beans < selectedCoffee.beansPerCup
                || _cups < 1)
    }

    fun identifyMissingIngredient(selectedCoffee: Int): String {
        val missingIngredient: String = when (selectedCoffee) {
            1 -> identifyMissingIngredient(espresso)
            2 -> identifyMissingIngredient(latte)
            else -> identifyMissingIngredient(cappuccino)
        }

        return missingIngredient
    }

    private fun identifyMissingIngredient(selectedCoffee: Coffee): String {
        val missingIngredient: String =
            if (_water < selectedCoffee.waterPerCup) {
                "water"
            } else if (_milk < selectedCoffee.milkPerCup) {
                "milk"
            } else if (_beans < selectedCoffee.beansPerCup) {
                "beans"
            } else {
                "cups"
            }

        return missingIngredient
    }

    fun makeCoffee(selectedCoffee: Int): Boolean {
        val success = when (selectedCoffee) {
            1 -> makeCoffee(espresso)
            2 -> makeCoffee(latte)
            else -> makeCoffee(cappuccino)
        }

        return success
    }

    private fun makeCoffee(selectedCoffee: Coffee): Boolean {
        _money += selectedCoffee.moneyPerCup
        _water -= selectedCoffee.waterPerCup
        _milk -= selectedCoffee.milkPerCup
        _beans -= selectedCoffee.beansPerCup
        _cups--

        return true
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
    var usersInput = ""

    while (usersInput != "exit") {
        println("\nWrite action (buy, fill, take, remaining, exit):")
        usersInput = readLine() ?: ""

        when (usersInput) {
            "buy" -> {
                println("\nWhat do you want to buy? " +
                        "1 - espresso, " +
                        "2 - latte, " +
                        "3 - cappuccino, " +
                        "back - to main menu:")
                val selectedCoffee = readLine() ?: ""

                if (selectedCoffee == "1"
                    || selectedCoffee == "2"
                    || selectedCoffee == "3") {

                    if (coffeeMachine.hasEnoughIngredients(selectedCoffee.toInt())) {
                        println("I have enough resources, making you a coffee!")
                        coffeeMachine.makeCoffee(selectedCoffee.toInt())
                    } else {
                        val missingIngredient = coffeeMachine.identifyMissingIngredient(selectedCoffee.toInt())
                        println("Sorry, not enough $missingIngredient!")
                    }
                } else {
                    continue
                }
            }
            "fill" -> {
                println("\nWrite how many ml of water do you want to add:")
                val water = readLine()?.toIntOrNull() ?: 0
                println("Write how many ml of milk do you want to add:")
                val milk = readLine()?.toIntOrNull() ?: 0
                println("Write how many grams of coffee beans do you want to add:")
                val beans = readLine()?.toIntOrNull() ?: 0
                println("Write how many disposable cups of coffee do you want to add:")
                val cups = readLine()?.toIntOrNull() ?: 0

                coffeeMachine.fillMachine(water, milk, beans, cups)
            }
            "take" -> {
                println("\nI gave you \$${coffeeMachine.money}")
                coffeeMachine.takeMoney()
            }
            "remaining" -> {
                coffeeMachine.ingredientsInfo()
            }
        }
    }
}
