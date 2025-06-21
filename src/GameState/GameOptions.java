package GameState;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import LoadSave.LoadAndSave;
import Mainpackage.Game;
import UI.AudioOptions;
import UI.MenuBackground;
import UI.PauseButton;
import UI.UrmButton;

public class GameOptions extends State implements StateMethods {
    private AudioOptions audioOptions;
	private BufferedImage optionsBackgroundImg;
	private int bgX, bgY, bgW, bgH;
	private UrmButton menuB;
    private MenuBackground backgroundImg;

	public GameOptions(Game game) {
		super(game);
		this.game = game;
		loadImgs();
		loadButton();
		audioOptions = game.getAudioOptions();
	}

	private void loadButton() {
		int menuX = (int) (Game.gameWidth*570f/1224);
		int menuY = (int) (Game.gameHeight*485f/675);

		menuB = new UrmButton(menuX, menuY, (int)(Game.gameWidth*84/1224), (int)(Game.gameHeight*84/675), 2);
	}

	private void loadImgs() {
		backgroundImg = new MenuBackground(Game.gameWidth, Game.gameHeight, "Background1");
		optionsBackgroundImg = LoadAndSave.GetSpriteAtlas(LoadAndSave.OPTIONS_MENU);

		bgW = (int) (Game.gameWidth*423/1224);
		bgH = (int) (Game.gameHeight*589/675);
		bgX = Game.gameWidth / 2 - bgW / 2;
		bgY = (int) (Game.gameHeight*49.5f/675);
	}

    @Override
    public void update() {
        menuB.update();
		audioOptions.update();
    }

    @Override
    public void render(Graphics g) {
        backgroundImg.render(g);
		g.drawImage(optionsBackgroundImg, bgX, bgY, bgW, bgH, null);

		menuB.draw(g);
		audioOptions.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);

		if (isIn(e, menuB))
			menuB.setMouseOver(true);
		else
			audioOptions.mouseMoved(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuB)) {
			menuB.setMousePressed(true);
		} else
			audioOptions.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuB)) {
			if (menuB.isMousePressed())
				GameStates.state = GameStates.MENU;
		} else
			audioOptions.mouseReleased(e);

		menuB.resetBools();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			GameStates.state = GameStates.MENU;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
}
