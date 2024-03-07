package co.andrescol.calculadora.resultadoinversion;

import co.andrescol.calculadora.impuesto.AporteSeguridadSocial;
import co.andrescol.calculadora.impuesto.Impuesto4x1000;
import co.andrescol.calculadora.inversion.InversionCDT;
import co.andrescol.calculadora.util.Util;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultadoInversion {

    private final double capitalInicial;
    private double gananciaReal;
    private final Impuesto4x1000 impuesto4x1000;
    private final double retencion;
    private AporteSeguridadSocial aporteSeguridadSocial;
    private InversionCDT inversion;

    public ResultadoInversion(double capitalInicial, double gananciaReal, double retencion, Impuesto4x1000 impuesto4x1000, AporteSeguridadSocial aportesSeguridadSocial) {
        this.capitalInicial = capitalInicial;
        this.gananciaReal = gananciaReal;
        this.retencion = retencion;
        this.impuesto4x1000 = impuesto4x1000;
        this.aporteSeguridadSocial = aportesSeguridadSocial;
    }

    public ResultadoInversion(InversionCDT inversion, double capitalInicial, double gananciaReal, double retencion) {
        this.capitalInicial = capitalInicial;
        this.gananciaReal = gananciaReal;
        this.retencion = retencion;
        this.inversion = inversion;
        this.impuesto4x1000 = new Impuesto4x1000();
        this.aporteSeguridadSocial = new AporteSeguridadSocial();
    }

    public ResultadoInversion aplicar4x1000() {
        double impuestoValorInicial = 0;
        double impuestoGanancia = 0;
        if (inversion.isAplica4x1000()) {
            impuestoValorInicial = this.capitalInicial * 4 / 1000;
            impuestoGanancia = (this.capitalInicial + this.gananciaReal) * 4 / 1000;
        }
        this.impuesto4x1000.setImpuestoMovimientoEntrada(impuestoValorInicial);
        this.impuesto4x1000.setImpuestoMovimientoSalida(impuestoGanancia);
        this.gananciaReal = this.gananciaReal - this.impuesto4x1000.getTotal();
        return this;
    }

    public ResultadoInversion aplicarBeneficios() {
        if (inversion.getBeneficios() != null) {
            inversion.getBeneficios().forEach(beneficio -> beneficio.getBeneficio().aplicar(this));
        }
        return this;
    }

    public ResultadoInversion aplicarSeguridadSocial() {
        aporteSeguridadSocial.calcularAporte(gananciaReal);
        this.gananciaReal = this.gananciaReal - aporteSeguridadSocial.getAporteSalud() - aporteSeguridadSocial.getAportePension();
        return this;
    }


    @Override
    public String toString() {
        return "Capital: %s, Retencion: %s, 4x1000: %s, Seguridad social: %s, Ganancia Total: %s".formatted(
                Util.toDinero(capitalInicial),
                Util.toDinero(retencion),
                impuesto4x1000,
                aporteSeguridadSocial,
                Util.toDinero(gananciaReal));
    }
}
