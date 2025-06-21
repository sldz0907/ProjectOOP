package UI;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import Mainpackage.Game;
import static UI.PauseOverlay.*;

public class AudioOptions {
    private VolumeButton volumeButton;
	private SoundButton musicButton, sfxButton;

	private Game game;
	private float valueBefore,valueAfter;
	public AudioOptions(Game game) {
		this.game = game;
		createSoundButtons();
		createVolumeButton();
	}
    private void createVolumeButton() {
		int vX = (int) (Game.gameWidth*453.9f/1224);
		int vY = (int) (Game.gameHeight*417/675);
		volumeButton = new VolumeButton(vX, vY, (int)(Game.gameWidth*320/1224), (int)(Game.gameHeight*66/675));
	}
	private void createSoundButtons() {
		int soundX = (int) (Game.gameWidth*663/1224);
		int musicY = (int) (Game.gameHeight*210/675);
		int sfxY = (int) (Game.gameHeight*277.5f/675);
		musicButton = new SoundButton(soundX, musicY, (int)(Game.gameWidth*63/1224), (int)(Game.gameHeight*63/675));
		sfxButton = new SoundButton(soundX, sfxY, (int)(Game.gameWidth*63/1224), (int)(Game.gameHeight*63/675));
	}

	public void update() {
		musicButton.update();
		sfxButton.update();

		volumeButton.update();
	}

	public void draw(Graphics g) {
		// Sound buttons
		musicButton.draw(g);
		sfxButton.draw(g);

		// Volume Button
		volumeButton.draw(g);
	}

	public void mouseDragged(MouseEvent e) {
		if (volumeButton.isMousePressed()) {
			valueBefore = volumeButton.getFloatValue();
			volumeButton.changeX(e.getX());
			valueAfter = volumeButton.getFloatValue();
			if(valueBefore != valueAfter)
				game.getAudioPlayer().setVolume(valueAfter);
		}
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(e, musicButton))
			musicButton.setMousePressed(true);
		else if (isIn(e, sfxButton))
			sfxButton.setMousePressed(true);
		else if (isIn(e, volumeButton))
			volumeButton.setMousePressed(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(e, musicButton)) {
			if (musicButton.isMousePressed())
				musicButton.setMuted(!musicButton.isMuted());
				game.getAudioPlayer().toggleSongMute();

		} else if (isIn(e, sfxButton)) {
			if (sfxButton.isMousePressed())
				sfxButton.setMuted(!sfxButton.isMuted());
				game.getAudioPlayer().toggleEffectMute();
		}

		musicButton.resetBools();
		sfxButton.resetBools();

		volumeButton.resetBools();
	}

	public void mouseMoved(MouseEvent e) {
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);

		volumeButton.setMouseOver(false);

		if (isIn(e, musicButton))
			musicButton.setMouseOver(true);
		else if (isIn(e, sfxButton))
			sfxButton.setMouseOver(true);
		else if (isIn(e, volumeButton))
			volumeButton.setMouseOver(true);
	}

	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
}
