package com.onboarding.chamadosms.dto;

import com.onboarding.chamadosms.model.Chamado;
import lombok.Data;

@Data
public class ChamadoResponseDTO {
    private Chamado chamado;
    private UsuarioDTO solicitante;
    private UsuarioDTO responsavel;
}