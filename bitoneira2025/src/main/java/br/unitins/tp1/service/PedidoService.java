package br.unitins.tp1.service;

import br.unitins.tp1.dto.PedidoRequestDTO;
import br.unitins.tp1.dto.PedidoResponseDTO;
import br.unitins.tp1.exception.ServiceException;
import br.unitins.tp1.model.Pedido;
import br.unitins.tp1.repository.PedidoRepository;
import br.unitins.tp1.repository.ClienteRepository; // Import ClienteRepository
import br.unitins.tp1.model.Cliente; // Import Cliente
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class PedidoService {

    private static final Logger LOGGER = Logger.getLogger(PedidoService.class.getName());

    @Inject
    PedidoRepository repository;

    @Inject
    ClienteRepository clienteRepository; // Inject ClienteRepository

    public List<PedidoResponseDTO> getAll() {
        LOGGER.info("Getting all pedidos");
        return repository.listAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO findById(Long id) {
        LOGGER.info("Finding pedido by ID: " + id);
        Pedido pedido = repository.findById(id);
        if (pedido == null) {
            throw new ServiceException("Pedido not found with ID: " + id, Response.Status.NOT_FOUND);
        }
        return toResponseDTO(pedido);
    }

    @Transactional
    public PedidoResponseDTO create(PedidoRequestDTO dto) {
        LOGGER.info("Creating a new pedido");
        Pedido pedido = new Pedido();
        pedido.dataPedido = dto.getDataPedido();

        // Fetch the Cliente entity using clienteId from DTO
        Cliente cliente = clienteRepository.findById(dto.getClienteId());
        if (cliente == null) {
            throw new ServiceException("Cliente not found with ID: " + dto.getClienteId(), Response.Status.NOT_FOUND);
        }
        pedido.cliente = cliente;

        repository.persist(pedido);
        return toResponseDTO(pedido);
    }

    @Transactional
    public PedidoResponseDTO update(Long id, PedidoRequestDTO dto) {
        LOGGER.info("Updating pedido with ID: " + id);
        Pedido pedido = repository.findById(id);
        if (pedido == null) {
            throw new ServiceException("Pedido not found with ID: " + id, Response.Status.NOT_FOUND);
        }
        pedido.dataPedido = dto.getDataPedido();

        // Fetch the Cliente entity using clienteId from DTO
        Cliente cliente = clienteRepository.findById(dto.getClienteId());
        if (cliente == null) {
            throw new ServiceException("Cliente not found with ID: " + dto.getClienteId(), Response.Status.NOT_FOUND);
        }
        pedido.cliente = cliente;

        repository.persist(pedido);
        return toResponseDTO(pedido);
    }

    @Transactional
    public void delete(Long id) {
        LOGGER.info("Deleting pedido with ID: " + id);
        if (!repository.deleteById(id)) {
            throw new ServiceException("Pedido not found with ID: " + id, Response.Status.NOT_FOUND);
        }
    }

    private PedidoResponseDTO toResponseDTO(Pedido pedido) {
        PedidoResponseDTO responseDTO = new PedidoResponseDTO();
        responseDTO.setId(pedido.getId());
        responseDTO.setDataPedido(pedido.getDataPedido());
        responseDTO.setClienteId(pedido.cliente.getId()); // Include clienteId in the response
        return responseDTO;
    }
}