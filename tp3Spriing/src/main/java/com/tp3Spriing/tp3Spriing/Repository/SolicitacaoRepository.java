package com.tp3Spriing.tp3Spriing.Repository;

import com.tp3Spriing.tp3Spriing.Model.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {
}