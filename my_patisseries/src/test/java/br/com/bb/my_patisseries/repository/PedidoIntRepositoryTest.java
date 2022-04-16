package br.com.bb.my_patisseries.repository;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.bb.my_patisseries.model.Pedido;

@SpringBootTest
public class PedidoIntRepositoryTest {

	@Autowired
	private PedidoIntRepository pedidoIntRepository;
	
	@Test
	void loudPedidoName() {
		String username = "joao";
		List<Pedido> list = pedidoIntRepository.findAllByUser(username);
		//Assert.assertNotNull(list);
		
		assertNotNull(list);
			
	}

}
