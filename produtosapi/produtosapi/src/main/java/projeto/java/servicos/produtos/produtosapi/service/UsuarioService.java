package projeto.java.servicos.produtos.produtosapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projeto.java.servicos.produtos.produtosapi.model.Usuario;
import projeto.java.servicos.produtos.produtosapi.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioService {
    private UsuarioRepository usuarioRepo;

    public UsuarioService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public Usuario findByEmail(String email) {
        return usuarioRepo.findById(email).get();
    }
}
