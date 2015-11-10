/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonoMain;

import Teste.FloatSampleTools;
import static Teste.FloatSampleTools.float2byte;
import static Teste.FloatSampleTools.float2byteInterleaved;
import ddf.minim.AudioRecorder;
import ddf.minim.AudioSample;
import ddf.minim.Minim;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import sonoMain.Serial.SerialRead;
import sonoMain.csv.GenerateCsv;
import sonoMain.realtime.RealTime;

/**
 *
 * @author luisfg30
 */
public class GerenciadorEventos {

    private ArrayList<Evento> eventosRegistrados;
    private GenerateCsv csvGenerate = new GenerateCsv("Eventos.csv");

    public GerenciadorEventos() {
        eventosRegistrados = new ArrayList();
    }

    /**
     * *
     * Metodo criado para adicionar eventos durante o sono
     *
     * @param e: Um evento singular com o horario e tipo a ser definido futuros
     * parametros: Serial com a temperatura e humidade de cada evento
     */
    public void adicionarEvento(Evento e) {
        // Setando de maneira perigosa a temperatura e a umidade
        e.temperatura = SerialRead.temperatura;
        e.umidade = SerialRead.umidade;
        eventosRegistrados.add(e);
        System.out.println("\nCLASSE GERENCIADOR :\n\tAdiconou evento em: " + e.horaRegistro.toString()
                + "\n tipo: " + e.tipo + "\n audio: " + Arrays.toString(e.audioData));
        //chama a classe contas apra avaliar o evento

        //chama a classe CSV para guardar o evento
        csvGenerate.saveEvent(e);
    }

    /**
     * *
     * Classe que ia ser chamada apos a leiura serial do push botton para a
     * finalizacao do intervalo de gravacao futuros parametros: Serial "com a
     * leitura do push botton e etc"
     */
    public void fecharGerenciador() {
        // Mandar comandos para o led ficar vermelho
        // Fechando o csv gerado
        //csvGenerate.saveAllEvents(eventosRegistrados);
        csvGenerate.CloseCsv();
    }

    /**
     * Mostra todos os eventos do sistema
     */
    public void printEventos() {
        for (int i = 0; i < eventosRegistrados.size(); i++) {
            System.out.println("\n[" + i + "] Hora= " + eventosRegistrados.get(i).horaRegistro
                    + "\n tipo= " + eventosRegistrados.get(i).tipo);
        }
    }

    /**
     * Mostra os dados de um evento espec�fico
     *
     * @param index indice desejado
     */
    public void printAudioEvento(int index) {
        for (int i = 0; i < eventosRegistrados.get(index).audioData.length; i++) {
            System.out.println("\n[" + i + "]= " + eventosRegistrados.get(index).audioData[i]);
        }
    }

    /**
     * Criando metodo que pegar vetor float e transforma em wav files A criaao
     * desse parametro funciona apenas por motivo de debug e nao deve ser usada
     * como o audio natural porque peder qualidade na conversao
     *
     * @param minim: referencia do minim para utilizar biblioteca
     */
    public void floatToWav(RealTime realTime) {
        float waveSampleRate = 8192f;
        // Os tres segundos a serem convertidos
        byte[] floConv = new byte[8192*3];
        System.out.println("Entrei no exportador Vetor = " + eventosRegistrados.size() + "\n");
        AudioFormat format = new AudioFormat(waveSampleRate, 8, 1, true, true);
        
        
        // Criando 
        for (int i = 0; i < eventosRegistrados.size(); i++) {
            try {
                float2byteInterleaved(eventosRegistrados.get(i).audioData, 0, floConv, 0, 8192*3, format, 0);
                ByteArrayInputStream ais = new ByteArrayInputStream(floConv);
                AudioInputStream inputStream = new AudioInputStream(ais, format, floConv.length);
                String str = "amostra";
                str = str.concat(String.valueOf(i));
                str = str.concat(".wav");
                System.out.println("A string tem nome de:" + str);
                AudioSystem.write(inputStream, AudioFileFormat.Type.WAVE, new File(str));
                System.out.println("Os audios foram exportados com sucesso");
            } catch (IOException ex) {
                Logger.getLogger(GerenciadorEventos.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
/*
    public InputStream createInput(String fileName) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(fileName);
        return inputStream;
    }
*/
}
