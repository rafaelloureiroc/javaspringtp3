package com.tp3Spriing.tp3Spriing.Controller;

import com.tp3Spriing.tp3Spriing.Model.Solicitacao;
import com.tp3Spriing.tp3Spriing.Service.SolicitacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/solicitacoes")
public class SolicitacaoController {

    @Autowired
    private SolicitacaoService solicitacaoService;

    @PostMapping("/criar")
    public ResponseEntity<Solicitacao> criarSolicitacao(@RequestBody Solicitacao solicitacao) {
        Solicitacao solicitacaoSalva = solicitacaoService.createSolicitacao(solicitacao);
        return new ResponseEntity<>(solicitacaoSalva, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Solicitacao>> listarSolicitacoes() {
        List<Solicitacao> solicitacoes = solicitacaoService.getAllSolicitacoes();
        return new ResponseEntity<>(solicitacoes, HttpStatus.OK);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Solicitacao> obterSolicitacao(@PathVariable Long id) {
        Optional<Solicitacao> solicitacao = solicitacaoService.getSolicitacaoById(id);
        return solicitacao.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Solicitacao> atualizarSolicitacao(@PathVariable Long id, @RequestBody Solicitacao solicitacao) {
        Solicitacao solicitacaoAtualizada = solicitacaoService.updateSolicitacao(id, solicitacao);
        return ResponseEntity.ok(solicitacaoAtualizada);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarSolicitacao(@PathVariable Long id) {
        solicitacaoService.deleteSolicitacao(id);
        return ResponseEntity.noContent().build();
    }
}