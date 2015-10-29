/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonoMain;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 *
 * @author Felipe Lambach
 */
public class Cortador {



//    public float RMS(float Sinal_Original[]) {
//        float rms = 0;
//        int i;
//        for (i = 0; i < Sinal_Original.length; i++) {
//            rms += Math.pow(Sinal_Original[i], 2);
//        }
//        rms = (float) Math.sqrt(rms / Sinal_Original.length);
//
//        return rms;
//    }

    public static void cortarAudio(float[] sinalOriginal,float rms,int freqAmostragem,LocalDateTime horaInicio) {

            System.out.println("CHAMOU CORTADOR\nrms="+rms+"\nfreqAmostragem="+freqAmostragem+"\nhorainicio="+horaInicio+
                    "\nvetor:\n"+Arrays.toString(sinalOriginal));
//        int contJanela=1,y2=0, i, j, aux, aux2, tam = sinalOriginal.length;
//        int theshold = 3;
//        float TempoJanela = (float) 0.2, max = 0;
//        int AmostraJanela = (int) (TempoJanela * FreqAmostragem);
//        
//        
//        for (i = 0; i < AmostraJanela; i++) {
//            max=0;
//            if (i + AmostraJanela > tam) {
//                aux = i;
//                temp = new float[tam - i];
//                for (j = 0; j < tam - i; j++) {
//                    temp[j] = sinalOriginal[aux];
//                    aux++;
//                }
//            } else {
//                aux2 = i + AmostraJanela;
//                temp = new float[aux2];
//                aux = i;
//                for (j = 0; j < aux2; j++) {
//                    temp[j] = sinalOriginal[aux];
//                }
//            }
//            for (j = 0; j < temp.length; j++) {
//                if (max < temp[j]) {
//                    max = temp[j];
//                }
//            }
//            if(max>theshold*rms_sinal_original){
//                if(y2==0){
//                    y2=i;
//                }
//            }
//        }
    }

}
