package co.andrescol.calculadora.beneficio;

import lombok.Getter;

public enum TipoBeneficio {
    INICIAL_4x1000(new Inicial4x1000Beneficio());

    @Getter
    private final Beneficio beneficio;
    TipoBeneficio(Beneficio beneficio) {
        this.beneficio = beneficio;
    }
}
