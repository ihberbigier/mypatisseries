package br.com.bb.my_patisseries.model.dto;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import br.com.bb.my_patisseries.model.Pedido;

class PedidoNovoDTOTest {

	@Test
	void testToPedido() {
		PedidoNovoDTO pedidoNovoDTO = new PedidoNovoDTO();
		pedidoNovoDTO.setNomeProduto("product test 1");
		pedidoNovoDTO.setDescricaoPedido("this is a test");
		pedidoNovoDTO.setUrlImagem("http://test.image");
		pedidoNovoDTO.setValorProduto("100");
		Pedido pedido = pedidoNovoDTO.toPedido();
		
		assertTrue(pedidoNovoDTO.getNomeProduto().equals(pedido.getNomeProduto()));
		
		assertTrue(pedidoNovoDTO.getDescricaoPedido().equals(pedido.getDescricao()));
		
		assertTrue(pedidoNovoDTO.getUrlImagem().equals(pedido.getUrlImagem()));
		
	}



}
