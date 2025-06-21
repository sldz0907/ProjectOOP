package Entities.GameObject;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import GameState.Playing;
import LoadSave.LoadAndSave;
import Mainpackage.Game;
import Physic.MyShape2D.MyRectangle;

public class Sign extends GameObjects {
    private BufferedImage signImage, popupImage;
    private Playing playing;
    private int PUX, PUY, PUWidth, PUHeight;
    private String text;
    public Sign(float x, float y, float width, float height, Playing playing, String text) // hitBox của sign là 1 hình chữ nhật đủ nhỏ nhá :))
    {
        super(x, y, width, height, playing);
        this.playing = playing;
        this.text = text;
        loadSignImg();
        loadPopUpMessageImg();
    }
    private void loadPopUpMessageImg() {
        popupImage = LoadAndSave.GetSpriteAtlas(LoadAndSave.POP_UP_MESSAGE);
        PUWidth = (int) (popupImage.getWidth()* 0.75f);
        PUHeight = (int) (popupImage.getHeight() * 0.5f);
        PUX = Game.gameWidth / 2 - PUWidth / 2;
        PUY = Game.gameHeight / 2 - PUHeight / 2;

        addTextToPopup(text);
    }
    private void loadSignImg()
    {
        signImage = LoadAndSave.GetSpriteAtlas(LoadAndSave.SIGN); // lấy ảnh sign ra thoai
    }
    private void addTextToPopup(String text) {
        // Lấy đối tượng Graphics từ popupImage
        Graphics2D g2d = (Graphics2D) popupImage.getGraphics();
    
        // Cài đặt font, màu sắc và kích thước chữ
        Font font = new Font("Arial", Font.BOLD, 30);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
    
        // Lấy FontMetrics để đo kích thước văn bản
        FontMetrics fm = g2d.getFontMetrics(font);
    
        // Chiều rộng và chiều cao từng dòng
        int lineHeight = fm.getHeight();
        int maxWidth = PUWidth*8/10; // Giới hạn chiều rộng, trừ khoảng cách padding
    
        // Chia văn bản thành các dòng vừa với chiều rộng
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        java.util.List<String> lines = new java.util.ArrayList<>();
        
        for (String word : words) {
            // Thêm từ và kiểm tra chiều rộng
            String testLine = line + word + " ";
            if (fm.stringWidth(testLine) > maxWidth) {
                // Nếu dòng quá dài, lưu dòng hiện tại
                lines.add(line.toString());
                line = new StringBuilder(word + " ");
            } else {
                line.append(word).append(" ");
            }
        }
        // Thêm dòng cuối cùng
        lines.add(line.toString());
    
        // Tính vị trí bắt đầu vẽ để căn giữa theo chiều dọc
        int totalTextHeight = lines.size() * lineHeight;
        int startY = (PUHeight - totalTextHeight/4) / 2 + fm.getAscent();
    
        // Vẽ từng dòng ở giữa theo chiều ngang
        int y = startY;
        for (String l : lines) {
            int textWidth = fm.stringWidth(l);
            int x = (int)((PUWidth - textWidth)+Game.TILES_IN_WIDTH*9) / 2; // Căn giữa theo chiều ngang
            g2d.drawString(l, x, y);
            y += lineHeight;
        }
    
        // Giải phóng tài nguyên
        g2d.dispose();
    }
    @Override
    public void update()
    {
        ableToInterAct(); // kiểm tra xem tương tác được ko, nếu có thì báo cho nhân vật là tương tác đc, và id của chúng
    }
    private void ableToInterAct()
    {
        if (((MyRectangle)playing.getPlayer().getPlayerHitBox()).isCollide(hitBox))
        {
            playing.getPlayer().setAbleToInteractWithAnObject(true, this);
        }
        else
        {
            if (playing.getPlayer().getIdOfGameObject() == this)
                playing.getPlayer().resetObjectInteract();
        }
    }
    @Override
    public float distanceToPlayer()
    {
        return (float)Math.sqrt((playing.getPlayer().getX() - x) * (playing.getPlayer().getX() - x) + (playing.getPlayer().getY() - y) * (playing.getPlayer().getY() - y));
        
    }
    @Override
    public void render(Graphics g,int xLvlOffset,int yLvlOffset)
    {
        g.setColor(Color.RED);
        // g.drawRect((int)x - xLvlOffset, (int)y - yLvlOffset,(int) ((MyRectangle)hitBox).width,(int) ((MyRectangle)hitBox).height);
        g.drawImage(signImage, (int)x - xLvlOffset,(int)y - yLvlOffset,(int) ((MyRectangle)hitBox).width, (int) ((MyRectangle)hitBox).height, null);
    }
    @Override
    public void processRender(Graphics g)
    {
        g.setColor(Color.RED);
        g.drawImage(popupImage, PUX, PUY, PUWidth,  PUHeight, null);
    }
    @Override
    public void processLogic()
    {
        
    }
}
