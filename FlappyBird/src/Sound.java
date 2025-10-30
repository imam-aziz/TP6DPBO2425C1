import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {
    private Clip clip;

    public Sound(String soundFileName) {
        try {
            URL url = getClass().getResource("assets/sound/" + soundFileName);
            if (url == null) {
                System.err.println("❌ File sound tidak ditemukan: " + soundFileName);
                return;
            }
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("⚠️ Gagal memuat sound: " + soundFileName);
            e.printStackTrace();
        }
    }

    /** 🔁 Main berulang mulai dari awal (selalu restart) */
    public void playLoop() {
        if (clip != null) {
            clip.stop();
            clip.flush(); // pastikan buffer bersih
            clip.setFramePosition(0); // mulai dari awal
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /** ▶️ Main sekali (untuk efek suara) */
    public void playOnce() {
        if (clip != null) {
            clip.stop();
            clip.flush();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    /** ⏹️ Stop suara sepenuhnya */
    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.flush();
            clip.setFramePosition(0); // reset biar siap diulang nanti
        }
    }
}
