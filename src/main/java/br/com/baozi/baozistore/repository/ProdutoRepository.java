package br.com.baozi.baozistore.repository;

import br.com.baozi.baozistore.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
