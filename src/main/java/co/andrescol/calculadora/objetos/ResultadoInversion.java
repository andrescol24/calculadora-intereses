package co.andrescol.calculadora.objetos;

import co.andrescol.calculadora.util.Util;

public record ResultadoInversion(double capitalFinal, double retencion, double gananciaReal, double impuesto4x1000) {
    private static double redondear(double valor) {
        return Math.round(valor * 100) / 100.0;
    }

    @Override
    public String toString() {
        return """
                Capital final: %s
                Retencion: %s
                4 x 1000: %s
                Ganancia Total: %s
                """.formatted(
                    Util.toDinero(capitalFinal),
                    Util.toDinero(retencion),
                    Util.toDinero(impuesto4x1000),
                    Util.toDinero(gananciaReal));
    }
}
