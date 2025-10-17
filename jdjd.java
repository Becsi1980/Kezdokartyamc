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
    for (let i = 0; i < snake.length; i++) {
        let brightness = i == snake.length - 1 ? 255 : 100
        led.plotBrightness(snake[i].x, snake[i].y, brightness)
    }
    if (apple) {
        led.plotBrightness(apple.x, apple.y, 40)
    }
}

function gameOver() {
    basic.showIcon(IconNames.Skull)
    control.reset()
}

function updateDirectionFromTilt() {
    let x = input.acceleration(Dimension.X)
    let y = input.acceleration(Dimension.Y)

    // érzékenységi küszöb (kisebb = érzékenyebb)
    let threshold = 200

    if (Math.abs(x) > Math.abs(y)) {
        if (x < -threshold && direction[0] != 1) direction = [-1, 0]  // Bal
        else if (x > threshold && direction[0] != -1) direction = [1, 0]  // Jobb
    } else {
        if (y < -threshold && direction[1] != 1) direction = [0, -1]  // Fel
        else if (y > threshold && direction[1] != -1) direction = [0, 1]   // Le
    }
}

// ----- Játék indítás -----
snake = [new Position(2, 4)]
placeApple()
draw()

basic.forever(function () {
    basic.pause(500)

    updateDirectionFromTilt()

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