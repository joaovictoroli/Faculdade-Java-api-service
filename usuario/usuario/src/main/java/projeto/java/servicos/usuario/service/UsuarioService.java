package projeto.java.servicos.usuario.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projeto.java.servicos.usuario.model.Usuario;
import projeto.java.servicos.usuario.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioService {
    private UsuarioRepository usuarioRepo;

    public UsuarioService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public void addUsuario(Usuario u) {
        //RespEnderecoAPI respApi = APICEPService.buscaEnderecoPor(u.cep);
        //Endereco e = new Endereco(u.getCep(), respApi.getLogradouro(), respApi.getBairro(), respApi.getLocalidade(), respApi.getUf(), ""+respApi.getDdd(), u);
        //enderecoService.AddEndereco(e);
        this.usuarioRepo.save(u);
    }
    
    public List<Usuario> findAll() {
        return usuarioRepo.findAll();
    }

    public Usuario findByEmail(String email) {
        return usuarioRepo.findById(email).get();
    }
    
    public void delete(String email){
        usuarioRepo.deleteById(email);
    }
    
    public void update(Usuario u) {
        usuarioRepo.save(u);
    }
}

