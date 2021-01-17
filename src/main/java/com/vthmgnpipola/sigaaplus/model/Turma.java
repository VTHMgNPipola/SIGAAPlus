package com.vthmgnpipola.sigaaplus.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Turma {
    private TurmaId turmaId;
    private String codigo;
    private String nome;
    private String local;
    private List<HorarioAula> aulas;
}
