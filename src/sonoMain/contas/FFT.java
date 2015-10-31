/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonoMain.contas;

import java.awt.Color; 
import java.lang.reflect.Array;
import java.math.MathContext;
//Comentario de teste
public class FFT extends FourierTransform {

    /**
     * Constructs an FFT that will accept sample buffers that are
     * <code>timeSize</code> long and have been recorded with a sample rate of
     * <code>sampleRate</code>. <code>timeSize</code> <em>must</em> be a power
     * of two. This will throw an exception if it is not.
     *
     * @param timeSize the length of the sample buffers you will be analyzing
     * @param sampleRate the sample rate of the audio you will be analyzing
     */
    public FFT(int timeSize, float sampleRate) {
        super(timeSize, sampleRate);
        if ((timeSize & (timeSize - 1)) != 0) {
            throw new IllegalArgumentException(
                    "FFT: timeSize must be a power of two.");
        }
        buildReverseTable();
        buildTrigTables();
    }

    protected void allocateArrays() {
        spectrum = new float[timeSize / 2 + 1];
        real = new float[timeSize];
        imag = new float[timeSize];
    }

    public void scaleBand(int i, float s) {
        if (s < 0) {
            throw new IllegalArgumentException("Can't scale a frequency band by a negative value.");
        }
        if (spectrum[i] != 0) {
            real[i] /= spectrum[i];
            imag[i] /= spectrum[i];
            spectrum[i] *= s;
            real[i] *= spectrum[i];
            imag[i] *= spectrum[i];
        }
        if (i != 0 && i != timeSize / 2) {
            real[timeSize - i] = real[i];
            imag[timeSize - i] = -imag[i];
        }
    }

    public void setBand(int i, float a) {
        if (a < 0) {
            throw new IllegalArgumentException("Can't set a frequency band to a negative value.");
        }
        if (real[i] == 0 && imag[i] == 0) {
            real[i] = a;
            spectrum[i] = a;
        } else {
            real[i] /= spectrum[i];
            imag[i] /= spectrum[i];
            spectrum[i] = a;
            real[i] *= spectrum[i];
            imag[i] *= spectrum[i];
        }
        if (i != 0 && i != timeSize / 2) {
            real[timeSize - i] = real[i];
            imag[timeSize - i] = -imag[i];
        }
    }

    // performs an in-place fft on the data in the real and imag arrays
    // bit reversing is not necessary as the data will already be bit reversed
    private void fft() {
        for (int halfSize = 1; halfSize < real.length; halfSize *= 2) {
            // float k = -(float)Math.PI/halfSize;
            // phase shift step
            // float phaseShiftStepR = (float)Math.cos(k);
            // float phaseShiftStepI = (float)Math.sin(k);
            // using lookup table
            float phaseShiftStepR = cos(halfSize);
            float phaseShiftStepI = sin(halfSize);
            // current phase shift
            float currentPhaseShiftR = 1.0f;
            float currentPhaseShiftI = 0.0f;
            for (int fftStep = 0; fftStep < halfSize; fftStep++) {
                for (int i = fftStep; i < real.length; i += 2 * halfSize) {
                    int off = i + halfSize;
                    float tr = (currentPhaseShiftR * real[off]) - (currentPhaseShiftI * imag[off]);
                    float ti = (currentPhaseShiftR * imag[off]) + (currentPhaseShiftI * real[off]);
                    real[off] = real[i] - tr;
                    imag[off] = imag[i] - ti;
                    real[i] += tr;
                    imag[i] += ti;
                }
                float tmpR = currentPhaseShiftR;
                currentPhaseShiftR = (tmpR * phaseShiftStepR) - (currentPhaseShiftI * phaseShiftStepI);
                currentPhaseShiftI = (tmpR * phaseShiftStepI) + (currentPhaseShiftI * phaseShiftStepR);
            }
        }
    }

    public void forward(float[] buffer) {
        if (buffer.length != timeSize) {
            throw new IllegalArgumentException("FFT.forward: The length of the passed sample buffer must be equal to timeSize().");
        }
        doWindow(buffer);
        // copy samples to real/imag in bit-reversed order
        bitReverseSamples(buffer);
        // perform the fft
        fft();
        // fill the spectrum buffer with amplitudes
        fillSpectrum();
    }

