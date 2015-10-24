/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Teste.RealTime;

import ddf.minim.AudioListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *Esta classe implementa uma interface que é chamada pelo buffer do programa
 * @author luisfg30
 */
public class AudioDataListener implements AudioListener {
    
    private ArrayList<float[]> bufferSamples;
    public boolean saveData;
    public int sampleCounter,sampleSize;
    
    public AudioDataListener(){
        bufferSamples= new ArrayList();
        saveData=false;
        sampleCounter=0;
    }
    
    /**
     * Cada vez que o buffer do programa é alterado ele chama esse método
     * Cada vez que o método é chamado eu guardo o sample em um Arraylist
     * @param samp uma amostra do buffer do programa
     */
    @Override
    public void samples(float[] samp) {
        if(saveData==true){
            bufferSamples.add(samp);
            sampleCounter++;
            sampleSize=samp.length;
            System.out.println("\n adicionou "+sampleSize+" no vetor\nvetor tem "+bufferSamples.size()+"samples");
            System.out.println("samples: "+Arrays.toString(samp));
        }
    }

    @Override
    public void samples(float[] sampL, float[] sampR) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public float[] getAllSamples(){
        float[] allSamples= new float[sampleSize*bufferSamples.size()];
        int copyCounter=0;
        for(int i=0;i<bufferSamples.size();i++){
            System.arraycopy(bufferSamples.get(i), 0, allSamples,copyCounter, bufferSamples.get(i).length);
            copyCounter+=bufferSamples.get(i).length;
        }
        return allSamples;
    }
    
    public void clearData(){
        bufferSamples.clear();
        sampleCounter=0;
    }
    
}
