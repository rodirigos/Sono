/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonoMain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Felipe Lambach
 */
public class Cortador {

    public static final float TRESHOLD = 3;
    public static final float TEMPO_JANELA = 0.25f; // 8192*0,25 dá 2048, que é o número de amostras por janela
    private GerenciadorEventos gerenciador;

    public Cortador(GerenciadorEventos gE) {
        gerenciador = gE;
    }

    /**
     * Método chamado cada vez que o cortador separar um evento de áudio de 3s
     *
     * @param horaCalculada
     * @param sinalEvento
     * @param tipoEvento
     */
    public void registrarEvento(LocalDateTime horaCalculada, float[] sinalEvento, String tipoEvento) {
        Evento e = new Evento(horaCalculada, sinalEvento, tipoEvento);
        gerenciador.adicionarEvento(e);
    }

    /**
     * Método chamado pela Classe AudioDataListener (dentro do Realtime) a cada
     * vez que a gravação atinge 10s
     *
     * @param sinalOriginal
     * @param rmsSinal
     * @param freqAmostragem
     * @param horaInicio
     */
    public void cortarAudio(float[] sinalOriginal, float rmsSinal, int freqAmostragem, LocalDateTime horaInicio) {

        System.out.println("CHAMOU CORTADOR\nrms=" + rmsSinal + "\nfreqAmostragem=" + freqAmostragem + "\nhorainicio=" + horaInicio
                + "\nvetor:\n" + Arrays.toString(sinalOriginal));

        int amostrasJanela = (int) (Cortador.TEMPO_JANELA * freqAmostragem);
        System.out.println("\n amostrasJanela: " + amostrasJanela);
        ArrayList<Integer> indicesJanelas = new ArrayList();
        int contJanelas = 0;
        int sinalLen = sinalOriginal.length;

        for (int i = 0; i < sinalLen; i = i + amostrasJanela) {
            //verifica se o passo vai estourar o vetor
            int indiceTempInicio = i, indiceTempFim;
            if (i + amostrasJanela > sinalLen) {
                indiceTempFim = sinalLen;
            } else {
                indiceTempFim = i + amostrasJanela;
            }
            //pega o valor máximo da janela
            float tempMax = 0;
            for (int j = indiceTempInicio; j < indiceTempFim; j++) {
                if (sinalOriginal[j] > tempMax) {
                    tempMax = sinalOriginal[j];
                }
            }
            //compara com o treshold
            if (tempMax > Cortador.TRESHOLD * rmsSinal) {
                
                //guarda o indice da janela
                indicesJanelas.add(i);
                int indicesLen=indicesJanelas.size()-1;
                //verifica se a distancia entre duas janelas é maior que 0.75
                if (i - indicesJanelas.get(contJanelas).intValue() < 0.75f * freqAmostragem) {
                    //concatena as janelas
                    indicesJanelas.add(i);
                }
                //verifica se o audio tem mais de 0.75 segundos de duração
                if(indicesJanelas.get(indicesLen).intValue()+amostrasJanela-indicesJanelas.get(0).intValue()>=0.75*freqAmostragem){
                    int indiceCorteInicio=indicesJanelas.get(0).intValue();
                    int indiceCorteFim=indicesJanelas.get(indicesLen).intValue()+amostrasJanela;
                    //pega somente sinais com menos de 3s
                    int corteLen=indiceCorteFim-indiceCorteInicio;
                    if(corteLen<=3*freqAmostragem){
                        System.out.println("\n indicesjanelas: \n "+Arrays.toString(indicesJanelas.toArray()));
                        int indiceCentral=(indiceCorteFim-indiceCorteInicio)/2 +indiceCorteInicio;
                        System.out.println("\n indiceCentral= "+indiceCentral);
                        //verifica se nao vai ficar antes do começo do vetor
                        if(indiceCentral-1.5f*freqAmostragem>0){
                            indiceCorteInicio=(int) (indiceCentral-1.5f*freqAmostragem);
                        }
                        else{
                            indiceCorteInicio=0;
                        }
                        //verifica se vai passar do tamamanho do vetor
                        if(indiceCentral+1.5f*freqAmostragem<sinalLen){
                            indiceCorteFim=(int) (indiceCentral+1.5f*freqAmostragem);
                        }
                        else{
                            indiceCorteFim=sinalLen;
                        }
                        corteLen=indiceCorteFim-indiceCorteInicio;
                        System.out.println("\n corteLen: "+corteLen);
                        //copia o pedaço do sinal desejado
                        float sinalCortado[] = new float[corteLen];
                        System.arraycopy(sinalOriginal, indiceCorteInicio, sinalCortado, 0, corteLen);
                        System.out.println("\n segundos depois do inicio: "+corteLen/freqAmostragem);
                        LocalDateTime horaEvento= horaInicio.plusSeconds(corteLen/freqAmostragem);
                        registrarEvento(horaEvento, sinalCortado, "Possível Ronco");
                    }
                    else{//sinais maiores que 3s ...
                        
                    }
                }
                indicesJanelas.clear();
                indicesJanelas.add(i);
                contJanelas=0;
            }
        }
    }

}