    /**
     * Performs a forward transform on the passed buffers.
     *
     * @param buffReal the real part of the time domain signal to transform
     * @param buffImag the imaginary part of the time domain signal to transform
     */
    public void forward(float[] buffReal, float[] buffImag) {
        if (buffReal.length != timeSize || buffImag.length != timeSize) {
            throw new IllegalArgumentException("FFT.forward: The length of the passed buffers must be equal to timeSize().");
        }
        setComplex(buffReal, buffImag);
        bitReverseComplex();
        fft();
        fillSpectrum();
    }

    public void inverse(float[] buffer) {
        if (buffer.length > real.length) {
            throw new IllegalArgumentException("FFT.inverse: the passed array's length must equal FFT.timeSize().");
        }
        // conjugate
        for (int i = 0; i < timeSize; i++) {
            imag[i] *= -1;
        }
        bitReverseComplex();
        fft();
        // copy the result in real into buffer, scaling as we do
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = real[i] / real.length;
        }
    }

    private int[] reverse;

    //usa no fft
    private void buildReverseTable() {
        int N = timeSize;
        reverse = new int[N];

        // set up the bit reversing table
        reverse[0] = 0;
        for (int limit = 1, bit = N / 2; limit < N; limit <<= 1, bit >>= 1) {
            for (int i = 0; i < limit; i++) {
                reverse[i + limit] = reverse[i] + bit;
            }
        }
    }

    // copies the values in the samples array into the real array
    // in bit reversed order. the imag array is filled with zeros.
    private void bitReverseSamples(float[] samples) {
        for (int i = 0; i < samples.length; i++) {
            real[i] = samples[reverse[i]];
            imag[i] = 0.0f;
        }
    }

    // bit reverse real[] and imag[]
    private void bitReverseComplex() {
        float[] revReal = new float[real.length];
        float[] revImag = new float[imag.length];
        for (int i = 0; i < real.length; i++) {
            revReal[i] = real[reverse[i]];
            revImag[i] = imag[reverse[i]];
        }
        real = revReal;
        imag = revImag;
    }

    // lookup tables
    private float[] sinlookup;
    private float[] coslookup;

    private float sin(int i) {
        return sinlookup[i];
    }

    private float cos(int i) {
        return coslookup[i];
    }

    //usa no fft
    private void buildTrigTables() {
        int N = timeSize;
        sinlookup = new float[N];
        coslookup = new float[N];
        for (int i = 0; i < N; i++) {
            sinlookup[i] = (float) Math.sin(-(float) Math.PI / i);
            coslookup[i] = (float) Math.cos(-(float) Math.PI / i);
        }
    }

    public Resultado Calcula_fft(FFT fft, float Sinal_Original[]) {

        int i;
        int aux = sampleRate * timeSize;
        int IndicesFreq[] = {Math.abs(100 / aux) + 1, Math.abs(150 / aux) + 1, Math.abs(250 / aux) + 1, Math.abs(350 / aux) + 1, Math.abs(500 / aux) + 1, Math.abs(1000 / aux) + 1, Math.abs(1500 / aux) + 1, Math.abs(2000 / aux) + 1};
        int Freq[] = new int[9];

        float EnergiaFreq[] = new float[9];
        float MaxFreq[] = new float[9];
        float energiaFreqRates[] = new float[9];
        float EnergiaFreqTotal = 0, vetor[];

        forward(Sinal_Original);
        vetor = getSpectrum();

        Resultado resultado = new Resultado();
        System.out.println("vetor indice");
        for (i = 0; i < 9; i++) {
            System.out.println(i + " ->" + indexToFreq(i));
        }

        System.out.println("divisao ->" + Math.abs(100 / aux) + 1);
        for (i = 0; i < vetor.length; i++) {
            if (i < Math.abs(4000 / aux) + 1) {

                EnergiaFreqTotal += Math.pow(vetor[i], 2);
            }
            if (i < IndicesFreq[0]) {
                EnergiaFreq[0] += Math.pow(vetor[i], 2);
                if (MaxFreq[0] < vetor[i]) {
                    MaxFreq[0] = vetor[i];
                    Freq[0] = i * sampleRate / timeSize;
                }
            } else if (i >= IndicesFreq[0] && i < IndicesFreq[1]) {
                EnergiaFreq[1] += Math.pow(vetor[i], 2);
                if (MaxFreq[1] < vetor[i]) {
                    MaxFreq[1] = vetor[i];
                    Freq[1] = i * sampleRate / timeSize;
                }
            } else if (i >= IndicesFreq[1] && i < IndicesFreq[2]) {
                EnergiaFreq[2] += Math.pow(vetor[i], 2);
                if (MaxFreq[2] < vetor[i]) {
                    MaxFreq[2] = vetor[i];
                    Freq[2] = i * sampleRate / timeSize;
                }
            } else if (i >= IndicesFreq[2] && i < IndicesFreq[3]) {
                EnergiaFreq[3] += Math.pow(vetor[i], 2);
                if (MaxFreq[3] < vetor[i]) {
                    MaxFreq[3] = vetor[i];
                    Freq[3] = i * sampleRate / timeSize;
                }
            } else if (i >= IndicesFreq[3] && i < IndicesFreq[4]) {
                EnergiaFreq[4] += Math.pow(vetor[i], 2);
                if (MaxFreq[4] < vetor[i]) {
                    MaxFreq[4] = vetor[i];
                    Freq[4] = i * sampleRate / timeSize;
                }
            } else if (i >= IndicesFreq[4] && i < IndicesFreq[5]) {
                EnergiaFreq[5] += Math.pow(vetor[i], 2);
                if (MaxFreq[5] < vetor[i]) {
                    MaxFreq[5] = vetor[i];
                    Freq[5] = i * sampleRate / timeSize;
                }
            } else if (i >= IndicesFreq[5] && i < IndicesFreq[6]) {
                EnergiaFreq[6] += Math.pow(vetor[i], 2);
                if (MaxFreq[6] < vetor[i]) {
                    MaxFreq[6] = vetor[i];
                    Freq[6] = i * sampleRate / timeSize;
                }
            } else if (i >= IndicesFreq[6] && i < IndicesFreq[7]) {
                EnergiaFreq[7] += Math.pow(vetor[i], 2);
                if (MaxFreq[7] < vetor[i]) {
                    MaxFreq[7] = vetor[i];
                    Freq[7] = i * sampleRate / timeSize;
                }
            } else if (i >= IndicesFreq[7] && i <= Math.abs(4000 / aux) + 1) {
                EnergiaFreq[8] += Math.pow(vetor[i], 2);
                if (MaxFreq[8] < vetor[i]) {
                    MaxFreq[8] = vetor[i];
                    Freq[8] = i * sampleRate / timeSize;
                }
            }

        }

        for (i = 0; i < 9; i++) {
            energiaFreqRates[i] = EnergiaFreq[i] / EnergiaFreqTotal;
        }
        resultado.setEnergiaFreqRates(energiaFreqRates);
        resultado.setMaxFreq(MaxFreq);

        return resultado;
    }

    public static void main(String[] argv) {

        //frequencia de amostragem do sinal
        int freamostragem = 8192;
        //tamanho do vetor
        int tam = 8192, i;
        FFT fft = new FFT(tam, freamostragem);

        float increment = (float) (2);
        float aux = 0;

        //sinal original
        float[] Sinal_Original = new float[tam];

        System.out.println("sinal original");
        for (i = 0; i < Sinal_Original.length; i++) {

            Sinal_Original[i] = (float) Math.sin(aux);
            aux += increment;
            //System.out.println(i + " = " + Sinal_Original[i]);
        }

        Resultado resultado = new Resultado();

        resultado = fft.Calcula_fft(fft, Sinal_Original);

        //float EnergiaFreq[]= resultado.getEnergiaFreqRates();
        System.out.println("energiaFreqRates");
        for (i = 0; i < 9; i++) {
            System.out.println(i + " = " + resultado.getEnergiaFreqRates()[i]);
        }

        System.out.println("Faixa de Frequencia");
        for (i = 0; i < 9; i++) {
            System.out.println(i + " = " + resultado.getMaxFreq()[i]);
        }
        /*
                
         for (int i = 0; i < vetor.length; i++) {
         System.out.println(i + "=" + vetor[i]);
         }

         Plot plot = new Plot("Note A Spectrum", 512, 512);
         plot.plot(fft.getSpectrum(), 1, Color.red);
         */
    }
}
