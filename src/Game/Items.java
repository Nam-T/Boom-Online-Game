package Game;

import FrameWork.Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Items extends Objects {
    private int status;
    private int time_;

    private BufferedImage heart_image;
    private BufferedImage bomsize_image;
    private BufferedImage boom_image;
    private BufferedImage shoe_image;

    public static final int HEART = 1000000;
    public static final int BOOM_SIZE = 2;
    public static final int BOOM = 3;
    public static final int SHOE = 4;

    public Items(int x,int y,int w,int h,int status_){
        super(x,y,w,h);
        status=status_;
        time_=21;
        try{
            heart_image = ImageIO.read(new File("image_game/Items/heart_1.png"));
            bomsize_image = ImageIO.read(new File("image_game/Items/item_bombsize.png"));
            boom_image = ImageIO.read(new File("image_game/Items/item_boom.png"));
            shoe_image = ImageIO.read(new File("image_game/Items/item_shoe.png"));
        }catch (IOException e){}
    }
    public void PaintItems(Graphics2D g2){
        if(this.status==HEART){
            g2.drawImage(heart_image,(int)this.getPosX(),(int)this.getPosY(),null);
        }
        if(this.status==BOOM_SIZE){
            g2.drawImage(bomsize_image,(int)this.getPosX(),(int)this.getPosY(),null);
        }
        if(this.status==BOOM){
            g2.drawImage(boom_image,(int)this.getPosX(),(int)this.getPosY(),null);
        }
        if(this.status==SHOE){
            g2.drawImage(shoe_image,(int)this.getPosX(),(int)this.getPosY(),null);
        }
    }

    public void Update_Items(){
        if(time_>0){
            time_--;
        }

    }

    public int getTime_(){
        return time_;
    }

    public int getStatus(){return  status;}
}
