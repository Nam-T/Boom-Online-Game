package Game;

import FrameWork.Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Boss extends Objects {
    BufferedImage boss_up_image;
    BufferedImage boss_down_image;
    BufferedImage boss_left_image;
    BufferedImage boss_right_image;
    private int status;
    public static int BOSS_UP =1;
    public static int BOSS_DOWN =2;
    public static int BOSS_LEFT =3;
    public static int BOSS_RIGHT =4;
    private int HP_boss;
    private int time_orient = 0;
    private int time_shield = 0;

    BufferedImage boss_dead_1;
    BufferedImage boss_dead_2;
    BufferedImage boss_dead_3;
    BufferedImage boss_dead_4;
    BufferedImage boss_dead_5;
    BufferedImage boss_dead_6;
    BufferedImage boss_dead_7;

    public boolean check_dead = false;
    private int time_dead=0;

    public Boss(int x,int y, int w,int h){
        super(x,y,w,h);
        try{
            boss_up_image = ImageIO.read(new File("image_game/Boss/boss_up.png"));
            boss_down_image = ImageIO.read(new File("image_game/Boss/boss_down.png"));
            boss_left_image = ImageIO.read(new File("image_game/Boss/boss_left.png"));
            boss_right_image = ImageIO.read(new File("image_game/Boss/boss_right.png"));

            boss_dead_1 = ImageIO.read(new File("image_game/Boss/boss_dead_1.png"));
            boss_dead_2 = ImageIO.read(new File("image_game/Boss/boss_dead_2.png"));
            boss_dead_3 = ImageIO.read(new File("image_game/Boss/boss_dead_3.png"));
            boss_dead_4 = ImageIO.read(new File("image_game/Boss/boss_dead_4.png"));
            boss_dead_5 = ImageIO.read(new File("image_game/Boss/boss_dead_5.png"));
            boss_dead_6 = ImageIO.read(new File("image_game/Boss/boss_dead_6.png"));
            boss_dead_7 = ImageIO.read(new File("image_game/Boss/boss_dead_7.png"));
        }catch (IOException e){}
        this.status = BOSS_DOWN;
        HP_boss = 135;
    }

    public void update(ArrayList<Boss_Boom> boss_booms){
        int dx_boss =(int) this.getPosX();
        int dy_boss =(int) this.getPosY();

        if(this.getStatus() == BOSS_UP){
            this.setPosY(this.getPosY()-1);
        }
        else if(this.getStatus() == BOSS_DOWN){
            this.setPosY(this.getPosY()+1);
        }
        else if(this.getStatus() == BOSS_LEFT){
            this.setPosX(this.getPosX()-1);
        }
        else if(this.getStatus() == BOSS_RIGHT){
            this.setPosX(this.getPosX()+1);
        }

        if(isOutOfMap()){
            if(this.getStatus() == BOSS_UP){
                this.setStatus(BOSS_DOWN);
                this.setPosY(dy_boss);
            }
            else if(this.getStatus() == BOSS_DOWN){
                this.setStatus(BOSS_UP);
                this.setPosY(dy_boss);
            }
            else if(this.getStatus() == BOSS_LEFT){
                this.setStatus(BOSS_RIGHT);
                this.setPosX(dx_boss);
            }
            else if(this.getStatus() == BOSS_RIGHT){
                this.setStatus(BOSS_LEFT);
                this.setPosX(dx_boss);
            }
        }
        if(time_orient == 200){

            Random rd = new Random();
            int number = rd.nextInt(5);
            if(number==1 && this.getStatus()!=BOSS_UP){
                this.setStatus(BOSS_UP) ;
            }
            else if(number==2 && this.getStatus()!=BOSS_DOWN){
                this.setStatus(BOSS_DOWN) ;
            }
            else if(number==3 && this.getStatus()!=BOSS_LEFT){
                this.setStatus(BOSS_LEFT) ;
            }
            else if(number==4 && this.getStatus()!=BOSS_RIGHT){
                this.setStatus(BOSS_RIGHT) ;
            }

            time_orient =0;
        }


        if(time_orient ==199){
            Random rdd = new Random();
            int xnumber = rdd.nextInt(11);
            int ynumber = rdd.nextInt(9);
            xnumber+=2;
            ynumber-=2;
            Boss_Boom boss_boom = new Boss_Boom(xnumber*45,ynumber*45,105,105,1);
            boss_booms.add(boss_boom);
        }
        time_orient++;
        if(time_shield>0){
            time_shield--;
        }

        if(time_dead>1){
            time_dead--;
        }
    }

    public boolean isOutOfMap(){
        if(this.getPosX()  > 585 ||
                this.getPosY()  > 455 ||
                this.getPosX() < 0 ||
                this.getPosY() < 0)
            return true;
        return false;
    }
    public void paintBoss(Graphics2D g2){

        if(this.time_dead==0){
        if(status == BOSS_UP){
        g2.drawImage(boss_up_image,(int)this.getPosX(),(int)this.getPosY(),null);}
        if(status ==BOSS_DOWN){
            g2.drawImage(boss_down_image,(int)this.getPosX(),(int)this.getPosY(),null);
        }
        if(status ==BOSS_LEFT){
            g2.drawImage(boss_left_image,(int)this.getPosX(),(int)this.getPosY(),null);
        }
        if(status ==BOSS_RIGHT){
            g2.drawImage(boss_right_image,(int)this.getPosX(),(int)this.getPosY(),null);
        }

        g2.setColor(Color.RED);
        g2.fillRect((int)this.getPosX(),(int)this.getPosY()-10,this.HP_boss,10);

        g2.setColor(Color.BLACK);
        g2.drawRect((int)this.getPosX(),(int)this.getPosY()-10,135,10);
        }else {
            if(time_dead<=197&&time_dead>169){
                g2.drawImage(boss_dead_1,(int)this.getPosX(),(int)this.getPosY(),null);
            }

            if(time_dead<=169&&time_dead>141){
                g2.drawImage(boss_dead_2,(int)this.getPosX(),(int)this.getPosY(),null);
            }

            if(time_dead<=141&&time_dead>113){
                g2.drawImage(boss_dead_3,(int)this.getPosX(),(int)this.getPosY(),null);
            }

            if(time_dead<=113&&time_dead>85){
                g2.drawImage(boss_dead_4,(int)this.getPosX(),(int)this.getPosY(),null);
            }

            if(time_dead<=85&&time_dead>57){
                g2.drawImage(boss_dead_5,(int)this.getPosX(),(int)this.getPosY(),null);
            }

            if(time_dead<=57&&time_dead>29){
                g2.drawImage(boss_dead_6,(int)this.getPosX(),(int)this.getPosY(),null);
            }

            if(time_dead<=29&&time_dead>1){
                g2.drawImage(boss_dead_7,(int)this.getPosX(),(int)this.getPosY(),null);
            }
        }


    }

    public int getStatus(){return status;}

    public void setStatus(int status){this.status = status;}

    public Rectangle recBoss(){
        Rectangle rectangle = new Rectangle((int)this.getPosX(),(int)this.getPosY()+50,135,143);
        return rectangle;
    }

    public Rectangle recBoss2(){
        Rectangle rectangle = new Rectangle((int)this.getPosX(),(int)this.getPosY()+60,135,113);
        return rectangle;
    }

    public void setHP_boss(){
        if(this.time_shield==0){
            if(this.HP_boss>0){
                this.HP_boss -=135;
            }
            //this.HP_boss -=5;
            this.time_shield = 20;}
    }

    public int getHP_boss(){
        return this.HP_boss;
    }

    public int getTime_dead(){return time_dead;}
    public void setTime_dead(int n){this.time_dead = n;}
}
