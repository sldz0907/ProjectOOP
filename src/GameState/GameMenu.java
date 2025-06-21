package GameState;

import Mainpackage.Game;
import UI.MenuBackground;
import UI.MenuButton;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import LoadSave.LoadAndSave;

public class GameMenu extends State implements StateMethods {

    private MenuButton[] menuButtons;
    private String[] buttonNames = {"PLAY", "OPTION", "QUIT"};
    private BufferedImage backgroundImg;
    private int menuX, menuY, menuWidth, menuHeight;
    private MenuBackground background;

    public GameMenu(Game game) {
        super(game);
        this.game = game;
        initBackground();
        initButtons();
        loadBackground();
    }

    private void loadBackground() {
        backgroundImg = LoadAndSave.GetSpriteAtlas(LoadAndSave.MENU_BACKGROUND);
        menuWidth = (int) (Game.gameWidth*423/1224);
        menuHeight = (int) (Game.gameHeight*576/675);
        menuX = Game.gameWidth / 2 - menuWidth / 2;
        menuY = Game.gameHeight / 2 - menuHeight / 2;
    }

    private void initBackground() {
        background = new MenuBackground(Game.gameWidth, Game.gameHeight, "Background1");
    }

    private void initButtons() {
        menuButtons = new MenuButton[buttonNames.length];
        menuButtons[0] = new MenuButton(Game.gameWidth / 2, (int) (Game.gameHeight * 1.2 / buttonNames.length), buttonNames[0], GameStates.PLAYING);
        menuButtons[1] = new MenuButton(Game.gameWidth / 2, (int) (Game.gameHeight * 1.55 / buttonNames.length), buttonNames[1], GameStates.OPTION);
        menuButtons[2] = new MenuButton(Game.gameWidth / 2, (int) (Game.gameHeight * 1.9 / buttonNames.length), buttonNames[2], GameStates.QUIT);
    }

    public void render(Graphics g) {
        background.render(g);
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);
        for (MenuButton button : menuButtons) {
            button.render(g);
        }
    }

    @Override
    public void update() {
        for (MenuButton button : menuButtons) {
            button.update();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Không cần hành động cho mouseClicked
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Không cần hành động cho mouseDragged
    }

    private void resetButtons() {
        for (MenuButton button : menuButtons) {
            button.resetBool();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton button : menuButtons) {
            button.setMouseOver(false);
        }

        for (MenuButton button : menuButtons) {
            if (isOn(e, button.getHitBox())) {
                button.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton button : menuButtons) {
            if (isOn(e, button.getHitBox())) {
                button.setMousePress(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton button : menuButtons) {
            if (isOn(e, button.getHitBox())) {
                if(button.isMousePress())
                    button.applyGameStates();
                if(button.getState() == GameStates.PLAYING)
                    game.getAudioPlayer().setLevelSong(0);
                break;
            }
        }
        resetButtons();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Không cần hành động cho keyPressed
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Không cần hành động cho keyReleased
    }
}
