package Main.Features;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.io.File;

public class EffectMusicPalyer {

    File musicPath;

    Clip clip;

    private int EffectSoundStatus = 2;


    {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    private void play(String musicLocation) {
        try {
            musicPath = new File(musicLocation);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } else {
                System.out.println("Error in playing Music");
                JOptionPane.showMessageDialog(null, "Can't find the music file!", "Caution", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //效果声开关
    public void setEffectSoundStatus() {
        EffectSoundStatus++;
    }

    //效果声常数
    public final int MOTION_SOUND = 0;
    public final int VICTORY_SOUND = 1;
    public final int LOST_SOUND = 2;
    public final int UNDO_SOUND = 3;
    public final int REPLAY_SOUND = 4;
    public final int EXPLOSION_SOUND = 5;

    //移动效果音播放
    public void playEffectSound(int soundConstant) {
        if (EffectSoundStatus % 2 != 0) {
            return;
        }
        String effectedMusicPath = "src/Main/Resources/Music/Effect/";
        switch (soundConstant) {
            case MOTION_SOUND -> play(effectedMusicPath + "effect_1_motion.wav");
            case VICTORY_SOUND -> play(effectedMusicPath + "effect_2_Victory.wav");
            case UNDO_SOUND -> play(effectedMusicPath + "effect_6_undo.wav");
            case LOST_SOUND -> play(effectedMusicPath + "effect_3_lost.wav");
            case REPLAY_SOUND -> play(effectedMusicPath + "effect_4_replay.wav");
            case EXPLOSION_SOUND -> play(effectedMusicPath + "effect_5_explosion.wav");
        }
    }

}
