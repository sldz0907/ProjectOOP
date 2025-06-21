package Entities.Traps;

import Entities.Player;
import LoadSave.LoadAndSave;
import java.awt.Graphics;
import java.util.ArrayList;



public class TrapsManager {
    private Player player;
    // private int[][] trapsData;
    private ArrayList<LazerTrap> lazerTraps = new ArrayList<>();
    private ArrayList<SpikeTrap> spikeTraps = new ArrayList<>();
    private ArrayList<WaterTrap> waterTraps = new ArrayList<>();
    private ArrayList<LavaTrap> lavaTraps = new ArrayList<>();
    private ArrayList<GunTrap> gunTraps = new ArrayList<>();
    private ArrayList<StaticSpike> staticSpikes = new ArrayList<>();
    private ArrayList<PoisonTrap> poisonTraps = new ArrayList<>();
    private ArrayList<MysteryBox> mysteryBox = new ArrayList<>();

    public TrapsManager(Player player) {
        this.player = player;
        // loadTrapData();
        initializeTraps();
    }

    private void initializeTraps() {
        lazerTraps = LoadAndSave.getLazerTraps();
        spikeTraps = LoadAndSave.getSpikeTraps();
        staticSpikes = LoadAndSave.getStaticSpikesTraps();
        lavaTraps = LoadAndSave.getLavaTraps();
        waterTraps = LoadAndSave.getWaterTraps();
        gunTraps = LoadAndSave.getGunTraps();
        poisonTraps = LoadAndSave.getPoisonData();
        mysteryBox = LoadAndSave.getMysteryBoxs();
    }

    public void update() {
        lazerTraps.forEach(trap -> trap.update());
        gunTraps.forEach(trap ->  trap.update());
        spikeTraps.forEach(trap ->  trap.update());
        staticSpikes.forEach(trap -> trap.update());
        lavaTraps.forEach(trap ->  trap.update());
        waterTraps.forEach(trap -> trap.update());
        poisonTraps.forEach(trap ->  trap.update());
        mysteryBox.forEach(trap -> trap.update());
        
    }

    public void drawTraps(Graphics g, int xLvlOffset, int yLvlOffset) {
        lazerTraps.forEach(trap -> trap.render(g, xLvlOffset, yLvlOffset));
        gunTraps.forEach(trap -> trap.render(g, xLvlOffset, yLvlOffset));
        spikeTraps.forEach(trap -> trap.render(g, xLvlOffset, yLvlOffset));
        staticSpikes.forEach(trap -> trap.render(g, xLvlOffset, yLvlOffset));
        lavaTraps.forEach(trap -> trap.render(g, xLvlOffset, yLvlOffset));
        waterTraps.forEach(trap -> trap.render(g, xLvlOffset, yLvlOffset));
        poisonTraps.forEach(trap -> trap.render(g, xLvlOffset, yLvlOffset));
        poisonTraps.forEach(trap -> trap.render(g, xLvlOffset, yLvlOffset));
       mysteryBox.forEach(trap -> trap.render(g, xLvlOffset, yLvlOffset));
    }

    public void resetAlltraps(){
        for (GunTrap G : gunTraps){
            G.reset();
        }

        for (PoisonTrap P : poisonTraps){
            P.reset();
        }

        for (LazerTrap L : lazerTraps){
            L.reset();
        }

        for (MysteryBox M : mysteryBox){
            M.reset();
        }

    }
}
