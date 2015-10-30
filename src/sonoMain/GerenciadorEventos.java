/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonoMain;

import java.util.ArrayList;
import java.util.Arrays;
import sonoMain.csv.GenerateCsv;

/**
 *
 * @author luisfg30
 */
public class GerenciadorEventos {
    
    private ArrayList<Evento> eventosRegistrados;
    private GenerateCsv csvGenerate = new GenerateCsv("Eventos.csv");
   
    public GerenciadorEventos(){
        eventosRegistrados= new ArrayList();
    }
    /***
     Metodo criado para adicionar eventos durante o sono
     * 
     * @param e: Um evento singular com o horario e tipo a ser definido
     * futuros parametros: Serial com a temperatura e humidade de cada evento
     */ 
    public void adicionarEvento(Evento e){
        eventosRegistrados.add(e);
        System.out.println("\nCLASSE GERENCIADOR :\n\tAdiconou evento em: "+e.horaRegistro.toString()+
                "\n tipo: "+e.tipo+"\n audio: "+Arrays.toString(e.audioData));
        //chama a classe contas apra avaliar o evento
        
        //chama a classe CSV para guardar o evento
        csvGenerate.CreateCsv(eventosRegistrados);
    }
    /***
     Classe que ia ser chamada apos a leiura serial do push botton para a finalizacao do intervalo
     de gravacao
     futuros parametros: Serial "com a leitura do push botton e etc"*/
    public void fecharGerenciador()
    {
        // Mandar comandos para o led ficar vermelho
        // Fechando o csv gerado
        csvGenerate.CloseCsv();
    }
    
    
    /**
     * Mostra todos os eventos do sistema
     */
    public void printEventos(){
        for(int i=0;i<eventosRegistrados.size();i++){
            System.out.println("\n["+i+"] Hora= "+eventosRegistrados.get(i).horaRegistro+
                    "\n tipo= "+eventosRegistrados.get(i).tipo);
        }
    }
    
    /**
     * Mostra os dados de um evento específico
     * @param index indice desejado
     */
    public void printAudioEvento(int index){
        for(int i=0;i<eventosRegistrados.get(index).audioData.length;i++){
            System.out.println("\n["+i+"]= "+eventosRegistrados.get(index).audioData[i]);
        }
    }
    
}
