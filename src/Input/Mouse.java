package Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import GameState.GameStates;
import Mainpackage.GamePanel;

public class Mouse implements MouseListener, MouseMotionListener {

    private GamePanel gamePanel;

    public Mouse(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameStates.state) {
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseDragged(e);
                break;
            case OPTION:
                gamePanel.getGame().getGameOptions().mouseDragged(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameStates.state) {
            case MENU:
                gamePanel.getGame().getMenu().mouseMoved(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseMoved(e);
                break;
            case OPTION:
                gamePanel.getGame().getGameOptions().mouseMoved(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameStates.state) {
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseClicked(e);;
                break;
            case OPTION:
                gamePanel.getGame().getGameOptions().mouseClicked(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameStates.state) {
            case MENU:
                gamePanel.getGame().getMenu().mousePressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mousePressed(e);
                break;
            case OPTION:
                gamePanel.getGame().getGameOptions().mousePressed(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameStates.state) {
            case MENU:
                gamePanel.getGame().getMenu().mouseReleased(e);;
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseReleased(e);
                break;
            case OPTION:
                gamePanel.getGame().getGameOptions().mouseReleased(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
