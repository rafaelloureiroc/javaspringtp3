package com.tp3Spriing.tp3Spriing.Repository;

import com.tp3Spriing.tp3Spriing.Model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}