package Game;

import FrameWork.Objects;
import FrameWork.Animation;
import FrameWork.AFrameOnImage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;

public class Sprite_1 extends Objects {
    private int TT;
    private int SPEED_=3;
    private int SPEED =5;
    private int Gap_ = 22;
    private int check_vacham_boom=0;
    private int countSpeed = 1000000;
    private int boom_Max = 2;
    private int speed = 1;
    public Animation boomber_dead_animation;
    public Animation boomber_up_animation;
    public Animation boomber_down_animation;
    public Animation boomber_left_animation;
    public Animation boomber_right_animation;
    public BufferedImage boomber_dead;
    public BufferedImage boomber_up_image;
    public BufferedImage boomber_down_image;
    public BufferedImage boomber_left_image;
    public BufferedImage boomber_right_image;
    public Animation hulk_up_animation;
    public Animation hulk_down_animation;
    public Animation hulk_left_animation;
    public Animation hulk_right_animation;
    public BufferedImage hulk_image;

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    public final static int ALIVE = 1;
    public final static int DEAD = 0;
    private int orient;
    private int status;
    private int boom_size_=1;

    public Sprite_1(int x, int y, int w, int h) {
        super(x, y, w, h);
        TT = 1;
        status = this.ALIVE;
        animation_bomber();
    }


    public boolean delayMove(){
        if (countSpeed == 0) countSpeed = 1000000;
        countSpeed--;
        if((countSpeed % (SPEED - SPEED_)) != 0)
            return true;
        return false;
    }

    public void move(ArrayList<Box> arrBox,ArrayList<Boom_1> boom1s,ArrayList<Items> items){
        if(SPEED==5){
        if(delayMove())
            return;}
        int xR = (int)this.getPosX();
        int yR = (int)this.getPosY();
        boolean check_in_boom = isInBoom(boom1s);
        switch (orient){
            case LEFT:
                this.setPosX(this.getPosX()-SPEED);
                break;
            case RIGHT:
                this.setPosX(this.getPosX()+SPEED);
                break;
            case UP:
                this.setPosY(this.getPosY()-SPEED);
                break;
            case DOWN:
                this.setPosY(this.getPosY()+SPEED);
                break;
        }
        boolean check = isImpactVsMap(arrBox);
        boolean check2 = isOutOfMap();
        if(check2 == true){
            this.setPosX(xR);
            this.setPosY(yR);
        }
        if(check == true){
            fixMove(orient, arrBox);
        }
        boolean check3 = isImpactVsBoom(boom1s);
        if(check_in_boom == true){
        }else{
            if(check3 == true){
                this.setPosX(xR);
                this.setPosY(yR);
            }
        }
        isImpactVsItems(items);
    }

    public void fixMove(int orient, ArrayList<Box> arrBox){
        switch (orient) {
            case UP:
                int mGap = gapUpDown(arrBox);
                this.setPosY(this.getPosY() + SPEED);
                if (mGap >= Gap_)
                    this.setPosX(this.getPosX() + SPEED);
                else if (mGap <= -Gap_)
                    this.setPosX(this.getPosX() - SPEED);
                break;
            case DOWN:
                int mGap1 = gapUpDown(arrBox);
                this.setPosY(this.getPosY() - SPEED);
                if (mGap1 >= Gap_ )
                    this.setPosX(this.getPosX() + SPEED);
                else if (mGap1 <= -Gap_ )
                    this.setPosX(this.getPosX() - SPEED);

                break;
            case LEFT:
                int mGap2 = gapLeftRight(arrBox);
                this.setPosX(this.getPosX()+SPEED);
                if(mGap2 >= Gap_)
                    this.setPosY(this.getPosY()+SPEED);
                else if(mGap2 <= -Gap_)
                    this.setPosY(this.getPosY()-SPEED);
                break;
            case RIGHT:
                int mGap3 = gapLeftRight(arrBox);
                this.setPosX(this.getPosX()-SPEED);
                if(mGap3 >= Gap_)
                    this.setPosY(this.getPosY()+SPEED);
                else if(mGap3 <= -Gap_)
                    this.setPosY(this.getPosY()-SPEED);
                break;
        }
    }

    public int gapUpDown(ArrayList<Box> arrBox){
        int count = 0;
        int mGap = 0;
        for(int i = 0; i < arrBox.size(); i++){
            if(this.getRec().intersects(arrBox.get(i).getRec())){
                count++;
                mGap = (int)this.getPosX() - (int)arrBox.get(i).getPosX();
            }
        }
        if(count > 1) return 0;
        return mGap;
    }

