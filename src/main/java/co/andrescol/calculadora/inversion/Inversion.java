package co.andrescol.calculadora.inversion;

import co.andrescol.calculadora.objetos.ResultadoInversion;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

@Getter
@Setter
public abstract class Inversion {
    private String nombre;
    private String tipo;

    public abstract ResultadoInversion calcularGanancia();
    public void calcularEImprimir() {
        ResultadoInversion resultado = calcularGanancia();
        calcularEImprimir(resultado);
    }

    public void calcularEImprimir(ResultadoInversion resultado) {
        Logger log = LogManager.getLogger();
        log.info("");
        log.info("""
                ====================================== {} ===========================================
                {}
                >>>>> Generara >>>>
                {}
                """, nombre, toString(), resultado.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, tipo);
    }
}
