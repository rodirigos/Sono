/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Teste;

import Teste.RealTime.RealTime;
import java.util.Scanner;

/**Classe auxiliar para pegar o input do usuário
 *
 * @author luisfg30
 */
public class TgetInput extends Thread {
    

    RealTime realtime;
    private Scanner in = new Scanner(System.in);
    private boolean stop=false;
    
    TgetInput(RealTime r){
        realtime=r;
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
        System.out.println("\n COMANDOS VÁLIDOS: \n start-- começa a capturar o audio"
                        + "\n stop-- para de capturar "+
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
            else if(input.equals("exit")==true){
                Runtime.getRuntime().exit(0);
            }
            else{
                System.out.println("\n COMANDOS VÁLIDOS: \n start-- começa a capturar o audio"
                        + "\n stop-- para de capturar "+
                          "\n exit-- sai do programa");
            }
        }
    }
    
}
