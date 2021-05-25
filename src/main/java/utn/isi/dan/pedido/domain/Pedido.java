package utn.isi.dan.pedido.domain;

import java.time.Instant;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "PEDIDO", schema = "MS_PEDIDOS")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_PED")
	private Integer id;
	
	@Column(name = "FEC_PED")
	private Instant fechaPedido;
	
	@ManyToOne
	@JoinColumn(name = "ID_OBRA")
	private Obra obra;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_PED", referencedColumnName = "ID_PED")
	private List<DetallePedido> detalle;
	
	@OneToOne
	@JoinColumn(name ="ID_EST_PED")
	private EstadoPedido estado;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Instant getFechaPedido() {
		return fechaPedido;
	}
	public void setFechaPedido(Instant fechaPedido) {
		this.fechaPedido = fechaPedido;
	}
	public Obra getObra() {
		return obra;
	}
	public void setObra(Obra obra) {
		this.obra = obra;
	}
	public List<DetallePedido> getDetalle() {
		return detalle;
	}
	public void setDetalle(List<DetallePedido> detalle) {
		this.detalle = detalle;
	}
	
    public void addDetalle(DetallePedido detalle) {
        this.detalle.add(detalle);
    }
	public EstadoPedido getEstado() {
		return estado;
	}
	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "Pedido [id=" + id + ", fechaPedido=" + fechaPedido + ", obra=" + obra + ", detalle=" + detalle
				+ ", estado=" + estado + "]";
	}
	
	
	
}
