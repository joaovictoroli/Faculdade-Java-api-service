package projeto.java.servicos.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projeto.java.servicos.usuario.model.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, String> {
    
}

