package LoadSave;
import Entities.Enemy;
import Entities.EntityActions;
import Entities.GameObject.Destination;
import Entities.GameObject.GameObjects;
import Entities.GameObject.HealIcon;
import Entities.GameObject.Sign;
import Entities.Traps.GunTrap;
import Entities.Traps.LavaTrap;
import Entities.Traps.LazerTrap;
import Entities.Traps.MysteryBox;
import Entities.Traps.PoisonTrap;
import Entities.Traps.SpikeTrap;
import Entities.Traps.StaticSpike;
import Entities.Traps.WaterTrap;
import GameState.Playing;
import Mainpackage.Game;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.imageio.ImageIO;

import Physic.MyShape2D.MyCircle;
import Physic.SupportVector.Pair;
import java.util.Map;
import java.util.Random;
public class LoadAndSave {
    
    private static String nameTailFile = ".png";
    private static String nameTiles = "/asset/Tiles/tile_0";
    public static final String MENU_BACKGROUND = "asset/MenuButton/menu.png";
	public static final String PAUSE_BACKGROUND = "asset/MenuButton/pause_menu.png";
	public static final String SOUND_BUTTONS = "asset/MenuButton/sound_button.png";
	public static final String URM_BUTTONS = "asset/MenuButton/urm_buttons.png";
	public static final String VOLUME_BUTTONS = "asset/MenuButton/volume_buttons.png";
    public static final String BIG_CLOUDS = "asset/PlayingBG/Big Clouds.png";
	public static final String SMALL_CLOUDS = "asset/PlayingBG/Small Cloud 1.png";
    public static final String DEATH_SCREEN = "asset/MenuButton/death_screen.png";
    public static final String OPTIONS_MENU = "asset/MenuButton/options_background.png";
    public static final String COMPLETED_IMG = "asset/MenuButton/level_completed.png";
    // signs url
    public static final String SIGN = "asset/Signs/sign.png";
    public static final String POP_UP_MESSAGE = "asset/Signs/2.png";

