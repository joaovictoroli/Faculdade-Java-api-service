package projeto.java.servicos.produtos.produtosapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "peid")
    private Integer peid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_total")
    private float valorTotal;
    @JoinColumn(name = "usuario_email", referencedColumnName = "email")
    @OneToOne(optional = false)
    @JsonBackReference
    private Usuario usuarioEmail;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", orphanRemoval=true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<PedidoHasProduto> pedidoHasProdutoList;

    public Pedido() {
    }

    public Pedido(Integer peid) {
        this.peid = peid;
    }

    public Pedido(Date data, float valorTotal, Usuario usuarioEmail) {
        this.data = data;
        this.valorTotal = valorTotal;
        this.usuarioEmail = usuarioEmail;
    }

    public Pedido(Integer peid, Date data, float valorTotal) {
        this.peid = peid;
        this.data = data;
        this.valorTotal = valorTotal;
    }

    public Integer getPeid() {
        return peid;
    }

    public void setPeid(Integer peid) {
        this.peid = peid;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Usuario getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(Usuario usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public List<PedidoHasProduto> getPedidoHasProdutoList() {
        return pedidoHasProdutoList;
    }

    public void setPedidoHasProdutoList(List<PedidoHasProduto> pedidoHasProdutoList) {
        this.pedidoHasProdutoList = pedidoHasProdutoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (peid != null ? peid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pedido)) {
            return false;
        }
        Pedido other = (Pedido) object;
        if ((this.peid == null && other.peid != null) || (this.peid != null && !this.peid.equals(other.peid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "aws.example.projeto.model.Pedido[ peid=" + peid + " pedidoHasProdutoList=" + pedidoHasProdutoList + "]";
    }
    
}

