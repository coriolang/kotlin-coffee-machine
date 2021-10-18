package machine

const val WRITE_ACTION = "\nWrite action (buy, fill, take, remaining, exit):"

enum class MachineState {
    CHOOSING_AN_ACTION,
    CHOOSING_A_VARIANT_OF_COFFEE,
    ADDING_WATER,
    ADDING_MILK,
    ADDING_BEANS,
    ADDING_CUPS
}

class CoffeeMachine(
    private var _money: Int = 550,
    private var _water: Int = 400,
    private var _milk: Int = 540,
    private var _beans: Int = 120,
    private var _cups: Int = 9
) {

    var machineState: MachineState = MachineState.CHOOSING_AN_ACTION

    private val espresso: Coffee = Coffee(250, 0, 16, 4)
    private val latte: Coffee = Coffee(350, 75, 20, 7)
    private val cappuccino: Coffee = Coffee(200, 100, 12, 6)

    init {
        println(WRITE_ACTION)
    }

    fun takeCommand(command: String) {
        when (machineState) {
            MachineState.CHOOSING_AN_ACTION -> {
                when (command) {
                    "buy" -> {
                        println("\nWhat do you want to buy? " +
                                "1 - espresso, " +
                                "2 - latte, " +
                                "3 - cappuccino, " +
                                "back - to main menu:")
                        machineState = MachineState.CHOOSING_A_VARIANT_OF_COFFEE
                    }
                    "fill" -> {
                        println("\nWrite how many ml of water do you want to add:")
                        machineState = MachineState.ADDING_WATER
                    }
                    "take" -> {
                        println("\nI gave you \$${_money}")
                        _money = 0
                        println(WRITE_ACTION)
                    }
                    "remaining" -> {
                        ingredientsInfo()
                        println(WRITE_ACTION)
                    }
                }
            }
            MachineState.CHOOSING_A_VARIANT_OF_COFFEE -> {
                machineState = MachineState.CHOOSING_AN_ACTION

                if (command == "1"
                    || command == "2"
                    || command == "3") {

                    if (hasEnoughIngredients(command.toInt())) {
                        println("I have enough resources, making you a coffee!")
                        makeCoffee(command.toInt())
                    } else {
                        val missingIngredient = identifyMissingIngredient(command.toInt())
                        println("Sorry, not enough $missingIngredient!")
                    }
                }

                println(WRITE_ACTION)
            }
            MachineState.ADDING_WATER -> {
                val ingredient = command.toInt()
                _water += ingredient
                machineState = MachineState.ADDING_MILK
                println("Write how many ml of milk do you want to add:")
            }
            MachineState.ADDING_MILK -> {
                val ingredient = command.toInt()
                _milk += ingredient
                machineState = MachineState.ADDING_BEANS
                println("Write how many grams of coffee beans do you want to add:")
            }
            MachineState.ADDING_BEANS -> {
                val ingredient = command.toInt()
                _beans += ingredient
                machineState = MachineState.ADDING_CUPS
                println("Write how many disposable cups of coffee do you want to add:")
            }
            MachineState.ADDING_CUPS -> {
                val ingredient = command.toInt()
                _cups += ingredient
                machineState = MachineState.CHOOSING_AN_ACTION
                println(WRITE_ACTION)
            }
        }
    }

    private fun ingredientsInfo() {
        println("\nThe coffee machine has:")
        println("$_water of water")
        println("$_milk of milk")
        println("$_beans of coffee beans")
        println("$_cups of disposable cups")
        println("$_money of money")
    }

    private fun hasEnoughIngredients(selectedCoffee: Int): Boolean {
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

    private fun identifyMissingIngredient(selectedCoffee: Int): String {
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

    private fun makeCoffee(selectedCoffee: Int): Boolean {
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
        usersInput = readLine() ?: ""
        coffeeMachine.takeCommand(usersInput)
    }
}
