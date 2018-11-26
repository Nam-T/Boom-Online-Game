package FrameWork;

import sun.audio.ContinuousAudioDataStream;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
    
    private Clip clip;
    
    public SoundPlayer(File path){
        try{
            AudioInputStream ais;
            ais = AudioSystem.getAudioInputStream(path);
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels()*2,
                    baseFormat.getSampleRate(),
                    false
            );
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(dais);
        }catch(Exception e){}
    }
    public void play(){
        if(clip !=null){
            stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }
    public void stop(){
        if(clip.isRunning()) clip.stop();
    }
    
    public void close(){
        clip.close();
    }

    public void loop(){
        if(clip !=null){
            stop();
            clip.setFramePosition(0);
            clip.loop(10);
            clip.start();
        }
    }

}
