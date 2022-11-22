package machine

fun main() {
    val coffeeMachine = CoffeeMachine()
    while (coffeeMachine.console()) {
    }
}

class CoffeeMachine(
    reserveOfWater: Int = 400,
    reserveOfMilk: Int = 540,
    reserveOfCoffeeBeans: Int = 120,
    reserveOfCups: Int = 9,
    moneyInMachine: Int = 550,
) {
    private var machineState = CoffeeMachineStates.NULL
    private var reserveOfWater = reserveOfWater
        get() {
            if (machineState.canReadProperties) return field
            else {
                throw Exception("You have no permissions to read property")
            }
        }
        set(value) {
            if (machineState.canChangeProperties) field = value
            else {
                throw Exception("You have no permissions to change property")
            }
        }
    private var reserveOfMilk = reserveOfMilk
        get() {
            if (machineState.canReadProperties) return field
            else {
                throw Exception("You have no permissions to read property")
            }
        }
        set(value) {
            if (machineState.canChangeProperties) field = value
            else {
                throw Exception("You have no permissions to change property")
            }
        }
    private var reserveOfCoffeeBeans = reserveOfCoffeeBeans
        get() {
            if (machineState.canReadProperties) return field
            else {
                throw Exception("You have no permissions to read property")
            }
        }
        set(value) {
            if (machineState.canChangeProperties) field = value
            else {
                throw Exception("You have no permissions to change property")
            }
        }
    private var reserveOfCups = reserveOfCups
        get() {
            if (machineState.canReadProperties) return field
            else {
                throw Exception("You have no permissions to read property")
            }
        }
        set(value) {
            if (machineState.canChangeProperties) field = value
            else {
                throw Exception("You have no permissions to change property")
            }
        }
    private var moneyInMachine = moneyInMachine
        get() {
            if (machineState.canReadProperties) return field
            else {
                throw Exception("You have no permissions to read property")
            }
        }
        set(value) {
            if (machineState.canChangeProperties) field = value
            else {
                throw Exception("You have no permissions to change property")
            }
        }

    enum class CoffeeMachineStates(
        val description: String,
        val canChangeProperties: Boolean,
        val canReadProperties: Boolean,
    ) {
        CHOOSING_ACTION("choosing an action", false, false),
        CHOOSING_COFFEE("choosing a type of coffee", false, true),
        GET_REMAINING("get remaining of reserves", false, true),
        FILLING("filling in progress", true, true),
        TAKING_MONEY("taking cash", true, true),
        MAKING_DRINK("make chosen drink", true, true),
        NULL("nothing", false, false)
    }

    enum class Drinks(
        val title: String,
        val water: Int,
        val milk: Int,
        val beans: Int,
        val cups: Int,
        val cost: Int
    ) {
        ESPRESSO("espresso", 250, 0, 16, 1, 4),
        LATTE("latte", 350, 75, 20, 1, 7),
        CAPPUCCINO("cappuccino", 200, 100, 12, 1, 6),
    }

    enum class Commands(val command: String) {
        BUY("buy"),
        FILL("fill"),
        TAKE("take"),
        REMAINING("remaining"),
        EXIT("exit")
    }

    private fun getSummaryMachineHas(): String {
        return "The coffee machine has:\n" +
                "$reserveOfWater ml of water\n" +
                "$reserveOfMilk ml of milk\n" +
                "$reserveOfCoffeeBeans g of coffee beans\n" +
                "$reserveOfCups disposable cups\n" +
                "\$$moneyInMachine of money"
    }

    fun console(): Boolean {
        var noExit = true

        println("Write action (buy, fill, take, remaining, exit):")
        machineState = CoffeeMachineStates.CHOOSING_ACTION
        when (readln()) {
            Commands.BUY.command -> {
                machineState = CoffeeMachineStates.CHOOSING_COFFEE
                actionBuy()
            }
            Commands.FILL.command -> {
                machineState = CoffeeMachineStates.FILLING
                actionFill()
            }
            Commands.TAKE.command -> {
                machineState = CoffeeMachineStates.TAKING_MONEY
                actionTAKE()
            }
            Commands.REMAINING.command -> {
                machineState = CoffeeMachineStates.GET_REMAINING
                println(getSummaryMachineHas())
            }
            Commands.EXIT.command -> noExit = false
        }
        return noExit

    }

    private fun actionBuy() {
        println(
            "What do you want to buy? 1 - ${Drinks.ESPRESSO.title}, " +
                    "2 - ${Drinks.LATTE.title}, " +
                    "3 - ${Drinks.CAPPUCCINO.title}, " +
                    "back - to main menu:"
        )
        when (readln()) {
            "1" -> makeDrink(Drinks.ESPRESSO)
            "2" -> makeDrink(Drinks.LATTE)
            "3" -> makeDrink(Drinks.CAPPUCCINO)
            "back" -> return
        }
    }

    private fun makeDrink(drink: Drinks) {
        if (getMissingIngredientsList(drink).isEmpty()) {
            println("I have enough resources, making you a coffee!")
            machineState = CoffeeMachineStates.MAKING_DRINK

            reserveOfWater -= drink.water
            reserveOfMilk -= drink.milk
            reserveOfCoffeeBeans -= drink.beans
            reserveOfCups -= drink.cups
            moneyInMachine += drink.cost
        } else {
            for (ingredient in getMissingIngredientsList(drink))
                println("Sorry, not enough $ingredient")
        }
    }

    private fun getMissingIngredientsList(drink: Drinks): List<String> {
        val whatAreMissing = emptyList<String>().toMutableList()

        if (reserveOfWater - drink.water < 0) whatAreMissing.add("water")
        if (reserveOfMilk - drink.milk < 0) whatAreMissing.add("milk")
        if (reserveOfCoffeeBeans - drink.beans < 0) whatAreMissing.add("coffee beans")
        if (reserveOfCups - drink.cups < 0) whatAreMissing.add("cups")

        return whatAreMissing.toList()
    }

    private fun actionFill() {
        println("Write how many ml of water you want to add:")
        reserveOfWater += readln().toInt()
        println("Write how many ml of milk you want to add:")
        reserveOfMilk += readln().toInt()
        println("Write how many grams of coffee beans you want to add:")
        reserveOfCoffeeBeans += readln().toInt()
        println("Write how many disposable cups you want to add:")
        reserveOfCups += readln().toInt()
    }

    private fun actionTAKE() {
        println("I gave you \$${moneyInMachine}")
        moneyInMachine = 0
    }

}
