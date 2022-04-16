package br.com.bb.my_patisseries.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import br.com.bb.my_patisseries.model.Pedido;
import br.com.bb.my_patisseries.model.StatusPedidoEnum;


public class PedidoDTO {

	private Long id;
	private String nomeProduto;
	private BigDecimal valorProduto;
	private LocalDate dataEntrega;
	private String urlProduto;
	private String urlImagem;
	private String descricao;
	
	private StatusPedidoEnum status;
	
	public PedidoDTO() {
		
	}
	
	public PedidoDTO(Pedido pedido) {
		super();
		this.id = pedido.getId();
		this.nomeProduto = pedido.getNomeProduto();
		this.valorProduto = pedido.getValorProduto();
		this.dataEntrega = pedido.getDataEntrega();
		this.urlProduto = pedido.getUrlProduto();
		this.urlImagem = pedido.getUrlImagem();
		this.descricao = pedido.getDescricao();
		this.status = pedido.getStatus();
	}
	
	public static List<PedidoDTO> convertList(List<Pedido> pedidos) {
		return pedidos.stream().map(PedidoDTO::new).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeProduto() {
		return nomeProduto;
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

	public void setStatus(StatusPedidoEnum status) {
		this.status = status;
	}
	
	
}
