/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonoMain.realtime;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.AudioRecorder;
import ddf.minim.Minim;
import ddf.minim.MultiChannelBuffer;
import ddf.minim.spi.AudioRecordingStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 *Classe Singleton para pegar o conetexto de áudio.<br>
 * É bom que só exita um objeto do tipo Minim, para não dar conflito depois<br>
 * Mais de uma classe pode tentar acessar o audio em tempo real ou o arquivo.<br>
 * Fica fácil de fazer referência (é um objeto global).
 * @author luisfg30
 */
public class RealTime1 {
    
    private static RealTime1 singleton=null;
    
    public boolean fileInput;
    public Minim minim;
    public AudioInput input;
    public AudioPlayer player;
    public AudioRecordingStream audioStream;
    AudioDataListener dataListener;
    AudioBuffer audioData;
    AudioRecorder recorder;
    MultiChannelBuffer multiChannelBuffer;

    private RealTime1(){
        minim = new Minim(this);
    }
    
    public static RealTime1 getInstance()
    {
        if(singleton==null)
        {
            singleton = new RealTime1();
        }
        return singleton;
    }
    
    /**
     * Inicializa o contexto de audio, define se o input é do mic ou arquivo
     */
    public void startAudioInput(boolean isFile){
        if(isFile==true){
            fileInput=true;
//           audioStream= minim.loadFileStream("InputSample.wav", 1024, true);
//           int frameNumber=(int) audioStream.getSampleFrameLength();
//           multiChannelBuffer= new MultiChannelBuffer(frameNumber,1);
//           audioStream.play();
//           for(long i=0;i<frameNumber;i++){
//               
//           }
//            fileInput=true;
//            minim.loadFileIntoBuffer("InputSample.wav", multiChannelBuffer);
//            dataListener = new AudioDataListener();
//            for(int i=0;i<multiChannelBuffer.getBufferSize();i++){
//                dataListener.samples(multiChannelBuffer);
//            }
            player=minim.loadFile("InputSample.wav", 1024);
            dataListener = new AudioDataListener(minim);
            player.addListener(dataListener);
            player.play();
            audioData=player.mix;
        }else{
            fileInput=false;
            input= minim.getLineIn(Minim.MONO, 1024, 8192, 16); 
            dataListener = new AudioDataListener(minim);
            input.addListener(dataListener);
            audioData=input.mix;
            recorder=minim.createRecorder(input,"InputSample.wav");
        }
    }

/**
 * Retorna o vetor de float gerado pelo áudio até o momento
 * @return 
 */    
public float[] getData(){ 
      
      float[] dataArray= dataListener.getAllSamples();
      System.out.println("\nData Len: "+dataArray.length);
      for(int i=0;i<dataArray.length;i++){
          System.out.println("["+i+"]="+dataArray[i]);
      }
      return dataArray;
}

public void startRecord(){
    if(fileInput==false){
        
    }
    else{
        input.enableMonitoring();
        recorder.beginRecord();
        dataListener.saveData=true;
    }
}

public void stopRecord(){
    if(fileInput==false){
        
    }
    else{
        recorder.endRecord();
        dataListener.saveData=false;
        input.disableMonitoring();
        recorder.save();
    }
}
    
    /**
 * Método necessário para funcionar o programa (compatibilidade da biblioteca com o Processing)
 * @param fileName
 * @return 
 */
public String sketchPath(String fileName){
    String path="./InputSample.wav";
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

/**
 * Precisa chamar esse método antes de sair do programa para fechar as interfaces de audio corretamente
 */
public void quitprogram(){
    input.close();
    player.close();
    minim.stop();
}
    
}
