<<<<<<< Updated upstream
let bird;
let pipes = [];
let score = 0;
let highScore = 0; // Variable to keep track of the high score

function setup() {
  createCanvas(400, 600);
  bird = new Bird();
  pipes.push(new Pipe());
}

function draw() {
  background(255);

  bird.update();
  bird.show();

  // Create new pipes at intervals
  if (frameCount % 100 == 0) {
    pipes.push(new Pipe());
  }

  // Update and display pipes
  for (let i = pipes.length - 1; i >= 0; i--) {
    pipes[i].update();
    pipes[i].show();

    if (pipes[i].offscreen()) {
      pipes.splice(i, 1);
    }

    if (pipes[i].hits(bird)) {
      gameOver();
    }

    if (!pipes[i].passed && pipes[i].x < bird.x) {
      pipes[i].passed = true;
      score++;
    }
  }

  displayScore();
}

function keyPressed() {
  if (key == ' ') {
    bird.flap();
  }
  if (key == 'r' && bird.dead) {
    restartGame();
  }
}

function displayScore() {
  fill(0);
  textSize(32);
  textAlign(RIGHT);
  text(score, width - 20, 40);

  // Display high score
  textSize(20);
  textAlign(LEFT);
  text('High Score: ' + highScore, 20, 40);
}

function gameOver() {
  bird.dead = true;
  noLoop();

  // Update high score if current score is greater
  if (score > highScore) {
    highScore = score;
  }

  fill(0);
  textSize(50);
  textAlign(CENTER);
  text('Game Over!', width / 2, height / 3);
  textSize(30);
  text('Press R to Restart', width / 2, height / 2);
}

function restartGame() {
  bird = new Bird();
  pipes = [];
  score = 0;
  loop();
}

class Bird {
  constructor() {
    this.y = height / 2;
    this.x = 100;
    this.gravity = 0.75;
    this.lift = -12;
    this.velocity = 0;
    this.dead = false;
    this.radius = 20;
  }

  flap() {
    if (!this.dead) {
      this.velocity = this.lift;
    }
  }

  update() {
    this.velocity += this.gravity;
    this.y += this.velocity;

    if (this.y > height) {
      this.y = height;
      gameOver();
    }

    if (this.y < 0) {
      this.y = 0;
    }
  }

  show() {
    fill(0, 150, 255);
    ellipse(this.x, this.y, this.radius * 2, this.radius * 2);
  }
}

class Pipe {
  constructor() {
    this.spacing = 150;
    this.top = random(height / 6, (2 / 3) * height);
    this.bottom = height - (this.top + this.spacing);
    this.x = width;
    this.w = 80;
    this.speed = 3;
    this.passed = false;
  }

  hits(bird) {
    let birdEdgeLeft = bird.x - bird.radius;
    let birdEdgeRight = bird.x + bird.radius;
    let birdEdgeTop = bird.y - bird.radius;
    let birdEdgeBottom = bird.y + bird.radius;

    return (
      (birdEdgeTop < this.top || birdEdgeBottom > height - this.bottom) &&
      (birdEdgeRight > this.x && birdEdgeLeft < this.x + this.w)
    );
  }

  update() {
    this.x -= this.speed;
  }

  offscreen() {
    return this.x < -this.w;
  }

