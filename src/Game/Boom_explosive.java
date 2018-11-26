package Game;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Boom_explosive {
    private int boom_size_up =1;
    private int boom_size_down = 1;
    private int boom_size_left=1;
    private int boom_size_right=1;
    private boolean check_size_up = false;
    private boolean check_size_down = false;
    private boolean check_size_left = false;
    private boolean check_size_right = false;
    private int x;
    private int y;
    private BufferedImage BoomBang_Up_image;
    private BufferedImage BoomBang_Down_image;
    private BufferedImage BoomBamng_Left_image;
    private BufferedImage BoomBang_Right_image;
    private int time_bombang;
    public Boom_explosive(int Boom_x, int Boom_y){
        x=Boom_x;
        y=Boom_y;
        this.time_bombang = 10;
        try{
            BoomBang_Up_image = ImageIO.read(new File("image_game/BoomBang/bombbang_up_1.png"));
            BoomBang_Down_image = ImageIO.read(new File("image_game/BoomBang/bombbang_down_1.png"));
            BoomBamng_Left_image = ImageIO.read(new File("image_game/BoomBang/bombbang_left_1.png"));
            BoomBang_Right_image = ImageIO.read(new File("image_game/BoomBang/bombbang_right_1.png"));
        }catch (IOException e){}
    }

    public void update_time(){
        this.time_bombang--;
    }
    public int getTime_bombang(){
        return this.time_bombang;
    }

    public void explosive(Game_Manager game_manager,Sprite_1 sprite1, ArrayList<Box> boxes, ArrayList<Items> items,ArrayList<Enemy> enemies,ArrayList<Boom_1> boom1s,Boss boss,ArrayList<Boom_explosive> boom_explosives){
        this.boom_size_up=0;
        this.boom_size_down=0;
        this.boom_size_left=0;
        this.boom_size_right=0;

        for(int k=1;k<=sprite1.getBoom_size_();k++) {
            Rectangle recUp = this.getRec(x, y - 45);
            Rectangle recDown = this.getRec(x, y + 45);
            Rectangle recLeft = this.getRec(x - 45, y);
            Rectangle recRight = this.getRec(x + 45, y);
            if(check_size_up == false){
                recUp = this.getRec(x, y - 45*k);
            }
            if(check_size_down == false){
                recDown = this.getRec(x, y + 45*k);
            }
            if(check_size_left == false){
                recLeft = this.getRec(x - 45*k, y);
            }
            if(check_size_right == false){
                recRight = this.getRec(x + 45*k, y);
            }


            boolean check_bomber_up = isImpactVsBomber(sprite1, recUp);
            boolean check_bomber_down = isImpactVsBomber(sprite1, recDown);
            boolean check_bomber_left = isImpactVsBomber(sprite1, recLeft);
            boolean check_bomber_right = isImpactVsBomber(sprite1, recRight);

            if (check_bomber_up == true ||
                    check_bomber_down == true ||
                    check_bomber_left == true ||
                    check_bomber_right == true) {
                sprite1.setStatus(Sprite_1.DEAD);
            }
            for (int i = 0; i < boxes.size(); i++) {
                Box box = boxes.get(i);
                boolean check_box_up = isImpactVsBox(box, recUp);
                boolean check_box_down = isImpactVsBox(box, recDown);
                boolean check_box_left = isImpactVsBox(box, recLeft);
                boolean check_box_right = isImpactVsBox(box, recRight);
                if(check_box_up == true){
                    check_size_up = true;
                }
                if(check_box_down == true){
                    check_size_down =true;
                }
                if(check_box_right == true){
                    check_size_right = true;
                }
                if(check_box_left == true){
                    check_size_left =true;
                }
                if (box.getNumberBox() == 2 || box.getNumberBox() ==4) {
                    if (check_box_up == true ||
                            check_box_down == true ||
                            check_box_left == true ||
                            check_box_right == true) {
                        if(box.getTime_deleteBox()<0){
                        Random rd = new Random();
                        int number = rd.nextInt(8);
                        items.add(new Items((int) box.getPosX(), (int) box.getPosY(), 45, 45, number));
                        box.setTime_deleteBox(10);}
                        //boxes.remove(i);
                    }
                }

            }

            for (int i = 0; i < items.size(); i++) {
                Items item = items.get(i);
                boolean check_item_up = isImpactVsItems(item, recUp);
                boolean check_item_down = isImpactVsItems(item, recDown);
                boolean check_item_left = isImpactVsItems(item, recLeft);
                boolean check_item_right = isImpactVsItems(item, recRight);
                if (item.getTime_() == 0) {
                    if (check_item_up == true ||
                            check_item_down == true ||
                            check_item_left == true ||
                            check_item_right == true) {
                        items.remove(i);
                    }
                }
            }

            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);
                boolean check_enemy_up = isImpactVsEnemy(enemy, recUp);
                boolean check_enemy_down = isImpactVsEnemy(enemy, recDown);
                boolean check_enemy_left = isImpactVsEnemy(enemy, recLeft);
                boolean check_enemy_right = isImpactVsEnemy(enemy, recRight);
                if (check_enemy_down == true ||
                        check_enemy_up == true ||
                        check_enemy_left == true ||
                        check_enemy_right == true) {
                    enemies.remove(i);
                    game_manager.setScore(game_manager.getScore() + 5);
                }
            }

            for (int i = 0; i < boom1s.size(); i++) {
                Boom_1 boom_1 = boom1s.get(i);
                boolean check_boom_up = isImpactVsBoom(boom_1, recUp);
                boolean check_boom_down = isImpactVsBoom(boom_1, recDown);
                boolean check_boom_left = isImpactVsBoom(boom_1, recLeft);
                boolean check_boom_right = isImpactVsBoom(boom_1, recRight);
                if (check_boom_up == true ||
                        check_boom_down == true ||
                        check_boom_left == true ||
                        check_boom_right == true) {
                    if (boom_1.getTime_explosive() > 0) {
                        boom_1.setTime_exlosive2();
                        Boom_explosive boom_explosive1 = new Boom_explosive((int) boom_1.getPosX(), (int) boom_1.getPosY());
                        boom_explosives.add(boom_explosive1);
                        //boom1s.remove(i);
                    }
                }

            }
            if (boss != null) {
                boolean check_boss_up = isImpactVsBoss(boss, recUp);
                boolean check_boss_down = isImpactVsBoss(boss, recDown);
                boolean check_boss_left = isImpactVsBoss(boss, recLeft);
                boolean check_boss_right = isImpactVsBoss(boss, recRight);
                if (check_boss_up == true ||
                        check_boss_down == true ||
                        check_boss_left == true ||
                        check_boss_right == true) {
                    boss.setHP_boss();
                    /*if(boss.getHP_boss() <=0){
                        boss = null;
                    }*/
                }
            }

            if(check_size_up == false){
                this.boom_size_up++;
            }
            if(check_size_down == false){
                this.boom_size_down++;
            }
            if(check_size_left == false){
                this.boom_size_left++;
            }
            if(check_size_right == false){
                this.boom_size_right++;
            }

        }
    }

    public boolean isImpactVsBomber(Sprite_1 sprite1,Rectangle rec){
        if(rec.intersects(sprite1.getBound())){
            return true;
        }
        return false;
    }

    public boolean isImpactVsBox(Box box, Rectangle rec){
        if(rec.intersects(box.getRec())){
            return true;
        }
        return false;
    }

    public boolean isImpactVsItems(Items item, Rectangle rec){
        if(rec.intersects(item.getRec())){
            return true;
        }
        return false;
    }

    public boolean isImpactVsEnemy(Enemy enemy,Rectangle rec){
        if(rec.intersects(enemy.getRec())){
            return true;
        }
        return false;
    }

    public boolean isImpactVsBoom(Boom_1 boom_1,Rectangle rec){
        if(rec.intersects(boom_1.getRec())){
            return true;
        }
        return false;
    }

    public boolean isImpactVsBoss(Boss boss,Rectangle rec){
        if(rec.intersects(boss.recBoss())){
            return true;
        }
        return false;
    }

    public  void Paint(Graphics2D g2){
        //System.out.println(this.getSizeUP());
        for(int i=1;i<=this.getSizeUP();i++) {
            g2.drawImage(BoomBang_Up_image, x, y -45*i, null);
            //System.out.println(45*i);
        }
        for(int i=0;i<boom_size_down;i++) {
            g2.drawImage(BoomBang_Down_image, x, y +45*i, null);
        }
        for(int i=1;i<=boom_size_left;i++) {
            g2.drawImage(BoomBamng_Left_image, x-45*i, y , null);
        }
        for(int i=0;i<=boom_size_right-1;i++) {
            g2.drawImage(BoomBang_Right_image, x+45*i, y , null);
        }
        //g2.drawImage(BoomBang_Up_image, x, y -45, null);
        //g2.drawImage(BoomBang_Up_image, x, y -45*2, null);
        //g2.drawImage(BoomBang_Up_image, x, y -45*3, null);
        //g2.drawImage(BoomBang_Down_image, x, y + 45, null);
        //g2.drawImage(BoomBamng_Left_image, x - 90, y, null);
        //g2.drawImage(BoomBang_Right_image, x + 45, y, null);
    }

    public Rectangle getRec(int recx,int recy){
        return new Rectangle(recx,recy, 45, 45);
    }
    public void setSizeUP(int a){
        boom_size_up = a;
    }
    public int getSizeUP(){return boom_size_up;}
}
