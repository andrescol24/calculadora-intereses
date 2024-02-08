package co.andrescol.calculadora.inversion;

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

    @Override
    public ResultadoInversion calcularGanancia() {
        double capital = 0;
        double retencion = 0;
        double ganancia = 0;
        double impuesto4x1000 = 0;
        double aportesSeguridad = 0;
        for (Inversion inversion : inversiones) {
            ResultadoInversion resultado = inversion.calcularGanancia();
            capital += resultado.getCapitalInicial();
            retencion += resultado.getRetencion();
            ganancia += resultado.getGananciaReal();
            impuesto4x1000 += resultado.getImpuesto4x1000();
            aportesSeguridad += resultado.getAportesSeguridadSocial();
        }
        if(aportesSeguridad != 0) {
            return new ResultadoInversion(capital, ganancia, retencion, impuesto4x1000, aportesSeguridad);
        } else {
            return new ResultadoInversion(capital, ganancia, retencion, impuesto4x1000, 0)
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
