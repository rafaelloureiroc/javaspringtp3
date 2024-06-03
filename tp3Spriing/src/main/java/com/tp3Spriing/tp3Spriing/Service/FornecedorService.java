package com.tp3Spriing.tp3Spriing.Service;

import com.tp3Spriing.tp3Spriing.Model.Fornecedor;
import com.tp3Spriing.tp3Spriing.Repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public List<Fornecedor> getAllFornecedores() {
        return fornecedorRepository.findAll();
    }

    public Optional<Fornecedor> getFornecedorById(Long id) {
        return fornecedorRepository.findById(id);
    }

    public Fornecedor createFornecedor(Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    public void deleteFornecedor(Long id) {
        fornecedorRepository.deleteById(id);
    }

    public Fornecedor updateFornecedor(Long id, Fornecedor fornecedorDetails) {
        Fornecedor fornecedor = fornecedorRepository.findById(id).orElseThrow();
        fornecedor.setNome(fornecedorDetails.getNome());
        fornecedor.setEndereco(fornecedorDetails.getEndereco());
        fornecedor.setTelefone(fornecedorDetails.getTelefone());
        return fornecedorRepository.save(fornecedor);
    }
}