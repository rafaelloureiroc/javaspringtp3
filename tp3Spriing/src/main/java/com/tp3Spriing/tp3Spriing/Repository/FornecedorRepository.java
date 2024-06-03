package com.tp3Spriing.tp3Spriing.Repository;

import com.tp3Spriing.tp3Spriing.Model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
}