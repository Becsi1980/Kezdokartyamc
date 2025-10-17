let pattern: number[] = []
let userStep = 0
let playing = false

function showPattern() {
    basic.clearScreen()
    basic.pause(500)
    for (let step of pattern) {
        if (step == 0) {
            led.plot(0, 2)  // bal oldali jel
        } else {
            led.plot(4, 2)  // jobb oldali jel
        }
        basic.pause(400)
        basic.clearScreen()
        basic.pause(200)
    }
    userStep = 0
    playing = true
}

function addStep() {
    pattern.push(randint(0, 1))  // 0 = bal (A), 1 = jobb (B)
}

function gameOver() {
    playing = false
    basic.showIcon(IconNames.Skull)
    basic.pause(1000)
    basic.clearScreen()
    pattern = []
    startGame()
}

function startGame() {
    basic.showString("Go!")
    pattern = []
    addStep()
    showPattern()
}

input.onButtonPressed(Button.A, function () {
    if (!playing) return
    if (pattern[userStep] == 0) {
        userStep++
    } else {
        gameOver()
        return
    }
    checkNext()
})

input.onButtonPressed(Button.B, function () {
    if (!playing) return
    if (pattern[userStep] == 1) {
        userStep++
    } else {
        gameOver()
        return
    }
    checkNext()
})

function checkNext() {
    if (userStep == pattern.length) {
        playing = false
        basic.showIcon(IconNames.Yes)
        basic.pause(500)
        addStep()
        showPattern()
    }
}

// Indítás
startGame()