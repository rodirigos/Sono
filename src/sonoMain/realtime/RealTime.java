/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonoMain.realtime;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioOutput;
import ddf.minim.AudioPlayer;
import ddf.minim.AudioRecorder;
import ddf.minim.AudioSample;
import ddf.minim.Minim;
import ddf.minim.ugens.FilePlayer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import sonoMain.Cortador;

/**
 *
 * @author luisfg30
 */
public class RealTime {

    public static final int SAMPLE_RATE = 8192;
    public static final int BUFFER_SIZE = 1024;
    public static final int BITS_PER_SAMPLE = 16;
    public static final int MAX_SAMPLES = 80; //8192*10(segundos)/1024 amostras por buffer
    public AudioBuffer audioData;
    public LocalDateTime horaInicio;
    public Cortador cortador;

    Minim minim;
    AudioPlayer player;
    AudioInput input;
    AudioRecorder recorder;
    AudioDataListener dataListener;

    public RealTime() {
        minim = new Minim(this);
        input = minim.getLineIn(Minim.MONO, BUFFER_SIZE, SAMPLE_RATE, BITS_PER_SAMPLE);
        dataListener = new AudioDataListener(this);
        input.addListener(dataListener);
        audioData = input.mix;
        recorder = minim.createRecorder(input, "AudioSample.wav");
    }

    public void setCortadorRef(Cortador c) {
        cortador = c;
    }

    public float[] getData() {

        float[] dataArray = dataListener.getAllSamples();
        System.out.println("\nData Len: " + dataArray.length);
        System.out.println("\n Valor RMS: " + dataListener.getRMS());
        cortador.cortarAudio(dataArray, dataListener.getRMS(), RealTime.SAMPLE_RATE, horaInicio);
        dataListener.clearData();
//      for(int i=0;i<dataArray.length;i++){
//          System.out.println("["+i+"]="+dataArray[i]);
//      }
        return dataArray;
    }

    public void startRecord() {
        input.enableMonitoring();//permite ouvir o áudio , questão de debug
        recorder.beginRecord();
        dataListener.saveData = true;
        horaInicio = LocalDateTime.now();
    }

    public void stopRecord() {
        recorder.endRecord();
        dataListener.saveData = false;
        input.disableMonitoring();
        recorder.save();
    }

    /**
     * Precisa chamar esse método antes de sair do programa para fechar as
     * interfaces de audio corretamente
     */
    public void quitprogram() {
        input.close();
        player.close();
        minim.stop();
    }

    /**
     * Método necessário para funcionar o programa (compatibilidade da
     * biblioteca com o Processing)
     *
     * @param fileName
     * @return
     */
    public String sketchPath(String fileName) {
        String path = "./AudioSample.wav";
        return path;
    }

    /**
     * Método necessário para funcionar o programa (compatibilidade da
     * biblioteca com o Processing)
     *
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public InputStream createInput(String fileName) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(fileName);
        return inputStream;
    }

    /**
     * Criando metodo necessario para a importar um audio
     *
     * @param fileName: Nome do arquivo
     * @return dArray: Array contendo os dados do arquivo
     */
    public float[] importAudio(String fileName) {
       
        float[] dArray=null;
       // FilePlayer filePlayer = new FilePlayer(minim.loadFileStream(fileName));
        AudioSample sound = minim.loadSample(fileName);
        dArray = sound.getChannel(AudioSample.LEFT);
        for(int i =0 ; i< dArray.length; i++){
        System.out.println( dArray[i]+"\n");
        }
      sound.close();
        
         
        return dArray;
    }

}
