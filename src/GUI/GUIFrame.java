package GUI;

import FrameWork.GameScreen;
import Game.Game_Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class GUIFrame extends JFrame {
    private static int GUI_W = 882;
    private static int GUI_H = 610;
    public GUIFrame(){
        setSize(GUI_W, GUI_H);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new GUIManager());
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

/*
public class GUIFrame extends GameScreen{
    private Game_Manager game_manager = new Game_Manager();
    public GUIFrame(){
        super(800,600);
        add(new GUIManager());
        //game_manager.initGame(1);
        //BeginGame();
    }

    @Override
    public void GAME_UPDATE(long deltaTime){

        //game_manager.game_update(deltaTime);
    }
    @Override
    public void GAME_PAINT(Graphics2D g2){

        //game_manager.draw_game(g2);
    }
    @Override
    public void KEY_ACTION(KeyEvent e,int Event){
        /*if(Event == KEY_PRESSED){
            game_manager.key_pressed_action(e);
        }
        if(Event == KEY_RELEASED){
            game_manager.key_released_action(e);
        }
    }
}*/
