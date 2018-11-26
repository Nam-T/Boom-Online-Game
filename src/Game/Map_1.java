package Game;

import FrameWork.Objects;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.awt.*;

public class Map_1 {
    private BufferedImage map_1_image;
    private BufferedImage map_2_image;
    private int x,y;
    private int number_map;
    public Map_1(int num){
        this.number_map = num;
        try{
            //map_1_image = ImageIO.read(new File("image_game/background_Play.png"));
            map_1_image = ImageIO.read(new File("image_game/map_robot.png"));
            map_2_image = ImageIO.read(new File("image_game/map_haloween.png"));
        }catch (IOException ex){}
        x=0;
        y=0;
    }
    public void PanitMap(Graphics2D g2){

        if(number_map==1){
        g2.drawImage(map_1_image,x,y,null);}
        if(number_map==2){
            g2.drawImage(map_2_image,x,y,null);
        }
    }
}
