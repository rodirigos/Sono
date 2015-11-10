/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonoMain;

import sonoMain.realtime.RealTime;
import java.util.Scanner;

/**Classe auxiliar para pegar o input do usuário
 *
 * @author luisfg30
 */
public class TgetInput extends Thread {
    

    private RealTime realtime;
    private GerenciadorEventos gerenciador;
    private Scanner in = new Scanner(System.in);
    private boolean stop=false;
    
    TgetInput(RealTime r,GerenciadorEventos g){
        realtime=r;
        gerenciador=g;
        this.start();
    }
    
    public void killThread(){
        stop=true;
    }
    
    /**Cada vez que o usuário digita um input válido o sistema executa um comando<br>
     *  Inputs Válidos: <br>
     */
    @Override
    public void run(){
        
        System.out.println("\n --------SONO-------\n ");
        System.out.println("\n COMANDOS VÁLIDOS: "
                       + "\n start-- começa a capturar o audio"+
                         "\n stop-- para de capturar "+
                         "\n import-- importa o áudio em AudioSample "+
                         "\n events-- lista todos os eventos do sistema por data"+
                         "\n show INDEX-- mostra os dados de um evento"+
                         "\n export-- Exportar os roncos para o formato wav"+
                         "\n exit-- sai do programa");
        while(stop!=true){
            String input=in.nextLine();
            if(input.equals("start")==true){
                realtime.startRecord();
            }
            else if(input.equals("stop")==true){
                     realtime.stopRecord();
                     realtime.getData();
            }
            else if(input.equals("import")==true){
                realtime.importAudio("AudioSample.wav");
            }
            else if(input.equals("events")==true){
                    gerenciador.printEventos();
            }
            else if(input.contains("show")==true){
                //depois eu faço isso, nem sei se precisa =p
            }
            else if(input.equals("export")==true){
                gerenciador.floatToWavAll();
            }
            else if(input.equals("exit")==true){
                realtime.quitprogram();
                gerenciador.fecharGerenciador();
                Runtime.getRuntime().exit(0);
            }
            
            else{
                System.out.println("\n COMANDOS VÁLIDOS: \n start-- começa a capturar o audio"
                        + "\n stop-- para de capturar "+
                          "\n import-- importa o áudio em AudioSample "+
                          "\n events-- lista todos os eventos do sistema por data"+
                          "\n show INDEX-- mostra os dados de um evento"+
                          "\n exit-- sai do programa");
            }
        }
    }
    
}
