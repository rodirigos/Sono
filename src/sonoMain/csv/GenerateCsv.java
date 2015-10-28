/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonoMain.csv;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Rodrigo 
 * Essa classe gera um csv
 */
public class GenerateCsv {
public GenerateCsv(){}
    // Simulando o a classe eventos que ainda nao foi desenvolvida
    private final String testeCsv = "Horas,Minutos,Tipo\n"
            + "1,20,Ronco\n"
            + "3,30,Ronco \n"
            + "4,21,Barulho\n"
            + "5,30,Ronco";
/***
 * Criacao do csv
 * 
     * futuros parametros: vetor de eventos( hora, minutos e tipo)
     * @param sFileName: Nome do arquivo a ser criado
     * 
 */
    public void CreateCsv(String sFileName,String data) {
        try {
            // Criar arquivo csv usando dados de um vetor que sera recebido 
            FileWriter writer = new FileWriter(sFileName);
            writer.append(data);
            writer.flush();
	    writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
