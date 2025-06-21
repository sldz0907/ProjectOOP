package UI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import GameState.GameStates;
import GameState.Playing;

import java.awt.event.MouseEvent;

import LoadSave.LoadAndSave;
import Mainpackage.Game;

public class PauseOverlay {
    private Playing playing;
	private BufferedImage backgroundImg;
	private int bgX, bgY, bgW, bgH;
	private UrmButton menuB, replayB, unpauseB;
	private AudioOptions audioOptions;

    public static final int SOUND_SIZE_DEFAULT = 42;
	public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * 1.5f);
    public static final int URM_DEFAULT_SIZE = 56;
	public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * 1.5f);
    public static final int VOLUME_DEFAULT_WIDTH = 28;
	public static final int VOLUME_DEFAULT_HEIGHT = 44;
	public static final int SLIDER_DEFAULT_WIDTH = 215;
	public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * 1.5f);
	public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * 1.5f);
	public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * 1.5f);

	public PauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();
		audioOptions = playing.getGame().getAudioOptions();
		createUrmButtons();

	}

	private void createUrmButtons() {
		int menuX = (int) (Game.gameWidth*462/1224);
		int replayX = (int) (Game.gameWidth*569/1224);
		int unpauseX = (int) (Game.gameWidth*678/1224);
		int bY = (int) (Game.gameHeight*487/675);

		menuB = new UrmButton(menuX, bY, Game.gameWidth*84/1224, Game.gameHeight*84/675, 2);
		replayB = new UrmButton(replayX, bY, Game.gameWidth*84/1224, Game.gameHeight*84/675, 1);
		unpauseB = new UrmButton(unpauseX, bY, Game.gameWidth*84/1224, Game.gameHeight*84/675, 0);

	}
	private void loadBackground() {
		backgroundImg = LoadAndSave.GetSpriteAtlas(LoadAndSave.PAUSE_BACKGROUND);
		bgW = (int) (Game.gameWidth*387/1224);
		bgH = (int) (Game.gameHeight*583/675);
		bgX = Game.gameWidth / 2 - bgW / 2;
		bgY = (int) (Game.gameHeight*37/675);
	}

	public void update() {
		menuB.update();
		replayB.update();
		unpauseB.update();

		audioOptions.update();
	}

	public void draw(Graphics g) {
		// Background
		g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);

		// UrmButtons
		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);

		audioOptions.draw(g);
	}

	public void mouseDragged(MouseEvent e) {
		audioOptions.mouseDragged(e);

	}

	public void mousePressed(MouseEvent e) {
		if (isIn(e, menuB))
			menuB.setMousePressed(true);
		else if (isIn(e, replayB))
			replayB.setMousePressed(true);
		else if (isIn(e, unpauseB))
			unpauseB.setMousePressed(true);
		else
			audioOptions.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(e, menuB)) {
			if (menuB.isMousePressed()) {
				playing.allReset();
				playing.setGamestates(GameStates.MENU);
			}
		} else if (isIn(e, replayB)) {
			if (replayB.isMousePressed()) {
				playing.allReset();
				playing.unpauseGame();
			}
		} else if (isIn(e, unpauseB)) {
			if (unpauseB.isMousePressed())
				playing.unpauseGame();
		} else
			audioOptions.mouseReleased(e);

		menuB.resetBools();
		replayB.resetBools();
		unpauseB.resetBools();

	}

	public void mouseMoved(MouseEvent e) {
		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);

		if (isIn(e, menuB))
			menuB.setMouseOver(true);
		else if (isIn(e, replayB))
			replayB.setMouseOver(true);
		else if (isIn(e, unpauseB))
			unpauseB.setMouseOver(true);
		else
			audioOptions.mouseMoved(e);

	}

	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
	
}
