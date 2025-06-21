package Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import GameState.GameStates;
import Mainpackage.GamePanel;

public class Keyboard implements KeyListener  {
    
    private GamePanel gamePanel;

    public Keyboard(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameStates.state) {
            case PLAYING:
                gamePanel.getGame().getPlaying().keyPressed(e);
                break;
            
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameStates.state) {
            case PLAYING:
                gamePanel.getGame().getPlaying().keyReleased(e);
                break;

            default:
                break;
        }
    }
        


    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    
}