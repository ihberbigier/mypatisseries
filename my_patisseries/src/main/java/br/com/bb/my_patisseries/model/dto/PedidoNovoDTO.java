package br.com.bb.my_patisseries.model.dto;

import java.math.BigDecimal;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import br.com.bb.my_patisseries.model.Pedido;
import br.com.bb.my_patisseries.model.User;
import br.com.bb.my_patisseries.repository.UserRepository;



public class PedidoNovoDTO {

	@NotBlank//NotBlank.pedidoNovoDTO.nomeProduto
	private String nomeProduto;
	@NotBlank //NotBlank.pedidoNovoDTO.valorProduto
	private String valorProduto;
	
	private String descricaoPedido;
	@NotBlank//NotBlank.pedidoNovoDTO.urlImagem
	private String urlImagem;
	
	private String nomeCliente;

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getValorProduto() {
		return valorProduto;
	}

	public void setValorProduto(String valorProduto) {
		this.valorProduto = valorProduto;
	}

	public String getDescricaoPedido() {
		return descricaoPedido;
	}

	public void setDescricaoPedido(String descricaoPedido) {
		this.descricaoPedido = descricaoPedido;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}
	
	public Pedido toPedido() {
		BigDecimal valor = null;
		if(valorProduto != null) {
			valor = new BigDecimal(valorProduto);
		}
		Pedido pedido = new Pedido(nomeProduto,valor, urlImagem, descricaoPedido);
		return pedido;
	}
	
	public Pedido toPedido(UserRepository userRepository) {
		Optional<User>  user = null;
		if(nomeCliente != null && !nomeCliente.isBlank()) {
			user = userRepository.findByUsername(nomeCliente);
		}
		if(user.isPresent()) {
			return new Pedido(nomeProduto, new BigDecimal(valorProduto), urlImagem, descricaoPedido,user.get());
		}
		
		return this.toPedido();
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	
	

}
