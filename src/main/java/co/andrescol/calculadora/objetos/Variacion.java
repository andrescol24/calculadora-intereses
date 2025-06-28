package co.andrescol.calculadora.objetos;

import co.andrescol.calculadora.inversion.InversionCompuesta;
import co.andrescol.calculadora.util.Util;

public record Variacion(double variacionInversion, double variacionEA, double topeVariacion, int ciclos) {
    public InversionCompuesta aplicarVariacion(InversionCompuesta inversion, double ganancia) {
        double nuevoCapital = inversion.getCapital() + variacionInversion;
        double nuevoCapitalInversion = inversion.getCapitalCompuesto() + variacionInversion + ganancia;

        double nuevoEA = aplicarVariacionEA(inversion.getEar());
        InversionCompuesta nuevaInversion = new InversionCompuesta(inversion);
        nuevaInversion.setCapital(nuevoCapital);
        nuevaInversion.setCapitalCompuesto(nuevoCapitalInversion);
        nuevaInversion.setEar(nuevoEA);
        return nuevaInversion;
    }

    private double aplicarVariacionEA(double ear) {
        double nuevoEar = ear + variacionEA;
        // variacion negativa, si el tope es mayor, no se aplica mas
        if(variacionEA < 0 && topeVariacion > nuevoEar) {
            return ear;
        }
        // variacion positiva, si el tope es menor, no se aplica mas
        if(variacionEA > 0 && topeVariacion < nuevoEar) {
            return ear;
        }
        return nuevoEar;
    }
    @Override
    public String toString() {
        return """
               Variacion de: %f%% EA
               Durante %d ciclos
               Incremento de %s durante cada ciclo
               Tope de %f%% EA
               """.formatted(variacionEA * 100, ciclos, Util.toDinero(variacionInversion), topeVariacion * 100);
    }
}
