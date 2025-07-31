package com.onboarding.chamadosms.repository;

import com.onboarding.chamadosms.model.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

}