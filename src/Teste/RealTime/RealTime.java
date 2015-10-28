/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Teste.RealTime;

import sonoMain.realtime.AudioDataListener;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.AudioRecorder;
import ddf.minim.Minim;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 *
 * @author luisfg30
 */
public class RealTime {
    
Minim minim;
AudioPlayer player;
AudioInput input;
public AudioBuffer audioData;
AudioRecorder recorder;
AudioDataListener dataListener;

public RealTime(){
    minim = new Minim(this);
    input= minim.getLineIn(Minim.MONO, 1024, 8192, 16);
    dataListener = new AudioDataListener(this);
    input.addListener(dataListener);
    audioData=input.mix;
    recorder=minim.createRecorder(input,"AudioSample.wav");
    System.out.println(input.getVolume());
  

}

public float[] getData(){ 
      
      float[] dataArray= dataListener.getAllSamples();
      System.out.println("\nData Len: "+dataArray.length);
      System.out.println("\n Valor RMS: "+dataListener.getRMS());
      for(int i=0;i<dataArray.length;i++){
          System.out.println("["+i+"]="+dataArray[i]);
      }
      return dataArray;
}

public void startRecord(){
    input.enableMonitoring();
    recorder.beginRecord();
    dataListener.saveData=true;
}

public void stopRecord(){
    recorder.endRecord();
    dataListener.saveData=false;
    input.disableMonitoring();
    recorder.save();
}

/**
 * Precisa chamar esse método antes de sair do programa para fechar as interfaces de audio corretamente
 */
public void quitprogram(){
    input.close();
    player.close();
    minim.stop();
}

/**
 * Método necessário para funcionar o programa (compatibilidade da biblioteca com o Processing)
 * @param fileName
 * @return 
 */
public String sketchPath(String fileName){
    String path="./AudioSample.wav";
    return path;
}

/**
 * Método necessário para funcionar o programa (compatibilidade da biblioteca com o Processing)
 * @param fileName
 * @return
 * @throws FileNotFoundException 
 */
public InputStream createInput(String fileName) throws FileNotFoundException{
    InputStream inputStream=new FileInputStream(fileName);
    return inputStream;
}


}
