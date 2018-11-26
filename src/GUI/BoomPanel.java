package GUI;

import FrameWork.HighScore;
import FrameWork.SoundPlayer;
import FrameWork.WriteToFileHighScore;
import Game.Game_Manager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.Scanner;

public class BoomPanel extends JPanel implements KeyListener, Runnable, MouseListener {
    private Game_Manager gameManager;
    private JLabel lb_back;
    private BitSet bitSet = new BitSet();
    private GUIManager guiManager;
    private int timeDead = 0;
    private int timeNextRound = 0;
    private int timeWin = 0;
    private int timeLose = 0;
    private boolean IS_RUNNING = true;
    private boolean IS_PAUSE = true;
    private int FPS = 60;
    private int round = 3;
    private int score;
    public static boolean HIT_PAUSE = false;

    Thread thread;

    public BoomPanel(GUIManager guiManager) {
        //super(800,600);
        setLayout(null);
        this.guiManager = guiManager;
        gameManager = new Game_Manager();
        //gameManager.initGame(1);
        addLabel();
        addKeyListener(this);
        thread = new Thread(this);
        thread.start();
        //BeginGame();
    }

    private void addLabel() {
        ImageIcon imageIcon = new ImageIcon("Images/button_back.png");
        lb_back = new JLabel();
        lb_back.setIcon(imageIcon);
        lb_back.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
        lb_back.setLocation(690, 520);
        lb_back.addMouseListener(this);
        add(lb_back);
    }


    public void newGame() {
        IS_PAUSE = true; // Trong qua trinh goi newGame thi Thread1 (this) van chay, neu khong stop -> nullpointerEx (enemy dang duoc khoi tao lai trong khi thread van chay)
        gameManager = new Game_Manager();
        gameManager.initGame(round);
        gameManager.setScore(this.score);
        //BeginGame();
        IS_PAUSE = false;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        gameManager.draw_game(g2d);
        //g2d.drawImage(InfoImage,675,0,null);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        gameManager.key_pressed_action(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gameManager.key_released_action(e);
    }

    @Override
    public void run() {
        long T = 1000 / FPS;
        long TimeBuffer = T / 2;

        long BeginTime = System.currentTimeMillis();
        long EndTime;
        long sleepTime;
        while (IS_RUNNING) {
            while (IS_PAUSE) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            gameManager.game_update(System.currentTimeMillis());


            if (gameManager.getCheckLose() == true) {
                timeLose++;
                if (timeLose == 400) {
                    timeLose = 0;
                    IS_PAUSE = true;
                    //String name = "Nguyễn Hiển Dương";
                    if(gameManager.getScore()>this.getHighScoreMin()){
                        String name = (String) JOptionPane.showInputDialog(this, "Nhập tên của bạn", "Chúc mừng bạn được điểm cao", JOptionPane.PLAIN_MESSAGE, null, null, null);
                        if(name == null){
                            name = " ";
                        }
                        WriteToFileHighScore c = new WriteToFileHighScore(name, gameManager.getScore());
                        guiManager.highScorePanel.initHighScore("FileTxt/HighScore.dat");}
                    this.round = 1;
                    gameManager.setScore(0);
                    this.score = 0;
                    guiManager.showMenu();
                    //gameManager.setGameStatus(GameManager.RUNNING);
                }
            }

            /*if(gameManager.getCheckWin() == true){
                timeWin++;
                if(timeWin == 3000){
                    timeWin = 0;
                    IS_PAUSE = true;
                    guiManager.showMenu();
                    BoomPanel.HIT_PAUSE = false;
                }
            }*/

            if (gameManager.getCheckWin() == true) {
                timeNextRound++;
                if (timeNextRound == 300) {
                    timeNextRound = 0;
                    round++;
                    if (round > 4) {
                        round = 1;
                    }
                    IS_PAUSE = true;
                    this.score = gameManager.getScore();
                    guiManager.showNextRound();
                }
            }

            repaint();

            EndTime = System.currentTimeMillis();
            sleepTime = T - (EndTime - BeginTime);
            if (sleepTime < 0) sleepTime = TimeBuffer;

            try {
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //BeginTime = System.currentTimeMillis();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource().equals(lb_back)) {
            setIS_PAUSE(true);
            HIT_PAUSE = true;
            guiManager.showMenu();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource().equals(lb_back)) {
            lb_back.setIcon(new ImageIcon("Images/button_back2.png"));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource().equals(lb_back)) {
            lb_back.setIcon(new ImageIcon("Images/button_back.png"));
        }
    }


    public boolean isIS_RUNNING() {
        return IS_RUNNING;
    }

    public void setIS_RUNNING(boolean IS_RUNNING) {
        this.IS_RUNNING = IS_RUNNING;
    }

    public boolean isIS_PAUSE() {
        return IS_PAUSE;
    }

    public void setIS_PAUSE(boolean IS_PAUSE) {
        this.IS_PAUSE = IS_PAUSE;
    }

    public int getHighScoreMin() {
        int min=0,i=0;
        try {
            String line = "";
            Scanner read = new Scanner(new FileInputStream("FileTxt/HighScore.dat"), "UTF-8");
            while (read.hasNextLine()) {
                line = read.nextLine();
                i++;
                if(i==8){
                   String arr[] = line.split(":");
                   min = Integer.parseInt(arr[1]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return min;
    }
}