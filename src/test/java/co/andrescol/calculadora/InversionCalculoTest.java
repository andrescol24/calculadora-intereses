package co.andrescol.calculadora;

import co.andrescol.calculadora.inversion.InversionCDT;
import co.andrescol.calculadora.objetos.PeriodicidadPago;
import co.andrescol.calculadora.resultadoinversion.ResultadoInversion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InversionCalculoTest {

    @Test
    public void testCalculoCdt() {
        InversionCDT cdt = new InversionCDT();
        cdt.setEar(0.127);
        cdt.setCapital(50000000);
        cdt.setRetencion(0.04);
        cdt.setDuracionDias(180);
        cdt.setPeriodicidadPago(PeriodicidadPago.AL_FINAL);

        ResultadoInversion resultado = cdt.calcularInversion();
        double gananciaEsperada = 2_619_833;
        double diferencia = Math.abs(resultado.calcularGananciaReal() - gananciaEsperada);
        assertTrue(diferencia < 100, gananciaEsperada + " vs " + resultado.calcularGananciaReal());
    }
}
