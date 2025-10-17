let rounds = 5
let currentRound = 0
let signalShown = false
let startTime = 0

let playerATime = 0
let playerBTime = 0

let playerAScore = 0
let playerBScore = 0

function showReady() {
    basic.clearScreen()
    basic.showString("R" + (currentRound + 1))
    basic.clearScreen()
}

function showSignal() {
    basic.showIcon(IconNames.Square)
    signalShown = true
    startTime = input.runningTime()
}

function finishGame() {
    basic.clearScreen()
    basic.showString("VEGE")

    // Pontszámok kiírása
    basic.showString("A:")
    basic.showNumber(playerAScore)
    basic.showString("T:")
    basic.showNumber(playerATime)

    basic.showString("B:")
    basic.showNumber(playerBScore)
    basic.showString("T:")
    basic.showNumber(playerBTime)

    // Győztes kiírása
    if (playerAScore > playerBScore) {
        basic.showString("A NYERT")
    } else if (playerBScore > playerAScore) {
        basic.showString("B NYERT")
    } else {
        basic.showString("DONTETLEN")
    }

    basic.pause(5000)
    control.reset()
}

// Gomb A
input.onButtonPressed(Button.A, function () {
    if (!signalShown) {
        playerATime += 1000 // Büntetés: +1000 ms
    } else {
        let reaction = input.runningTime() - startTime
        playerATime += reaction
        playerAScore++
        signalShown = false
        nextRound()
    }
})

// Gomb B
input.onButtonPressed(Button.B, function () {
    if (!signalShown) {
        playerBTime += 1000 // Büntetés: +1000 ms
    } else {
        let reaction = input.runningTime() - startTime
        playerBTime += reaction
        playerBScore++
        signalShown = false
        nextRound()
    }
})

function nextRound() {
    currentRound++
    if (currentRound >= rounds) {
        finishGame()
    } else {
        playRound()
    }
}

function playRound() {
    showReady()
    signalShown = false
    // Egyre gyorsuló játékmenet
    let minWait = Math.max(1000 - currentRound * 150, 200)
    basic.pause(randint(minWait, minWait + 1000))
    showSignal()
}

// Játék indítása
currentRound = 0
playerAScore = 0
playerBScore = 0
playerATime = 0
playerBTime = 0

basic.showString("START")
playRound()