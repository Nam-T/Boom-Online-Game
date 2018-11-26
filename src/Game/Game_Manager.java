package Game;

import FrameWork.GameScreen;
import FrameWork.SoundPlayer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;


public class Game_Manager{

    public int heart = 3;
    public int time_heart = 0;
    private Sprite_1 sprite_1;
    private ArrayList<Boom_1> boom_1s;
    private Map_1 map;
    private ArrayList<Box> boxes;
    private ArrayList<Items> items;
    private ArrayList<Enemy> enemies;
    private Boss boss;
    private ArrayList<Boom_explosive> boom_explosives;
    private ArrayList<Boss_Boom> boss_booms;
    private BlackHole blackHole;
    private BitSet bitSet = new BitSet();
    private SoundPlayer naruto_theme;
    private SoundPlayer lose_theme;
    private SoundPlayer explosive_theme;
    private boolean check_lose_theme = true;
    private boolean check_win = false;
    private boolean check_lose = false;
    private BufferedImage InfoImage;
    private int Score =0 ;
    private int DEADD =0;

    public void initGame(int round){
        naruto_theme = new SoundPlayer(new File("Sound/Fooling_Mode.wav"));
        lose_theme = new SoundPlayer(new File("Sound/lose.mid"));
        explosive_theme = new SoundPlayer(new File("Sound/heartbeat.wav"));
        switch (round){
            case 1:
                sprite_1=new Sprite_1(7*45,6*45,45,45);
                boom_1s=new ArrayList<Boom_1>();
                items=new ArrayList<Items>();
                boom_explosives=new ArrayList<Boom_explosive>();
                boss_booms = new ArrayList<Boss_Boom>();
                map=new Map_1(1);
                blackHole = new BlackHole(14*45,12*45,45,45);
                initBox("maps/Map1/Box.txt");
                initEnemy("maps/Map1/Enemy.txt");
                check_win = false;
                check_lose = false;
                break;
            case 2:
                sprite_1=new Sprite_1(0,0,45,45);
                boom_1s=new ArrayList<Boom_1>();
                items=new ArrayList<Items>();
                boom_explosives=new ArrayList<Boom_explosive>();
                boss_booms=new ArrayList<Boss_Boom>();
                map=new Map_1(2);
                blackHole = new BlackHole(14*45,12*45,45,45);
                initBox("maps/Map2/Box.txt");
                initEnemy("maps/Map2/Enemy.txt");
                check_win = false;
                check_lose = false;
                break;
            case 3:
                sprite_1=new Sprite_1(90,0,45,45);
                boom_1s=new ArrayList<Boom_1>();
                items=new ArrayList<Items>();
                boom_explosives=new ArrayList<Boom_explosive>();
                boss_booms=new ArrayList<Boss_Boom>();
                map=new Map_1(1);
                blackHole = new BlackHole(14*45,12*45,45,45);
                initBox("maps/Map3/Box.txt");
                initEnemy("maps/Map3/Enemy.txt");
                check_win = false;
                check_lose = false;
                break;
            case 4:
                sprite_1=new Sprite_1(0,0,45,45);
                boom_1s=new ArrayList<Boom_1>();
                items=new ArrayList<Items>();
                boom_explosives=new ArrayList<Boom_explosive>();
                boss_booms = new ArrayList<Boss_Boom>();
                map=new Map_1(1);
                blackHole = new BlackHole(14*45,12*45,45,45);
                initBox("maps/Map1/Box.txt");
                initEnemy("maps/Map1/Enemy.txt");
                check_win = false;
                check_lose = false;
                break;
        }
        naruto_theme.loop();
        try{
            InfoImage = ImageIO.read(new File("Images/background_Info.png"));
        }catch (IOException e){}
    }


