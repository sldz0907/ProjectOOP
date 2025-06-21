package Mainpackage;
import java.awt.*;
public class Main {
    //release: 0.1
    public static void main(String[] args) throws Exception {
        System.out.println("Classpath: " + System.getProperty("java.class.path"));
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        
        int twidth =(int)(screenSize.width * 0.8f);
        int theight = (int)(screenSize.height * 0.8f);

        int tileWidthSize = (int)(0.03f * twidth);
        int tileHeightSize = (int)(0.04f * theight);

        int tileInWidth = (int) (twidth / tileWidthSize);
        int tileInHeight = (int) (theight / tileHeightSize);

        int width = tileWidthSize * tileInWidth;
        int height = tileHeightSize * tileInHeight;

        //System.out.println(width + " " + height + " " + tileInWidth + " " + tileInHeight + " " + tileWidthSize + " " + tileHeightSize);
        @SuppressWarnings("unused")
        Game game = new Game(width, height, tileInWidth, tileInHeight, tileWidthSize, tileHeightSize);

    }
}
