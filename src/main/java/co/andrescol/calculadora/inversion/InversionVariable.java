package co.andrescol.calculadora.inversion;

import co.andrescol.calculadora.impuesto.Impuesto4x1000;
import co.andrescol.calculadora.objetos.Variacion;
import co.andrescol.calculadora.resultadoinversion.ResultadoInversion;
import co.andrescol.calculadora.util.Impuesto4x1000Calculador;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class InversionVariable extends Inversion {
    private InversionEntrante inversion;
    private Variacion variacion;
    private List<ResultadoInversion> historial;
    private boolean aplica4x1000Final;
    private boolean aplica4x1000Inicial;
    private boolean inicioInmediato;

    @Override
    public ResultadoInversion calcularInversion() {
        historial = new LinkedList<>();
        ResultadoInversion resultadoFinal = new ResultadoInversion();
        iterarCiclo(resultadoFinal);
        aplicar4x1000Final(resultadoFinal);
        return resultadoFinal;
    }

    private void iterarCiclo(ResultadoInversion resultadoFinal) {
        InversionCompuesta inversionActual = obtenerInversionActual();
        ResultadoInversion resultadoActual = new ResultadoInversion();
        for (int i = 0;  i < variacion.ciclos(); i++) {
            resultadoActual = inversionActual.calcularInversion();
            actualizar4x1000(i, resultadoActual);
            historial.add(resultadoActual);
            actualizarResultadoFinal(resultadoFinal, resultadoActual);
            inversionActual =  variacion.aplicarVariacion(inversionActual, resultadoActual.calcularGananciaReal());
        }
        resultadoFinal.setCapitalInversion(resultadoFinal.getCapitalInversion() + resultadoActual.getGananciaAntesImpuestos() - resultadoActual.getRetencion());
    }

    private InversionCompuesta obtenerInversionActual() {
        if(inicioInmediato) {
            InversionEntrante entranteModificada = inversion.clone();
            entranteModificada.setCapital(inversion.capital + variacion.variacionInversion());
            return new InversionCompuesta(entranteModificada);
        } else {
            return new InversionCompuesta(inversion);
        }
    }

    private void actualizar4x1000(int ciclo, ResultadoInversion actual) {
        if(ciclo == 0 && aplica4x1000Inicial) {
            Impuesto4x1000 impuesto = new Impuesto4x1000(Impuesto4x1000Calculador.calcular(inversion.capital), 0);
            actual.setImpuesto4x1000(impuesto);
        } else if(inversion.aplica4x1000Inicial){
            Impuesto4x1000 impuesto = new Impuesto4x1000(Impuesto4x1000Calculador.calcular(variacion.variacionInversion()), 0);
            actual.setImpuesto4x1000(impuesto);
        }
    }

    private void actualizarResultadoFinal(ResultadoInversion resultadoFinal, ResultadoInversion actual) {
        resultadoFinal.setCapitalInicial(actual.getCapitalInicial());
        resultadoFinal.setCapitalInversion(actual.getCapitalInversion());
        resultadoFinal.setComision(resultadoFinal.getComision() + actual.getComision());
        resultadoFinal.setRetencion(resultadoFinal.getRetencion() + actual.getRetencion());
        resultadoFinal.getImpuesto4x1000().sumar(actual.getImpuesto4x1000());
        resultadoFinal.getAporteSeguridadSocial().sumar(actual.getAporteSeguridadSocial());
        resultadoFinal.setGananciaAntesImpuestos(resultadoFinal.getGananciaAntesImpuestos() + actual.getGananciaAntesImpuestos());
    }

    private void aplicar4x1000Final(ResultadoInversion resultadoFinal) {
        if (aplica4x1000Final) {
            double retiro = resultadoFinal.getGananciaAntesImpuestos() - resultadoFinal.getRetencion();
            double impuesto = Impuesto4x1000Calculador.calcular(resultadoFinal.getCapitalInicial() + retiro);
            resultadoFinal.getImpuesto4x1000().sumar(new Impuesto4x1000(0, impuesto));
        }
    }

    @Override
    public String toString() {
        String historial = this.historial.stream().map(ResultadoInversion::toString).collect(Collectors.joining("\n"));
        return inversion.toString() + "\n" + variacion.toString() + "\n Historial:\n" + historial;
    }
}
