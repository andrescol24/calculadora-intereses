package co.andrescol.calculadora.inversion;

import co.andrescol.calculadora.objetos.ResultadoInversion;
import co.andrescol.calculadora.util.Util;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public final class InversionCDT extends Inversion {
    private double inversionInicial;
    private double ear;
    private int tiempoEnDias;
    private double porcentajeRetencion;

    public InversionCDT(String nombre, double nuevaInversion, double nuevoEA, int tiempoEnDias, double porcentajeRetencion) {
        setNombre(nombre);
        inversionInicial = nuevaInversion;
        ear = nuevoEA;
        this.tiempoEnDias = tiempoEnDias;
        this.porcentajeRetencion = porcentajeRetencion;
    }

    public ResultadoInversion calcularGanancia() {
        // Convertir el EAR a una tasa nominal compuesta más frecuentemente
        double tasaNominal = Math.pow(1 + ear, tiempoEnDias / 360.0) - 1;

        // Calcular el rendimiento compuesto
        double ganado = inversionInicial * tasaNominal;
        double retencion = ganado * porcentajeRetencion;
        return new ResultadoInversion(inversionInicial, retencion, ganado - retencion);
    }
    @Override
    public String toString() {
        return """
                Inversion: %s
                EA: %f %%
                Inversion inicial: %s
                Tiempo: %d dias
                Retencion: %f
                """.formatted(getNombre(), ear * 100, Util.toDinero(inversionInicial), tiempoEnDias, porcentajeRetencion);
    }
}
