package br.unitins.tp1.service;

import br.unitins.tp1.dto.BetoneiraRequestDTO;
import br.unitins.tp1.dto.BetoneiraResponseDTO;
import br.unitins.tp1.dto.FabricanteResponseDTO;
import br.unitins.tp1.exception.ServiceException;
import br.unitins.tp1.model.Betoneira;
import br.unitins.tp1.model.Fabricante;
import br.unitins.tp1.repository.BetoneiraRepository;
import br.unitins.tp1.repository.FabricanteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class BetoneiraService {

    private static final Logger LOGGER = Logger.getLogger(BetoneiraService.class.getName());

    @Inject
    BetoneiraRepository betoneiraRepository; // Removed @Named

    @Inject
    FabricanteRepository fabricanteRepository; // Removed @Named

    public List<BetoneiraResponseDTO> getAll() {
        LOGGER.info("Getting all betoneiras");
        return betoneiraRepository.listAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public BetoneiraResponseDTO findById(Long id) {
        LOGGER.info("Finding betoneira by ID: " + id);
        Betoneira betoneira = betoneiraRepository.findById(id);
        if (betoneira == null) {
            throw new ServiceException("Betoneira not found with ID: " + id, Response.Status.NOT_FOUND);
        }
        return toResponseDTO(betoneira);
    }

    public List<BetoneiraResponseDTO> findByModelo(String modelo) {
        LOGGER.info("Finding betoneiras by modelo: " + modelo);
        return betoneiraRepository.find("modelo", modelo)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public BetoneiraResponseDTO create(BetoneiraRequestDTO dto) {
        LOGGER.info("Creating a new betoneira");
        Betoneira betoneira = new Betoneira();
        betoneira.marca = dto.getMarca(); // Use getter
        betoneira.modelo = dto.getModelo(); // Use getter
        betoneira.capacidade = dto.getCapacidade(); // Use getter
        betoneira.tipo = dto.getTipo(); // Use getter

        if (dto.getFabricanteId() != null) { // Use getter
            Fabricante fabricante = fabricanteRepository.findById(dto.getFabricanteId()); // Use getter
            if (fabricante == null) {
                throw new ServiceException("Fabricante not found with ID: " + dto.getFabricanteId(), Response.Status.NOT_FOUND); // Use getter
            }
            betoneira.fabricante = fabricante;
        }

        betoneiraRepository.persist(betoneira);
        return toResponseDTO(betoneira);
    }

    @Transactional
    public BetoneiraResponseDTO update(Long id, BetoneiraRequestDTO dto) {
        LOGGER.info("Updating betoneira with ID: " + id);
        Betoneira betoneira = betoneiraRepository.findById(id);
        if (betoneira == null) {
            throw new ServiceException("Betoneira not found with ID: " + id, Response.Status.NOT_FOUND);
        }
        betoneira.marca = dto.getMarca(); // Use getter
        betoneira.modelo = dto.getModelo(); // Use getter
        betoneira.capacidade = dto.getCapacidade(); // Use getter
        betoneira.tipo = dto.getTipo(); // Use getter

        if (dto.getFabricanteId() != null) { // Use getter
            Fabricante fabricante = fabricanteRepository.findById(dto.getFabricanteId()); // Use getter
            if (fabricante == null) {
                throw new ServiceException("Fabricante not found with ID: " + dto.getFabricanteId(), Response.Status.NOT_FOUND); // Use getter
            }
            betoneira.fabricante = fabricante;
        } else {
            betoneira.fabricante = null; // Permite remover a associação
        }

        betoneiraRepository.persist(betoneira);
        return toResponseDTO(betoneira);
    }

    @Transactional
    public void delete(Long id) {
        LOGGER.info("Deleting betoneira with ID: " + id);
        if (!betoneiraRepository.deleteById(id)) {
            throw new ServiceException("Betoneira not found with ID: " + id, Response.Status.NOT_FOUND);
        }
    }

    private BetoneiraResponseDTO toResponseDTO(Betoneira betoneira) {
        // Defensive programming: Null check for the main entity
        if (betoneira == null) {
            return null; // Or throw an exception, or log an error, depending on your error handling policy
        }

        BetoneiraResponseDTO responseDTO = new BetoneiraResponseDTO();
        responseDTO.setId(betoneira.id);  // Use setId()
        responseDTO.setMarca(betoneira.marca);  // Use setMarca()
        responseDTO.setModelo(betoneira.modelo);  // Use setModelo()
        responseDTO.setCapacidade(betoneira.capacidade);  // Use setCapacidade()
        responseDTO.setTipo(betoneira.tipo);  // Use setTipo()
        if (betoneira.getFabricante() != null) {  // Use getFabricante()
            FabricanteResponseDTO fabricanteDTO = new FabricanteResponseDTO();
            fabricanteDTO.setId(betoneira.getFabricante().getId());  // Use getId()
            fabricanteDTO.setNome(betoneira.getFabricante().getNome());  // Use getNome()
            fabricanteDTO.setCnpj(betoneira.getFabricante().getCnpj());  // Use getCnpj()
            responseDTO.setFabricante(fabricanteDTO);  // Use setFabricante()
        }
        return responseDTO;
    }
}