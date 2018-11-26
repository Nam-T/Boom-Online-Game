package FrameWork;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.*;

public abstract class GameScreen_Panel extends Panel implements KeyListener{

    public static int KEY_PRESSED = 0;
    public static int KEY_RELEASED = 1;

    public int CUSTOM_WIDTH  = 500;
    public int CUSTOM_HEIGHT = 500;

    private GameThread_Panel G_Thread_;

    public static int MASTER_WIDTH = 500, MASTER_HEIGHT = 500;

    public GameScreen_Panel(){
        InitThread();
        InitScreen();
    }

    public void RegisterImage(int id, BufferedImage image){

    }

    public BufferedImage getImageWithID(int id){
        return null;
    }

    public GameScreen_Panel(int w,int h){
        this.CUSTOM_WIDTH = w;
        this.CUSTOM_HEIGHT = h;
        MASTER_WIDTH = CUSTOM_WIDTH;
        MASTER_HEIGHT = CUSTOM_HEIGHT;
        InitThread();
        InitScreen();
    }

    private void InitScreen(){

        this.addKeyListener(this);
        //setSize(CUSTOM_WIDTH, CUSTOM_HEIGHT);
        setVisible(true);

    }

    public void BeginGame(){
        G_Thread_.StartThread();
    }

    private void InitThread(){
        G_Thread_ = new GameThread_Panel(this);
        add(G_Thread_);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        KEY_ACTION(e, GameScreen_Panel.KEY_PRESSED);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        KEY_ACTION(e, GameScreen_Panel.KEY_RELEASED);
    }

    public abstract void GAME_UPDATE(long deltaTime);
    public abstract void GAME_PAINT(Graphics2D g2);
    public abstract void KEY_ACTION(KeyEvent e, int Event);
    protected void paintComponent(Graphics g){

    };
}

