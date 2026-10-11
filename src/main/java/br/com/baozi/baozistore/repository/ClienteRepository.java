package br.com.baozi.baozistore.repository;

import br.com.baozi.baozistore.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
