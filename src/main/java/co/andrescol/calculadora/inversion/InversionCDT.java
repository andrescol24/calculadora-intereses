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
    private boolean aplica4x1000;

    public InversionCDT(double nuevaInversion, double nuevoEA, InversionCDT inversion) {
        setNombre(inversion.getNombre());
        inversionInicial = nuevaInversion;
        ear = nuevoEA;
        this.tiempoEnDias = inversion.getTiempoEnDias();
        this.porcentajeRetencion = inversion.getPorcentajeRetencion();
        this.aplica4x1000 = inversion.aplica4x1000;
    }

    public ResultadoInversion calcularGanancia() {
        // Convertir el EAR a una tasa nominal compuesta más frecuentemente
        double tasaNominal = Math.pow(1 + ear, tiempoEnDias / 360.0) - 1;

        // Calcular el rendimiento compuesto
        double ganado = inversionInicial * tasaNominal;
        double retencion = ganado * porcentajeRetencion;
        double gananciaReal = ganado - retencion;
        double impuesto4x1000 = 0;
        if(aplica4x1000) {
            double impuestoValorInicial = inversionInicial * 4 / 1000;
            double impuestoGanancia = (inversionInicial + gananciaReal) * 4 / 1000;
            impuesto4x1000 = impuestoValorInicial + impuestoGanancia;
            gananciaReal = gananciaReal - impuesto4x1000;
        }
        return new ResultadoInversion(inversionInicial, retencion, gananciaReal, impuesto4x1000);
    }
    @Override
    public String toString() {
        return "Inversion: %s, EA: %f %%, Inversion inicial: %s, Tiempo: %d dias, Retencion: %f "
                .formatted(getNombre(), ear * 100, Util.toDinero(inversionInicial), tiempoEnDias, porcentajeRetencion);
    }
}
