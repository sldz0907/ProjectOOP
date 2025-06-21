package Audio;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {

    public static int MENU_1 = 0;
	public static int LEVEL_1 = 1;

	public static int DIE = 0;
	public static int JUMP = 1;
	public static int HURT = 2;
	public static int GAMEOVER = 3;
	public static int LVL_COMPLETED = 4;
	public static int ATTACK_ONE = 5;
	public static int ATTACK_TWO = 6;
	public static int ATTACK_THREE = 7;
	public static int HURT_LAVA = 8;
	public static int HURT_LASER = 9;
	public static int WADING = 10;
	public static int ULTIMATE = 11;
	

    private Clip[] songs, effects;
    private int currentSongId, currentEffectId;
    private float volume = 1f;
    private boolean songMute, effectMute;
    private Random rand = new Random();

    public AudioPlayer(){
        loadSongs();
        loadEffects();
        playSong(MENU_1);
    }

    private void loadSongs(){
        String[] names = { "piano2", "piano3","piano1","hal1","hal2","hal3","hal4" };
		songs = new Clip[names.length];
		for (int i = 0; i < songs.length; i++)
			songs[i] = getClip(names[i]);
    }

    private void loadEffects() {
        String[] effectNames = { "die", "jump1","hurt", "gameover", "lvlcompleted","attack1", "attack2", "attack3","lava_hurt","laser_hurt","wading_2","ultimate" };
		effects = new Clip[effectNames.length];
		for (int i = 0; i < effects.length; i++)
			effects[i] = getClip(effectNames[i]);

        updateEffectsVolume();
    }

    private Clip getClip(String name){
        URL url = getClass().getResource("/asset/Audio/" + name + ".wav");
        AudioInputStream audio;

		try {
			audio = AudioSystem.getAudioInputStream(url);
			Clip c = AudioSystem.getClip();
			c.open(audio);
			return c;

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

			e.printStackTrace();
		}

		return null;
    }
    public void setVolume(float volume) {
		this.volume = volume;
		updateSongVolume();
		updateEffectsVolume();
	}

    public void stopSong() {
		if (songs[currentSongId].isActive())
			songs[currentSongId].stop();
	}

	public void setLevelSong(int lvlIndex) {
			playSong(LEVEL_1);
	}

	public void lvlCompleted() {
		stopSong();
		playEffect(LVL_COMPLETED);
	}

    public void playAttackSound() {
		int start = 5;
		start += rand.nextInt(3);
		playEffect(start);
	}
	public void playHurt(int effect){
		stopEffect();
		currentEffectId = effect;
		effects[currentEffectId].setMicrosecondPosition(0);
		effects[currentEffectId].loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void stopEffect() {
		if (effects[currentEffectId].isActive())
			effects[currentEffectId].stop();
	}

	public void playEffect(int effect) {
		effects[effect].setMicrosecondPosition(0);
		effects[effect].start();
	}

    public void playSong(int song) {
		stopSong();
	
		int newSongId;
	
		do {
			newSongId = rand.nextInt(songs.length); 
		} while (newSongId == currentSongId); 
	
		currentSongId = newSongId;
	
		updateSongVolume();
		songs[currentSongId].setMicrosecondPosition(0);
		songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
	}

    public void toggleSongMute() {
		this.songMute = !songMute;
		for (Clip c : songs) {
			BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
			booleanControl.setValue(songMute);
		}
	}

    public void toggleEffectMute() {
		this.effectMute = !effectMute;
		for (Clip c : effects) {
			BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
			booleanControl.setValue(effectMute);
		}
		if (!effectMute)
			playEffect(JUMP);
	}

    private void updateSongVolume(){
        FloatControl gainControl = (FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();
		gainControl.setValue(gain);
    }

    private void updateEffectsVolume(){
        for (Clip c : effects) {
			FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
			float range = gainControl.getMaximum() - gainControl.getMinimum();
			float gain = (range * volume) + gainControl.getMinimum();
			gainControl.setValue(gain);
		}
    }
}
