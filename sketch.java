// Flappy Bird in JavaScript using p5.js

let bird;
let pipes = [];
let score = 0;

function setup() {
  createCanvas(400, 600);
  bird = new Bird();
  pipes.push(new Pipe());
}

function draw() {
  background(255);

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
}

function gameOver() {
  bird.dead = true;
  noLoop();
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
    this.gravity = 0.8;
    this.lift = -12;
    this.velocity = 0;
    this.dead = false;
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
    ellipse(this.x, this.y, 40, 40);
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
    return (
      bird.y < this.top ||
      bird.y > height - this.bottom) &&
      (bird.x > this.x && bird.x < this.x + this.w);
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
}
