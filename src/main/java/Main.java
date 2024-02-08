import co.andrescol.calculadora.inversion.Inversion;
import co.andrescol.calculadora.objetos.InversionDeserializer;
import co.andrescol.calculadora.resultadoinversion.ResultadoInversion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String path = args[0];
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Inversion.class, new InversionDeserializer())
                .create();

        JsonReader jsonReader = new JsonReader(new FileReader(path));
        Inversion[] inversiones = gson.fromJson(jsonReader, Inversion[].class);
        Inversion mejorInversion = null;
        ResultadoInversion mejorResultado = null;
        for(Inversion inversion : inversiones) {
            ResultadoInversion resultado = inversion.calcularGanancia();
            inversion.calcularEImprimir(resultado);
            if(mejorResultado == null || mejorResultado.getGananciaReal() < resultado.getGananciaReal()) {
                mejorResultado = resultado;
                mejorInversion = inversion;
            }
        }
        Logger log = LogManager.getLogger();
        log.info("""
            =================== MEJOR INVERSION =====================
            {}
            {}
            """, mejorInversion, mejorResultado);
    }
}
