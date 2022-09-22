package projeto.java.servicos.produtos.produtosapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projeto.java.servicos.produtos.produtosapi.model.Pedido;
import projeto.java.servicos.produtos.produtosapi.model.PedidoHasProduto;
import projeto.java.servicos.produtos.produtosapi.model.PedidoHasProdutoPK;
import projeto.java.servicos.produtos.produtosapi.repository.PedidoHasProdutoRepository;
import projeto.java.servicos.produtos.produtosapi.repository.PedidoRepository;


@Service
@Transactional
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final PedidoHasProdutoRepository pedidoHasProdutoRepository;

    public PedidoService(PedidoRepository pedidoRepository, PedidoHasProdutoRepository pedidoHasProdutoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoHasProdutoRepository = pedidoHasProdutoRepository;
    }
        
    public void addPedido(Pedido pe) {
        pedidoRepository.save(pe);
    }
    
    public void addProduto(PedidoHasProduto pep) {
        pedidoHasProdutoRepository.save(pep);
    }
    
    public List<PedidoHasProduto> findAllProd(Pedido pe) {
        return pedidoHasProdutoRepository.findByPedidoPeid(pe.getPeid());
    }
    
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }
    
    public Pedido findById(int peid) {
        return pedidoRepository.findById(peid).get();
    }
    
    public void deleteById(int peid){
        Pedido resetedPedido = pedidoRepository.findById(peid).get();
        Date data = new Date();
        resetedPedido.setData(data);
        resetedPedido.setValorTotal(0);

        List<PedidoHasProduto> pedidoHasProdutos = pedidoHasProdutoRepository.findAll();
        for (PedidoHasProduto pedidoHasProduto : pedidoHasProdutos) {
            Integer id = pedidoHasProduto.getPedido().getPeid();
            if (peid == id) {
                pedidoHasProdutoRepository.delete(pedidoHasProduto);
            }
        }

        //pedidoRepository.deleteById(peid);
    }
    
    public void update(Pedido pe){
        pedidoRepository.save(pe);
    }

    public PedidoHasProduto findPedidoHasProduto(PedidoHasProdutoPK pids) {
        return pedidoHasProdutoRepository.getById(pids);
    }
    
    public void deleteProdutoInPedido(PedidoHasProdutoPK pids) {
        Pedido resetedPedido = pedidoRepository.findById(pids.getPedidoId()).get();
        Date data = new Date();
        resetedPedido.setData(data);
        resetedPedido.setValorTotal(0);

        pedidoHasProdutoRepository.deleteById(pids);
    }
}