    public static int[][] trapsData;
    // traps
    private static ArrayList<Enemy> enemyData;
    private static ArrayList<LazerTrap> lazerData;
    private static ArrayList<StaticSpike> staticSpikesData;
    private static ArrayList<SpikeTrap> spikeTrapsData;
    private static ArrayList<WaterTrap> waterData;
    private static ArrayList<LavaTrap> lavaData;
    private static ArrayList<GunTrap> gunData;
    private static ArrayList<PoisonTrap> poisonData;
    private static ArrayList<GameObjects> objectsData;
    private static ArrayList<MysteryBox> mysteryBoxsData;
    public static BufferedImage[][] loadPlayer()
    {
        BufferedImage imgs[];
        BufferedImage animations[][];
        InputStream is[] = new InputStream[9];
        is[0] = LoadAndSave.class.getResourceAsStream("/asset/PlayerActions/Idle.png");
        is[1] = LoadAndSave.class.getResourceAsStream("/asset/PlayerActions/Run.png");
        is[2] = LoadAndSave.class.getResourceAsStream("/asset/PlayerActions/Jump.png");
        is[3] = LoadAndSave.class.getResourceAsStream("/asset/PlayerActions/Dead.png");
        is[4] = LoadAndSave.class.getResourceAsStream("/asset/PlayerActions/Attack_1.png");
        is[5] = LoadAndSave.class.getResourceAsStream("/asset/PlayerActions/Attack_2.png");
        is[6] = LoadAndSave.class.getResourceAsStream("/asset/PlayerActions/Attack_3.png");
        is[7] = LoadAndSave.class.getResourceAsStream("/asset/PlayerActions/Attack_4.png");
        is[8] = LoadAndSave.class.getResourceAsStream("/asset/PlayerActions/Hurt.png");
        imgs = new BufferedImage[9];
        try {
            for(int i=0;i<9;i++) {
                imgs[i] = ImageIO.read(is[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                for(int i=0;i<9;i++) {
                    is[i].close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        animations = new BufferedImage[9][10];
        for(int i=0;i<9;i++) {
            for(int j=0;j<EntityActions.PlayerConstants.getSpriteAmount(i);j++) {
                animations[i][j] = imgs[i].getSubimage(j*128, 0, 128, 128);
            }
        }
        return animations;
    }
    private static String getStringNumber(int t)
    {
        String re = String.valueOf(t);
        try {
            if (re.length() == 1)
            {
                re = "00" + re;
            }
            else if (re.length() == 2)
            {
                re = "0" + re;
            }
            else if (re.length() == 3)
            {
                // do nothing
            }
            else
            {
                throw new Exception("nhập số bé hơn 150 với lón hơn 0 ấy");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return re;
    }
    
    public static BufferedImage[] loadTiles() // Cần được xem lại
    {
        BufferedImage[] tempt = new BufferedImage[151]; // có 150 tiles
        InputStream is;

        String t; 
        for (int i = 0; i < 151; i++)
        {
            t = nameTiles + getStringNumber(i) + nameTailFile;
            try {
                is = LoadAndSave.class.getResourceAsStream(t);
                tempt[i] = ImageIO.read(is);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return tempt;
    }
    public static BufferedImage[][] loadEnemy()
    {
        BufferedImage imgs[];
        BufferedImage animations[][];
        InputStream is[] = new InputStream[6];
        is[0] = LoadAndSave.class.getResourceAsStream("/asset/EnemyActions/Idle.png");
        is[1] = LoadAndSave.class.getResourceAsStream("/asset/EnemyActions/Walk.png");
        is[2] = LoadAndSave.class.getResourceAsStream("/asset/EnemyActions/Run.png");
        is[3] = LoadAndSave.class.getResourceAsStream("/asset/EnemyActions/Hurt.png");
        is[4] = LoadAndSave.class.getResourceAsStream("/asset/EnemyActions/Dead.png");
        is[5] = LoadAndSave.class.getResourceAsStream("/asset/EnemyActions/Attack_3.png");
        imgs = new BufferedImage[6];
        try {
            for(int i=0;i<6;i++) {
                imgs[i] = ImageIO.read(is[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                for(int i=0;i<6;i++) {
                    is[i].close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        animations = new BufferedImage[6][8];
        for(int i=0;i<6;i++) {
            for(int j=0;j<EntityActions.EnemyConstants.getSpriteAmount(i);j++) {
                animations[i][j] = imgs[i].getSubimage(j*128, 0, 128, 128);
            }
        }
        return animations;
    }
    public static BufferedImage[] loadSpike()
    {
        int numberOfTrapsImg = 8;   //có gì sửa mỗi cái này, rồi thêm phần vào mảng is là đc
        BufferedImage imgs[] = new BufferedImage[numberOfTrapsImg];
        InputStream[] is = new InputStream[numberOfTrapsImg];
        for (int i = 0; i < numberOfTrapsImg; i++) {
            is[i] = LoadAndSave.class.getResourceAsStream("/asset/Traps/SpikeTrap/tile00" + i + ".png");
        }
        try
        {
          for (int i = 0; i < numberOfTrapsImg; i++)
            {
                imgs[i] = ImageIO.read(is[i]);
            }  
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return imgs;
    }
    public static BufferedImage loadStaticSpike()
    {
        BufferedImage imgs = null;
        InputStream is = LoadAndSave.class.getResourceAsStream("/asset/Tiles/tile_0075.png");
        try {
            imgs = ImageIO.read(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgs;
    }
    public static BufferedImage[] loadLaser()
    {
        int numberOfTrapsImg = 3;   //có gì sửa mỗi cái này, rồi thêm phần vào mảng is là đc
        BufferedImage imgs[] = new BufferedImage[numberOfTrapsImg];
        InputStream[] is = new InputStream[numberOfTrapsImg];
        for (int i = 0; i < numberOfTrapsImg; i++) {
            is[i] = LoadAndSave.class.getResourceAsStream("/asset/Traps/LazerTrap/tile00" + i + ".png");
        }
        try
        {
          for (int i = 0; i < numberOfTrapsImg; i++)
            {
                imgs[i] = ImageIO.read(is[i]);
            }  
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return imgs;
    }
    public static void loadTrapAndEnemies(Playing playing, Level level) {
        enemyData = new ArrayList<>();
        lazerData = new ArrayList<>();
        staticSpikesData = new ArrayList<>();
        spikeTrapsData = new ArrayList<>();
        lavaData = new ArrayList<>();
        waterData = new ArrayList<>();
        gunData = new ArrayList<>();
        poisonData = new ArrayList<>();
        objectsData = new ArrayList<>();
        poisonData = new ArrayList<>();
        mysteryBoxsData= new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("./src/asset/Level/level"+ Integer.toString(playing.currentLevel) + "_traps_enemies.txt"))) {
            ArrayList<String[]> lines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine().trim().split("\s+"));
            }
     
            trapsData = new int[lines.size()][lines.get(0).length];
            boolean[][] visited = new boolean[lines.size()][lines.get(0).length];
            ArrayList<Pair> keyCor = new ArrayList<>();
            Map<Pair,Integer> mp = new HashMap<>();
            for (int i = 0; i < lines.size(); i++) {
                for (int j = 0; j < lines.get(i).length; j++) {
                    trapsData[i][j] = Integer.parseInt(lines.get(i)[j]);
                    int value = trapsData[i][j];
                    if (value == 135 || value == 136 || value == 137){
                        Pair pair = new Pair(i, j, playing.getLevelManager().levelId);
                        keyCor.add(pair);
                        mp.put(pair,value);
                    }
                }
            }

            for (int i = 0; i < lines.size(); i++) {
                for (int j = 0; j < lines.get(i).length; j++) {
                    if (trapsData[i][j] == 90) {
                        enemyData.add(new Enemy(j*Game.TILE_WIDTH_SIZE, (i-1)*Game.TILE_HEIGHT_SIZE, Game.gameWidth, Game.gameHeight, level.getLvData()));
                        //System.out.println("print Enemy");
                    }
                    else if (trapsData[i][j] == 83)
                    {
                        lazerData.add(new LazerTrap(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, 1, 0,0,0, playing.getPlayer(), playing));
                    }
                    else if (trapsData[i][j] == 75)

                    if (trapsData[i][j] == 120 && !visited[i][j])
                    {
                        int countLazerCol = 1;
                        while (j + countLazerCol < trapsData.length && trapsData[i + countLazerCol][j] == 83){
                            visited[i + countLazerCol][j] = true;
                            countLazerCol++;
                        }

                       for (Pair P : keyCor){
                        if (mp.get(P) == 135){
                        lazerData.add(new LazerTrap(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, 1, countLazerCol,P.secondNum*Game.TILE_WIDTH_SIZE,P.firstNum*Game.TILE_HEIGHT_SIZE, playing.getPlayer(),playing));
                        //System.out.println("Init sucess");
                        }
                       }
                    }

                    if (trapsData[i][j] == 121 && !visited[i][j])
                    {
                        int countLazerCol = 1;
                        while (j + countLazerCol < trapsData.length && trapsData[i + countLazerCol][j] == 121){
                            visited[i + countLazerCol][j] = true;
                            countLazerCol++;
                        }

                       for (Pair P : keyCor){
                        if (mp.get(P) == 136){
                        lazerData.add(new LazerTrap(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, 1, countLazerCol,P.secondNum*Game.TILE_WIDTH_SIZE,P.firstNum*Game.TILE_HEIGHT_SIZE, playing.getPlayer(),playing));
                        //System.out.println("Init sucess");
                        }
                       }
                    }

                    if (trapsData[i][j] == 122 && !visited[i][j])
                    {
                        int countLazerCol = 1;
                        while (j + countLazerCol < trapsData.length && trapsData[i + countLazerCol][j] == 122){
                            visited[i + countLazerCol][j] = true;
                            countLazerCol++;
                        }

                       for (Pair P : keyCor){
                        if (mp.get(P) == 137){
                        lazerData.add(new LazerTrap(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, 1, countLazerCol,P.secondNum*Game.TILE_WIDTH_SIZE,P.firstNum*Game.TILE_HEIGHT_SIZE, playing.getPlayer(),playing));
                        //System.out.println("Init sucess");
                        }
                       }
                    }

                    if (trapsData[i][j] == 123 && !visited[i][j])
                    {
                        int countLazerCol = 1;
                        while (j + countLazerCol < trapsData.length && trapsData[i + countLazerCol][j] == 123){
                            visited[i + countLazerCol][j] = true;
                            countLazerCol++;
                        }

                      
                        lazerData.add(new LazerTrap(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, 1, countLazerCol,0,0, playing.getPlayer(),playing));
                        //System.out.println("Init sucess");
                        
                       
                    }


                    if (trapsData[i][j] == 120 && !visited[i][j])
                    {
                        int countLazerCol = 1;
                        while (j + countLazerCol < trapsData.length && trapsData[i + countLazerCol][j] == 83){
                            visited[i + countLazerCol][j] = true;
                            countLazerCol++;
                        }

                       for (Pair P : keyCor){
                        if (mp.get(P) == 135){
                        lazerData.add(new LazerTrap(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, 1, countLazerCol,P.secondNum*Game.TILE_WIDTH_SIZE,P.firstNum*Game.TILE_HEIGHT_SIZE, playing.getPlayer(),playing));
                        //System.out.println("Init sucess");
                        }
                       }
                    }
                    
                   
                    if (trapsData[i][j] == 75)
                    {
                        staticSpikesData.add(new StaticSpike(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, Game.TILE_WIDTH_SIZE, Game.TILE_HEIGHT_SIZE, playing.getPlayer(), playing));
                    }
                    else if (trapsData[i][j] == 76)
                    {
                        spikeTrapsData.add(new SpikeTrap(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, 20, playing.getPlayer(), playing, LoadAndSave.getLvData(Integer.toString(playing.getLevelManager().levelId)))); // sửa lại radius
                    }
                    else if (trapsData[i][j] == 151)
                    {
                        lavaData.add(new LavaTrap(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, Game.TILE_WIDTH_SIZE, Game.TILE_HEIGHT_SIZE, playing.getPlayer(),playing, 1));
                    }
                    else if (trapsData[i][j] == 155)
                    {
                        lavaData.add(new LavaTrap(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, Game.TILE_WIDTH_SIZE, Game.TILE_HEIGHT_SIZE, playing.getPlayer(),playing, 0));
                    }
                    else if (trapsData[i][j] == 150)
                    {
                        int countWaterBlock = 1;
                        while (j + countWaterBlock < trapsData[0].length && trapsData[i][j + countWaterBlock] == 150){
                            countWaterBlock++;
                        }
                        waterData.add(new WaterTrap(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, Game.TILE_WIDTH_SIZE*countWaterBlock, Game.TILE_HEIGHT_SIZE, playing.getPlayer(),playing, 1));
                        j += countWaterBlock - 1;
                    }
                    else if (trapsData[i][j] == 152)
                    {
                        int countWaterBlock = 1;
                        while (j + countWaterBlock < trapsData[0].length && trapsData[i][j + countWaterBlock] == 152){
                            countWaterBlock++;
                        }
                        waterData.add(new WaterTrap(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, Game.TILE_WIDTH_SIZE*countWaterBlock, Game.TILE_HEIGHT_SIZE, playing.getPlayer(),playing, 0));
                        j += countWaterBlock - 1;
                    }
                    else if (trapsData[i][j] == 153)
                    {
                        gunData.add(new GunTrap(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, Game.TILE_WIDTH_SIZE, Game.TILE_HEIGHT_SIZE, playing,1));
                    }
                    else if (trapsData[i][j] == 154){
                        gunData.add(new GunTrap(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, Game.TILE_WIDTH_SIZE, Game.TILE_HEIGHT_SIZE, playing,-1));
                    }
                    else if (trapsData[i][j] == 100){
                        poisonData.add(new PoisonTrap(j*Game.TILE_WIDTH_SIZE, i*Game.TILE_HEIGHT_SIZE, Game.TILE_WIDTH_SIZE, Game.TILE_HEIGHT_SIZE, playing.getPlayer(), LoadAndSave.getLvData(Integer.toString(playing.getLevelManager().levelId))));
                    // else if (trapsData[i][j] == 156){
                    //     poisonData.add(new PoisonTrap(j*Game.TILE_WIDTH_SIZE, i*Game.TILE_HEIGHT_SIZE, Game.TILE_WIDTH_SIZE, Game.TILE_HEIGHT_SIZE, playing.getPlayer(), LoadAndSave.getLvData()));

                    }else if (trapsData[i][j] == 147){
                        Random random = new Random();
                        int luckyNumber = random.nextInt(4);
                        mysteryBoxsData.add(new MysteryBox(j*Game.TILE_WIDTH_SIZE, i*Game.TILE_HEIGHT_SIZE, Game.TILE_WIDTH_SIZE, Game.TILE_HEIGHT_SIZE, playing.getPlayer(), luckyNumber));

                    }
                    else if (trapsData[i][j] == 73)
                    {
                        objectsData.add(new Sign(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, Game.TILE_WIDTH_SIZE, Game.TILE_HEIGHT_SIZE, playing, "Bạn đang bước vào khu vực cực kỳ nguy hiểm! Mục tiêu của bạn là thoát khỏi nơi này càng nhanh càng tốt. Hãy cẩn thận với những mối đe dọa sau: Độc có thể rơi từ trên đầu xuống, tránh đứng yên dưới các khu vực có dấu hiệu nguy hiểm. Khi đứng trước các ụ súng, chúng sẽ tự động khai hỏa, tìm nơi ẩn nấp hoặc chạy thật nhanh. Nếu bị phát hiện, quái vật sẽ truy đuổi và tấn công bạn. Sẵn sàng phản công bằng đánh thường (Chuột Trái) hoặc sử dụng Ultimate (Chuột Phải) khi đủ năng lượng để gây sát thương lớn. Các vũng nước sẽ làm chậm tốc độ di chuyển, nên tránh bước vào trừ khi cần thiết. Cẩn thận với bẫy gai vì bạn sẽ mất máu nếu giẫm phải. Nhiệm vụ của bạn là tìm 3 chìa khóa để vô hiệu hóa 3 trong 6 tia laser. Bạn sẽ phải mạo hiểm vượt qua các laser còn lại. Nếu mất quá nhiều máu, hãy tìm trái tim để hồi phục sức khỏe."));
                    }
                    else if(trapsData[i][j] == 74)
                    {
                        objectsData.add(new Sign(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, Game.TILE_WIDTH_SIZE, Game.TILE_HEIGHT_SIZE, playing, "Cảnh báo! Phía trước là khu vực đầy rẫy bẫy răng cưa nguy hiểm. Hãy chuẩn bị tinh thần vì đoạn đường leo trèo tiếp theo sẽ cực kỳ khó khăn. Quan sát kỹ, căn thời gian chính xác và đừng từ bỏ!")); 
                    }
                    else if (trapsData[i][j] == 89)
                    {
                        objectsData.add(new Sign(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, Game.TILE_WIDTH_SIZE, Game.TILE_HEIGHT_SIZE, playing, "Cố lên! Bạn đã gần đến đích rồi! Nhưng đừng chủ quan. Phía trước là vùng dung nham chết người, chỉ cần một sai lầm nhỏ là bạn sẽ gặp nguy hiểm. Cẩn thận với các ụ súng gắn trên tường, chúng sẽ bắn khi phát hiện bạn. Tiến lên và hoàn thành thử thách!"));
                    }
                    else if (trapsData[i][j] == 101)
                    {
                        objectsData.add(new Destination(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, Game.TILE_WIDTH_SIZE, Game.TILE_HEIGHT_SIZE, playing, 101));
                    }
                    else if (trapsData[i][j] == 102)
                    {
                        objectsData.add(new Destination(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, Game.TILE_WIDTH_SIZE, Game.TILE_HEIGHT_SIZE, playing, 102));
                    }
                    else if (trapsData[i][j] == 116)
                    {
                        objectsData.add(new Destination(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, Game.TILE_WIDTH_SIZE, Game.TILE_HEIGHT_SIZE, playing, 116));
                    }
                    else if (trapsData[i][j] == 117)
                    {
                        objectsData.add(new Destination(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, Game.TILE_WIDTH_SIZE, Game.TILE_HEIGHT_SIZE, playing, 117));
                    }
                    else if(trapsData[i][j] == 132)
                    {
                        objectsData.add(new HealIcon(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, Game.TILE_WIDTH_SIZE, Game.TILE_HEIGHT_SIZE, playing));
                    }
                    else if (trapsData[i][j] == 156)
                    {
                        poisonData.add(new PoisonTrap(j * Game.TILE_WIDTH_SIZE, i * Game.TILE_HEIGHT_SIZE, Game.TILE_WIDTH_SIZE, Game.TILE_HEIGHT_SIZE, playing.getPlayer(), playing.getLevelManager().level.getLvData()));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static BufferedImage[] loadLava()
    {
        int numberOfTrapsImg = 2;   //có gì sửa mỗi cái này, rồi thêm phần vào mảng is là đc
        BufferedImage imgs[] = new BufferedImage[numberOfTrapsImg];
        InputStream[] is = new InputStream[numberOfTrapsImg];
        for (int i = 0; i < numberOfTrapsImg; i++) {
            is[i] = LoadAndSave.class.getResourceAsStream("/asset/Traps/LavaTrap/tile00" + i + ".png");
        }
        try
        {
          for (int i = 0; i < numberOfTrapsImg; i++)
            {
                imgs[i] = ImageIO.read(is[i]);
            }  
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return imgs;
    }

    public static BufferedImage[] loadWater()
    {
        int numberOfTrapsImg = 2;   //có gì sửa mỗi cái này, rồi thêm phần vào mảng is là đc
        BufferedImage imgs[] = new BufferedImage[numberOfTrapsImg];
        InputStream[] is = new InputStream[numberOfTrapsImg];
        for (int i = 0; i < numberOfTrapsImg; i++) {
            is[i] = LoadAndSave.class.getResourceAsStream("/asset/Traps/WaterTrap/tile00" + i + ".png");
        }
        try
        {
          for (int i = 0; i < numberOfTrapsImg; i++)
            {
                imgs[i] = ImageIO.read(is[i]);
            }  
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return imgs;
    }

    public static BufferedImage[] loadBullet()
    {
        int numberOfTrapsImg = 1;   //có gì sửa mỗi cái này, rồi thêm phần vào mảng is là đc
        BufferedImage imgs[] = new BufferedImage[numberOfTrapsImg];
        InputStream[] is = new InputStream[numberOfTrapsImg];
        for (int i = 0; i < numberOfTrapsImg; i++) {
            is[i] = LoadAndSave.class.getResourceAsStream("/asset/Traps/Bullet/tile00" + i + ".png");
        }
        try
        {
          for (int i = 0; i < numberOfTrapsImg; i++)
            {
                imgs[i] = ImageIO.read(is[i]);
            }  
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return imgs;
    }

    public static BufferedImage[] loadGun()
    {
        int numberOfTrapsImg = 2;   //có gì sửa mỗi cái này, rồi thêm phần vào mảng is là đc
        BufferedImage imgs[] = new BufferedImage[numberOfTrapsImg];
        InputStream[] is = new InputStream[numberOfTrapsImg];
        for (int i = 0; i < numberOfTrapsImg; i++) {
            is[i] = LoadAndSave.class.getResourceAsStream("/asset/Traps/GunTrap/tile00" + i + ".png");
        }
        try
        {
          for (int i = 0; i < numberOfTrapsImg; i++)
            {
                imgs[i] = ImageIO.read(is[i]);
            }  
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return imgs;
    }

    public static BufferedImage[] loadPoison()
    {
        int numberOfTrapsImg = 1;   //có gì sửa mỗi cái này, rồi thêm phần vào mảng is là đc
        BufferedImage imgs[] = new BufferedImage[numberOfTrapsImg];
        InputStream[] is = new InputStream[numberOfTrapsImg];
        for (int i = 0; i < numberOfTrapsImg; i++) {
            is[i] = LoadAndSave.class.getResourceAsStream("/asset/Traps/PoisonTrap/tile00" + i + ".png");
        }
        try
        {
          for (int i = 0; i < numberOfTrapsImg; i++)
            {
                imgs[i] = ImageIO.read(is[i]);
            }  
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return imgs;
    }

    public static BufferedImage[] loadMysteryBox()
    {
        int numberOfTrapsImg = 3;   //có gì sửa mỗi cái này, rồi thêm phần vào mảng is là đc
        BufferedImage imgs[] = new BufferedImage[numberOfTrapsImg];
        InputStream[] is = new InputStream[numberOfTrapsImg];
        for (int i = 0; i < numberOfTrapsImg; i++) {
            is[i] = LoadAndSave.class.getResourceAsStream("/asset/Traps/MysteryBox/tile00" + i + ".png");
        }
        try
        {
          for (int i = 0; i < numberOfTrapsImg; i++)
            {
                imgs[i] = ImageIO.read(is[i]);
            }  
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return imgs;
    }

    public static BufferedImage[] loadKey()
    {
        int numberOfTrapsImg = 1;   //có gì sửa mỗi cái này, rồi thêm phần vào mảng is là đc
        BufferedImage imgs[] = new BufferedImage[numberOfTrapsImg];
        InputStream[] is = new InputStream[numberOfTrapsImg];
        for (int i = 0; i < numberOfTrapsImg; i++) {
            is[i] = LoadAndSave.class.getResourceAsStream("/asset/Traps/Key/tile00" + i + ".png");
        }
        try
        {
          for (int i = 0; i < numberOfTrapsImg; i++)
            {
                imgs[i] = ImageIO.read(is[i]);
            }  
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return imgs;
    }
    public static ArrayList<GunTrap> getGunTraps()
    {
        return gunData;
    }
    public static ArrayList<SpikeTrap> getSpikeTraps()
    {
        return spikeTrapsData;
    }
    public static ArrayList<MysteryBox> getMysteryBoxs()
    {
        return mysteryBoxsData;
    }
    public static ArrayList<StaticSpike> getStaticSpikesTraps()
    {
        return staticSpikesData;
    }
    public static ArrayList<Enemy> getEnemies() {
        return enemyData;
    }
    public static ArrayList<PoisonTrap> getPoisonData()
    {
        return poisonData;
    }
    public static ArrayList<LazerTrap> getLazerTraps()
    {
        return lazerData;
    }
    public static ArrayList<LavaTrap> getLavaTraps()
    {
        return lavaData;
    }
    public static ArrayList<WaterTrap> getWaterTraps()
    {
        return waterData;
    }
    public static ArrayList<PoisonTrap> getPoisonTraps(){
        return poisonData;
    }
    public static ArrayList<GameObjects> getGameObjects()
    {
        ArrayList<GameObjects> t = new ArrayList<>();
        for (int i = 0; i < objectsData.size(); i++)
        {
            if (objectsData.get(i) instanceof HealIcon)
            {
                ((HealIcon) objectsData.get(i)).active = true;
            }
            t.add(objectsData.get(i));
        }
        return t;
    }
    public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadAndSave.class.getResourceAsStream("/" + fileName);
		try {
			img = ImageIO.read(is);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}
    public static int[][] getLvData(String level)
    {
        int[][] lvData = new int[0][0];

        try {
            Scanner scanner = new Scanner(new File("./src/asset/Level/level"+ level +"_tiles.txt"));

            ArrayList<String[]> a = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                a.add(line.trim().split("\\s+"));
            }
            scanner.close();

            lvData = new int[a.size()][a.get(0).length];

            for (int i = 0; i < a.size(); i++) {
                for (int j = 0; j < a.get(i).length; j++) {
                    lvData[i][j] = Integer.parseInt(a.get(i)[j]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        return lvData;
    }
    public static BufferedImage loadStatusBarImg() {
        BufferedImage img = null;
        InputStream is = LoadAndSave.class.getResourceAsStream("/asset/PlayerBar/health_power_bar.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }
    public static int[][] getTrapsEnemiesData()
    {
        int[][] trapsEnemiesData = new int[0][0];
        try {
            Scanner scanner = new Scanner(new File("./src/asset/Level/level1_traps_enemies.txt"));

            ArrayList<String[]> a = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                a.add(line.trim().split("\\s+"));
            }
            scanner.close();

            trapsEnemiesData = new int[a.size()][a.get(0).length];

            for (int i = 0; i < a.size(); i++) {
                for (int j = 0; j < a.get(i).length; j++) {
                    trapsEnemiesData[i][j] = Integer.parseInt(a.get(i)[j]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        return trapsEnemiesData;
    }
    public static BufferedImage[] loadUltimate() {
        BufferedImage animations[];
        InputStream is[] = new InputStream[30];
        for (int i=0; i<30; i++) {
            is[i] = LoadAndSave.class.getResourceAsStream("/asset/Ultimate/1_" + i +".png");
        }
        animations = new BufferedImage[30];
        try {
            for(int i=0;i<30;i++) {
                animations[i] = ImageIO.read(is[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                for(int i=0;i<30;i++) {
                    is[i].close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return animations;
    }
    public static BufferedImage loadHeartIcon() {
        BufferedImage img = null;
        InputStream is = LoadAndSave.class.getResourceAsStream("/asset/PlayerBar/heart.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return img;
    }
    public static BufferedImage getDestination(int imageIndex) {
        BufferedImage img = null;
        InputStream is = LoadAndSave.class.getResourceAsStream("/asset/Tiles/tile_0" + imageIndex + ".png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return img;
    }
}
