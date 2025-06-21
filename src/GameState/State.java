package GameState;

import java.awt.event.MouseEvent;

import Audio.AudioPlayer;
import Mainpackage.Game;
import Physic.MyShape2D.MyRectangle;

public class State {
    protected Game game;
    public State(Game game){
        this.game = game;
    }
    public boolean isOn(MouseEvent e, MyRectangle roundBox)
    {
        if (roundBox.isAPointCollideRect(e.getX(), e.getY(),roundBox))
            return true;
        return false;
    }
    public Game getGame() {
		return game;
	}
    public void setGamestates(GameStates state) {
		switch (state) {
                    case MENU -> game.getAudioPlayer().playSong(AudioPlayer.MENU_1);
                    case PLAYING -> game.getAudioPlayer().setLevelSong(0);
		}

		GameStates.state = state;
	}
}
