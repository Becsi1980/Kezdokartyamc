let snake: Position[] = []
let direction = [0, -1]  // Kezdetben felfelé
let apple: Position = null

class Position {
    constructor(public x: number, public y: number) {}

    equals(other: Position): boolean {
        return this.x === other.x && this.y === other.y
    }

    add(dir: number[]): Position {
        return new Position(this.x + dir[0], this.y + dir[1])
    }

    isInside(): boolean {
        return this.x >= 0 && this.x < 5 && this.y >= 0 && this.y < 5
    }
}

function placeApple() {
    let empty: Position[] = []
    for (let x = 0; x < 5; x++) {
        for (let y = 0; y < 5; y++) {
            let p = new Position(x, y)
            if (!snake.some(s => s.equals(p))) {
                empty.push(p)
            }
        }
    }
    apple = empty[randint(0, empty.length - 1)]
}

function draw() {
    basic.clearScreen()
    // Kígyó kirajzolása
    for (let i = 0; i < snake.length; i++) {
        let brightness = i == snake.length - 1 ? 255 : 100
        led.plotBrightness(snake[i].x, snake[i].y, brightness)
    }
    // Alma kirajzolása
    if (apple) {
        led.plotBrightness(apple.x, apple.y, 40)
    }
}

function gameOver() {
    basic.showIcon(IconNames.Skull)
    control.reset()
}

// ----- Irányítás döntéssel -----
input.onGesture(Gesture.TiltLeft, function () {
    if (direction[0] != 1) direction = [-1, 0]  // Balra
})
input.onGesture(Gesture.TiltRight, function () {
    if (direction[0] != -1) direction = [1, 0]  // Jobbra
})
input.onGesture(Gesture.LogoUp, function () {
    if (direction[1] != 1) direction = [0, -1]  // Felfelé
})
input.onGesture(Gesture.LogoDown, function () {
    if (direction[1] != -1) direction = [0, 1]  // Lefelé
})

// ----- Játék indítás -----
snake = [new Position(2, 4)]
placeApple()
draw()

basic.forever(function () {
    basic.pause(500)

    let head = snake[snake.length - 1]
    let next = head.add(direction)

    if (!next.isInside() || snake.some(s => s.equals(next))) {
        gameOver()
    }

    if (apple && next.equals(apple)) {
        snake.push(next)
        placeApple()
    } else {
        snake.push(next)
        snake.shift()
    }

    draw()
})