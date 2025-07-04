package co.andrescol.calculadora.inversion;

import co.andrescol.calculadora.impuesto.AporteSeguridadSocial;
import co.andrescol.calculadora.impuesto.Impuesto4x1000;
import co.andrescol.calculadora.resultadoinversion.ResultadoInversion;
import co.andrescol.calculadora.util.Impuesto4x1000Calculador;
import co.andrescol.calculadora.util.ImpuestoSeguridadSocialCalculador;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InversionCDT extends InversionEntrante {
    private boolean aplica4x1000Final;

    @Override
    public ResultadoInversion calcularInversion() {
        // Convertir el EAR a una tasa nominal compuesta más frecuentemente
        double tasaNominal = Math.pow(1 + ear, duracionDias / 360.0) - 1;

        // Calcular el rendimiento compuesto
        double gananciaAntesImpuestos = capital * tasaNominal;
        double retencion = gananciaAntesImpuestos * this.retencion;

        // 4x1000
        Impuesto4x1000 impuesto4x1000 = new co.andrescol.calculadora.impuesto.Impuesto4x1000();
        if(aplica4x1000Inicial) {
            impuesto4x1000.setEntrada(Impuesto4x1000Calculador.calcular(capital));
        }
        if(aplica4x1000Final) {
            impuesto4x1000.setSalida(Impuesto4x1000Calculador.calcular(capital + gananciaAntesImpuestos - retencion));
        }

        // Seguridad social
        double gananciaAporteSeguridad = gananciaAntesImpuestos - retencion - impuesto4x1000.getTotal();
        AporteSeguridadSocial aporte = ImpuestoSeguridadSocialCalculador.calcular(duracionDias, periodicidadPago, gananciaAporteSeguridad);

        ResultadoInversion resultado = new ResultadoInversion();
        resultado.setCapitalInicial(capital);
        resultado.setGananciaAntesImpuestos(gananciaAntesImpuestos);
        resultado.setRetencion(retencion);
        resultado.setComision(comision);
        resultado.setImpuesto4x1000(impuesto4x1000);
        resultado.setAporteSeguridadSocial(aporte);

        return resultado;
    }
}