    private void initBox(String boxPath){
        boxes=new ArrayList<Box>();
        try{
            FileReader file =new FileReader(boxPath);
            BufferedReader reader = new BufferedReader(file);
            String line="";
            int row=0;
            while ((line = reader.readLine()) !=null){
                for(int i=0;i<line.length();i++){
                    char c=line.charAt(i);
                    int code = Integer.parseInt(c +"");
                    if(code ==1){
                        int xBox1 = i*45;
                        int yBox1 = row*45;
                        Box box = new Box(xBox1,yBox1,45,45,1);
                        boxes.add(box);
                    }
                    if(code ==2){
                        int xBox2 = i*45;
                        int yBox2 = row*45;
                        Box box = new Box(xBox2,yBox2,45,45,2);
                        boxes.add(box);
                    }
                    if(code ==3){
                        int xBox3 = i*45;
                        int yBox3 = row*45;
                        Box box = new Box(xBox3,yBox3,45,45,3);
                        boxes.add(box);
                    }
                    if(code ==4){
                        int xBox4 = i*45;
                        int yBox4 = row*45;
                        Box box = new Box(xBox4,yBox4,45,45,4);
                        boxes.add(box);
                    }
                    if(code ==5){
                        int xBox5 = i*45;
                        int yBox5 = row*45;
                        Box box = new Box(xBox5,yBox5,45,45,5);
                        boxes.add(box);
                    }

                }
                row++;
            }
        }catch (Exception e){}
    }

    private void initEnemy(String EnemyPath){
        enemies= new ArrayList<Enemy>();
        try{
            FileReader file =new FileReader(EnemyPath);
            BufferedReader reader = new BufferedReader(file);
            String line="";
            int row=0;
            while ((line = reader.readLine()) !=null){
                for(int i=0;i<line.length();i++){
                    char c=line.charAt(i);
                    int code = Integer.parseInt(c +"");
                    if(code ==1){
                        int xEnemy1 = i*45;
                        int yEnemy1 = row*45;
                        Enemy enemy = new Enemy((int)xEnemy1,(int)yEnemy1,45,45);
                        enemies.add(enemy);
                    }
                    if(code == 2){
                        int xBoss = i*45;
                        int yBoss = row*45;
                        boss = new Boss(xBoss,yBoss,135,173);
                    }
                }
                row++;
            }
        }catch (Exception e){}
    }

    public void spriteMove(int orient){
        if(sprite_1.getStatus() == sprite_1.DEAD)
            return;
        sprite_1.changeOrient(orient);
        sprite_1.move(boxes,boom_1s,items);
    }

    public void game_update(long deltaTime){
        if(sprite_1.getTT()==1){sprite_1.boomber_down_animation.Update_Me(deltaTime);}
        if(sprite_1.getTT()==2){sprite_1.boomber_left_animation.Update_Me(deltaTime);}
        if(sprite_1.getTT()==3){sprite_1.boomber_right_animation.Update_Me(deltaTime);}
        if(sprite_1.getTT()==4){sprite_1.boomber_up_animation.Update_Me(deltaTime);}



        /*if(sprite_1.getTT()==1){sprite_1.hulk_down_animation.Update_Me(deltaTime);}
        if(sprite_1.getTT()==2){sprite_1.hulk_left_animation.Update_Me(deltaTime);}
        if(sprite_1.getTT()==3){sprite_1.hulk_right_animation.Update_Me(deltaTime);}
        if(sprite_1.getTT()==4){sprite_1.hulk_up_animation.Update_Me(deltaTime);}*/

        for(int i=0;i<boom_1s.size();i++){
            Boom_1 boom_1 = boom_1s.get(i);
            boom_1.update();
            boom_1.boom_animation.Update_Me(deltaTime);
            if(boom_1.getTime_explosive()==0){
                boom_1.setTime_exlosive();
                Boom_explosive boom_explosive = new Boom_explosive((int)boom_1.getPosX(),(int)boom_1.getPosY());
                boom_explosives.add(boom_explosive);
                boom_1s.remove(i);
                explosive_theme.play();
            }
        }

        for(int i=0;i<boom_explosives.size();i++){
            Boom_explosive boom_explosive1 = boom_explosives.get(i);
            boom_explosive1.update_time();
            boom_explosive1.explosive(this,sprite_1,boxes,items,enemies,boom_1s,boss,boom_explosives);
            if(boom_explosive1.getTime_bombang() == 0){
                boom_explosives.remove(i);
            }
        }

        for(int i=0;i<boxes.size();i++){
            Box box = boxes.get(i);
            box.update_deleteBox();
            if(box.getTime_deleteBox()==0){
                boxes.remove(i);
            }
        }

        for(int i=0;i<items.size();i++){
            Items item = items.get(i);
            item.Update_Items();
        }
        //
        for(int i=0;i<enemies.size();i++){
            Enemy enemy = enemies.get(i);
            enemy.update(boom_1s,sprite_1,boxes);
        }


        //
        for(int i=0;i<boss_booms.size();i++){
            Boss_Boom boss_boom = boss_booms.get(i);
            boss_boom.updateTime(sprite_1);
            if(boss_boom.getTime()==-20){
                boss_booms.remove(i);
            }
        }


        //
        if(boss!=null){
            boss.update(boss_booms);
            if(boss.recBoss2().intersects(sprite_1.getBound()) && boss.getTime_dead()==0 ){
                sprite_1.setStatus(sprite_1.DEAD);
            }
            if(boss.getHP_boss() <=0){
                if(boss.getTime_dead()==0){
                    this.Score +=200;
                    boss.setTime_dead(197);
                }
            }
            if(boss.getTime_dead()==1){
                boss=null;
            }
        }

        //
        if(bitSet.get(KeyEvent.VK_LEFT) && check_win == false){
            if(sprite_1.getStatus()!=sprite_1.DEAD ){
                sprite_1.setTT(2);
            }
            spriteMove(sprite_1.LEFT);
        }else
        if(bitSet.get(KeyEvent.VK_RIGHT) && check_win == false){
            spriteMove(sprite_1.RIGHT);
            if(sprite_1.getStatus()!=sprite_1.DEAD){
                sprite_1.setTT(3);
            }
        }else
        if(bitSet.get(KeyEvent.VK_UP) && check_win == false){
            spriteMove(sprite_1.UP);
            if(sprite_1.getStatus()!=sprite_1.DEAD){
                sprite_1.setTT(4);
            }
        }else
        if(bitSet.get(KeyEvent.VK_DOWN) && check_win == false){
            spriteMove(sprite_1.DOWN);
            if(sprite_1.getStatus()!=sprite_1.DEAD){
                sprite_1.setTT(1);
            }
        }


        if(sprite_1.getStatus()==Sprite_1.DEAD  && check_lose_theme == true){
            lose_theme.play();
            check_lose_theme = false;
            naruto_theme.close();
        }

        if(enemies.isEmpty() && boss == null){
            if(sprite_1.getRec().intersects(blackHole.getRec())){
                check_win = true;
                naruto_theme.close();
            }
        }

        if(sprite_1.getStatus() == sprite_1.DEAD){
            check_lose = true;
        }
    }

