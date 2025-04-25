package br.unitins.tp1.service;

import br.unitins.tp1.dto.ClienteRequestDTO;
import br.unitins.tp1.dto.ClienteResponseDTO;
import br.unitins.tp1.exception.ServiceException;
import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.repository.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClienteService {

    private static final Logger LOGGER = Logger.getLogger(ClienteService.class.getName());

    @Inject
    ClienteRepository repository;

    public List<ClienteResponseDTO> getAll() {
        LOGGER.info("Getting all clientes");
        return repository.listAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO findById(Long id) {
        LOGGER.info("Finding cliente by ID: " + id);
        Cliente cliente = repository.findById(id);
        if (cliente == null) {
            throw new ServiceException("Cliente not found with ID: " + id, Response.Status.NOT_FOUND);
        }
        return toResponseDTO(cliente);
    }

    @Transactional
    public ClienteResponseDTO create(ClienteRequestDTO dto) {
        LOGGER.info("Creating a new cliente");
        Cliente cliente = new Cliente();
        cliente.nome = dto.getNome();
        cliente.email = dto.getEmail();
        repository.persist(cliente);
        return toResponseDTO(cliente);
    }

    @Transactional
    public ClienteResponseDTO update(Long id, ClienteRequestDTO dto) {
        LOGGER.info("Updating cliente with ID: " + id);
        Cliente cliente = repository.findById(id);
        if (cliente == null) {
            throw new ServiceException("Cliente not found with ID: " + id, Response.Status.NOT_FOUND);
        }
        cliente.nome = dto.getNome();
        cliente.email = dto.getEmail();
        repository.persist(cliente);
        return toResponseDTO(cliente);
    }

    @Transactional
    public void delete(Long id) {
        LOGGER.info("Deleting cliente with ID: " + id);
        if (!repository.deleteById(id)) {
            throw new ServiceException("Cliente not found with ID: " + id, Response.Status.NOT_FOUND);
        }
    }

    private ClienteResponseDTO toResponseDTO(Cliente cliente) {
        ClienteResponseDTO responseDTO = new ClienteResponseDTO();
        responseDTO.setId(cliente.getId());
        responseDTO.setNome(cliente.getNome());
        responseDTO.setEmail(cliente.getEmail());
        return responseDTO;
    }
}