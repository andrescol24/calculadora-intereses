package co.andrescol.calculadora.util;

import java.text.NumberFormat;
import java.util.Locale;

public interface Util {
    static String toDinero(double valor) {
        Locale locale = new Locale("es", "CO");
        NumberFormat pesosFormat = NumberFormat.getCurrencyInstance(locale);
        return pesosFormat.format(valor);
    }
}
