import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class App extends JFrame implements ActionListener {

    private Image background;
    private JButton playButton;
    private JButton exitButton;
    private Clip bgMusic; // 🎵 musik latar

    public App() {
        setTitle("Flappy Momoi! - Main Menu");
        setSize(360, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // 🖼️ Load background
        background = new ImageIcon(getClass().getResource("assets/bg.png")).getImage();

        // Panel background
        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        bgPanel.setLayout(null);

        // 🩷 Judul
        JLabel title = new JLabel("Flappy Momoi!", SwingConstants.CENTER);
        title.setFont(new Font("Impact", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 100, 360, 60);
        bgPanel.add(title);

        // 🐱 Tambahkan gambar Momoi (GIF)
        ImageIcon momoiIcon = new ImageIcon(getClass().getResource("assets/momo.gif"));
        JLabel momoiLabel = new JLabel(momoiIcon);
        momoiLabel.setBounds(110, 150, 150, 200); // posisi & ukuran
        bgPanel.add(momoiLabel);

        // Tombol Play Game
        playButton = new JButton("GAS  MAIN!");
        styleBlueTransparentButton(playButton);
        playButton.setBounds(100, 360, 160, 50);
        playButton.addActionListener(this);
        bgPanel.add(playButton);

        // Tombol Exit
        exitButton = new JButton("MENDING  TURU");
        styleBlueTransparentButton(exitButton);
        exitButton.setBounds(100, 440, 160, 50);
        exitButton.addActionListener(this);
        bgPanel.add(exitButton);

        add(bgPanel);

        // 🎵 Putar musik latar saat menu muncul
        playBackgroundMusic("assets/sound/bgs.wav");
    }

    /**
     * 🎨 Styling tombol biru transparan 40%
     */
    private void styleBlueTransparentButton(JButton button) {
        button.setFont(new Font("Impact", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 100, 255, 102)); // biru transparan 40%
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setOpaque(true);

        // Efek hover individual
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 100, 255, 180)); // lebih pekat saat hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 100, 255, 102)); // kembali transparan
            }
        });
    }

    /**
     * 🎶 Memainkan musik latar belakang
     */
    private void playBackgroundMusic(String soundPath) {
        try {
            URL url = getClass().getResource(soundPath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            bgMusic = AudioSystem.getClip();
            bgMusic.open(audioIn);
            bgMusic.loop(Clip.LOOP_CONTINUOUSLY); // loop terus
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * 🚪 Berhentikan musik sebelum keluar menu
     */
    private void stopBackgroundMusic() {
        if (bgMusic != null && bgMusic.isRunning()) {
            bgMusic.stop();
            bgMusic.close();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            stopBackgroundMusic(); // stop musik saat main
            dispose();
            openGame();
        } else if (e.getSource() == exitButton) {
            stopBackgroundMusic();
            System.exit(0);
        }
    }

    private void openGame() {
        Logic logic = new Logic();
        View view = new View(logic);
        logic.setView(view);

        JFrame gameFrame = new JFrame("Flappy Momoi! Game");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(360, 640);
        gameFrame.add(view);
        gameFrame.addKeyListener(logic);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);

        view.startCountdown();
    }

    public static void main(String[] args) {
        new App().setVisible(true);
    }
}
