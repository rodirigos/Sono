/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonoMain.contas;

import java.awt.BorderLayout;
import java.util.Arrays;

/**
 *
 * @author Felipe Lambach
 */
public class Contas {
    
    public Contas(){
                //frequencia de amostragem do sinal
//        int freamostragem = 128;
//        //tamanho do vetor
//        int tam = 256, i;
//        FFT fft = new FFT(tam, freamostragem);
//
//        float increment = (float) (2);
//        float aux = 0;
//
//        //sinal original
//        float[] Sinal_Original = new float[tam];
//
//        System.out.println("sinal original");
//        for (i = 0; i < Sinal_Original.length; i++) {
//
//            Sinal_Original[i] = (float) Math.sin(aux);
//            aux++;
//           System.out.println(i + " = " + Sinal_Original[i]);
//        }
//        System.out.println("original ->"+Sinal_Original.length);
//        fft.forward(Sinal_Original);
//        float vetor[]=fft.getSpectrum();
//        System.out.println("fft ->"+vetor.length);
//         System.out.println(" FFT ");
//         
//         for (i = 0; i < vetor.length; i++){
//            System.out.println(i+" = "+vetor[i]);
//        } 
//                 int indice=21*(vetor.length)/(freamostragem/2);
//        System.out.println("indice "+indice);
    }
    
    
    public float calcIndice(int freq,int tamFFT,int fs){
        float indice= (freq*tamFFT)/(fs/2);
        return indice;
    }
    
    public float calcFreq(int indice,int tamFFT,int fs){
        float freq= (indice*(fs/2))/tamFFT;
        return freq;
    }
    
    public void calcFaixas(float[] sinalOriginal,int freqAmostragem){
        FFT fft = new FFT(sinalOriginal.length, freqAmostragem);
        
        System.out.println("original ->"+sinalOriginal.length);
                for (int i = 0; i < sinalOriginal.length; i++) {
           System.out.println(i + " = " + sinalOriginal[i]);
        }
        fft.forward(sinalOriginal);
        float vetor[]=fft.getSpectrum();
        System.out.println("fft ->"+vetor.length);
         System.out.println(" FFT ");
         
         for (int i = 0; i < vetor.length; i++){
            System.out.println(i+" = "+vetor[i]);
        } 
        int indice=500*(vetor.length)/(freqAmostragem/2);
        System.out.println("indice "+indice);
        System.out.println("ind funcao "+calcIndice(500, vetor.length, freqAmostragem));
        System.out.println("frq do infice funcao "+calcFreq(indice, vetor.length, freqAmostragem));
//        int frequencias[]={100,150,250,350,500,1000,1500,2000};
//        int indicesFreq[]=new int[]
//        for(int i=0;i<frequencias.length;i++){
//            
//        }
  
        int IndicesFreq[] = {100*(vetor.length)/(freqAmostragem/2),150*(vetor.length)/(freqAmostragem/2),250*(vetor.length)/(freqAmostragem/2),350*(vetor.length)/(freqAmostragem/2),500*(vetor.length)/(freqAmostragem/2),1000*(vetor.length)/(freqAmostragem/2),1500*(vetor.length)/(freqAmostragem/2),2000*(vetor.length)/(freqAmostragem/2)};
       
               float EnergiaFreq[] = new float[9];
        float MaxFreq[] = new float[9];
        float energiaFreqRates[] = new float[9];
        float EnergiaFreqTotal = 0;
        float Freq[] = new float[9];
        
        System.out.println("indices freq "+ Arrays.toString(IndicesFreq));
            for (int i = 0; i < vetor.length; i++) {
//                if (i < Math.abs(4000 / aux) + 1) {
//
                    EnergiaFreqTotal += Math.pow(vetor[i], 2);
//                }
                if (i < IndicesFreq[0]) {
                    EnergiaFreq[0] += Math.pow(vetor[i], 2);
                    if (MaxFreq[0] < vetor[i]) {
                        MaxFreq[0] = vetor[i];
                        Freq[0] = calcFreq(i,vetor.length , freqAmostragem) ;
                    }
                } else if (i >= IndicesFreq[0] && i < IndicesFreq[1]) {
                    EnergiaFreq[1] += Math.pow(vetor[i], 2);
                    if (MaxFreq[1] < vetor[i]) {
                        MaxFreq[1] = vetor[i];
                        Freq[1] = calcFreq(i,vetor.length , freqAmostragem) ;
                    }
                } else if (i >= IndicesFreq[1] && i < IndicesFreq[2]) {
                    EnergiaFreq[2] += Math.pow(vetor[i], 2);
                    if (MaxFreq[2] < vetor[i]) {
                        MaxFreq[2] = vetor[i];
                        Freq[2] = calcFreq(i,vetor.length, freqAmostragem) ;
                    }
                } else if (i >= IndicesFreq[2] && i < IndicesFreq[3]) {
                    EnergiaFreq[3] += Math.pow(vetor[i], 2);
                    if (MaxFreq[3] < vetor[i]) {
                        MaxFreq[3] = vetor[i];
                        Freq[3] = calcFreq(i,vetor.length , freqAmostragem) ;
                    }
                } else if (i >= IndicesFreq[3] && i < IndicesFreq[4]) {
                    EnergiaFreq[4] += Math.pow(vetor[i], 2);
                    if (MaxFreq[4] < vetor[i]) {
                        MaxFreq[4] = vetor[i];
                        Freq[4] = calcFreq(i,vetor.length , freqAmostragem) ;
                    }
                } else if (i >= IndicesFreq[4] && i < IndicesFreq[5]) {
                    EnergiaFreq[5] += Math.pow(vetor[i], 2);
                    if (MaxFreq[5] < vetor[i]) {
                        MaxFreq[5] = vetor[i];
                        Freq[5] = calcFreq(i,vetor.length , freqAmostragem) ;
                    }
                } else if (i >= IndicesFreq[5] && i < IndicesFreq[6]) {
                    EnergiaFreq[6] += Math.pow(vetor[i], 2);
                    if (MaxFreq[6] < vetor[i]) {
                        MaxFreq[6] = vetor[i];
                        Freq[6] = calcFreq(i,vetor.length, freqAmostragem) ;
                    }
                } else if (i >= IndicesFreq[6] && i < IndicesFreq[7]) {
                    EnergiaFreq[7] += Math.pow(vetor[i], 2);
                    if (MaxFreq[7] < vetor[i]) {
                        MaxFreq[7] = vetor[i];
                        Freq[7] = calcFreq(i,vetor.length, freqAmostragem) ;
                    }
                } else if (i >= IndicesFreq[7] && i <= calcIndice(4000,vetor.length, freqAmostragem)) {
                    EnergiaFreq[8] += Math.pow(vetor[i], 2);
                    if (MaxFreq[8] < vetor[i]) {
                        MaxFreq[8] = vetor[i];
                        Freq[8] = calcFreq(i,vetor.length , freqAmostragem) ;
                    }
                }
        }
          for (int i = 0; i < 9; i++) {
            energiaFreqRates[i] = EnergiaFreq[i] / EnergiaFreqTotal;
              System.out.println(i+" energia= "+energiaFreqRates[i]);
              System.out.println(Freq[i]+" magnitude = "+MaxFreq[i]);
        }
          Resultado resultado = new Resultado();
          resultado.setEnergiaFreqRates(energiaFreqRates);
        resultado.setMaxFreq(Freq);
            
    }
    
}
