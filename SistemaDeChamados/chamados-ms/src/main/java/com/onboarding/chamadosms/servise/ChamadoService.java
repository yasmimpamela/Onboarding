package com.onboarding.chamadosms.servise; // Verifique se o nome do pacote está "service"

import com.onboarding.chamadosms.dto.ChamadoResponseDTO;
import com.onboarding.chamadosms.dto.UsuarioDTO;
import com.onboarding.chamadosms.model.Chamado;
import com.onboarding.chamadosms.repository.ChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException; // Importante adicionar este import
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String USUARIO_API_URL = "http://localhost:8081/api/usuarios/";

    public ChamadoResponseDTO buscarChamadoCompleto(Long chamadoId) {
        Chamado chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));


        UsuarioDTO solicitante = null;
        try {
            solicitante = restTemplate.getForObject(USUARIO_API_URL + chamado.getSolicitanteId(), UsuarioDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            System.err.println("AVISO: Solicitante com ID " + chamado.getSolicitanteId() + " não foi encontrado no serviço de usuários.");
        }


        UsuarioDTO responsavel = null;
        if (chamado.getResponsavelId() != null) {
            try {
                responsavel = restTemplate.getForObject(USUARIO_API_URL + chamado.getResponsavelId(), UsuarioDTO.class);
            } catch (HttpClientErrorException.NotFound e) {
                System.err.println("AVISO: Responsável com ID " + chamado.getResponsavelId() + " não foi encontrado no serviço de usuários.");
            }
        }

        ChamadoResponseDTO response = new ChamadoResponseDTO();
        response.setChamado(chamado);
        response.setSolicitante(solicitante);
        response.setResponsavel(responsavel);
        return response;
    }

    public List<ChamadoResponseDTO> listarChamadosCompletos() {
        List<Chamado> todosChamados = chamadoRepository.findAll();
        List<ChamadoResponseDTO> respostaCompleta = new ArrayList<>();

        for (Chamado chamado : todosChamados) {

            ChamadoResponseDTO responseDTO = buscarChamadoCompleto(chamado.getId());
            respostaCompleta.add(responseDTO);
        }

        return respostaCompleta;
    }
}