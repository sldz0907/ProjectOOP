package Mainpackage;


import GameState.GameMenu;
import GameState.GameOptions;
import GameState.GameStates;
import GameState.Playing;
import UI.AudioOptions;

import java.awt.Graphics;

import Audio.AudioPlayer;

public class Game implements Runnable {
	public static int TILES_IN_WIDTH,TILES_IN_HEIGHT;

	public static int TILE_WIDTH_SIZE;

	public static int TILE_HEIGHT_SIZE;

    public static int gameWidth,gameHeight;
    public GameWindow gameWindow;
    public GamePanel gamePanel;

	private GameMenu menu;
	private Playing playing;
	private GameOptions gameOptions;
	private AudioOptions audioOptions;
	private AudioPlayer audioPlayer;

    private static final int FPS = 120;
    private static final int UPS = 200;

    public Game(int gameWidth, int gameHeight, int TILES_IN_WIDTH, int TILES_IN_HEIGHT, int TILE_WIDTH_SIZE, int TILE_HEIGHT_SIZE)
    {
        Game.gameWidth = gameWidth;
        Game.gameHeight = gameHeight;

		Game.TILES_IN_WIDTH = TILES_IN_WIDTH;
		Game.TILES_IN_HEIGHT = TILES_IN_HEIGHT;
		Game.TILE_HEIGHT_SIZE = TILE_HEIGHT_SIZE;
		Game.TILE_WIDTH_SIZE = TILE_WIDTH_SIZE;

		initGame();
        gamePanel = new GamePanel(gameWidth, gameHeight, this);
        gameWindow = new GameWindow(gamePanel);

        gamePanel.requestFocus();

        startGameLoop();
    }
	public Playing getPlaying() {
		return playing;
	}
	public GameMenu getMenu()
	{
		return menu;
	}
	public GameOptions getGameOptions() {
		return gameOptions;
	}

	public AudioOptions getAudioOptions() {
		return audioOptions;
	}
	public AudioPlayer getAudioPlayer(){
		return audioPlayer;
	}
	private void initGame()
	{
		audioOptions = new AudioOptions(this);
		menu = new GameMenu(this);
		playing = new Playing(this);
		gameOptions = new GameOptions(this);
		audioPlayer = new AudioPlayer();
	}
    private void startGameLoop()
    {
        Thread thread = new Thread(this);
        thread.start();
    }
    public synchronized void render(Graphics g)
    {
        switch (GameStates.state) {
			case MENU:
				menu.render(g);
				break;
			case PLAYING:
				playing.render(g);
				break;
			case OPTION:
				gameOptions.render(g);;
				break;
			default:
				break;
		}
    }
    private synchronized void update()
    {
		switch (GameStates.state) {
			case MENU:
				menu.update();;
				break;
			case PLAYING:
				playing.update();
				break;
			case OPTION:
				gameOptions.update();;
				break;
			case QUIT:
				System.exit(0);
			default:
				break;
		}
    }
    @Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS;
		double timePerUpdate = 1000000000.0 / UPS;

		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}

			if (deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				//System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;

			}
		}

	}   
}
