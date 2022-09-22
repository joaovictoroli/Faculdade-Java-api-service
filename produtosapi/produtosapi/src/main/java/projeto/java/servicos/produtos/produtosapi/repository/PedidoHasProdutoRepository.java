package projeto.java.servicos.produtos.produtosapi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projeto.java.servicos.produtos.produtosapi.model.PedidoHasProduto;
import projeto.java.servicos.produtos.produtosapi.model.PedidoHasProdutoPK;

@Repository
public interface PedidoHasProdutoRepository extends JpaRepository<PedidoHasProduto, PedidoHasProdutoPK> {
    public List<PedidoHasProduto> findByPedidoPeid(int pedidoId);
}
