package projeto.java.servicos.produtos.produtosapi.service;


import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import projeto.java.servicos.produtos.produtosapi.model.Produto;
import projeto.java.servicos.produtos.produtosapi.repository.ProdutoRepository;


@Service
@Transactional
public class ProdutoService {
    private final ProdutoRepository repo;

    public ProdutoService(ProdutoRepository repo) {
        this.repo = repo;
    }
    
    public void addProduto(Produto p) {
        repo.save(p);
    }
    
    public List<Produto> findAll() {
        return repo.findAll();
    }
    
    public void deleteById(int pid) {
        repo.deleteById(pid);
    }
    
    public Produto findById(int pid) {
        return repo.findById(pid).get();
    }
    
    public void update(Produto p) {
        repo.save(p);
    }
}

