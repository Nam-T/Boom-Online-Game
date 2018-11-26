package Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Box2 {
    private int x;
    private int  y;
    private int w;
    private int h;
    private int numberBox;
    private BufferedImage haloween_cotden_1;
    private BufferedImage haloween_cotden_2;

    public Box2(int x,int y,int w,int h,int num){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        numberBox = num;
        try{
            haloween_cotden_1 = ImageIO.read(new File("image_game/Box/haloween_cotden1.png"));
            haloween_cotden_2 = ImageIO.read(new File("image_game/Box/haloween_cotden2.png"));
        }catch (IOException e){

        }

    }

    public void paint(Graphics2D g2){
        if(numberBox ==1){
            g2.drawImage(haloween_cotden_1,this.x,this.y,null);
        }
        if(numberBox ==2){
            g2.drawImage(haloween_cotden_2,this.x,this.y,null);
        }
    }
}
