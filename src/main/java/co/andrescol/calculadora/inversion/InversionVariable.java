package co.andrescol.calculadora.inversion;

import co.andrescol.calculadora.objetos.ResultadoInversion;
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
        double capitalFinal = inversion.getInversionInicial();
        InversionCDT inversionActual = inversion;
        for(int i = 0; i < variacion.ciclos(); i++) {
            ResultadoInversion resultado = inversionActual.calcularGanancia();
            totalGanancia += resultado.gananciaReal();
            capitalFinal += variacion.variacionInversion();
            totalRetencion += resultado.retencion();
            inversionActual = variacion.aplicarVariacion(inversionActual, resultado.gananciaReal());
        }
        return new ResultadoInversion(capitalFinal, totalRetencion, totalGanancia);
    }

    @Override
    public String toString() {
        return inversion.toString() + "\n" + variacion.toString();
    }
}
