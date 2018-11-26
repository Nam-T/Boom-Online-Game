package Game;

import FrameWork.GameScreen;
import FrameWork.SoundPlayer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.BitSet;


public class MainGame extends GameScreen{

    private Sprite_1 sprite_1;
    private ArrayList<Boom_1> boom_1s;
    private Map_1 map;
    private ArrayList<Box> boxes;
    private ArrayList<Items> items;
    private ArrayList<Enemy> enemies;
    private BitSet bitSet = new BitSet();
    private SoundPlayer naruto_theme;
    private SoundPlayer lose_theme;
    private boolean check_lose = true;

    public MainGame(){
        super(800,600);
        sprite_1=new Sprite_1(0,0,45,45);
        boom_1s=new ArrayList<Boom_1>();
        items=new ArrayList<Items>();
        map=new Map_1(1);
        initBox("maps/Map2/Box.txt");
        initEnemy("maps/Map1/Enemy.txt");
        naruto_theme = new SoundPlayer(new File("Sound/Fooling_Mode.wav"));
        naruto_theme.play();
        lose_theme = new SoundPlayer(new File("Sound/lose.mid"));
        BeginGame();
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

    public static void main(String args[]){
        new MainGame();
    }

    @Override
    public void GAME_UPDATE(long deltaTime){
        if(sprite_1.getTT()==1){sprite_1.boomber_down_animation.Update_Me(deltaTime);}
        if(sprite_1.getTT()==2){sprite_1.boomber_left_animation.Update_Me(deltaTime);}
        if(sprite_1.getTT()==3){sprite_1.boomber_right_animation.Update_Me(deltaTime);}
        if(sprite_1.getTT()==4){sprite_1.boomber_up_animation.Update_Me(deltaTime);}
        for(int i=0;i<boom_1s.size();i++){
            Boom_1 boom_1 = boom_1s.get(i);
            boom_1.update();
            if(boom_1.getTime_explosive()==0){
                boom_1.setTime_exlosive();
            }
            if(boom_1.getTime_explosive()<0&&boom_1.getTime_explosive()>-20){
                Boom_explosive boom_explosive = new Boom_explosive((int)boom_1.getPosX(),(int)boom_1.getPosY());
                //boom_explosive.explosive(sprite_1,boxes,items,enemies,boom_1s);
            }
            if(boom_1.getTime_explosive()==-20){
                boom_1s.remove(i);
            }
        }

        for(int i=0;i<items.size();i++){
            Items item = items.get(i);
            item.Update_Items();
        }
        //
        for(int i=0;i<enemies.size();i++){
            Enemy enemy = enemies.get(i);
            //enemy.update(boom_1s,sprite_1,boxes,this);
        }

        //
        if(bitSet.get(KeyEvent.VK_LEFT)){
            if(sprite_1.getStatus()!=sprite_1.DEAD){
                sprite_1.setTT(2);
            }
            spriteMove(sprite_1.LEFT);
        }else
        if(bitSet.get(KeyEvent.VK_RIGHT)){
            spriteMove(sprite_1.RIGHT);
            if(sprite_1.getStatus()!=sprite_1.DEAD){
                sprite_1.setTT(3);
            }
        }else
        if(bitSet.get(KeyEvent.VK_UP)){
            spriteMove(sprite_1.UP);
            if(sprite_1.getStatus()!=sprite_1.DEAD){
                sprite_1.setTT(4);
            }
        }else
        if(bitSet.get(KeyEvent.VK_DOWN)){
            spriteMove(sprite_1.DOWN);
            if(sprite_1.getStatus()!=sprite_1.DEAD){
                sprite_1.setTT(1);
            }
        }
        if(sprite_1.getStatus()==Sprite_1.DEAD && check_lose == true){
            lose_theme.play();
            check_lose = false;
            naruto_theme.close();
        }

    }
    @Override
    public void GAME_PAINT(Graphics2D g2){
        map.PanitMap(g2);

        for(int i=0;i<items.size();i++){
            Items item = items.get(i);
            item.PaintItems(g2);
        }

        for(int i=0;i<boom_1s.size();i++){
            Boom_1 boom_1 = boom_1s.get(i);
            boom_1.boom_animation.PaintAnims((int)boom_1.getPosX(),(int)boom_1.getPosY(),boom_1.boom_image,g2,0,0);
            if(boom_1.getTime_explosive()<0){
                boom_1.boom_bang_animation.PaintAnims((int)boom_1.getPosX()-45,(int)boom_1.getPosY()-45,boom_1.boombbang_image,g2,0,0);
                if(sprite_1.getBoom_size_()==2){
                Boom_explosive boom_explosive = new Boom_explosive((int)boom_1.getPosX(),(int)boom_1.getPosY());
                boom_explosive.Paint(g2);
                }
            }
        }

        for(Box box: boxes){
            box.PaintBox(g2);
        }

        for(int i=0;i<enemies.size();i++){
            Enemy enemy = enemies.get(i);
            enemy.Paint(g2);
        }

        sprite_1.Paint(g2);

        if(sprite_1.getCheck_vacham_boom()==1){
            sprite_1.boomber_dead_animation.PaintAnims((int)sprite_1.getPosX()-15,(int)sprite_1.getPosY()-6,sprite_1.boomber_dead,g2,0,0);
        }
    }
    @Override
    public void KEY_ACTION(KeyEvent e,int Event){
        if(Event == KEY_PRESSED){
            bitSet.set(e.getKeyCode());

            if(boom_1s.size()<sprite_1.getBoom_Max()){
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    sprite_1.bombing(boom_1s);
                }
            }
        }
        if(Event == KEY_RELEASED){
            bitSet.clear(e.getKeyCode());
        }
    }
}
