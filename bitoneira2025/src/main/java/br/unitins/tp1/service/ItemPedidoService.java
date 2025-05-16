package br.unitins.tp1.service;

import br.unitins.tp1.dto.ItemPedidoRequestDTO;
import br.unitins.tp1.dto.ItemPedidoResponseDTO;
import br.unitins.tp1.exception.ServiceException;
import br.unitins.tp1.model.ItemPedido;
import br.unitins.tp1.model.Pedido;
import br.unitins.tp1.model.Betoneira;
import br.unitins.tp1.repository.ItemPedidoRepository;
import br.unitins.tp1.repository.PedidoRepository;
import br.unitins.tp1.repository.BetoneiraRepository; // Import BetoneiraRepository
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class ItemPedidoService {

    private static final Logger LOGGER = Logger.getLogger(ItemPedidoService.class.getName());

    @Inject
    ItemPedidoRepository repository;

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    BetoneiraRepository betoneiraRepository; // Inject BetoneiraRepository

    public List<ItemPedidoResponseDTO> getAll() {
        LOGGER.info("Getting all itensPedido");
        return repository.listAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ItemPedidoResponseDTO findById(Long id) {
        LOGGER.info("Finding itemPedido by ID: " + id);
        ItemPedido itemPedido = repository.findById(id);
        if (itemPedido == null) {
            throw new ServiceException("ItemPedido not found with ID: " + id, Response.Status.NOT_FOUND);
        }
        return toResponseDTO(itemPedido);
    }

    @Transactional
    public ItemPedidoResponseDTO create(ItemPedidoRequestDTO dto) {
        LOGGER.info("Creating a new itemPedido");
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setQuantidade(dto.getQuantidade());
        itemPedido.setPreco(dto.getPreco());

        // Fetch the Pedido entity using pedidoId from DTO
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId());
        if (pedido == null) {
            throw new ServiceException("Pedido not found with ID: " + dto.getPedidoId(), Response.Status.NOT_FOUND);
        }
        itemPedido.setPedido(pedido);

        // Fetch the Betoneira entity using betoneiraId from DTO
        Betoneira betoneira = betoneiraRepository.findById(dto.getBetoneiraId());
        if (betoneira == null) {
            throw new ServiceException("Betoneira not found with ID: " + dto.getBetoneiraId(), Response.Status.NOT_FOUND);
        }
        itemPedido.setBetoneira(betoneira);

        repository.persist(itemPedido);
        return toResponseDTO(itemPedido);
    }

    @Transactional
    public ItemPedidoResponseDTO update(Long id, ItemPedidoRequestDTO dto) {
        LOGGER.info("Updating itemPedido with ID: " + id);
        ItemPedido itemPedido = repository.findById(id);
        if (itemPedido == null) {
            throw new ServiceException("ItemPedido not found with ID: " + id, Response.Status.NOT_FOUND);
        }
        itemPedido.setQuantidade(dto.getQuantidade());
        itemPedido.setPreco(dto.getPreco());

        // Fetch the Pedido entity using pedidoId from DTO
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId());
        if (pedido == null) {
            throw new ServiceException("Pedido not found with ID: " + dto.getPedidoId(), Response.Status.NOT_FOUND);
        }
        itemPedido.setPedido(pedido);

        // Fetch the Betoneira entity using betoneiraId from DTO
        Betoneira betoneira = betoneiraRepository.findById(dto.getBetoneiraId());
        if (betoneira == null) {
            throw new ServiceException("Betoneira not found with ID: " + dto.getBetoneiraId(), Response.Status.NOT_FOUND);
        }
        itemPedido.setBetoneira(betoneira);

        repository.persist(itemPedido);
        return toResponseDTO(itemPedido);
    }

    @Transactional
    public void delete(Long id) {
        LOGGER.info("Deleting itemPedido with ID: " + id);
        if (!repository.deleteById(id)) {
            throw new ServiceException("ItemPedido not found with ID: " + id, Response.Status.NOT_FOUND);
        }
    }

    private ItemPedidoResponseDTO toResponseDTO(ItemPedido itemPedido) {
        ItemPedidoResponseDTO responseDTO = new ItemPedidoResponseDTO();
        if (itemPedido != null) { // Null check
            responseDTO.setId(itemPedido.getId());
            responseDTO.setQuantidade(itemPedido.getQuantidade());
            responseDTO.setPreco(itemPedido.getPreco());
            if (itemPedido.getPedido() != null) { // Nested null check
                responseDTO.setPedidoId(itemPedido.getPedido().getId());
            }
            if (itemPedido.getBetoneira() != null) { // Nested null check
                responseDTO.setBetoneiraId(itemPedido.getBetoneira().getId());
            }
        }
        return responseDTO;
    }
}