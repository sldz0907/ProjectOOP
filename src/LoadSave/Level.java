package LoadSave;

public class Level {
    private int[][] lvData;
    public Level(int[][] lvData)
    {
        this.lvData = lvData;
    }
    public int[][] getLvData()
    {
        return lvData;
    }
    public int getMapIndex(int x, int y)
    {
        return lvData[x][y];
    }
}
