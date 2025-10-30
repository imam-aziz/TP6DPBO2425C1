import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View extends JPanel {
    int width = 360;
    int height = 640;

    private Logic logic;
    private Image background;

    private boolean isCountdown = false;
    private int countdownValue = 3;
    private Timer countdownTimer;

    public View(Logic logic) {
        this.logic = logic;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.cyan);

        background = new ImageIcon(getClass().getResource("assets/bg.png")).getImage();

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(logic);

        JLabel scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        int frameWidth = 360;
        scoreLabel.setBounds(frameWidth - 180, 20, 160, 30);
        scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        setLayout(null);
        add(scoreLabel);

        logic.setScoreLabel(scoreLabel);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }

    public void startCountdown() {
        isCountdown = true;
        countdownValue = 3;
        logic.setCountdownActive(true);
        repaint(); // langsung gambar 3

        if (countdownTimer != null && countdownTimer.isRunning()) {
            countdownTimer.stop();
        }

        // Timer baru dimulai SETELAH 1 detik pertama (biar angka 3 muncul dulu)
        countdownTimer = new Timer(1000, e -> {
            countdownValue--;
            repaint();

            if (countdownValue <= 0) {
                ((Timer) e.getSource()).stop();
                isCountdown = false;
                logic.setCountdownActive(false);
                logic.setGameStarted(true);
                logic.startGameLoop();
                logic.startPipeSpawner();
            }
        });

        countdownTimer.setInitialDelay(1000); // ❗ delay pertama 1 detik, jadi “3” tampil dulu
        countdownTimer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

        if (logic.isGameOver()) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Impact", Font.BOLD, 32));
            g.drawString("GAME OVER", 95, 250);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Tekan 'R' untuk restart", 70, 300);
        }

        if (isCountdown) {
            g.setFont(new Font("Impact", Font.BOLD, 72));
            g.setColor(Color.WHITE);
            String text = (countdownValue > 0) ? String.valueOf(countdownValue) : "GO!";
            FontMetrics fm = g.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(text)) / 2;
            int y = getHeight() / 2;
            g.drawString(text, x, y);
        }
    }

    public void draw(Graphics g) {
        if (background != null)
            g.drawImage(background, 0, 0, width, height, null);

        Player player = logic.getPlayer();
        if (player != null)
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(),
                    player.getWidth(), player.getHeight(), null);

        ArrayList<Pipe> pipes = logic.getPipes();
        if (pipes != null) {
            for (Pipe pipe : pipes) {
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(),
                        pipe.getWidth(), pipe.getHeight(), null);
            }
        }
    }
}
