package co.andrescol.calculadora.objetos;

import co.andrescol.calculadora.util.Util;

public record ResultadoInversion (double capitalFinal, double retencion, double gananciaReal){
    private static double redondear(double valor) {
        return Math.round(valor * 100) / 100.0;
    }
    @Override
    public String toString() {
        return """
               Capital final: %s
               Ganancia Total: %s
               Retencion: %s
               """.formatted(Util.toDinero(capitalFinal), Util.toDinero(gananciaReal), Util.toDinero(retencion));
    }
}
