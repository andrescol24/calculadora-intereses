package co.andrescol.calculadora;

import co.andrescol.calculadora.inversion.Inversion;
import co.andrescol.calculadora.objetos.InversionDeserializer;
import co.andrescol.calculadora.resultadoinversion.ResultadoInversion;
import co.andrescol.calculadora.util.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String path = args[0];
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Inversion.class, new InversionDeserializer())
                .create();

        JsonReader jsonReader = new JsonReader(new FileReader(path));
        Inversion[] inversiones = gson.fromJson(jsonReader, Inversion[].class);
        List<Map.Entry<Inversion, ResultadoInversion>> resultados = new LinkedList<>();
        for(Inversion inversion : inversiones) {
            ResultadoInversion resultado = inversion.calcularInversion();
            inversion.imprimir(resultado);
            resultados.add(Map.entry(inversion, resultado));
        }
        Logger log = LogManager.getLogger();
        log.info("======================== RESUMEN ==================================");
        resultados.forEach(x -> log.info("{} ===> capital: {} , interes: {}, total: {}",
                x.getKey().getNombre(),
                Util.toDinero(x.getValue().getCapitalInicial()),
                Util.toDinero(x.getValue().calcularGananciaReal()),
                Util.toDinero(x.getValue().getCapitalInicial() + x.getValue().calcularGananciaReal())));
    }
}
