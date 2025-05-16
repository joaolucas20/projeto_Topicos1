package br.unitins.tp1.service;

import br.unitins.tp1.dto.FabricanteRequestDTO;
import br.unitins.tp1.dto.FabricanteResponseDTO;
import br.unitins.tp1.exception.ServiceException;
import br.unitins.tp1.model.Fabricante;
import br.unitins.tp1.repository.FabricanteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class FabricanteService {

    private static final Logger LOGGER = Logger.getLogger(FabricanteService.class.getName());

    @Inject
    FabricanteRepository repository;

    public List<FabricanteResponseDTO> getAll() {
        LOGGER.info("Getting all fabricantes");
        return repository.listAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public FabricanteResponseDTO findById(Long id) {
        LOGGER.info("Finding fabricante by ID: " + id);
        Fabricante fabricante = repository.findById(id);
        if (fabricante == null) {
            throw new ServiceException("Fabricante not found with ID: " + id, Response.Status.NOT_FOUND);
        }
        return toResponseDTO(fabricante);
    }

    @Transactional
    public FabricanteResponseDTO create(FabricanteRequestDTO dto) {
        LOGGER.info("Creating a new fabricante");
        Fabricante fabricante = new Fabricante();
        fabricante.setNome(dto.getNome());
        fabricante.setCnpj(dto.getCnpj());

        repository.persist(fabricante);
        return toResponseDTO(fabricante);
    }

    @Transactional
    public FabricanteResponseDTO update(Long id, FabricanteRequestDTO dto) {
        LOGGER.info("Updating fabricante with ID: " + id);
        Fabricante fabricante = repository.findById(id);
        if (fabricante == null) {
            throw new ServiceException("Fabricante not found with ID: " + id, Response.Status.NOT_FOUND);
        }
        fabricante.setNome(dto.getNome());
        fabricante.setCnpj(dto.getCnpj());

        repository.persist(fabricante);
        return toResponseDTO(fabricante);
    }

    @Transactional
    public void delete(Long id) {
        LOGGER.info("Deleting fabricante with ID: " + id);
        if (!repository.deleteById(id)) {
            throw new ServiceException("Fabricante not found with ID: " + id, Response.Status.NOT_FOUND);
        }
    }

    private FabricanteResponseDTO toResponseDTO(Fabricante fabricante) {
        FabricanteResponseDTO responseDTO = new FabricanteResponseDTO();
        responseDTO.setId(fabricante.getId());
        responseDTO.setNome(fabricante.getNome());
        responseDTO.setCnpj(fabricante.getCnpj());
        return responseDTO;
    }
}