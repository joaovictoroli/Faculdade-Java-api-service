package projeto.java.servicos.usuario.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projeto.java.servicos.usuario.model.Endereco;
import projeto.java.servicos.usuario.repository.EnderecoRepository;

@Service
@Transactional
public class EnderecoService {
    private final EnderecoRepository repo;

    public EnderecoService(EnderecoRepository repo) {
        this.repo = repo;
    }
    
    public void AddEndereco(Endereco e) {
        repo.save(e);
    }
    
}

