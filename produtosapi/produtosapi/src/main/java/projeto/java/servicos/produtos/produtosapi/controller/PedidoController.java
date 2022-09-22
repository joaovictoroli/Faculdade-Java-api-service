package projeto.java.servicos.produtos.produtosapi.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projeto.java.servicos.produtos.produtosapi.model.Pedido;
import projeto.java.servicos.produtos.produtosapi.model.PedidoHasProduto;
import projeto.java.servicos.produtos.produtosapi.model.PedidoHasProdutoPK;
import projeto.java.servicos.produtos.produtosapi.model.Produto;
import projeto.java.servicos.produtos.produtosapi.model.Usuario;
import projeto.java.servicos.produtos.produtosapi.repository.ProdutoRepository;
import projeto.java.servicos.produtos.produtosapi.service.PedidoService;
import projeto.java.servicos.produtos.produtosapi.service.ProdutoService;
import projeto.java.servicos.produtos.produtosapi.service.UsuarioService;



@RestController
@RequestMapping("/api/v1")
public class PedidoController {
    private final PedidoService pedidoService;
    private final ProdutoService produtoService;
    private final UsuarioService usuarioService;
    private final ProdutoRepository produtoRepository;
    

    public PedidoController(PedidoService pedidoService, ProdutoService produtoService, UsuarioService usuarioService, ProdutoRepository produtoRepository) {
        this.pedidoService = pedidoService;
        this.produtoService = produtoService;
        this.usuarioService = usuarioService;
        this.produtoRepository = produtoRepository;
    }
    
    @RequestMapping(method=RequestMethod.POST, value="/pedidos")
    public void addPedido(@RequestParam("emailUsuario") String emailUsuario,
                          @RequestParam("idproduto") int idproduto,
                          @RequestParam("quantidade") int quantidade){
                        
        Produto produto = produtoService.findById(idproduto);
        Usuario usuario = usuarioService.findByEmail(emailUsuario);   
        if (produto != null || usuario != null) {
            if (checkWUserHasPedido(usuario)){
                Pedido pe = usuario.getPedido();
                PedidoHasProdutoPK pedhasPK = new PedidoHasProdutoPK(pe.getPeid(), idproduto);
                PedidoHasProduto pedhas = new PedidoHasProduto(pedhasPK, quantidade);
                pedidoService.addProduto(pedhas);
                Pedido pu = pedidoService.findById(pe.getPeid());
                atualizarValorTotal(pu);
            } else {
            //add pedido
            Date data = new Date();
            Pedido pe = new Pedido(data, 0.0F, usuario);        
            pedidoService.addPedido(pe);
            
            PedidoHasProdutoPK pedhasPK = new PedidoHasProdutoPK(pe.getPeid(), idproduto);
            PedidoHasProduto pedhas = new PedidoHasProduto(pedhasPK, quantidade);
            pedidoService.addProduto(pedhas);
            
            Pedido pu = pedidoService.findById(pe.getPeid());
            atualizarValorTotal(pu);
            }
        }
    }
    
    @RequestMapping(method=RequestMethod.GET, value="/pedidos")
    public List<Pedido> findAll(){
        return pedidoService.findAll();
    }
    
    @RequestMapping(method=RequestMethod.GET, value="/pedidos/{peid}")
    public Pedido findByPeid(@PathVariable int peid){
        return pedidoService.findById(peid);
    }
    
    @RequestMapping(method=RequestMethod.PUT, value="/pedidos/{peid}")
    public void updatePedido(@PathVariable int peid,
                            @RequestParam("idproduto") int idproduto,
                            @RequestParam("quantidade") int quantidade){
        
        Produto produto = produtoService.findById(idproduto);
        if (produto != null) {
           PedidoHasProdutoPK pedhasPK = new PedidoHasProdutoPK(peid, idproduto);
            PedidoHasProduto pedhas = new PedidoHasProduto(pedhasPK, quantidade);
            pedidoService.addProduto(pedhas);             
            Pedido pe = pedidoService.findById(peid);
            atualizarValorTotal(pe);
        }
    }
    
    @RequestMapping(method=RequestMethod.DELETE, value="/pedidos/{peid}/produto/{pid}")
    public void deleteProduto(@PathVariable int peid,
                            @PathVariable Integer pid){
        PedidoHasProdutoPK primarykeys = new PedidoHasProdutoPK(peid, pid);
        pedidoService.deleteProdutoInPedido(primarykeys);
        Pedido pedido = pedidoService.findById(peid);
        atualizarValorTotal(pedido);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/pedidos/{peid}")
    public void delete(@PathVariable Integer peid){
        pedidoService.deleteById(peid);
    }

    @RequestMapping(method=RequestMethod.DELETE, value = "/produtos/{id}")
    public Map<String, Boolean> deleteProduto(@PathVariable(value = "id") Integer produtoId)
            throws ResourceNotFoundException {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto not found for this id :: " + produtoId));
        
                //produtoRepository
        produtoRepository.delete(produto);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        List<PedidoHasProduto> pedidohasprodutos = produto.getPedidoHasProdutoList();
        for (PedidoHasProduto pedidohasproduto : pedidohasprodutos) {
            Pedido pedido = pedidohasproduto.getPedido();
            atualizarValorTotal(pedido);
        }

        return response;
    }

    public void atualizarValorTotal(Pedido Pe) {
        float valor_total = 0;
        List<PedidoHasProduto> listPeHas = pedidoService.findAllProd(Pe);
        System.out.println(listPeHas);
        for (PedidoHasProduto element : listPeHas) {
            System.out.println(element.getProduto());
            System.out.println(element);
            float preco = produtoService.findById(element.getPedidoHasProdutoPK().getProdutoId()).getPreco();
            int quantidade = element.getQuantidade();
            
            valor_total += preco * quantidade;
        }   
        Pe.setValorTotal(valor_total);
        pedidoService.update(Pe);
    }

    public boolean checkWUserHasPedido(Usuario user) {
        Pedido pedido = user.getPedido();
        if (pedido != null) {
            return true;
        } else {
            return false;
        }
    }
}
