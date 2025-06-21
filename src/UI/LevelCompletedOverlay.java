package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import GameState.GameStates;
import GameState.Playing;
import LoadSave.LoadAndSave;
import Mainpackage.Game;

public class LevelCompletedOverlay {
    private Playing playing;
	private UrmButton menu, next, reset;
	private BufferedImage img, popupImage;
	private int bgX, bgY, bgW, bgH;
    private int PUX, PUY, PUWidth, PUHeight;
	private boolean showPopup = false;

	public LevelCompletedOverlay(Playing playing) {
		this.playing = playing;
		initImg();
		initButtons();
	}

	private void initButtons() {
		int menuX = (int) (Game.gameWidth*475/1224);
		int nextX = (int) (Game.gameWidth*670/1224);
        int resetX = (int) (Game.gameWidth*570/1224);
		int y = (int) (Game.gameHeight*292.5f/675);
		next = new UrmButton(nextX, y, Game.gameWidth*84/1224, Game.gameHeight*84/675, 0);
        reset = new UrmButton(resetX, y, Game.gameWidth*84/1224, Game.gameHeight*84/675, 1);
		menu = new UrmButton(menuX, y, Game.gameWidth*84/1224, Game.gameHeight*84/675, 2);
	}

	private void initImg() {
		img = LoadAndSave.GetSpriteAtlas(LoadAndSave.COMPLETED_IMG);
		bgW = (int) (Game.gameWidth*336/1224);
		bgH = (int) (Game.gameWidth*306/1224);
		bgX = Game.gameWidth / 2 - bgW / 2;
		bgY = (int) (Game.gameHeight*112.5/675);
	}
    private void loadPopUpMessageImg() {
        popupImage = LoadAndSave.GetSpriteAtlas(LoadAndSave.POP_UP_MESSAGE);
        PUWidth = (int) (popupImage.getWidth()* 0.75f);
        PUHeight = (int) (popupImage.getHeight() * 0.5f);
        PUX = Game.gameWidth / 2 - PUWidth / 2;
        PUY = Game.gameHeight / 2 - PUHeight / 2;

        addTextToPopup("CHÚC MỪNG BẠN ĐÃ HOÀN THÀNH THỬ THÁCH.");
		showPopup = true;
    }
    private void addTextToPopup(String text) {
        Graphics2D g2d = (Graphics2D) popupImage.getGraphics();

        Font font = new Font("Arial", Font.BOLD, 50);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        FontMetrics fm = g2d.getFontMetrics(font);
        int lineHeight = fm.getHeight();
        int maxWidth = PUWidth*8/10;
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        java.util.List<String> lines = new java.util.ArrayList<>();
        for (String word : words) {
            String testLine = line + word + " ";
            if (fm.stringWidth(testLine) > maxWidth) {
                lines.add(line.toString());
                line = new StringBuilder(word + " ");
            } else {
                line.append(word).append(" ");
            }
        }
        lines.add(line.toString());
        int totalTextHeight = lines.size() * lineHeight;
        int startY = ((PUHeight - totalTextHeight)+Game.TILES_IN_HEIGHT*10) / 2 + fm.getAscent();
        int y = startY;
        for (String l : lines) {
            int textWidth = fm.stringWidth(l);
            int x = (int)((PUWidth - textWidth)+Game.TILES_IN_WIDTH*9) / 2;
            g2d.drawString(l, x, y);
            y += lineHeight;
        }
        g2d.dispose();
    }

	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.gameWidth, Game.gameHeight);

		g.drawImage(img, bgX, bgY, bgW, bgH, null);
		next.draw(g);
        reset.draw(g);
		menu.draw(g);
		if (showPopup){
        	g.drawImage(popupImage, PUX, PUY, PUWidth,  PUHeight, null);
		}
	}

	public void update() {
		if (!showPopup) { 
			next.update();
			reset.update();
			menu.update();
		}
	}

	private boolean isIn(UrmButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		if (!showPopup) { 
			next.setMouseOver(false);
			reset.setMouseOver(false);
			menu.setMouseOver(false);

			if (isIn(menu, e))
				menu.setMouseOver(true);
			else if (isIn(reset, e))
				reset.setMouseOver(true);
			else if (isIn(next, e))
				next.setMouseOver(true);
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (showPopup) {
			return;
		}

		if (isIn(menu, e)) {
			if (menu.isMousePressed()) {
				playing.allReset();
				playing.setGamestates(GameStates.MENU);
			}
        } else if (isIn(reset, e)) {
            if (reset.isMousePressed()) {
				playing.allReset();
				playing.getGame().getAudioPlayer().setLevelSong(0);
			}
		} else if (isIn(next, e)) {
			if (next.isMousePressed()) {
				if (playing.currentLevel >= 2)// hiện tại mới có 2 level thoaithoai
				{
					loadPopUpMessageImg();
				}
				if(playing.currentLevel<2) {
					playing.currentLevel++;
					playing.setLevelCompleted(false);
				}	
			}
		}

		menu.resetBools();
        reset.resetBools();
		next.resetBools();
	}

	public void mousePressed(MouseEvent e) {
		if (showPopup) {
			return;
		}

		if (isIn(menu, e))
			menu.setMousePressed(true);
        else if (isIn(reset, e))
            reset.setMousePressed(true);
		else if (isIn(next, e))
			next.setMousePressed(true);
        
	}
    public void keyPressed(KeyEvent e){
		if(showPopup){
			switch (e.getKeyCode()) {
				case KeyEvent.VK_ESCAPE:
					playing.setGamestates(GameStates.MENU);
					playing.allReset();
					showPopup = false;
					break;
			}
		}
    }

}

