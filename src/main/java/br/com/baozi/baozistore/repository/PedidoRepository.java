package br.com.baozi.baozistore.repository;

import br.com.baozi.baozistore.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
