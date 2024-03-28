package co.andrescol.calculadora.resultadoinversion;

import co.andrescol.calculadora.impuesto.AporteSeguridadSocial;
import co.andrescol.calculadora.impuesto.Impuesto4x1000;
import co.andrescol.calculadora.util.Util;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultadoInversion {

    private final double capitalInicial;
    private final Impuesto4x1000 impuesto4x1000;
    private final double retencion;
    private final AporteSeguridadSocial aporteSeguridadSocial;
    private final double gananciaAntesDeBeneficiosEImpuestos;
    private double gananciaReal;

    public ResultadoInversion(double capitalInicial, double gananciaReal, double retencion, Impuesto4x1000 impuesto4x1000, AporteSeguridadSocial aportesSeguridadSocial) {
        this.capitalInicial = capitalInicial;
        this.gananciaReal = gananciaReal;
        this.gananciaAntesDeBeneficiosEImpuestos = gananciaReal;
        this.retencion = retencion;
        this.impuesto4x1000 = impuesto4x1000;
        this.aporteSeguridadSocial = aportesSeguridadSocial;
    }

    public ResultadoInversion(double capitalInicial, double gananciaReal, double retencion, Impuesto4x1000 impuesto4x1000) {
        this(capitalInicial, gananciaReal, retencion, impuesto4x1000, new AporteSeguridadSocial());
    }

    public ResultadoInversion aplicar4x1000() {
        this.gananciaReal = this.gananciaReal - this.impuesto4x1000.getTotal();
        return this;
    }

    public ResultadoInversion aplicarSeguridadSocial() {
        aporteSeguridadSocial.calcularAporte(gananciaReal);
        this.gananciaReal = this.gananciaReal - aporteSeguridadSocial.getAporteSalud() - aporteSeguridadSocial.getAportePension();
        return this;
    }


    @Override
    public String toString() {
        return "Capital: %s, Retencion: %s, 4x1000: %s, Seguridad social: %s, Ganancia inicial: %s, Ganancia final: %s".formatted(
                Util.toDinero(capitalInicial),
                Util.toDinero(retencion),
                impuesto4x1000,
                aporteSeguridadSocial,
                Util.toDinero(gananciaAntesDeBeneficiosEImpuestos),
                Util.toDinero(gananciaReal));
    }
}
