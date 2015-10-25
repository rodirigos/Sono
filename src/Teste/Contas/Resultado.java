/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Teste.Contas;



/**
 *
 * @author Felipe Lambach
 */
public class Resultado {
   float energiaFreqRates []= new float[9];
   float MaxFreq[] = new float[9];

    public float[] getEnergiaFreqRates() {
        return energiaFreqRates;
    }

    public void setEnergiaFreqRates(float[] energiaFreqRates) {
        this.energiaFreqRates = energiaFreqRates;
    }

    public float[] getMaxFreq() {
        return MaxFreq;
    }

    public void setMaxFreq(float[] MaxFreq) {
        this.MaxFreq = MaxFreq;
    }
   
}
