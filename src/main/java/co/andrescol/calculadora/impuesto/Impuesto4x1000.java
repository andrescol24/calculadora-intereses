package co.andrescol.calculadora.impuesto;

import co.andrescol.calculadora.util.Util;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Impuesto4x1000 {
    private double entrada;
    private double salida;
    public double getTotal() {
        return entrada + salida;
    }
    public void sumar(Impuesto4x1000 impuesto4x1000) {
        this.entrada += impuesto4x1000.getEntrada();
        this.salida += impuesto4x1000.getSalida();
    }

    @Override
    public String toString() {
        return "%s (%s + %s)".formatted(
                Util.toDinero(getTotal()),
                Util.toDinero(entrada),
                Util.toDinero(salida));
    }
}
