/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonoMain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import sonoMain.contas.Contas;

/**
 *
 * @author Felipe Lambach
 */
public class Cortador {

    public static final float TRESHOLD = 3;
    public static final float TEMPO_JANELA = 0.20f; // 8192*0,25 dá 2048, que é o número de amostras por janela
    private GerenciadorEventos gerenciador;
    private Contas contas;

    public Cortador(GerenciadorEventos gE,Contas c) {
        gerenciador = gE;
        contas=c;
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
        
        
        //teste das contas
//        float [] sinalContas= new float[1024];
//        System.arraycopy(sinalOriginal, 0, sinalContas, 0, 1024);
//        contas.calcFaixas(sinalContas, freqAmostragem);
        
        int amostrasJanela = (int) (Cortador.TEMPO_JANELA * freqAmostragem);
        System.out.println("\n amostrasJanela: " + amostrasJanela);
        ArrayList<Integer> indicesJanelas = new ArrayList();
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
                System.out.println("\n indice["+i+"] passou de: "+Cortador.TRESHOLD * rmsSinal);
                indicesJanelas.add(i);
                int indicesLen=indicesJanelas.size();
                if(indicesLen>1){
                    //verifica se a distancia entre duas janelas é menor que 3 janelas
                    System.out.println("\n indiceComparado=" +indicesJanelas.get(indicesLen-2));
                    if (i - indicesJanelas.get(indicesLen-2) <= 0.6f * freqAmostragem) {
                        //concatena as janelas
//                        if(indicesJanelas.contains(i)==false){
//                            indicesJanelas.add(i);
//                        }
                        System.out.println("indicesJanelas "+Arrays.toString(indicesJanelas.toArray()));
                    }
                    else{//distancia entre janelas é>0.6, chegou no proximo sinal
                        System.out.println("\n distancia entre janelas maior que 0,6");
                        System.out.println("indicesJanelas "+Arrays.toString(indicesJanelas.toArray()));
                        int indiceCorteInicio=indicesJanelas.get(0);
                        int indiceCorteFim=indicesJanelas.get(indicesJanelas.size()-2)+amostrasJanela;
                        int corteLen=indiceCorteFim-indiceCorteInicio;
                        System.out.println("\nindiceInicio="+indiceCorteInicio+
                                " indiceFim= "+indiceCorteFim);
                        //pega somente sinais com menos de 3s
                        if(corteLen>=0.6f*freqAmostragem && corteLen<=3*freqAmostragem){
                            System.out.println(" indicesJanelasSelecionadas "+Arrays.toString(indicesJanelas.toArray()));
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
                            System.out.println("\n corteLen: "+corteLen+
                                    "indiceCorteInicio: "+indiceCorteInicio+
                                    "indiceCorteFim: "+indiceCorteFim);
                            //copia o pedaço do sinal desejado
                            float sinalCortado[] = new float[corteLen];
                            System.arraycopy(sinalOriginal, indiceCorteInicio, sinalCortado, 0, corteLen);
                            float tempoCorte=indiceCorteInicio/freqAmostragem;
                            System.out.println("\n segundos depois do inicio: "+tempoCorte);
                            LocalDateTime horaEvento= horaInicio.plusSeconds((long) tempoCorte);
                            registrarEvento(horaEvento, sinalCortado, "Possível Ronco");
                            indicesJanelas.clear();
                            indicesJanelas.add(i);
                        }
                        else{
                            System.out.println("\n Eevento com menos de 0,6s ou mais de 3s");
                            indicesJanelas.clear();
                            indicesJanelas.add(i);
                        }
                    }
                }    
            }
        }//se o evento detectado era o único ou o último
        int indicesLen=indicesJanelas.size();
        if(indicesLen>=1){
            int indiceCorteInicio=indicesJanelas.get(0);
                        int indiceCorteFim=indicesJanelas.get(indicesJanelas.size()-2)+amostrasJanela;
                        int corteLen=indiceCorteFim-indiceCorteInicio;
                        System.out.println("\nindiceInicio="+indiceCorteInicio+
                                " indiceFim= "+indiceCorteFim);
                        //pega somente sinais com menos de 3s
                        if(corteLen>=0.6f*freqAmostragem && corteLen<=3*freqAmostragem){
                            System.out.println(" indicesJanelasSelecionadas "+Arrays.toString(indicesJanelas.toArray()));
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
                            System.out.println("\n corteLen: "+corteLen+
                                    "indiceCorteInicio: "+indiceCorteInicio+
                                    "indiceCorteFim: "+indiceCorteFim);
                            //copia o pedaço do sinal desejado
                            float sinalCortado[] = new float[corteLen];
                            System.arraycopy(sinalOriginal, indiceCorteInicio, sinalCortado, 0, corteLen);
                            float tempoCorte=indiceCorteInicio/freqAmostragem;
                            System.out.println("\n segundos depois do inicio: "+tempoCorte);
                            LocalDateTime horaEvento= horaInicio.plusSeconds((long) tempoCorte);
                            registrarEvento(horaEvento, sinalCortado, "Possível Ronco");
                        }
        }
    }

}
