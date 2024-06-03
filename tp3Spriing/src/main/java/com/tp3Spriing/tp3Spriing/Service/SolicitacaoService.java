package com.tp3Spriing.tp3Spriing.Service;

import com.tp3Spriing.tp3Spriing.Model.Solicitacao;
import com.tp3Spriing.tp3Spriing.Repository.SolicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitacaoService {

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    public List<Solicitacao> getAllSolicitacoes() {
        return solicitacaoRepository.findAll();
    }

    public Optional<Solicitacao> getSolicitacaoById(Long id) {
        return solicitacaoRepository.findById(id);
    }

    public Solicitacao createSolicitacao(Solicitacao solicitacao) {
        return solicitacaoRepository.save(solicitacao);
    }

    public void deleteSolicitacao(Long id) {
        solicitacaoRepository.deleteById(id);
    }

    public Solicitacao updateSolicitacao(Long id, Solicitacao solicitacaoDetails) {
        Solicitacao solicitacao = solicitacaoRepository.findById(id).orElseThrow();
        solicitacao.setDescricao(solicitacaoDetails.getDescricao());
        return solicitacaoRepository.save(solicitacao);
    }
}