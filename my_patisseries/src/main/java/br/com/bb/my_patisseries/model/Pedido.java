package br.com.bb.my_patisseries.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Pedido implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nomeProduto;
	private BigDecimal valorProduto;
	private LocalDate dataEntrega;
	private String urlProduto;
	private String urlImagem;
	private String descricao;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private User user;
	
	@Enumerated(EnumType.STRING	)
	private StatusPedidoEnum status;

	public String getNomeProduto() {
		return nomeProduto;
	}
	
	

	public Pedido(String nomeProduto, BigDecimal valorProduto, String urlImagem, String descricao) {
		super();
		this.nomeProduto = nomeProduto;
		this.valorProduto = valorProduto;
		this.urlImagem = urlImagem;
		this.descricao = descricao;
		this.status = StatusPedidoEnum.AGUARDANDO;
	}
	public Pedido(String nomeProduto, BigDecimal valorProduto, String urlImagem, String descricao,User user) {
		super();
		this.nomeProduto = nomeProduto;
		this.valorProduto = valorProduto;
		this.urlImagem = urlImagem;
		this.descricao = descricao;
		this.status = StatusPedidoEnum.AGUARDANDO;
		this.user = user;
	}


	public Pedido() {
		super();
	}



	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public BigDecimal getValorProduto() {
		return valorProduto;
	}

	public void setValorProduto(BigDecimal valorProduto) {
		this.valorProduto = valorProduto;
	}

	public LocalDate getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public String getUrlProduto() {
		return urlProduto;
	}

	public void setUrlProduto(String urlProduto) {
		this.urlProduto = urlProduto;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public StatusPedidoEnum getStatus() {
		return status;
	}


	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public void setStatus(StatusPedidoEnum status) {
		this.status = status;
	}




	public void setUser(User user) {
		this.user = user;
	}

	
	
}
