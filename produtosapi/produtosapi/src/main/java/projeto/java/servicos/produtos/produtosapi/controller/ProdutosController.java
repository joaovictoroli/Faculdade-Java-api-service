package projeto.java.servicos.produtos.produtosapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projeto.java.servicos.produtos.produtosapi.model.Produto;
import projeto.java.servicos.produtos.produtosapi.repository.ProdutoRepository;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1")
public class ProdutosController {
    @Autowired
    ProdutoRepository produtoRepository;
    
    @GetMapping("/produtos")
    public List<Produto> getAllProdutos() {
        return produtoRepository.findAll();
    }

    @GetMapping("/produtos/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable(value = "id") Integer produtoId) throws ResourceNotFoundException { 
        Produto produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new ResourceNotFoundException("Produto not found for this id :: " + produtoId));
            return ResponseEntity.ok().body(produto);
        }
    
    @RequestMapping(method=RequestMethod.POST, value="/produtos/")
    public void addProduto(@RequestParam("nome") String nome,
            @RequestParam("descricao") String descricao,
            @RequestParam("preco") float preco){
            Produto p = new Produto(nome, descricao, preco);
            
            produtoRepository.save(p);
        }
        
    @RequestMapping(method=RequestMethod.PUT, value="/produtos/{pid}")
    public void update(@PathVariable int pid,
                       @RequestParam("nome") String nome,
                       @RequestParam("descricao") String descricao,
                       @RequestParam("preco") float preco){
        
        Produto p = new Produto(pid, nome, descricao, preco);
        produtoRepository.save(p);
    }

}

