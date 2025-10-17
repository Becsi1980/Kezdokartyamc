let startTime = 0
let waiting = false

basic.showString("GO!")

input.onButtonPressed(Button.A, function () {
    if (waiting) {
        // Korai nyomás = csalás
        basic.showString("Too soon!")
        control.reset()
    } else {
        // Mérjük a reakcióidőt
        let reactionTime = input.runningTime() - startTime
        basic.showNumber(reactionTime)
        basic.pause(1000)
        control.reset()
    }
})

basic.forever(function () {
    // Várakozás véletlen ideig (2-5 másodperc)
    let wait = randint(2000, 5000)
    basic.pause(wait)

    // Jelzés, hogy most kell nyomni!
    basic.showIcon(IconNames.Happy)
    startTime = input.runningTime()
    waiting = false
})