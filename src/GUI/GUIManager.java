package GUI;

import FrameWork.SoundPlayer;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GUIManager extends JPanel{
    public static final int PLAY_W = 882 - 200;
    public static final int PLAY_H = 610;
    public static final int W_FRAME = 882;
    public static final int H_FRAME = 610;

    private MenuPanel menuPanel;
    private CardLayout cardLayout;
    private BoomPanel boomPanel;
    private NextRoundPanel nextRoundPanel;
    private OptionPanel optionPanel;
    public HighScorePanel highScorePanel;

    private static String MENU_TAG = "menu";
    private static String SUB_MENU_TAG = "submenu";
    private static String OPTION_TAG = "option";
    private static String HIGHSCORE_TAG = "highscore";
    private static String PLAYGAME_TAG = "playgame";
    private static  String NEXTROUND_TAG= "nextround";

    private SoundPlayer Sound_Display;

    public GUIManager() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        menuPanel = new MenuPanel(this);
        add(menuPanel, MENU_TAG);

        boomPanel = new BoomPanel(this);
        add(boomPanel, PLAYGAME_TAG);

        nextRoundPanel = new NextRoundPanel(this);
        add(nextRoundPanel,NEXTROUND_TAG);

        highScorePanel = new HighScorePanel(this);
        add(highScorePanel,HIGHSCORE_TAG);

        optionPanel = new OptionPanel(this);
        add(optionPanel,OPTION_TAG);

        Sound_Display = new SoundPlayer(new File("Sound/naruto.wav"));
        showMenu();
    }

    public void showMenu(){
        cardLayout.show(this, MENU_TAG);
        menuPanel.requestFocus();
        Sound_Display.play();
    }

    public void showPlayGame(){
        cardLayout.show(this, PLAYGAME_TAG);
        boomPanel.requestFocus();
        Sound_Display.stop();
    }

    public void showNewGame(){
        boomPanel.newGame();
        cardLayout.show(this, PLAYGAME_TAG);
        boomPanel.requestFocus();
        Sound_Display.stop();
    }

    public BoomPanel getBoomPanel() {
        return boomPanel;
    }

    public void showNextRound(){
        cardLayout.show(this,NEXTROUND_TAG);
        nextRoundPanel.requestFocus();
    }

    public void showHighScore(){
        cardLayout.show(this,HIGHSCORE_TAG);
        highScorePanel.requestFocus();
    }

    public void showOption(){
        cardLayout.show(this,OPTION_TAG);
        optionPanel.requestFocus();
    }

    public void exit(){
        boomPanel.setIS_RUNNING(false);
        System.exit(0);
    }

    public SoundPlayer getSound_Display(){return Sound_Display;}
}
