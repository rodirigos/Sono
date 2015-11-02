/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonoMain;

import sonoMain.realtime.RealTime;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import sonoMain.Serial.SerialRead;

/**
 *
 * @author luisfg30
 */
public class SonoMain extends JFrame{
    
    private RealTime audiotest;
    private Scanner inputKey = new Scanner(System.in);
    public SerialRead serRead = new SerialRead();
    
    //GUI
    JButton capture,stop,play;
    ButtonListener listener;
    public static void main(String args[]){
        //inicia as partes do sistema
        GerenciadorEventos gerenciador= new GerenciadorEventos();
        Cortador cortador= new Cortador(gerenciador);
            
        JFrame frame = new SonoMain(cortador,gerenciador);
        frame.pack();
        frame.setVisible(true);
      
    }
    
    public SonoMain(Cortador cortador,GerenciadorEventos gerenciador){
        super("Capture Sound Demo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container content = getContentPane();


        listener= new ButtonListener();
        capture = new JButton("Capture");
        capture.addActionListener(listener);
        stop = new JButton("Stop");
        stop.addActionListener(listener);
        play = new JButton("Play");
        play.addActionListener(listener);

        capture.setEnabled(true);
        stop.setEnabled(true);
        play.setEnabled(true);

        content.add(capture, BorderLayout.NORTH);
        content.add(stop, BorderLayout.CENTER);
        content.add(play, BorderLayout.SOUTH);

        audiotest= new RealTime();
        audiotest.setCortadorRef(cortador);
        TgetInput t1= new TgetInput(audiotest,gerenciador);
    //    System.out.println("\nDigite algo e aperte entter para começar.");
    //    String s= inputKey.nextLine();
    //    if(s.equals("")==false){
    //        
    //    }
    }
    
    public void captureAudio(){
        audiotest.startRecord();  
    }
    
    public void stopCapture(){
        audiotest.stopRecord();
        audiotest.getData();
    }
    
    public void playAudio(){
        
    }
    
        private class ButtonListener implements ActionListener
        {
             @Override
             public void actionPerformed(ActionEvent ae) 
             {
                  JButton clickedButton=(JButton)(ae.getSource());
                 if(clickedButton==capture)
                 {
                      captureAudio();
                 }
                 else if(clickedButton==stop){
                     stopCapture();
                 }
                 else if(clickedButton==play){
                     playAudio();
                 }
             }
        }
    
}