  show() {
    fill(0, 200, 0);
    rect(this.x, 0, this.w, this.top);
    rect(this.x, height - this.bottom, this.w, this.bottom);
  }
=======
let bird;
let pipes = [];
let score = 0;
let highScore = 0; // Variable to keep track of the high score
let gameStarted = false; // Variable to track if the game has started
let backgroundImg; // Variable to hold the background image

function preload() {
  // Load the background image
  backgroundImg = loadImage('background.png'); // Flappy Bird background
}

function setup() {
  createCanvas(400, 600);
  bird = new Bird();
  pipes.push(new Pipe());
}

function draw() {
  image(backgroundImg, 0, 0, width, height);

  if (gameStarted) {
    bird.update();
    bird.show();

    if (frameCount % 100 == 0) {
      pipes.push(new Pipe());
    }

    for (let i = pipes.length - 1; i >= 0; i--) {
      pipes[i].update();
      pipes[i].show();

      if (pipes[i].offscreen()) {
        pipes.splice(i, 1);
      }

      if (pipes[i].hits(bird)) {
        gameOver();
      }

      if (!pipes[i].passed && pipes[i].x < bird.x) {
        pipes[i].passed = true;
        score++;
      }
    }

    displayScore();
  } else {
    displayStartMessage(); // Display start message when game hasn't started
  }
}

function keyPressed() {
  if (key == ' ') {
    if (!gameStarted) {
      startGame(); // Start the game if not already started
    } else {
      bird.flap(); // Flap if the game is already started
    }
  }
  if (key == 'r' && bird.dead) {
    restartGame();
  }
}

function displayScore() {
  fill(0);
  textSize(32);
  textAlign(RIGHT);
  text(score, width - 20, 40);

  // Display high score
  textSize(20);
  textAlign(LEFT);
  text('High Score: ' + highScore, 20, 40);
}

function displayStartMessage() {
  fill(0);
  textSize(32); // Adjusted text size for better fit
  textAlign(CENTER);
  text('Press SPACE to Start', width / 2, height / 2); // Centered vertically
}

function gameOver() {
  bird.dead = true;
  noLoop();

  // Update high score if current score is greater
  if (score > highScore) {
    highScore = score;
  }

  fill(0);
  textSize(50);
  textAlign(CENTER);
  text('Game Over!', width / 2, height / 3);
  textSize(30);
  text('Press R to Restart', width / 2, height / 2);
}

function startGame() {
  gameStarted = true; // Set the game as started
  score = 0; // Reset score
  pipes = []; // Clear pipes
  bird = new Bird(); // Reset bird
  loop(); // Start the draw loop
}

function restartGame() {
  bird = new Bird();
  pipes = [];
  score = 0;
  loop();
  bird.dead = false; // Reset bird state
  gameStarted = false; // Reset game state
}

class Bird {
  constructor() {
    this.y = height / 2;
    this.x = 100;
    this.gravity = 0.72;
    this.lift = -12;
    this.velocity = 0;
    this.dead = false;
    this.radius = 20;
  }

  flap() {
    if (!this.dead) {
      this.velocity = this.lift;
    }
  }

  update() {
    this.velocity += this.gravity;
    this.y += this.velocity;

    if (this.y > height) {
      this.y = height;
      gameOver();
    }

    if (this.y < 0) {
      this.y = 0;
    }
  }

  show() {
    fill(0, 150, 255);
    ellipse(this.x, this.y, this.radius * 2, this.radius * 2);
  }
}

class Pipe {
  constructor() {
    this.spacing = 150;
    this.top = random(height / 6, (2 / 3) * height);
    this.bottom = height - (this.top + this.spacing);
    this.x = width;
    this.w = 80;
    this.speed = 3;
    this.passed = false;
  }

  hits(bird) {
    let birdEdgeLeft = bird.x - bird.radius;
    let birdEdgeRight = bird.x + bird.radius;
    let birdEdgeTop = bird.y - bird.radius;
    let birdEdgeBottom = bird.y + bird.radius;

    return (
      (birdEdgeTop < this.top || birdEdgeBottom > height - this.bottom) &&
      (birdEdgeRight > this.x && birdEdgeLeft < this.x + this.w)
    );
  }

  update() {
    this.x -= this.speed;
  }

  offscreen() {
    return this.x < -this.w;
  }

  show() {
    fill(0, 200, 0);
    rect(this.x, 0, this.w, this.top);
    rect(this.x, height - this.bottom, this.w, this.bottom);
  }
>>>>>>> Stashed changes
}