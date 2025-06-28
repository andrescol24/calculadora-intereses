package co.andrescol.calculadora.inversion;

import co.andrescol.calculadora.impuesto.AporteSeguridadSocial;
import co.andrescol.calculadora.resultadoinversion.ResultadoInversion;
import co.andrescol.calculadora.util.ImpuestoSeguridadSocialCalculador;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InversionCompuesta extends InversionEntrante {
    protected double capitalCompuesto;

    public InversionCompuesta(InversionEntrante inversion) {
         super(inversion);
         capitalCompuesto = inversion.getCapital();
    }

    @Override
    public ResultadoInversion calcularInversion() {
        // Convertir el EAR a una tasa nominal compuesta más frecuentemente
        double tasaNominal = Math.pow(1 + ear, duracionDias / 360.0) - 1;

        // Calcular el rendimiento compuesto
        double gananciaAntesImpuestos = capitalCompuesto * tasaNominal;
        double retencion = gananciaAntesImpuestos * this.retencion;

        // Seguridad social
        double gananciaAporteSeguridad = gananciaAntesImpuestos - retencion;
        AporteSeguridadSocial aporte = ImpuestoSeguridadSocialCalculador.calcular(duracionDias, periodicidadPago, gananciaAporteSeguridad);

        ResultadoInversion resultado = new ResultadoInversion();
        resultado.setCapitalInversion(capitalCompuesto);
        resultado.setCapitalInicial(capital);
        resultado.setGananciaAntesImpuestos(gananciaAntesImpuestos);
        resultado.setRetencion(retencion);
        resultado.setComision(comision);
        resultado.setAporteSeguridadSocial(aporte);

        return resultado;
    }
}
