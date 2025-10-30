import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Logic implements ActionListener, KeyListener {
    int frameWidth = 360;
    int frameHeight = 640;

    int playerStartPosX = frameWidth / 2;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 40;
    int playerHeight = 45;

    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    View view;
    Image birdImage;
    Player player;
    Image lowerPipeImage;
    Image upperPipeImage;
    ArrayList<Pipe> pipes;

    Timer gameLoop;
    Timer pipesCooldown;

    int gravity = 1;
    int pipeVelocityX = -2;

    boolean gameOver = false;
    boolean gameStarted = false;
    int score = 0;
    JLabel scoreLabel;

    private boolean isCountdownActive = false;

    private Sound bgMusic;
    private Sound scoreSound;
    private Sound gameOverSound;
    private Sound flapSound;

    public void setCountdownActive(boolean active) {
        this.isCountdownActive = active;
    }

    public Logic() {
        birdImage = new ImageIcon(getClass().getResource("assets/momoi.gif")).getImage();
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();
        pipes = new ArrayList<>();

        //bekson
        bgMusic = new Sound("bg_music.wav");
        scoreSound = new Sound("score.wav");
        gameOverSound = new Sound("gameover.wav");
        flapSound = new Sound("flap.wav");
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setScoreLabel(JLabel scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameStarted(boolean started) {
        this.gameStarted = started;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Pipe> getPipes() {
        return pipes;
    }

    // dipanggil setelah countdown
    public void startGameLoop() {
        if (bgMusic != null) bgMusic.playLoop();

        if (gameLoop == null || !gameLoop.isRunning()) {
            gameLoop = new Timer(1000 / 60, this);
            gameLoop.start();
        }
    }

    public void startPipeSpawner() {
        if (pipesCooldown == null || !pipesCooldown.isRunning()) {
            pipesCooldown = new Timer(1500, e -> placePipes());
            pipesCooldown.start();
        }
    }

    public void placePipes() {
        int randomPosY = (int) (pipeStartPosY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = frameHeight / 4;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);
        Pipe lowerPipe = new Pipe(pipeStartPosX, (randomPosY + openingSpace + pipeHeight),
                pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    public void move() {
        if (!gameStarted || gameOver) return;

        // gravitasi
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        if (player.getPosY() + playerHeight >= frameHeight) {
            player.setPosY(frameHeight - playerHeight);
            gameOver = true;
            stopGame();
        }

        for (int i = 0; i < pipes.size(); i += 2) {
            Pipe upperPipe = pipes.get(i);
            Pipe lowerPipe = pipes.get(i + 1);

            upperPipe.setPosX(upperPipe.getPosX() + pipeVelocityX);
            lowerPipe.setPosX(lowerPipe.getPosX() + pipeVelocityX);

            // skor
            if (!upperPipe.isPassed() && player.getPosX() > upperPipe.getPosX() + pipeWidth) {
                upperPipe.setPassed(true);
                lowerPipe.setPassed(true);
                score++;
                if (scoreSound != null) scoreSound.playOnce();

                if (scoreLabel != null) scoreLabel.setText("Score: " + score);
            }
        }
    }

    public void checkCollision() {
        Rectangle playerRect = new Rectangle(player.getPosX(), player.getPosY(), playerWidth, playerHeight);
        for (Pipe pipe : pipes) {
            Rectangle pipeRect = new Rectangle(pipe.getPosX(), pipe.getPosY(), pipeWidth, pipeHeight);
            if (playerRect.intersects(pipeRect)) {
                gameOver = true;
                stopGame();
                return;
            }
        }

        if (player.getPosY() + playerHeight >= frameHeight) {
            gameOver = true;
            stopGame();
        }
    }

    public void stopGame() {
        if (bgMusic != null) bgMusic.stop();
        if (gameOverSound != null) gameOverSound.playOnce();

        if (gameLoop != null) gameLoop.stop();
        if (pipesCooldown != null) pipesCooldown.stop();
        System.out.println("GAME OVER");
    }

    public void restartGame() {
        gameOver = false;
        score = 0;
        pipes.clear();
        player.setPosX(playerStartPosX);
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);
        if (scoreLabel != null) scoreLabel.setText("Score: 0");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isCountdownActive) return;

        if (!gameOver) {
            move();
            checkCollision();
            if (view != null) view.repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameStarted && !gameOver) {
            player.setVelocityY(-10);
            if (flapSound != null) flapSound.playOnce();
        } else if (gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                restartGame();
                if (view != null) view.startCountdown();
            }
            else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}
