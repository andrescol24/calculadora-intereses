package co.andrescol.calculadora.inversion;

import co.andrescol.calculadora.impuesto.AporteSeguridadSocial;
import co.andrescol.calculadora.impuesto.Impuesto4x1000;
import co.andrescol.calculadora.objetos.Variacion;
import co.andrescol.calculadora.resultadoinversion.ResultadoInversion;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class InversionVariable extends Inversion {
    private InversionCDT inversion;
    private Variacion variacion;
    private List<ResultadoInversion> historial;
    private boolean aplica4x100Final;

    @Override
    public ResultadoInversion calcularGanancia() {
        double totalGanancia = 0;
        double totalRetencion = 0;
        this.historial = new LinkedList<>();
        Impuesto4x1000 total4x1000 = new Impuesto4x1000();
        AporteSeguridadSocial totalAportesSeguridad = new AporteSeguridadSocial();
        InversionCDT inversionActual = inversion;
        ResultadoInversion ultimoResultado = null;
        for (int i = 0; i < variacion.ciclos(); i++) {
            ultimoResultado = inversionActual.calcularGanancia();
            totalGanancia += ultimoResultado.getGananciaReal();
            totalRetencion += ultimoResultado.getRetencion();
            total4x1000.sumar(ultimoResultado.getImpuesto4x1000());
            totalAportesSeguridad.sumar(ultimoResultado.getAporteSeguridadSocial());
            historial.add(ultimoResultado);
            inversionActual = variacion.aplicarVariacion(inversionActual, ultimoResultado.getGananciaReal());
        }
        Objects.requireNonNull(ultimoResultado);
        double capitalFinalConGanancia = ultimoResultado.getCapitalInicial() + ultimoResultado.getGananciaReal();
        ResultadoInversion resultado = new ResultadoInversion(capitalFinalConGanancia, totalGanancia, totalRetencion, total4x1000, totalAportesSeguridad);
        if (aplica4x100Final) {
            Impuesto4x1000 impuesto4x100Final = new Impuesto4x1000();
            impuesto4x100Final.setImpuestoMovimientoSalida(Impuesto4x1000.calcular(capitalFinalConGanancia));
            total4x1000.sumar(impuesto4x100Final);
            return resultado.aplicar4x1000();
        }
        return resultado;
    }

    @Override
    public String toString() {
        String historial = this.historial.stream().map(ResultadoInversion::toString).collect(Collectors.joining("\n"));
        return inversion.toString() + "\n" + variacion.toString() + "\n Historial:\n" + historial;
    }
}
