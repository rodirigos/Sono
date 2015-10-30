/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonoMain.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sonoMain.Evento;


/**
 *
 * @author Rodrigo Essa classe gera um csv
 */
public class GenerateCsv {

    private FileWriter writer;
   
    /***
     Crianco o csv como objeto
     * @param nome: nome do arquivo a ser criado
     */
    public GenerateCsv(String nome) {
        try {
             this.writer = new FileWriter(nome);
             String campos = "Hora,Minuto,Segundo,Tipo\n";
             writer.append(campos);
             writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(GenerateCsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * *
     * Criacao do csv
     *
     * futuros parametros: vetor de eventos( hora, minutos e tipo)
     *
     * @param sFileName: Nome do arquivo a ser criado
     * @param gerEventos: Gerenciador de eventos finalizado e pronto para gerar
     * o csv
     *
     */
    // Modificado para receber um vetor de eventos
    public void CreateCsv(ArrayList<Evento> gerEventos) {
        try {
            for (int i = 0; i < gerEventos.size(); i++) {
                writer.append((char) gerEventos.get(i).horaRegistro.getHour());
                writer.append(",");
                writer.append((char) gerEventos.get(i).horaRegistro.getMinute());
                writer.append(",");
                writer.append((char) gerEventos.get(i).horaRegistro.getSecond());
                writer.append(",");
                writer.append(gerEventos.get(i).tipo);
                writer.append("\n");
            }
        
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void CloseCsv()
    {
        try {
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(GenerateCsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
