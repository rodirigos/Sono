/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonoMain;

import GUI.JanelaPrincipal;
import sonoMain.realtime.RealTime1;

/**
 *
 * @author luisfg30
 */
public class SonoControl {
    
    private TgetInput tInput;
    public RealTime1 realTime;
    public JanelaPrincipal janela;
    
    public SonoControl(){
       realTime=RealTime1.getInstance();
       tInput = new TgetInput(this);
    }
    
}
