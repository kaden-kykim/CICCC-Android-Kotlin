package example.myapp

fun buildAquarium() {
    makeFish()

    val aquarium1 = Aquarium()
    aquarium1.printSize()
    val aquarium2 = Aquarium(width = 55)
    aquarium2.printSize()
    val aquarium3 = Aquarium(width = 60, height = 200, length = 400)
    aquarium3.printSize()
    val aquarium4 = Aquarium(numberOfFish = 100)
    aquarium4.printSize()
    aquarium4.volume = 70
    aquarium4.printSize()

    val tower1 = TowerTank(diameter = 25, height = 40)
    tower1.printSize()
}

fun makeFish() {
    val shark = Shark()
    val placo = Plecostomus()

    println("Shark: ${shark.color}")
    shark.eat()
    println("Plecostoms: ${placo.color}")
    placo.eat()
}

fun main() {
    buildAquarium()
}