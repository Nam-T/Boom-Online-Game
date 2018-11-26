package Game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import FrameWork.AFrameOnImage;
import FrameWork.Animation;
import FrameWork.Objects;
public class Enemy extends Objects {
    private int TT;
    private int check_vacham_boom=0;
    public Animation ghost_die_animation;
    public Animation ghost_up_animation;
    public Animation ghost_down_animation;
    public Animation ghost_left_animation;
    public Animation ghost_right_animation;
    public BufferedImage ghost_die;
    public BufferedImage ghost_up_image;
    public BufferedImage ghost_down_image;
    public BufferedImage ghost_left_image;
    public BufferedImage ghost_right_image;

    public static final int ALIVE = 1;
    public static final int E_UP = 0;
    public static final int E_DOWN = 1;
    public static final int E_LEFT = 2;
    public static final int E_RIGHT = 3;

    private int orient;
    private int status;
    private boolean direction_right = true;

    public Enemy(int x,int y, int w, int h) {
        super(x,y,w,h);
        //TT = 2;
        status = this.ALIVE;
        animation_ghost();
    }

    public void update(ArrayList<Boom_1>boom1s,Sprite_1 sprite_1,ArrayList<Box>boxes){
        int dx=(int)this.getPosX();
        int dy=(int)this.getPosY();
        boolean check_in_boom = isImpactBoom(boom1s);
        switch (this.orient){
            case E_RIGHT:
                this.setPosX(this.getPosX()+1);
                break;
            case E_LEFT:
                this.setPosX(this.getPosX()-1);
                break;
            case E_DOWN:
                this.setPosY(this.getPosY()+1);
                break;
            case E_UP:
                this.setPosY(this.getPosY()-1);
                break;
        }

        if(this.isOutOfMap()==true){
            this.setPosX(dx);
            this.setPosY(dy);
            switch (this.orient){
                case E_RIGHT:
                    this.orient = E_LEFT;
                    this.TT =3;
                    break;
                case E_LEFT:
                    this.orient = E_RIGHT;
                    this.TT =4;
                    break;
                case E_DOWN:
                    this.orient = E_UP;
                    this.TT =1;
                    break;
                case E_UP:
                    this.orient = E_DOWN;
                    this.TT = 2;
                    break;
            }
        }

            if(isImpactBoom(boom1s) == true){
                this.setPosX(dx);
                this.setPosY(dy);
                switch (this.orient){
                    case E_RIGHT:
                        this.orient = E_LEFT;
                        this.TT = 3;
                        break;
                    case E_LEFT:
                        this.orient = E_RIGHT;
                        this.TT =4;
                        break;
                    case E_DOWN:
                        this.orient = E_UP;
                        this.TT =1;
                        break;
                    case E_UP:
                        this.orient = E_DOWN;
                        this.TT = 2;
                        break;
                }
            }


        if(isImpactVsBox(boxes) == true){
                this.setPosX(dx);
                this.setPosY(dy);
                switch (this.orient){
                    case E_RIGHT:
                        this.orient = E_LEFT;
                        this.TT = 3;
                        break;
                    case E_LEFT:
                        this.orient = E_RIGHT;
                        this.TT =4;
                        break;
                    case E_DOWN:
                        this.orient = E_UP;
                        this.TT = 1;
                        break;
                    case E_UP:
                        this.orient = E_DOWN;
                        this.TT =2;
                        break;
                }
        }

        int dxx = (int)this.getPosX();
        int dyy = (int)this.getPosY();
        Random rddd = new Random();
        int sddd = rddd.nextInt(101);
        if(this.getPosX()%45==0 && this.getPosY()%45==0 &&sddd>60){
            Random rd = new Random();
            int temp = rd.nextInt(4);
            if(this.orient == temp){
            }else{
                switch (temp){
                    case E_UP:
                        this.setPosY(this.getPosY()-1);
                        if(isImpactVsBox(boxes)==false || isImpactBoom(boom1s) == false || isOutOfMap() == false){
                            this.orient = E_UP;
                            this.TT = 1;
                        }else{
                            this.setPosY(dyy);
                        }
                        break;
                    case E_DOWN:
                        this.setPosY(this.getPosY()+1);
                        if(isImpactVsBox(boxes)==false || isImpactBoom(boom1s) == false || isOutOfMap() == false){
                            this.orient = E_DOWN;
                            this.TT = 2;
                        }else{
                            this.setPosY(dyy);
                        }
                        break;
                    case E_LEFT:
                        this.setPosX(this.getPosX()-1);
                        if(isImpactVsBox(boxes)==false || isImpactBoom(boom1s) == false || isOutOfMap() == false){
                            this.orient = E_LEFT;
                            this.TT =3;
                        }else{
                            this.setPosY(dxx);
                        }
                        break;
                    case E_RIGHT:
                        this.setPosX(this.getPosX()+1);
                        if(isImpactVsBox(boxes)==false || isImpactBoom(boom1s) == false || isOutOfMap() == false){
                            this.orient = E_RIGHT;
                            this.TT =4;
                        }else{
                            this.setPosY(dxx);
                        }
                        break;
                }
            }
        }

        if(isImpactBomber(sprite_1)==true){
            //if(game_manager.time_heart==0){
            sprite_1.setTT(0);
            sprite_1.setStatus(Sprite_1.DEAD);
        }
    }

