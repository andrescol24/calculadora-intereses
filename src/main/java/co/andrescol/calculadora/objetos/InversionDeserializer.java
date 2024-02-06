package co.andrescol.calculadora.objetos;

import co.andrescol.calculadora.inversion.Inversion;
import co.andrescol.calculadora.inversion.InversionCDT;
import co.andrescol.calculadora.inversion.InversionCombinada;
import co.andrescol.calculadora.inversion.InversionVariable;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class InversionDeserializer implements JsonDeserializer<Inversion> {
    @Override
    public Inversion deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        TipoInversion tipo = TipoInversion.valueOf(json.getAsJsonObject().get("tipo").getAsString());

        return switch (tipo) {
            case NORMAL -> context.deserialize(json, InversionCDT.class);
            case COMBINADA -> context.deserialize(json, InversionCombinada.class);
            case VARIABLE -> context.deserialize(json, InversionVariable.class);
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }
}
