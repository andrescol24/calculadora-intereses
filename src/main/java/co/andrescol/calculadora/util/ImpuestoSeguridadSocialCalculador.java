package co.andrescol.calculadora.util;

import co.andrescol.calculadora.impuesto.AporteSeguridadSocial;
import co.andrescol.calculadora.inversion.InversionCDT;
import co.andrescol.calculadora.objetos.PeriodicidadPago;

public interface ImpuestoSeguridadSocialCalculador {
    double SALARIO_MINIMO = 1_423_500;
    double PORCENTAJE_BASE_COTIZACION = 0.4;
    double PORCENTAJE_SALUD = 0.125;
    double PORCENTAJE_PENSION = 0.16;

    static AporteSeguridadSocial calcular(int tiempoDias, PeriodicidadPago periodicidadPago, double ganancia) {
        int diasPorPeriodo = getNumeroDiasPorPeriodo(tiempoDias, periodicidadPago);

        int numeroPeriodos = tiempoDias / diasPorPeriodo;
        if (numeroPeriodos == 0) numeroPeriodos = 1;

        double ingresoPorPeriodo = ganancia / numeroPeriodos;

        if (ingresoPorPeriodo <= SALARIO_MINIMO) {
            return new AporteSeguridadSocial();
        }

        // Se debe cotizar: base = 40% del ingreso mensual
        double baseCotizacion = ingresoPorPeriodo * PORCENTAJE_BASE_COTIZACION;
        double aporteSalud = baseCotizacion * PORCENTAJE_SALUD * numeroPeriodos;
        double aportePension = baseCotizacion * PORCENTAJE_PENSION * numeroPeriodos;
        return new AporteSeguridadSocial(baseCotizacion, aporteSalud, aportePension);
    }

    static int getNumeroDiasPorPeriodo(int tiempoDias, PeriodicidadPago periodicidadPago) {
        return switch (periodicidadPago) {
            case MENSUAL -> 30;
            case TRIMESTRAL -> 90;
            case AL_FINAL -> tiempoDias;
        };
    }
}
