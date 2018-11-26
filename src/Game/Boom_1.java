package Game;

import FrameWork.Objects;
import FrameWork.Animation;
import FrameWork.AFrameOnImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Boom_1 extends Objects {
    private int time_explosive=200;
    public Animation boom_animation;
    public Animation boom_bang_animation;
    public BufferedImage boombbang_image;
    public BufferedImage boom_image;
    public Boom_1(int x,int y, int w, int h){
        super(x,y,w,h);
        animation_boom();
    }
    public void update(){
        if(time_explosive>0){
            time_explosive--;
        }
        if(time_explosive>-20&&time_explosive<0){
            time_explosive--;
        }

    }
    void animation_boom(){
        try{
            boom_image=ImageIO.read(new File("image_game/bomb_DarkHero.png"));
        }catch(IOException ex){}
        try{
            boombbang_image=ImageIO.read(new File("image_game/bombbang.png"));
        }catch(IOException ex){}
        boom_animation=new Animation(70);
        AFrameOnImage f;
        f=new AFrameOnImage(0,0,45,45);
        boom_animation.AddFrame(f);
        boom_animation.AddFrame(f);
        f=new AFrameOnImage(45,0,45,45);
        boom_animation.AddFrame(f);
        boom_animation.AddFrame(f);
        f=new AFrameOnImage(90,0,45,45);
        boom_animation.AddFrame(f);
        boom_animation.AddFrame(f);
        f=new AFrameOnImage(0,45,45,45);
        boom_animation.AddFrame(f);
        boom_animation.AddFrame(f);
        f= new AFrameOnImage(45,45,45,45);
        boom_animation.AddFrame(f);
        boom_animation.AddFrame(f);
        f = new AFrameOnImage(90,45,45,45);
        boom_animation.AddFrame(f);
        boom_animation.AddFrame(f);
        f = new AFrameOnImage(0,90,45,45);
        boom_animation.AddFrame(f);
        boom_animation.AddFrame(f);

        ///////////////////////////////////////////////////////////
        f=new AFrameOnImage(0,0,135,135);
        boom_bang_animation=new Animation(70);
        boom_bang_animation.AddFrame(f);
    }

    public int getTime_explosive() {
        return time_explosive;
    }

    public void setTime_exlosive(){
        time_explosive=1;
    }

    public void setTime_exlosive2(){time_explosive=0;}

    public Rectangle getBound() {
        return new Rectangle((int)this.getPosX(),(int) this.getPosY(),(int) this.getW(),(int) this.getH());
    }
}




