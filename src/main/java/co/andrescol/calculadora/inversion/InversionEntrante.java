package co.andrescol.calculadora.inversion;

import co.andrescol.calculadora.impuesto.AporteSeguridadSocial;
import co.andrescol.calculadora.objetos.PeriodicidadPago;
import co.andrescol.calculadora.resultadoinversion.ResultadoInversion;
import co.andrescol.calculadora.util.ImpuestoSeguridadSocialCalculador;
import co.andrescol.calculadora.util.Util;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa una inversion que solo se consigna, mas no se saca de la cuenta destino
 */
@Getter
@Setter
public class InversionEntrante extends Inversion implements Cloneable {
    protected double capital;
    protected double ear;
    protected int duracionDias;
    protected double retencion;
    protected boolean aplica4x1000Inicial;
    protected double comision;
    protected PeriodicidadPago periodicidadPago;
    @Override
    public ResultadoInversion calcularInversion() {
        // Convertir el EAR a una tasa nominal compuesta más frecuentemente
        double tasaNominal = Math.pow(1 + ear, duracionDias / 360.0) - 1;

        // Calcular el rendimiento compuesto
        double gananciaAntesImpuestos = capital * tasaNominal;
        double retencion = gananciaAntesImpuestos * this.retencion;

        // Seguridad social
        double gananciaAporteSeguridad = gananciaAntesImpuestos - retencion;
        AporteSeguridadSocial aporte = ImpuestoSeguridadSocialCalculador.calcular(duracionDias, periodicidadPago, gananciaAporteSeguridad);

        ResultadoInversion resultado = new ResultadoInversion();
        resultado.setCapitalInversion(capital);
        resultado.setGananciaAntesImpuestos(gananciaAntesImpuestos);
        resultado.setRetencion(retencion);
        resultado.setComision(comision);
        resultado.setAporteSeguridadSocial(aporte);

        return resultado;
    }

    @Override
    public String toString() {
        return "Inversion: %s, EA: %f%%, Inversion inicial: %s, Tiempo: %d dias, Retencion: %.2f%% "
                .formatted(getNombre(), ear * 100, Util.toDinero(capital), duracionDias, retencion * 100);
    }

    @Override
    public InversionEntrante clone() {
        try {
            return (InversionEntrante) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
