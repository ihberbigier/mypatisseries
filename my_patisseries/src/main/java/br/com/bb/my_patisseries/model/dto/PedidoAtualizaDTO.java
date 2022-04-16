package br.com.bb.my_patisseries.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PedidoAtualizaDTO {

	private String idPedido;
	
	@NotNull
	@Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$",message = "correct date format is dd/MM/yyyy")
	private String data;
	
	@NotNull
	@Pattern(regexp = "^\\d+(\\.\\d+{2})?$")
	private String valor;

	public String getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
}