    public int gapLeftRight(ArrayList<Box> arrBox){
        int count = 0;
        int mGap = 0;
        for(int i = 0; i < arrBox.size(); i++){
            if(this.getRec().intersects(arrBox.get(i).getRec())){
                count++;
                mGap = (int)this.getPosY() - (int)arrBox.get(i).getPosY();
            }
        }
        if(count > 1) return 0;
        return mGap;
    }

    public boolean isImpactVsMap(ArrayList<Box> arrBox){
        for(int i = 0; i < arrBox.size(); i++){
            if(this.getRec().intersects(arrBox.get(i).getRec()))
                return true;
        }
        return false;
    }

    public boolean isOutOfMap(){
        if(this.getPosX()  > 630 ||
                this.getPosY()  > 545 ||
                this.getPosX() < 0 ||
                this.getPosY() < 0)
            return true;
        return false;
    }

    public boolean isImpactVsBoom(ArrayList<Boom_1> boom1s){
        for(int i=0;i<boom1s.size();i++){
            Boom_1 boom_1 = boom1s.get(i);
            if(this.getRec().intersects(boom_1.getRec())){
                return true;
            }
        }
        return false;
    }

    public boolean isInBoom(ArrayList<Boom_1> boom1s){
        for(int i=0;i<boom1s.size();i++){
            Boom_1 boom_1 = boom1s.get(i);
            if(this.getRec().intersects(boom_1.getRec())){
                return true;
            }
        }
        return false;
    }

    public void isImpactVsItems(ArrayList<Items> items){
        for(int i=0;i<items.size();i++){
            Items item = items.get(i);
            if(this.getBound().intersects(item.getRec())){
                items.remove(i);
                if(item.getStatus() == 2){
                    if(this.boom_size_<5){
                    this.boom_size_++;}
                }
                else if(item.getStatus() == 3){
                    if(this.boom_Max<7){
                        this.boom_Max ++;
                    }
                }
                else if(item.getStatus() == 4){
                    speed++;
                    if(speed == 2){
                        SPEED = 3;
                        fixXY();
                    }
                    if(speed == 4){
                        SPEED = 5;
                        SPEED_=4;
                        fixXY();
                    }
                }
            }
        }
    }

    public void fixXY(){
        int dx=(int)this.getPosX();
        int dy=(int)this.getPosY();
        for(int j=0;j<13;j++){
            for(int i=0;i<15;i++){
                if(abs((int)this.getPosX()-i*45)<22 && abs((int)this.getPosY()-j*45)<22){
                    dx=i*45;
                    dy=j*45;
                }
            }
        }
        this.setPosX(dx);
        this.setPosY(dy);
    }

    public int abs(int x){
        if(x<0){
            x=-x;
        }
        return x;
    }

    public void bombing(ArrayList<Boom_1> bombs){
        if(status == DEAD) return;
        //calculate postion
        int xBomb = 0, yBomb = 0;
        int xBomber = (int)this.getPosX();
        int yBomber = (int)this.getPosY();
        boolean check = true;
        for(int i = 0; i <= 14; i++){
            if(xBomber <= i * 45 + 22){
                xBomb = i * 45;
                check = false;
                break;
            }
        }
        if(check == true) xBomb = 13 * 45;
        check = true;
        for(int i = 0; i <= 12; i++){
            if(yBomber <= i * 45 + 22){
                yBomb = i * 45;
                check = false;
                break;
            }
        }
        if(check == true) yBomb = 13 * 45;

        check=true;
        for(int i=0;i<bombs.size();i++){
            Boom_1 boom_1 = bombs.get(i);
            if(boom_1.getPosX()==xBomb && boom_1.getPosY()==yBomb){
                check=false;
                break;
            }
        }
        if(check==true){
            Boom_1 b1 = new Boom_1(xBomb, yBomb, 45, 45);
            bombs.add(b1);
        }

        //GameSound.getIstance().getAudio(GameSound.BOMB).play();
    }

    public void changeOrient(int orient){
        this.orient = orient;
    }


    public int getTT(){return TT;}

    public void setTT(int n){
        TT=n;
    }

    public int getStatus(){
        return status;
    }

    public int getBoom_size_(){return boom_size_;}

    public int getBoom_Max(){return  boom_Max;}

    public void setStatus(int status) {
        this.status = status;
        TT=0;
    }


