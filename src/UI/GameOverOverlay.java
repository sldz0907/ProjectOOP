package UI;

import Mainpackage.Game;
import GameState.GameStates;
import GameState.Playing;
import LoadSave.LoadAndSave;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;

public class GameOverOverlay {
    private Playing playing;
    private BufferedImage img;
	private int imgX, imgY, imgW, imgH;
	private UrmButton menu, play;

    public GameOverOverlay(Playing playing) {
        this.playing = playing;
        createImg();
		createButtons();
    }
    private void createButtons() {
		int menuX = (int) (Game.gameWidth*494/1224);
		int playX = (int) (Game.gameWidth*647/1224);
		int y = (int) (Game.gameHeight*292/675);
		play = new UrmButton(playX, y, Game.gameWidth*84/1224, Game.gameHeight*84/675, 1);
		menu = new UrmButton(menuX, y, Game.gameWidth*84/1224, Game.gameHeight*84/675, 2);
	}

	private void createImg() {
		img = LoadAndSave.GetSpriteAtlas(LoadAndSave.DEATH_SCREEN);
		imgW = (int) (img.getWidth() * 1.5f);
		imgH = (int) (img.getHeight() * 1.5f);
		imgX = Game.gameWidth / 2 - imgW / 2;
		imgY = (int) (Game.gameHeight*150/675);

	}

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.gameWidth, Game.gameHeight);
        g.drawImage(img, imgX, imgY, imgW, imgH, null);

		menu.draw(g);
		play.draw(g);
    }
    public void update() {
		menu.update();
		play.update();
	}
	
    private boolean isIn(UrmButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		play.setMouseOver(false);
		menu.setMouseOver(false);

		if (isIn(menu, e))
			menu.setMouseOver(true);
		else if (isIn(play, e))
			play.setMouseOver(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(menu, e)) {
			if (menu.isMousePressed()) {
				playing.allReset();
				playing.setGamestates(GameStates.MENU);	
			}
		} else if (isIn(play, e))
			if (play.isMousePressed()){
				playing.allReset();
				playing.getGame().getAudioPlayer().setLevelSong(0);
			}
		menu.resetBools();
		play.resetBools();
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(menu, e))
			menu.setMousePressed(true);
		else if (isIn(play, e))
			play.setMousePressed(true);
	}
}
