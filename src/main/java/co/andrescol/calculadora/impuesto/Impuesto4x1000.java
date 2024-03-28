package co.andrescol.calculadora.impuesto;

import co.andrescol.calculadora.util.Util;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Impuesto4x1000 {
    private double impuestoMovimientoEntrada;
    private double impuestoMovimientoSalida;
    public Impuesto4x1000 () {}
    public Impuesto4x1000 (double capital, double ganancia) {
        this.impuestoMovimientoEntrada = calcular(capital);
        this.impuestoMovimientoSalida = calcular(capital + ganancia);
    }
    public double getTotal() {
        return impuestoMovimientoEntrada + impuestoMovimientoSalida;
    }
    public void sumar(Impuesto4x1000 impuesto4x1000) {
        this.impuestoMovimientoEntrada += impuesto4x1000.getImpuestoMovimientoEntrada();
        this.impuestoMovimientoSalida += impuesto4x1000.getImpuestoMovimientoSalida();
    }
    public static double calcular(double valor) {
        return  valor * 4 / 1000;
    }

    @Override
    public String toString() {
        return "%s (%s + %s)".formatted(
                Util.toDinero(getTotal()),
                Util.toDinero(impuestoMovimientoEntrada),
                Util.toDinero(impuestoMovimientoSalida));
    }
}
