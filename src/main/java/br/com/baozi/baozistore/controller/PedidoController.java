package br.com.baozi.baozistore.controller;

import br.com.baozi.baozistore.model.Cliente;
import br.com.baozi.baozistore.model.Pedido;
import br.com.baozi.baozistore.model.Produto;
import br.com.baozi.baozistore.repository.ClienteRepository;
import br.com.baozi.baozistore.repository.PedidoRepository;
import br.com.baozi.baozistore.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(PedidoRepository pedidoRepository,
                            ClienteRepository clienteRepository,
                            ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    /*
     * Corpo esperado:
     * { "cliente": { "id": 1 }, "produto": { "id": 1 }, "quantidade": 10 }
     * A resposta devolve cliente e produto completos (nome, preco etc.).
     */
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Pedido pedido) {
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null
                || pedido.getProduto() == null || pedido.getProduto().getId() == null) {
            return ResponseEntity.badRequest().body("Informe cliente.id e produto.id");
        }
        if (pedido.getQuantidade() == null || pedido.getQuantidade() <= 0) {
            return ResponseEntity.badRequest().body("Quantidade deve ser maior que zero");
        }

        Cliente cliente = clienteRepository.findById(pedido.getCliente().getId()).orElse(null);
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente nao encontrado");
        }
        Produto produto = produtoRepository.findById(pedido.getProduto().getId()).orElse(null);
        if (produto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto nao encontrado");
        }

        pedido.setId(null);
        pedido.setCliente(cliente);
        pedido.setProduto(produto);
        Pedido salvo = pedidoRepository.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!pedidoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pedidoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
