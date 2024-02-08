package co.andrescol.calculadora;

import co.andrescol.calculadora.inversion.InversionCDT;
import co.andrescol.calculadora.resultadoinversion.ResultadoInversion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InversionCalculoTest {

    @Test
    public void testCalculoCdt() {
        InversionCDT cdt = new InversionCDT();
        cdt.setEar(0.127);
        cdt.setInversionInicial(50000000);
        cdt.setPorcentajeRetencion(0.04);
        cdt.setTiempoEnDias(180);

        ResultadoInversion resultado = cdt.calcularGanancia();
        double gananciaEsperada = 2_956_944;
        double diferencia = Math.abs(resultado.getGananciaReal() - gananciaEsperada);
        assertTrue(diferencia < 100, gananciaEsperada + " vs " + resultado.getGananciaReal());
    }
}
