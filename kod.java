let x = 2
let y = 2

radio.setGroup(1)

input.onButtonPressed(Button.A, function () {
    basic.showNumber(input.temperature())
})

input.onButtonPressed(Button.B, function () {
    radio.sendString("Hello")
    basic.showIcon(IconNames.Heart)
})

input.onGesture(Gesture.Shake, function () {
    x = 2
    y = 2
    basic.clearScreen()
    led.plot(x, y)
})

basic.forever(function () {
    basic.clearScreen()

    // Mozgás gyorsulásmérővel
    let ax = input.acceleration(Dimension.X)
    let ay = input.acceleration(Dimension.Y)

    if (ax > 200) {
        x = Math.min(4, x + 1)
    } else if (ax < -200) {
        x = Math.max(0, x - 1)
    }

    if (ay > 200) {
        y = Math.min(4, y + 1)
    } else if (ay < -200) {
        y = Math.max(0, y - 1)
    }

    led.plot(x, y)
    basic.pause(200)
})