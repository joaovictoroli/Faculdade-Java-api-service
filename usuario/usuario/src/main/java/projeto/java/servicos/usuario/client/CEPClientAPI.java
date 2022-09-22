package projeto.java.servicos.usuario.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import projeto.java.servicos.usuario.model.RespEnderecoAPI;

@Component
public class CEPClientAPI {
    public RespEnderecoAPI buscaEnderecoPor(String cep){
        RestTemplate template = new RestTemplate();
        return template.getForObject("https://viacep.com.br/ws/{cep}/json/", RespEnderecoAPI.class, cep);
    }
}
