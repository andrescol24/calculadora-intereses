package co.andrescol.calculadora.inversion;

import co.andrescol.calculadora.impuesto.AporteSeguridadSocial;
import co.andrescol.calculadora.impuesto.Impuesto4x1000;
import co.andrescol.calculadora.resultadoinversion.ResultadoInversion;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Setter
@Getter
public class InversionCombinada extends Inversion {
    private List<Inversion> inversiones;
    private boolean terminanDiferenteMes;

    @Override
    public ResultadoInversion calcularGanancia() {
        double capital = 0;
        double retencion = 0;
        double ganancia = 0;
        Impuesto4x1000 total4x1000 = new Impuesto4x1000();
        AporteSeguridadSocial totalAportesSeguridad = new AporteSeguridadSocial();
        for (Inversion inversion : inversiones) {
            ResultadoInversion resultado = inversion.calcularGanancia();
            capital += resultado.getCapitalInicial();
            retencion += resultado.getRetencion();
            ganancia += resultado.getGananciaReal();
            total4x1000.sumar(resultado.getImpuesto4x1000());
            totalAportesSeguridad.sumar(resultado.getAporteSeguridadSocial());
        }
        if(terminanDiferenteMes) {
            return new ResultadoInversion(capital, ganancia, retencion, total4x1000, totalAportesSeguridad);
        } else {
            return new ResultadoInversion(capital, ganancia, retencion, total4x1000, totalAportesSeguridad)
                    .aplicarSeguridadSocial();
        }
    }

    public Map<Inversion, ResultadoInversion> calcularResultadoPorInversion() {
        Map<Inversion, ResultadoInversion> map = new HashMap<>();
        for (Inversion inversion : inversiones) {
            map.put(inversion, inversion.calcularGanancia());
        }
        return map;
    }

    @Override
    public String toString() {
        Map<Inversion, ResultadoInversion> resultadoPorInversion = calcularResultadoPorInversion();
        String inversionesString = resultadoPorInversion
                .entrySet()
                .stream()
                .map(entry -> entry.getKey().toString() + "Resulta en ->\n" + entry.getValue().toString()).collect(Collectors.joining("\n"));
        return "Inversion Combinada " + getNombre() + "\n" + inversionesString;
    }
}
