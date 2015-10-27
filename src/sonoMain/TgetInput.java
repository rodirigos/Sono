/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonoMain;

import GUI.JanelaPrincipal;
import sonoMain.realtime.RealTime1;
import java.util.Scanner;

/**Classe auxiliar para pegar o input do usuário
 *
 * @author luisfg30
 */
public class TgetInput extends Thread {
    
    private SonoControl control;
    RealTime1 realtime;
    private Scanner in = new Scanner(System.in);
    private boolean stop=false;
    
    TgetInput(SonoControl s){
        control=s;
        realtime=RealTime1.getInstance();
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
                        + "\n stop-- para de capturar ");
        while(stop!=true){
            String input=in.nextLine();
            realtime.fileInput=false;
            //System.out.println("\n input: "+input);
//            if(input.equals("s") || input.equals("S") ){
//              System.out.println("\n Abrir áudio de arquivo? (s/n)");
//              String input1=in.nextLine();
//              if(input1.equals("s") || input1.equals("S") ){
//                  realtime.fileInput=true;
//              }
//              else{
//                  realtime.fileInput=false;
//              }
//              control.janela= new JanelaPrincipal();
//              
//           }
//            else if(input.equals("n") ||input.equals("N") ){
//                 System.out.println("\n Abrir áudio de arquivo? (s/n)");
//                 String input1=in.nextLine();
//                 if(input1.equals("s") || input1.equals("S") ){
//                     realtime.fileInput=true;
//                 }
//                 else{
//                     realtime.fileInput=false;
//                 }
//            }
            if(input.equals("start")==true){
                realtime.startRecord();
            }
            else if(input.equals("stop")==true){
                                     realtime.stopRecord();
                     realtime.getData();
            }
            else{
                System.out.println("\n COMANDOS VÁLIDOS: \n start-- começa a capturar o audio"
                        + "\n stop-- para de capturar ");
            }
        }
    }
    
}
