package co.andrescol.calculadora.impuesto;

import co.andrescol.calculadora.util.Util;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AporteSeguridadSocial {
    private static final double SALARIO_MINIMO = 1_300_000;
    private static final double IBC = 0.4; // Ingreso Base de cotizacion del 40%
    private static final double APORTE_SALUD = 0.125;
    private static final double APORTE_PENSION = 0.16;
    private double gananciaIbc;
    private double aporteSalud;
    private double aportePension;

    public void calcularAporte(double gananciaReal) {
        this.gananciaIbc = gananciaReal * IBC;
        if(gananciaIbc > SALARIO_MINIMO) {
            this.aporteSalud = gananciaIbc * APORTE_SALUD;
            this.aportePension = gananciaIbc * APORTE_PENSION;
        }
    }
    public void sumar(AporteSeguridadSocial aporteSeguridadSocial) {
        this.aporteSalud += aporteSeguridadSocial.getAporteSalud();
        this.aportePension += aporteSeguridadSocial.getAportePension();
    }

    @Override
    public String toString() {
        return "(IBC: %s, Salud: %s, Pension: %s, TOTAL: %s)".formatted(
                Util.toDinero(gananciaIbc),
                Util.toDinero(aporteSalud),
                Util.toDinero(aportePension),
                Util.toDinero(aporteSalud + aportePension)
        );
    }
}
