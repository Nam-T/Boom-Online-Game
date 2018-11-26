package FrameWork;

import sun.reflect.generics.tree.Tree;

import java.util.TreeMap;

public class HighScore {
    private String name;
    private int score;

    public HighScore(String name,int score){
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore(){
        return score;
    }
}
