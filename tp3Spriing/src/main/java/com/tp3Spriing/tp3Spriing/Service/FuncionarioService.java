package com.tp3Spriing.tp3Spriing.Service;

import com.tp3Spriing.tp3Spriing.Model.Funcionario;
import com.tp3Spriing.tp3Spriing.Repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<Funcionario> getAllFuncionarios() {
        return funcionarioRepository.findAll();
    }

    public Optional<Funcionario> getFuncionarioById(Long id) {
        return funcionarioRepository.findById(id);
    }

    public Funcionario createFuncionario(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    public void deleteFuncionario(Long id) {
        funcionarioRepository.deleteById(id);
    }

    public Funcionario updateFuncionario(Long id, Funcionario funcionarioDetails) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow();
        funcionario.setNome(funcionarioDetails.getNome());
        funcionario.setCargo(funcionarioDetails.getCargo());
        funcionario.setSalario(funcionarioDetails.getSalario());
        return funcionarioRepository.save(funcionario);
    }
}