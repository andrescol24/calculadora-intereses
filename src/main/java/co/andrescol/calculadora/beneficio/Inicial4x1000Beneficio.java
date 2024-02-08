package co.andrescol.calculadora.beneficio;

import co.andrescol.calculadora.resultadoinversion.ResultadoInversion;

public record Inicial4x1000Beneficio() implements Beneficio{
    @Override
    public void aplicar(ResultadoInversion resultadoInversion) {
        double total4x1000 = resultadoInversion.getImpuesto4x1000();
        resultadoInversion.setGananciaReal(resultadoInversion.getGananciaReal() + total4x1000);

        double final4x1000 = (resultadoInversion.getCapitalInicial() + resultadoInversion.getGananciaReal()) * 4 / 1000;
        resultadoInversion.setImpuesto4x1000(final4x1000);
        resultadoInversion.setGananciaReal(resultadoInversion.getGananciaReal() - final4x1000);
    }
}
