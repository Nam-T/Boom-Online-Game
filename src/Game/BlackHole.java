package Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BlackHole {
    private int x;
    private  int y;
    private  int w;
    private  int h;

    private BufferedImage black_hole_image;

    public BlackHole(int x,int y,int w,int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        try{
            black_hole_image = ImageIO.read(new File("image_game/back_hole.png"));
        }catch (IOException e){}

    }

    public void Paint(Graphics2D g2){
        g2.drawImage(black_hole_image,this.x,this.y,null);
    }

    public Rectangle getRec(){
        return new Rectangle(this.x+43,this.y,2,this.h);
    }
}
