package co.andrescol.calculadora.resultadoinversion;

import co.andrescol.calculadora.inversion.InversionCDT;
import co.andrescol.calculadora.util.Util;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultadoInversion {
    private static final double RETENCION = 0.04;
    private static final double SALARIO_MINIMO = 1_300_000;
    private static final double IBC = 0.4;
    private static final double APORTE_SALUD = 0.125;
    private static final double APORTE_PENSION = 0.16;

    private final double capitalInicial;
    private double gananciaReal;
    private double impuesto4x1000;
    private final double retencion;
    private double aportesSeguridadSocial;
    private InversionCDT inversion;

    public ResultadoInversion(double capitalInicial, double gananciaReal, double retencion, double impuesto4x1000, double aportesSeguridadSocial) {
        this.capitalInicial = capitalInicial;
        this.gananciaReal = gananciaReal;
        this.retencion = retencion;
        this.impuesto4x1000 = impuesto4x1000;
        this.aportesSeguridadSocial = aportesSeguridadSocial;
    }

    public ResultadoInversion(InversionCDT inversion, double capitalInicial, double gananciaReal, double retencion) {
        this.capitalInicial = capitalInicial;
        this.gananciaReal = gananciaReal;
        this.retencion = retencion;
        this.inversion = inversion;
    }

    public ResultadoInversion aplicar4x1000() {
        double impuestoValorInicial = inversion.isAplica4x1000() ? this.capitalInicial * 4 / 1000 : 0;
        double impuestoGanancia = inversion.isAplica4x1000() ? (this.capitalInicial + this.gananciaReal) * 4 / 1000 : 0;
        this.impuesto4x1000 = impuestoValorInicial + impuestoGanancia;
        this.gananciaReal = this.gananciaReal - impuesto4x1000;
        return this;
    }
    public ResultadoInversion aplicarBeneficios() {
        if(inversion.getBeneficios() != null) {
            inversion.getBeneficios().forEach(beneficio -> beneficio.getBeneficio().aplicar(this));
        }
        return this;
    }

    public ResultadoInversion aplicarSeguridadSocial() {
        double gananciaIbc = this.gananciaReal * IBC;
        if(this.gananciaReal > SALARIO_MINIMO && gananciaIbc > SALARIO_MINIMO) {
            this.aportesSeguridadSocial = gananciaIbc * APORTE_SALUD;
            this.aportesSeguridadSocial += gananciaIbc * APORTE_PENSION;
            this.gananciaReal -= this.aportesSeguridadSocial;
        } else {
            this.aportesSeguridadSocial = 0;
        }
        return this;
    }


    @Override
    public String toString() {
        return """
                Capital: %s
                Retencion: %s
                4x1000: %s
                Seguridad social: %s
                Ganancia Total: %s
                """.formatted(
                    Util.toDinero(capitalInicial),
                    Util.toDinero(retencion),
                    Util.toDinero(impuesto4x1000),
                    Util.toDinero(aportesSeguridadSocial),
                    Util.toDinero(gananciaReal));
    }
}
