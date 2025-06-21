package Entities.GameObject;

import java.awt.Graphics;
import java.util.ArrayList;

import GameState.Playing;
import LoadSave.LoadAndSave;

public class ObjectManager {
    private Playing playing;
    private ArrayList<GameObjects> gameObjects;
    
    public ObjectManager(Playing playing)
    {
        this.playing = playing;
        gameObjects = new ArrayList<>();
        loadGameObjects();
    }
    private void loadGameObjects()
    {
        gameObjects = LoadAndSave.getGameObjects();
    }
    public void render(Graphics g, int xLvlOffset, int yLvlOffset)
    {
        for (GameObjects i : gameObjects)
        {
            i.render(g, xLvlOffset, yLvlOffset);
        }
    }
    public void update()
    {
        for (GameObjects i : gameObjects)
        {
            i.update();
        }
    }
    public void resetAllObject() {
        gameObjects.clear();
        loadGameObjects();
    }
}
