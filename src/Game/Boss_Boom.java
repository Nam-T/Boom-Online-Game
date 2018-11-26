package Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;

public class Boss_Boom {
    private int x;
    private int y;
    private int w;
    private int h;
    private int x_blurred;
    private int y_blurred;
    private int code;
    private BufferedImage boom_boss_image;
    private BufferedImage big_Boom_bang_image;
    private BufferedImage blurred_image;
    private BufferedImage big_boom_bong_may_1;
    private BufferedImage big_boom_bong_may_2;
    private BufferedImage big_boom_bong_may_3;
    private int timeMove = 90;

    public Boss_Boom(int x,int y,int w,int h,int code){
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
        this.code=code;
        this.x_blurred = x+45;
        this.y_blurred = y+5*45;
        initBoomBoss();
    }

    public void initBoomBoss(){
        try{
            boom_boss_image = ImageIO.read(new File("image_game/Boss/BigBoom.png"));
            big_Boom_bang_image = ImageIO.read(new File("image_game/Boss/BigBoomBang.png"));
            blurred_image = ImageIO.read(new File("image_game/Boss/blurred.png"));
            big_boom_bong_may_1 = ImageIO.read(new File("image_game/Boss/BigBoomBongMay_1.png"));
            big_boom_bong_may_2 = ImageIO.read(new File("image_game/Boss/BigBoomBongMay_2.png"));
            big_boom_bong_may_3 = ImageIO.read(new File("image_game/Boss/BigBoomBongMay_3.png"));
        }catch (IOException e){}
    }

    public void PaintBoomBoss(Graphics2D g2){
        g2.drawImage(blurred_image,this.x_blurred,this.y_blurred,null);
        if(timeMove>0) {
            if(timeMove>=60){
                g2.drawImage(big_boom_bong_may_1, this.x, this.y, null);
            }
            if(timeMove>30&&timeMove<60){
                g2.drawImage(big_boom_bong_may_2,this.x,this.y,null);
            }
            if(timeMove<=30){
                g2.drawImage(big_boom_bong_may_3, this.x, this.y, null);
            }

        }else {
            g2.drawImage(big_Boom_bang_image,this.x,this.y,null);
        }
    }

    public void updateTime(Sprite_1 sprite1){
        if(timeMove>0){
            this.timeMove--;
            this.y+=2;
        }

        if(timeMove>-20 && timeMove<=0){
            this.timeMove--;
            if(this.getRec().intersects(sprite1.getRec())){
                sprite1.setTT(0);
                sprite1.setStatus(sprite1.DEAD);
            }

        }
    }

    public Rectangle getRec(){
        Rectangle  rec = new Rectangle(x+15,y+15,w,h);
        return rec;
    }

    public int getTime(){
        return this.timeMove;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getH() {
        return h;
    }

    public int getW() {
        return w;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setW(int w) {
        this.w = w;
    }

    public void setH(int h) {
        this.h = h;
    }
}
