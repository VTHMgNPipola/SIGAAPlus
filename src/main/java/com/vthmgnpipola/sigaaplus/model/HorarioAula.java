package com.vthmgnpipola.sigaaplus.model;

import lombok.Data;

@Data
public class HorarioAula {
    private Dia dia;
    private Turno turno;
    private int aula;

    public enum Dia {
        DOMINGO, SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA, SABADO
    }

    public enum Turno {
        MATUTINO, VESPERTINO, NOTURNO
    }
}
