package co.andrescol.calculadora.impuesto;

import co.andrescol.calculadora.util.Util;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AporteSeguridadSocial {

    private double ibc;
    private double aporteSalud;
    private double aportePension;

    public double getTotal() {
        return aporteSalud + aportePension;
    }

    public void sumar(AporteSeguridadSocial aporteSeguridadSocial) {
        this.aporteSalud += aporteSeguridadSocial.getAporteSalud();
        this.aportePension += aporteSeguridadSocial.getAportePension();
    }

    @Override
    public String toString() {
        return "(IBC: %s, Salud: %s, Pension: %s, TOTAL: %s)".formatted(
                Util.toDinero(ibc),
                Util.toDinero(aporteSalud),
                Util.toDinero(aportePension),
                Util.toDinero(aporteSalud + aportePension)
        );
    }
}