    public boolean isOutOfMap(){
        if(this.getPosX()  > 630 ||
                this.getPosY()  > 545 ||
                this.getPosX() < 0 ||
                this.getPosY() < 0)
            return true;
        return false;
    }

    public boolean isImpactBoom(ArrayList<Boom_1> boom1s){
        for(int i=0;i<boom1s.size();i++){
            Boom_1 boom_1 = boom1s.get(i);
            if(this.getRec().intersects(boom_1.getRec())){
                return true;
            }
        }
        return false;
    }

    public boolean isImpactVsBox(ArrayList<Box> boxes){
        for(int i=0;i<boxes.size();i++){
            Box box = boxes.get(i);
            if(this.getRec().intersects(box.getRec())){
                return true;
            }
        }
        return false;
    }

    public boolean isImpactBomber(Sprite_1 sprite1){
        if(this.getRec().intersects(sprite1.getBound())){
            return true;
        }
        return false;
    }

    public void animation_ghost() {
        try {
            //ghost_die = ImageIO.read(new File("image_game/Enemy/ghost_die.png"));
            ghost_up_image = ImageIO.read(new File("image_game/Enemy/ghost_up.png"));
            ghost_down_image = ImageIO.read(new File("image_game/Enemy/ghost_down.png"));
            ghost_left_image = ImageIO.read(new File("image_game/Enemy/ghost_left.png"));
            ghost_right_image = ImageIO.read(new File("image_game/Enemy/ghost_right.png"));

        }catch(IOException ex) {
        }
        AFrameOnImage f;
        ghost_up_animation = new Animation(70);
        ghost_down_animation= new Animation(70);
        ghost_left_animation= new Animation(70);
        ghost_right_animation= new Animation(70);
        f = new AFrameOnImage(0,0,45,65);
        ghost_up_animation.AddFrame(f);
        ghost_down_animation.AddFrame(f);
        ghost_left_animation.AddFrame(f);
        ghost_right_animation.AddFrame(f);
    }


    public void Paint(Graphics2D g2) {
        if(this.getTT()==1) {
            this.ghost_up_animation.PaintAnims((int)this.getPosX(), (int)this.getPosY()-20,this.ghost_up_image, g2, 0, 0);
        }
        if(this.getTT()==2) {
            this.ghost_down_animation.PaintAnims((int)this.getPosX(), (int)this.getPosY()-20,this.ghost_down_image, g2, 0, 0);
        }
        if(this.getTT()==3) {
            this.ghost_left_animation.PaintAnims((int)this.getPosX(), (int)this.getPosY()-20,this.ghost_left_image, g2, 0, 0);
        }
        if(this.getTT()==4) {
            this.ghost_right_animation.PaintAnims((int)this.getPosX(), (int)this.getPosY()-20,this.ghost_right_image, g2, 0, 0);
        }
        if(this.getTT()==0) {
            //this.ghost_die_animation.PaintAnims((int)this.getPosX(), (int)this.getPosY(),this.ghost_die, g2, 0, 0);
        }
    }
    public int getTT() {
        return TT;
    }
    public void setTT(int tT) {
        TT = tT;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}