    public void animation_bomber(){
        try{
            boomber_dead = ImageIO.read(new File("image_game/Bomber/bomber_dead.png"));
            boomber_up_image = ImageIO.read(new File("image_game/Bomber/bomber_up.png"));
            boomber_down_image = ImageIO.read(new File("image_game/Bomber/bomber_down.png"));
            boomber_left_image = ImageIO.read(new File("image_game/Bomber/bomber_left.png"));
            boomber_right_image = ImageIO.read(new File("image_game/Bomber/bomber_right.png"));

            //

            hulk_image = ImageIO.read(new File("image_game/Bomber/hulk2.png"));

        }catch (IOException ex){}

        boomber_dead_animation = new Animation(70);
        AFrameOnImage f;
        f=new AFrameOnImage(0,0,61,69);
        boomber_dead_animation.AddFrame(f);
        boomber_up_animation = new Animation(70);
        boomber_down_animation = new Animation(70);
        boomber_left_animation = new Animation(70);
        boomber_right_animation = new Animation(70);
        f=new AFrameOnImage(0,0,45,65);
        boomber_up_animation.AddFrame(f);
        boomber_down_animation.AddFrame(f);
        boomber_left_animation.AddFrame(f);
        boomber_right_animation.AddFrame(f);


        hulk_down_animation = new Animation(70);
        hulk_up_animation = new Animation(70);
        hulk_left_animation = new Animation(70);
        hulk_right_animation = new Animation(70);
        f = new AFrameOnImage(0,0,45,65);
        hulk_down_animation.AddFrame(f);
        f = new AFrameOnImage(45,0,45,65);
        hulk_down_animation.AddFrame(f);
        f = new AFrameOnImage(90,0,45,65);
        hulk_down_animation.AddFrame(f);
        f = new AFrameOnImage(135,0,45,65);
        hulk_down_animation.AddFrame(f);

        f = new AFrameOnImage(0,65,45,65);
        hulk_left_animation.AddFrame(f);
        f = new AFrameOnImage(45,65,45,65);
        hulk_left_animation.AddFrame(f);
        f = new AFrameOnImage(90,65,45,65);
        hulk_left_animation.AddFrame(f);
        f = new AFrameOnImage(135,65,45,65);
        hulk_left_animation.AddFrame(f);

        f = new AFrameOnImage(0,130,45,65);
        hulk_right_animation.AddFrame(f);
        f = new AFrameOnImage(45,130,45,65);
        hulk_right_animation.AddFrame(f);
        f = new AFrameOnImage(90,130,45,65);
        hulk_right_animation.AddFrame(f);
        f = new AFrameOnImage(135,130,45,65);
        hulk_right_animation.AddFrame(f);

        f = new AFrameOnImage(0,195,45,65);
        hulk_up_animation.AddFrame(f);
        f = new AFrameOnImage(45,195,45,65);
        hulk_up_animation.AddFrame(f);
        f = new AFrameOnImage(90,195,45,65);
        hulk_up_animation.AddFrame(f);
        f = new AFrameOnImage(135,195,45,65);
        hulk_up_animation.AddFrame(f);
    }

    public Rectangle getBound() {
        return new Rectangle((int)this.getPosX()+15,(int) this.getPosY()+15,15,15);
    }
    public int getCheck_vacham_boom(){
        return check_vacham_boom;
    }

    public void Paint(Graphics2D g2){

        if(this.getTT()==1){this.boomber_down_animation.PaintAnims((int)this.getPosX(),(int)this.getPosY()-20,this.boomber_down_image,g2,0,0);}
        if(this.getTT()==2){this.boomber_left_animation.PaintAnims((int)this.getPosX(),(int)this.getPosY()-20,this.boomber_left_image,g2,0,0);}
        if(this.getTT()==3){this.boomber_right_animation.PaintAnims((int)this.getPosX(),(int)this.getPosY()-20,this.boomber_right_image,g2,0,0);}
        if(this.getTT()==4){this.boomber_up_animation.PaintAnims((int)this.getPosX(),(int)this.getPosY()-20,this.boomber_up_image,g2,0,0);}
        if(this.getTT()==0){this.boomber_dead_animation.PaintAnims((int)this.getPosX(),(int)this.getPosY()-20,this.boomber_dead,g2,0,0);}

        //////////////////
        /*if(this.getTT()==1){this.hulk_down_animation.PaintAnims((int)this.getPosX(),(int)this.getPosY()-20,this.hulk_image,g2,0,0);}
        if(this.getTT()==2){this.hulk_left_animation.PaintAnims((int)this.getPosX(),(int)this.getPosY()-20,this.hulk_image,g2,0,0);}
        if(this.getTT()==3){this.hulk_right_animation.PaintAnims((int)this.getPosX(),(int)this.getPosY()-20,this.hulk_image,g2,0,0);}
        if(this.getTT()==4){this.hulk_up_animation.PaintAnims((int)this.getPosX(),(int)this.getPosY()-20,this.hulk_image,g2,0,0);}
        //if(this.getTT()==0){this.boomber_dead_animation.PaintAnims((int)this.getPosX(),(int)this.getPosY()-20,this.boomber_dead,g2,0,0);}
        */

    }
}

