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
    
    /* Pegar o array terminado para a criacao do csv(pode ser colocado no
     mesmo para ser protected mas por enquanto vou fazer um getEvento ok?
     Maneira alternativa: adicionar em metodo addCsv depende de como sera feita
    o gerenciamento de eventos. Se vai existir um gerenciador para cada 10 segundos
    ou se tera um gerenciador contendo todos os eventos*/
    public ArrayList<Evento> getEventosRegistrados() {
        return eventosRegistrados;
    }
   
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
