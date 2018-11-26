package Game;

import FrameWork.Objects;

import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Box extends Objects {
    private BufferedImage box1_image;
    private BufferedImage box2_image;
    private BufferedImage box_haloween_1_image;
    private BufferedImage box_haloween_2_image;
    private BufferedImage box_haloween_cotden_3;
    private int numberBox;
    private int time_deleteBox = -1;
    public Box(int x,int y, int w, int h,int number_Box){
        super(x,y,w,h);
        numberBox = number_Box;
        try {
            //box1_image = ImageIO.read(new File("image_game/Box/box1.png"));
            box1_image = ImageIO.read(new File("image_game/Box/box_robot1.png"));
            box_haloween_1_image = ImageIO.read(new File("image_game/Box/box_haloween.png"));
        } catch (IOException e) {
        }
        try {
            //box2_image = ImageIO.read(new File("image_game/Box/box2.png"));
            box2_image = ImageIO.read(new File("image_game/Box/box_robot2.png"));
            box_haloween_2_image = ImageIO.read(new File("image_game/Box/pumpkin.png"));
            box_haloween_cotden_3 = ImageIO.read(new File("image_game/Box/haloween_cotden3.png"));
        } catch (IOException e) {
        }
    }
    public void PaintBox(Graphics2D g2){
        if(numberBox==1){
            g2.drawImage(box1_image,(int)this.getPosX(),(int)this.getPosY(),null);
        }
        if(numberBox==2){
            g2.drawImage(box2_image,(int)this.getPosX(),(int)this.getPosY(),null);
        }
        if(numberBox==3){
            g2.drawImage(box_haloween_1_image,(int)this.getPosX(),(int)this.getPosY(),null);
        }
        if(numberBox==4){
            g2.drawImage(box_haloween_2_image,(int)this.getPosX(),(int)this.getPosY(),null);
        }
        if(numberBox==5){
            g2.drawImage(box_haloween_cotden_3,(int)this.getPosX(),(int)this.getPosY(),null);
            Box2 box2;
            box2 = new Box2((int)this.getPosX(),(int)this.getPosY()-45,45,45,2);
            box2.paint(g2);
            box2 = new Box2((int)this.getPosX(),(int)this.getPosY()-90,45,45,1);
            box2.paint(g2);
        }
    }

    public void setTime_deleteBox(int time_deleteBox) {
        this.time_deleteBox = time_deleteBox;
    }

    public int getTime_deleteBox() {
        return time_deleteBox;
    }

    public void update_deleteBox(){
        if(time_deleteBox>0){
            time_deleteBox--;
        }
    }

    public int getNumberBox(){
        return numberBox;
    }
}
