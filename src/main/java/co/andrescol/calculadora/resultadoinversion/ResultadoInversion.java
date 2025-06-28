package co.andrescol.calculadora.resultadoinversion;

import co.andrescol.calculadora.impuesto.AporteSeguridadSocial;
import co.andrescol.calculadora.impuesto.Impuesto4x1000;
import co.andrescol.calculadora.util.Util;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultadoInversion {

    private double capitalInicial;
    private double capitalInversion;
    private Impuesto4x1000 impuesto4x1000;
    private double retencion;
    private double comision;
    private AporteSeguridadSocial aporteSeguridadSocial;
    private double gananciaAntesImpuestos;

    public ResultadoInversion() {
        impuesto4x1000 = new Impuesto4x1000();
        aporteSeguridadSocial = new AporteSeguridadSocial();
    }

    public double calcularGananciaReal() {
        return gananciaAntesImpuestos - comision - retencion - impuesto4x1000.getTotal() - aporteSeguridadSocial.getTotal();
    }
    @Override
    public String toString() {
        return "Cap: %s, Comp: %s, Ant. de Imp.: %s, Ret: %s, 4x1000: %s, Comision: %s, Seg. S: %s, Ganancia: %s".formatted(
                Util.toDinero(capitalInicial),
                Util.toDinero(capitalInversion),
                Util.toDinero(gananciaAntesImpuestos),
                Util.toDinero(retencion),
                impuesto4x1000,
                Util.toDinero(comision),
                aporteSeguridadSocial,
                Util.toDinero(calcularGananciaReal())
        );
    }
}
