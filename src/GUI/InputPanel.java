/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import sonoMain.realtime.RealTime1;

/**
 *
 * @author luisfg30
 */
public class InputPanel extends JPanel{
    
    private RealTime1 realtime;
    private JButton capture,stop;
    private JPanel pBotoes;
    private ButtonListener listener;
    private JCheckBox useFile;
    
    public InputPanel(){
        
        realtime=RealTime1.getInstance();
        
        this.setLayout(new BorderLayout());
        pBotoes=new JPanel();
        pBotoes.setLayout(new GridLayout(3,0));
        pBotoes.setBackground(Color.red);
        this.add(pBotoes,BorderLayout.CENTER);
        
        
//        useFile= new JCheckBox("Abrir Arquivo");
//        useFile.setBackground(this.getBackground());
//        useFile.setSelected(false);
//        useFile.addItemListener(new ItemListener() 
//                        {
//            @Override
//            public void itemStateChanged(ItemEvent ie) {
//                if(ie.getStateChange()==ItemEvent.DESELECTED)
//                {
//
//                }
//                else
//                {
//
//                }
//            }
//        });
//        pBotoes.add(useFile);
        
        //Adiciona os botoes e o listener
        listener= new ButtonListener();
        capture=new JButton("Capturar");
        capture.addActionListener(listener);
        stop=new JButton("Parar");
        stop.addActionListener(listener);
        pBotoes.add(capture);
        pBotoes.add(stop);

    }
    

        
            /**
     * Classe auxiliar usada para pdetectar o clique no botão da interface
     */
    private class ButtonListener implements ActionListener
        {
             @Override
             public void actionPerformed(ActionEvent ae) 
             {
                  JButton clickedButton=(JButton)(ae.getSource());
                 if(clickedButton==capture)
                 {
                     System.out.println("clicou1");
                     realtime.startRecord();
                 }
                 else if(clickedButton==stop){
                     System.out.println("clicou2");
                     realtime.stopRecord();
                     realtime.getData();
                 }
             }
        }
}
