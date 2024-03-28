package co.andrescol.calculadora.inversion;

import co.andrescol.calculadora.impuesto.Impuesto4x1000;
import co.andrescol.calculadora.resultadoinversion.ResultadoInversion;
import co.andrescol.calculadora.util.Util;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InversionCDT extends Inversion {
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
        this.aplica4x1000 = inversion.isAplica4x1000();
    }

    @Override
    public ResultadoInversion calcularGanancia() {
        // Convertir el EAR a una tasa nominal compuesta más frecuentemente
        double tasaNominal = Math.pow(1 + ear, tiempoEnDias / 360.0) - 1;

        // Calcular el rendimiento compuesto
        double ganado = inversionInicial * tasaNominal;
        double retencion = ganado * porcentajeRetencion;
        double gananciaReal = ganado - retencion;
        Impuesto4x1000 impuesto4x1000 = aplica4x1000 ? new Impuesto4x1000(inversionInicial, gananciaReal) : new Impuesto4x1000();
        ResultadoInversion resultado = new ResultadoInversion(inversionInicial, gananciaReal, retencion, impuesto4x1000);
        return resultado.aplicar4x1000().aplicarSeguridadSocial();
    }
    @Override
    public String toString() {
        return "Inversion: %s, EA: %f%%, Inversion inicial: %s, Tiempo: %d dias, Retencion: %.2f%% "
                .formatted(getNombre(), ear * 100, Util.toDinero(inversionInicial), tiempoEnDias, porcentajeRetencion * 100);
    }
}
