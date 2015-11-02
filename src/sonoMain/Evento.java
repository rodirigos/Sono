/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonoMain;

import java.time.LocalDateTime;

/**
 *Classe criada somente para guardar os dados de de um evento<br>
 * Um evento é criado na classe Cortador a cada vez que ele detecta um nível de<br>
 * som acima do treshold.
 * @author luisfg30
 */
public class Evento {
    
    public LocalDateTime horaRegistro;
    public float[] audioData;
    public String tipo;
    public float temperatura;
    public float umidade;
    
    public Evento(LocalDateTime hora, float[] audio,String t){
        horaRegistro=hora;
        audioData= new float[audio.length];
        System.arraycopy(audio, 0, audioData, 0, audio.length);
        tipo=t;
    }
}
