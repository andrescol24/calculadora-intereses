package co.andrescol.calculadora.inversion;

import co.andrescol.calculadora.resultadoinversion.CalculoInversion;
import co.andrescol.calculadora.resultadoinversion.ResultadoInversion;
import co.andrescol.calculadora.objetos.Variacion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InversionVariable extends Inversion {
    private InversionCDT inversion;
    private Variacion variacion;

    @Override
    public ResultadoInversion calcularGanancia() {
        double totalGanancia = 0;
        double totalRetencion = 0;
        double total4x1000 = 0;
        double totalAportesSeguridad = 0;
        double capitalFinal = inversion.getInversionInicial();
        InversionCDT inversionActual = inversion;
        for(int i = 0; i < variacion.ciclos(); i++) {
            ResultadoInversion resultado = inversionActual.calcularGanancia();
            totalGanancia += resultado.getGananciaReal();
            capitalFinal += variacion.variacionInversion();
            totalRetencion += resultado.getRetencion();
            total4x1000 += resultado.getImpuesto4x1000();
            totalAportesSeguridad += resultado.getAportesSeguridadSocial();
            inversionActual = variacion.aplicarVariacion(inversionActual, resultado.getGananciaReal());
        }
        return new ResultadoInversion(capitalFinal, totalGanancia, totalRetencion, total4x1000, totalAportesSeguridad);
    }

    @Override
    public String toString() {
        return inversion.toString() + "\n" + variacion.toString();
    }
}
