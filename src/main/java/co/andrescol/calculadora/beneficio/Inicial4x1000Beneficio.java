package co.andrescol.calculadora.beneficio;

import co.andrescol.calculadora.resultadoinversion.ResultadoInversion;

public record Inicial4x1000Beneficio() implements Beneficio{
    @Override
    public void aplicar(ResultadoInversion resultadoInversion) {
        double beneficio = resultadoInversion.getImpuesto4x1000().getImpuestoMovimientoEntrada();
        resultadoInversion.setGananciaReal(resultadoInversion.getGananciaReal() + beneficio);
        resultadoInversion.getImpuesto4x1000().setImpuestoMovimientoEntrada(0);
    }
}
