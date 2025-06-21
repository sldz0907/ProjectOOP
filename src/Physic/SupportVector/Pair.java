package Physic.SupportVector;
import LoadSave.LoadAndSave;
import LoadSave.LoadAndSave.*;
public class Pair {
    public int[][] trapsData;
    public int firstNum;
    public int secondNum;

    public Pair(int firstNum, int secondNum, int levelId){
        this.firstNum = firstNum;
        this.secondNum = secondNum;
        this.trapsData = LoadAndSave.getLvData(Integer.toString(levelId));
    }

    public int getValue(){
        return trapsData[firstNum][secondNum];
    }
}
