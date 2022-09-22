package projeto.java.servicos.usuario.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projeto.java.servicos.usuario.client.CEPClientAPI;
import projeto.java.servicos.usuario.model.Endereco;
import projeto.java.servicos.usuario.model.RespEnderecoAPI;
import projeto.java.servicos.usuario.model.Usuario;
import projeto.java.servicos.usuario.service.EnderecoService;
import projeto.java.servicos.usuario.service.UsuarioService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v1")
public class UsuarioController {
    private final UsuarioService userService;
    private final CEPClientAPI APICEPService;
    private final EnderecoService enderecoService;

    public UsuarioController(UsuarioService userService, CEPClientAPI aPICEPService,
            EnderecoService enderecoService) {
        this.userService = userService;
        this.APICEPService = aPICEPService;
        this.enderecoService = enderecoService;
    }

    @RequestMapping(method= RequestMethod.POST, value="/usuarios/add")
    public void addUsuario(@RequestBody Usuario u) {
        userService.addUsuario(u);
    } 
    
    @RequestMapping(method= RequestMethod.GET, value="/usuarios")
    public List<Usuario> findAll(){
        return userService.findAll();
    } 
    
    @RequestMapping(method=RequestMethod.GET, value="/usuarios/{email}" )
    public Usuario findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }
    
    @RequestMapping(method=RequestMethod.DELETE, value="/usuarios/{email}" )
    public void delete(@PathVariable String email){
        userService.delete(email);
        
    }
    
    //
    @RequestMapping(method= RequestMethod.POST, value="/usuarios")
    public void addUsuario(@RequestParam("email") String email, 
            @RequestParam("nome") String nome,
            @RequestParam("telefone") String telefone, 
            @RequestParam("CEP") String CEP,
            @RequestParam("numero") String numero,
            @RequestParam("complemento") String complemento) {
        
        Usuario u = new Usuario(email, nome, telefone);
       
        userService.addUsuario(u);

        System.out.println(u);
        
        RespEnderecoAPI respApi = APICEPService.buscaEnderecoPor(CEP);

        System.out.println(u);
        Endereco e = new Endereco(CEP, respApi.getUf(), respApi.getLocalidade(), respApi.getBairro(), respApi.getLogradouro(), numero, complemento, ""+respApi.getDdd(), u);
        enderecoService.AddEndereco(e);
    }
    
    @RequestMapping(method= RequestMethod.PUT, value="/usuarios/{email}")
    public void update(@PathVariable String email,
                        @RequestParam("nome") String nome,
                        @RequestParam("telefone") String telefone,
                        @RequestParam("CEP") String CEP,
                        @RequestParam("numero") String numero,
                        @RequestParam("complemento") String complemento) {
        
        Usuario u = new Usuario(email, nome, telefone);
        Usuario user = userService.findByEmail(email);
        System.out.println("ASDHUASHUDSAHUD" +user);
        //u.getEndereco();
        RespEnderecoAPI respApi = APICEPService.buscaEnderecoPor(CEP);
        Endereco e = new Endereco(CEP, respApi.getUf(), respApi.getLocalidade(), respApi.getBairro(), respApi.getLogradouro(), numero, complemento, ""+respApi.getDdd(), u);
        enderecoService.AddEndereco(e);
        
        userService.update(u);
    } 
    
}

