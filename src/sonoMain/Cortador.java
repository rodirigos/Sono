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

private GerenciadorEventos gerenciador;
    
public  Cortador(GerenciadorEventos gE){
    gerenciador= gE;
}

 
    /**
     * Método chamado cada vez que o cortador separar um evento de áudio de 3s
     * @param horaCalculada
     * @param sinalEvento
     * @param tipoEvento 
     */
    public void registrarEvento(LocalDateTime horaCalculada, float[] sinalEvento,String tipoEvento){
        Evento e = new Evento(horaCalculada,sinalEvento,tipoEvento);
        gerenciador.adicionarEvento(e);
    }
    
    /**
     * Método chamado pela Classe AudioDataListener (dentro do Realtime) a cada vez que a gravação atinge 10s
     * @param sinalOriginal
     * @param rms
     * @param freqAmostragem
     * @param horaInicio 
     */
    public  void cortarAudio(float[] sinalOriginal,float rms,int freqAmostragem,LocalDateTime horaInicio) {

            System.out.println("CHAMOU CORTADOR\nrms="+rms+"\nfreqAmostragem="+freqAmostragem+"\nhorainicio="+horaInicio+
                    "\nvetor:\n"+Arrays.toString(sinalOriginal));
            
            // EXEMPLO DE TESTE:
            float sinalCortado[]= new float[1024];
            System.arraycopy(sinalOriginal, 0, sinalCortado, 0, 1024);
            registrarEvento(horaInicio.plusSeconds(10),sinalCortado,"Exemplo");
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