    public void draw_game(Graphics2D g2){
        map.PanitMap(g2);
        blackHole.Paint(g2);
        for(int i=0;i<items.size();i++){
            Items item = items.get(i);
            item.PaintItems(g2);
        }

        for(int i=0;i<boom_1s.size();i++){
            Boom_1 boom_1 = boom_1s.get(i);
            boom_1.boom_animation.PaintAnims((int)boom_1.getPosX(),(int)boom_1.getPosY(),boom_1.boom_image,g2,0,0);
        }

        for(int i=0;i<boom_explosives.size();i++){
            Boom_explosive boom_explosive_1 = boom_explosives.get(i);
            boom_explosive_1.Paint(g2);
        }

        g2.drawImage(InfoImage,675,0,null);
        for(int i=0;i<boxes.size();i++){
            Box box = boxes.get(i);
            box.PaintBox(g2);
        }

        for(int i=0;i<enemies.size();i++){
            Enemy enemy = enemies.get(i);
            enemy.Paint(g2);
        }

        for(int i=0;i<boss_booms.size();i++){
            Boss_Boom boss_boom = boss_booms.get(i);
            boss_boom.PaintBoomBoss(g2);
        }

        sprite_1.Paint(g2);

        if(boss!=null){
        boss.paintBoss(g2);}
        if(sprite_1.getCheck_vacham_boom()==1){
            sprite_1.boomber_dead_animation.PaintAnims((int)sprite_1.getPosX()-15,(int)sprite_1.getPosY()-6,sprite_1.boomber_dead,g2,0,0);
        }

        g2.setColor(Color.RED);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString("Score : ",700,50);
        g2.drawString(Integer.toString(this.Score),780,50);

    }

    public void key_pressed_action(KeyEvent e){
            bitSet.set(e.getKeyCode());

            if(boom_1s.size()<sprite_1.getBoom_Max()){
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    sprite_1.bombing(boom_1s);
                }
            }

    }
    public void key_released_action(KeyEvent e){
        bitSet.clear(e.getKeyCode());
    }

    public boolean getCheckWin(){
        return check_win;
    }

    public boolean getCheckLose() {return check_lose;}

    public int getScore(){return this.Score;}

    public void setScore(int score){
        this.Score = score;
    }
}