/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonoMain;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author luisfg30
 */
public class GerenciadorEventos {
    
    private ArrayList<Evento> eventosRegistrados;
    
    public GerenciadorEventos(){
        eventosRegistrados= new ArrayList();
    }
    
    public void adicionarEvento(Evento e){
        eventosRegistrados.add(e);
        System.out.println("\nCLASSE GERENCIADOR :\n\tAdiconou evento em: "+e.horaRegistro.toString()+
                "\n tipo: "+e.tipo+"\n audio: "+Arrays.toString(e.audioData));
        //chama a classe contas apra avaliar o evento
        //chama a classe CSV para guardar o evento
    }
    
}
