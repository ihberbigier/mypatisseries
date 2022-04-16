package br.com.bb.my_patisseries.repository;


import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.bb.my_patisseries.model.Pedido;
import br.com.bb.my_patisseries.model.StatusPedidoEnum;

@Repository
public interface PedidoIntRepository extends JpaRepository<Pedido, Long>{

	
	List<Pedido> findByStatus(StatusPedidoEnum status, Pageable pagina);
	
	List<Pedido>findByNomeProduto(String nomeProduto,Pageable pagina);
	
	List<Pedido>findByUser_Username(String username,Pageable pagina);
	
	@Query("SELECT p FROM Pedido p join p.user u where u.username = :username")
	List<Pedido> findAllByUser(@Param("username")String username);
	
	@Query("SELECT p FROM Pedido p join p.user u where u.username = :username and p.status = :status")
	List<Pedido> findAllByUserAndStatus(@Param("username")String username,@Param("status")StatusPedidoEnum status);
	
}
