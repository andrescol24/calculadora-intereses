package co.andrescol.calculadora.util;

public interface Impuesto4x1000Calculador {

    static double calcular(double valor) {
        return valor / 1000 * 4;
    }
}
