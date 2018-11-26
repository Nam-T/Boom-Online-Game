package FrameWork;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.TreeMap;

public class WriteToFileHighScore {
    private ArrayList<HighScore> List;
    FileOutputStream fos;
    OutputStreamWriter osw;
    public WriteToFileHighScore(String name,int score){
        initTree();
        HighScore highScore = new HighScore(name,score);
        //System.out.println(score);
        List.add(highScore);
        Success(List);

        try{
            fos = new FileOutputStream("FileTxt/HighScore.dat",false);
            osw = new OutputStreamWriter(fos,"utf-8");
            for(int i=0;i<List.size();i++){
                HighScore highScore1 = List.get(i);
                //System.out.println(highScore1.getScore());
                osw.write(highScore1.getName()+":"+Integer.toString(highScore1.getScore())+"\r\n");
                if(i==7){
                    break;
                }
            }
            osw.flush();
            fos.close();
        }catch (IOException e){}

    }
    public void initTree(){
        List = new ArrayList<HighScore>();
        try{
            Scanner read = new Scanner(new FileInputStream("FileTxt/HighScore.dat"),"UTF-8");
            String line = "";
            while (read.hasNextLine()){
                line = read.nextLine();
                String arr[];
                arr = line.split(":");
                arr[0] = arr[0].trim();
                arr[1] = arr[1].trim();
                HighScore highScore = new HighScore(arr[0],Integer.parseInt(arr[1]));
                List.add(highScore);
                //System.out.println(Integer.parseInt(arr[1]));
            }

        }catch (IOException e){}

    }

    public void Success(ArrayList<HighScore> list){
        for(int i=0;i<list.size();i++){
            for(int j=i+1;j<list.size();j++){
                HighScore highScore1 = list.get(i);
                HighScore highScore2 = list.get(j);
                HighScore temp;
                if(highScore1.getScore()<highScore2.getScore()){
                    temp = list.get(i);
                    list.set(i,highScore2);
                    list.set(j,temp);
                }
            }
        }
    }
}
