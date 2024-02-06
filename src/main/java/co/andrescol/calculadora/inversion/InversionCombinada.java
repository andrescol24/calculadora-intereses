package co.andrescol.calculadora.inversion;

import co.andrescol.calculadora.objetos.ResultadoInversion;
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
        double capitalFinal = 0;
        double retencion = 0;
        double ganancia = 0;
        for (Inversion inversion : inversiones) {
            ResultadoInversion resultado = inversion.calcularGanancia();
            capitalFinal += resultado.capitalFinal();
            retencion += resultado.retencion();
            ganancia += resultado.gananciaReal();
        }
        return new ResultadoInversion(capitalFinal, retencion, ganancia);
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
                .map(entry -> entry.getKey().toString() + ">>>>> genero >>>>>\n" + entry.getValue().toString()).collect(Collectors.joining("\n"));
        return "Inversion Combinada " + getNombre() + "\n" + inversionesString;
    }
}
