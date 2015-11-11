/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonoMain.contas;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Felipe Lambach
 */
public class Contas {

    public Contas() {

    }

    public float calcIndice(int freq, int tamFFT, int fs) {
        float indice = (freq * tamFFT) / (fs / 2);
        return indice;
    }

    public float calcFreq(int indice, int tamFFT, int fs) {
        float freq = (indice * (fs / 2)) / tamFFT;
        return freq;
    }

    public boolean calcFaixas(float[] sinalOriginal, int freqAmostragem) {
        FFT fft = new FFT(sinalOriginal.length, freqAmostragem);
       
        
        fft.forward(sinalOriginal);
        float vetor[] = fft.getSpectrum();

        int IndicesFreq[] = {100 * (vetor.length) / (freqAmostragem / 2), 150 * (vetor.length) / (freqAmostragem / 2), 250 * (vetor.length) / (freqAmostragem / 2), 350 * (vetor.length) / (freqAmostragem / 2), 500 * (vetor.length) / (freqAmostragem / 2), 600 * (vetor.length) / (freqAmostragem / 2), 700 * (vetor.length) / (freqAmostragem / 2), 800 * (vetor.length) / (freqAmostragem / 2), 900 * (vetor.length) / (freqAmostragem / 2), 1000 * (vetor.length) / (freqAmostragem / 2), 1500 * (vetor.length) / (freqAmostragem / 2), 2000 * (vetor.length) / (freqAmostragem / 2)};

        float[] energiaFreq = new float[13];
        float[] maxFreq = new float[13];
        float energiaFreqRates[] = new float[13];
        float energiaFreqTotal = 0;
        float[] freq = new float[13];
        boolean resposta;

        for (int i = 0; i < vetor.length; i++) {
//                if (i < Math.abs(4000 / aux) + 1) {
//
            energiaFreqTotal += Math.pow(vetor[i], 2);
//                }
            // faixa 0 a 100
            if (i < IndicesFreq[0]) {
                energiaFreq[0] += Math.pow(vetor[i], 2);
                if (maxFreq[0] < vetor[i]) {
                    maxFreq[0] = vetor[i];
                    freq[0] = calcFreq(i, vetor.length, freqAmostragem);
                }
                //faixa 100 a 150
            } else if (i >= IndicesFreq[0] && i < IndicesFreq[1]) {
                energiaFreq[1] += Math.pow(vetor[i], 2);
                if (maxFreq[1] < vetor[i]) {
                    maxFreq[1] = vetor[i];
                    freq[1] = calcFreq(i, vetor.length, freqAmostragem);
                }
                //faixa 150 a 250
            } else if (i >= IndicesFreq[1] && i < IndicesFreq[2]) {
                energiaFreq[2] += Math.pow(vetor[i], 2);
                if (maxFreq[2] < vetor[i]) {
                    maxFreq[2] = vetor[i];
                    freq[2] = calcFreq(i, vetor.length, freqAmostragem);
                }
                //faixa 250 a 300
            } else if (i >= IndicesFreq[2] && i < IndicesFreq[3]) {
                energiaFreq[3] += Math.pow(vetor[i], 2);
                if (maxFreq[3] < vetor[i]) {
                    maxFreq[3] = vetor[i];
                    freq[3] = calcFreq(i, vetor.length, freqAmostragem);
                }
                //faixa 300 a 500
            } else if (i >= IndicesFreq[3] && i < IndicesFreq[4]) {
                energiaFreq[4] += Math.pow(vetor[i], 2);
                if (maxFreq[4] < vetor[i]) {
                    maxFreq[4] = vetor[i];
                    freq[4] = calcFreq(i, vetor.length, freqAmostragem);
                }
                //faixa 500 a 600
            } else if (i >= IndicesFreq[4] && i < IndicesFreq[5]) {
                energiaFreq[5] += Math.pow(vetor[i], 2);
                if (maxFreq[5] < vetor[i]) {
                    maxFreq[5] = vetor[i];
                    freq[5] = calcFreq(i, vetor.length, freqAmostragem);
                }
                //faixa 600 a 700
            } else if (i >= IndicesFreq[5] && i < IndicesFreq[6]) {
                energiaFreq[6] += Math.pow(vetor[i], 2);
                if (maxFreq[6] < vetor[i]) {
                    maxFreq[6] = vetor[i];
                    freq[6] = calcFreq(i, vetor.length, freqAmostragem);
                }
                //faixa 700 a 800
            } else if (i >= IndicesFreq[6] && i < IndicesFreq[7]) {
                energiaFreq[7] += Math.pow(vetor[i], 2);
                if (maxFreq[7] < vetor[i]) {
                    maxFreq[7] = vetor[i];
                    freq[7] = calcFreq(i, vetor.length, freqAmostragem);
                }
                //faixa 800 a 900
            } else if (i >= IndicesFreq[7] && i < IndicesFreq[8]) {
                energiaFreq[8] += Math.pow(vetor[i], 2);
                if (maxFreq[8] < vetor[i]) {
                    maxFreq[8] = vetor[i];
                    freq[8] = calcFreq(i, vetor.length, freqAmostragem);
                }
                //faixa 900 a 1000
            } else if (i >= IndicesFreq[8] && i < IndicesFreq[9]) {
                energiaFreq[9] += Math.pow(vetor[i], 2);
                if (maxFreq[9] < vetor[i]) {
                    maxFreq[9] = vetor[i];
                    freq[9] = calcFreq(i, vetor.length, freqAmostragem);
                }
                //faixa 1000 a 1500
            } else if (i >= IndicesFreq[9] && i < IndicesFreq[10]) {
                energiaFreq[10] += Math.pow(vetor[i], 2);
                if (maxFreq[10] < vetor[i]) {
                    maxFreq[10] = vetor[i];
                    freq[10] = calcFreq(i, vetor.length, freqAmostragem);
                }
                //faixa 1500 a 2000
            } else if (i >= IndicesFreq[10] && i < IndicesFreq[11]) {
                energiaFreq[11] += Math.pow(vetor[i], 2);
                if (maxFreq[11] < vetor[i]) {
                    maxFreq[11] = vetor[i];
                    freq[11] = calcFreq(i, vetor.length, freqAmostragem);
                }
                //faixa 2000 a 4000
            } else if (i >= IndicesFreq[11] && i <= calcIndice(4000, vetor.length, freqAmostragem)) {
                energiaFreq[12] += Math.pow(vetor[i], 2);
                if (maxFreq[12] < vetor[i]) {
                    maxFreq[12] = vetor[i];
                    freq[12] = calcFreq(i, vetor.length, freqAmostragem);
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            energiaFreqRates[i] = energiaFreq[i] / energiaFreqTotal;
            System.out.println(i + " energia= " + energiaFreqRates[i]);
            System.out.println(freq[i] + " magnitude = " + maxFreq[i]);
        }
        
        resposta=equacao_1(energiaFreqRates,freq);
        return resposta;
    }

   public boolean equacao_1(float[] energiaFreqRates, float[] freq) {
        int j;
 
        int[] auxiliarIndiceOriginal = new int[13];
        float[] vetorordenado = ordenado_bolha(energiaFreqRates);

        //encontrando o indice do vetor de energia ordenado com o original
        for (int i = 0; i < 13; i++) {      
            for (j = 0; j < 13; j++) {   
               if (vetorordenado[i] == energiaFreqRates[j]) {
                    auxiliarIndiceOriginal[i] = j;
                } 
            }
        }
        
        //frequencias da 1º menor energia (coluna A)
        if (freq[auxiliarIndiceOriginal[0]]>=1000 && freq[auxiliarIndiceOriginal[0]]<=1700 || freq[auxiliarIndiceOriginal[0]]>=1900 && freq[auxiliarIndiceOriginal[0]]<=2000 || freq[auxiliarIndiceOriginal[0]]>=400 && freq[auxiliarIndiceOriginal[0]]<=500){
            return true;
        }
        //frequencias da 2º menor energia (coluna B)
        else if (freq[auxiliarIndiceOriginal[1]]>=3000 && freq[auxiliarIndiceOriginal[1]]<=3800 || freq[auxiliarIndiceOriginal[1]]>=1600 && freq[auxiliarIndiceOriginal[1]]<1800 || freq[auxiliarIndiceOriginal[1]]>=1000 && freq[auxiliarIndiceOriginal[1]]<1400 || freq[auxiliarIndiceOriginal[1]]>=350 && freq[auxiliarIndiceOriginal[1]]<=500){
            return true;
        }
        //frequencias da 3º menor energia (coluna C)
         else if (freq[auxiliarIndiceOriginal[2]]>=1700 && freq[auxiliarIndiceOriginal[2]]<=2000 || freq[auxiliarIndiceOriginal[2]]>=1000 && freq[auxiliarIndiceOriginal[2]]<=1100  || freq[auxiliarIndiceOriginal[2]]>=3000 && freq[auxiliarIndiceOriginal[2]]<=4000){
            return true;
        }
        //frequencias da 4º menor energia (coluna D)
        else if (freq[auxiliarIndiceOriginal[3]]>=3700 && freq[auxiliarIndiceOriginal[3]]<=3800 || freq[auxiliarIndiceOriginal[3]]>=70 && freq[auxiliarIndiceOriginal[3]]<=90){
            return true;
        }
        //frequencias da 5º menor energia (coluna E)
        else if (freq[auxiliarIndiceOriginal[4]]>=3200 && freq[auxiliarIndiceOriginal[4]]<=3600){
            return true;
        }
        //frequencias da 6º menor energia (coluna F)
        else if (freq[auxiliarIndiceOriginal[5]]>=3000 && freq[auxiliarIndiceOriginal[5]]<3700 || freq[auxiliarIndiceOriginal[5]]>=3800 && freq[auxiliarIndiceOriginal[5]]<=4000 || freq[auxiliarIndiceOriginal[5]]>=0 && freq[auxiliarIndiceOriginal[5]]<90){
            return true;
        }
        //frequencias da 7º menor energia (coluna G)
        else if (freq[auxiliarIndiceOriginal[6]]>=3000 && freq[auxiliarIndiceOriginal[6]]<=3400 || freq[auxiliarIndiceOriginal[6]]>=420 && freq[auxiliarIndiceOriginal[6]]<=450 || freq[auxiliarIndiceOriginal[6]]>=70 && freq[auxiliarIndiceOriginal[6]]<=120){
            return true;
        }
        //frequencias da 8º menor energia (coluna H)
        else if (freq[auxiliarIndiceOriginal[7]]>=0 && freq[auxiliarIndiceOriginal[7]]<=100){
            return true;
        }
        //frequencias da 9º menor energia (coluna I) contem 1 não ronco
        else if (freq[auxiliarIndiceOriginal[8]]>=0 && freq[auxiliarIndiceOriginal[8]]<=100){
            return true;
        }
        //frequencias da 10º menor energia (coluna J)
        else if (freq[auxiliarIndiceOriginal[9]]>910 && freq[auxiliarIndiceOriginal[9]]<=950){
            return true;
        }
        //frequencias da 11º menor energia (coluna K)
        else if (freq[auxiliarIndiceOriginal[10]]>=900 && freq[auxiliarIndiceOriginal[10]]<920 || freq[auxiliarIndiceOriginal[10]]>930 && freq[auxiliarIndiceOriginal[10]]<990){
            return true;
        }
        //frequencias da 12ª menor energia (coluna L)
        else if (freq[auxiliarIndiceOriginal[11]]>=0 && freq[auxiliarIndiceOriginal[11]]<=100 || freq[auxiliarIndiceOriginal[11]]>720 && freq[auxiliarIndiceOriginal[11]]<=750 || freq[auxiliarIndiceOriginal[11]]>250 && freq[auxiliarIndiceOriginal[11]]<=280 ){
            return true;
        }   
        //frequencias da 13ª menor energia (coluna M)
        else if (freq[auxiliarIndiceOriginal[12]]>=800 && freq[auxiliarIndiceOriginal[12]]<=900 || freq[auxiliarIndiceOriginal[12]]>=0 && freq[auxiliarIndiceOriginal[12]]<=100){
            return true;
        }    
        else{ 
            return false;
        }        
    }

    public float[] ordenado_bolha(float[] vetor){
        float[] vetorordenado = new float[vetor.length];
        float aux;
        int j,i;
        
        for(i = 0; i < vetorordenado.length; i++){
            vetorordenado[i]=vetor[i];
        }
               
        for (i = 0; i < vetorordenado.length; i++) {  
            for (j = 0; j < vetorordenado.length; j++) {  
                if (vetorordenado[i]<vetorordenado[j]) {  
                    aux=vetorordenado[j];
                    vetorordenado[j]=vetorordenado[i]; 
                    vetorordenado[i]=aux; 
                }  
            }  
        }  
        return vetorordenado;
    }

}
