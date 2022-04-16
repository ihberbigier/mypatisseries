package br.com.bb.my_patisseries.api;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.bb.my_patisseries.model.Pedido;
import br.com.bb.my_patisseries.model.StatusPedidoEnum;
import br.com.bb.my_patisseries.model.dto.PedidoAtualizaDTO;
import br.com.bb.my_patisseries.model.dto.PedidoDTO;
import br.com.bb.my_patisseries.model.dto.PedidoNovoDTO;
import br.com.bb.my_patisseries.repository.PedidoIntRepository;
import br.com.bb.my_patisseries.repository.UserRepository;

	

@RestController
@RequestMapping("/api/pedidos")
public class PedidoRestController {
	
	@Autowired
	private PedidoIntRepository pedidoRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@GetMapping
	@Cacheable(value = "listaPedidos")
	public ResponseEntity<List<PedidoDTO>> listAll(@PageableDefault(sort = "id",direction = Direction.ASC,page = 0,size = 10) Pageable page ){
		//Sort sort = Sort.by(ordenacao).descending();
		//PageRequest pag = PageRequest.of(pagina, quantidade,sort);
		Page<Pedido> pagePedidos = pedidoRepository.findAll(page);
		return ResponseEntity.ok(PedidoDTO.convertList(pagePedidos.toList())) ;
	}
	
	@GetMapping("aguardando")
	public List<PedidoDTO> listaPedidoRecentes() {
		Sort sort = Sort.by("id").descending();
		PageRequest pag = PageRequest.of(0, 2,sort);
		List<Pedido> pedidos =  pedidoRepository.findByStatus(StatusPedidoEnum.AGUARDANDO, pag);
		return PedidoDTO.convertList(pedidos);
	}
	
	@GetMapping("porProduto")
	public List<PedidoDTO> listaPedidoProduto(@RequestParam String nomeProduto) {
		Sort sort = Sort.by("id").descending();
		PageRequest pag = PageRequest.of(0, 2,sort);
		List<Pedido> pedidos =  pedidoRepository.findByNomeProduto(nomeProduto,pag);
		return PedidoDTO.convertList(pedidos);
	}
	
	
	@GetMapping("porUsername")
	public List<PedidoDTO> listaPedidoPorUsuario(@RequestParam String username) {
		Sort sort = Sort.by("id").descending();
		PageRequest pag = PageRequest.of(0, 2,sort);
		List<Pedido> pedidos =  pedidoRepository.findByUser_Username(username,pag);
		return PedidoDTO.convertList(pedidos);
	}
	
	@PostMapping("new")
	@Transactional
	@CacheEvict(value = "listaPedidos", allEntries = true)
	public ResponseEntity<PedidoDTO> novoPedido(@RequestBody @Valid PedidoNovoDTO pedidoDTO, UriComponentsBuilder uriComponentsBuilder) {
		Pedido pedido = pedidoDTO.toPedido(userRepository);
		pedidoRepository.save(pedido);
		URI uri = uriComponentsBuilder.path("/api/pedidos/{id}").buildAndExpand(pedido.getId()).toUri();
		return ResponseEntity.created(uri).body(new PedidoDTO(pedido));	
	}
	
	
	@PostMapping("atualiza")
	@CacheEvict(value = "listaPedidos", allEntries = true)
	public String atualiza(@Valid  @RequestBody PedidoAtualizaDTO pedidoDTO) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Pedido pedido = pedidoRepository.getById(Long.valueOf(pedidoDTO.getIdPedido()));
		if(pedidoDTO.getValor() !=null && !pedidoDTO.getValor().isBlank()) {
			pedido.setValorProduto(new BigDecimal(pedidoDTO.getValor()));
		}
		
		if(pedidoDTO.getData() !=null && !pedidoDTO.getData().isBlank()) {
			pedido.setDataEntrega(LocalDate.parse(pedidoDTO.getData(), formatter));
		}
		
		pedidoRepository.save(pedido);
		return "ok";
	}
	
	
	@PutMapping("update")
	@Transactional
	@CacheEvict(value = "listaPedidos", allEntries = true)
	public ResponseEntity<PedidoDTO> update(@Valid  @RequestBody PedidoAtualizaDTO pedidoDTO) {
		Optional<Pedido> pedidoOpt = pedidoRepository.findById(Long.valueOf(pedidoDTO.getIdPedido()));
		if(!pedidoOpt.isPresent()) {
			return ResponseEntity.noContent().build();	
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Pedido pedido = pedidoOpt.get();
		if(pedidoDTO.getValor() !=null && !pedidoDTO.getValor().isBlank()) {
			pedido.setValorProduto(new BigDecimal(pedidoDTO.getValor()));
		}
		
		if(pedidoDTO.getData() !=null && !pedidoDTO.getData().isBlank()) {
			pedido.setDataEntrega(LocalDate.parse(pedidoDTO.getData(), formatter));
		}
		
		//pedidoRepository.save(pedido);
		
		return ResponseEntity.ok(new PedidoDTO(pedido));
	}
	
	@DeleteMapping("/delete/{id}")
	@Transactional
	@CacheEvict(value = "listaPedidos", allEntries = true)
	public ResponseEntity<PedidoDTO> remove(@PathVariable Long id) {
		Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);
		if(!pedidoOpt.isPresent()) {
			return ResponseEntity.noContent().build();	
		}
		pedidoRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<PedidoDTO> detalhe(@PathVariable Long id) {
		Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);
		if(!pedidoOpt.isPresent()) {
			return ResponseEntity.noContent().build();	
		}
		
		return ResponseEntity.ok(new PedidoDTO(pedidoOpt.get()));
		
	}

}
