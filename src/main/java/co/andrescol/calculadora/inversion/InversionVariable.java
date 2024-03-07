package co.andrescol.calculadora.inversion;

import co.andrescol.calculadora.impuesto.AporteSeguridadSocial;
import co.andrescol.calculadora.impuesto.Impuesto4x1000;
import co.andrescol.calculadora.objetos.Variacion;
import co.andrescol.calculadora.resultadoinversion.ResultadoInversion;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class InversionVariable extends Inversion {
    private InversionCDT inversion;
    private Variacion variacion;
    private List<ResultadoInversion> historial;

    @Override
    public ResultadoInversion calcularGanancia() {
        double totalGanancia = 0;
        double totalRetencion = 0;
        this.historial = new LinkedList<>();
        Impuesto4x1000 total4x1000 = new Impuesto4x1000();
        AporteSeguridadSocial totalAportesSeguridad = new AporteSeguridadSocial();
        double capitalFinal = inversion.getInversionInicial();
        InversionCDT inversionActual = inversion;
        for(int i = 0; i < variacion.ciclos(); i++) {
            ResultadoInversion resultado = inversionActual.calcularGanancia();
            totalGanancia += resultado.getGananciaReal();
            capitalFinal += variacion.variacionInversion();
            totalRetencion += resultado.getRetencion();
            total4x1000.sumar(resultado.getImpuesto4x1000());
            totalAportesSeguridad.sumar(resultado.getAporteSeguridadSocial());
            historial.add(resultado);
            inversionActual = variacion.aplicarVariacion(inversionActual, resultado.getGananciaReal());
        }
        return new ResultadoInversion(capitalFinal, totalGanancia, totalRetencion, total4x1000, totalAportesSeguridad);
    }

    @Override
    public String toString() {
        String historial = this.historial.stream().map(ResultadoInversion::toString).collect(Collectors.joining("\n"));
        return inversion.toString() + "\n" + variacion.toString() + "\n Historial:\n" + historial;
    }
}
