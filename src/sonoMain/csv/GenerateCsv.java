/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonoMain.csv;

import java.io.FileWriter;
import java.io.IOException;
import sonoMain.GerenciadorEventos;

/**
 *
 * @author Rodrigo 
 * Essa classe gera um csv
 */
public class GenerateCsv {
public GenerateCsv(){}
 
/***
 * Criacao do csv
 * 
     * futuros parametros: vetor de eventos( hora, minutos e tipo)
     * @param sFileName: Nome do arquivo a ser criado
     * @param gerEventos: Gerenciador de eventos finalizado e pronto para gerar o csv
     * 
 */
    // Modificado para receber um vetor de eventos
    public void CreateCsv(String sFileName,GerenciadorEventos gerEventos) {
        try {
            // Criar arquivo csv usando dados de um vetor que sera recebido 
            FileWriter writer = new FileWriter(sFileName);
            // Gerando os nomes dos campos
            String campos = "Hora,Minuto,Segundo,Tipo\n";
            writer.append(campos);
            for(int i =0; i<gerEventos.getEventosRegistrados().size(); i++){
            writer.append((char) gerEventos.getEventosRegistrados().get(i).horaRegistro.getHour());
            writer.append(",");
            writer.append((char)gerEventos.getEventosRegistrados().get(i).horaRegistro.getMinute());
            writer.append(",");
            writer.append((char)gerEventos.getEventosRegistrados().get(i).horaRegistro.getSecond());
            writer.append(",");
            writer.append(gerEventos.getEventosRegistrados().get(i).tipo);
            writer.append("\n");
            }
            writer.append(";");
            writer.flush();
	    writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
