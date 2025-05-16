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
     BetoneiraRepository repository;
 
     @Inject
     FabricanteRepository fabricanteRepository;
 
     public List<BetoneiraResponseDTO> getAll() {
         LOGGER.info("Getting all betoneiras");
         return repository.listAll()
                 .stream()
                 .map(this::toResponseDTO)
                 .collect(Collectors.toList());
     }
 
     public BetoneiraResponseDTO findById(Long id) {
         LOGGER.info("Finding betoneira by ID: " + id);
         Betoneira betoneira = repository.findById(id);
         if (betoneira == null) {
             throw new ServiceException("Betoneira not found with ID: " + id, Response.Status.NOT_FOUND);
         }
         return toResponseDTO(betoneira);
     }
 
     public List<BetoneiraResponseDTO> findByModelo(String modelo) {
         LOGGER.info("Finding betoneiras by modelo: " + modelo);
         return repository.find("modelo", modelo)
                 .stream()
                 .map(this::toResponseDTO)
                 .collect(Collectors.toList());
     }
 
     @Transactional
     public BetoneiraResponseDTO create(BetoneiraRequestDTO dto) {
         LOGGER.info("Creating a new betoneira");
         Betoneira betoneira = new Betoneira();
         betoneira.setMarca(dto.getMarca());
         betoneira.setModelo(dto.getModelo());
         betoneira.setCapacidade(dto.getCapacidade());
         betoneira.setTipo(dto.getTipo());
 
         // Fetch the Fabricante entity
         if (dto.getFabricanteId() != null) {
             Fabricante fabricante = fabricanteRepository.findById(dto.getFabricanteId());
             if (fabricante == null) {
                 throw new ServiceException("Fabricante not found with ID: " + dto.getFabricanteId(), Response.Status.NOT_FOUND);
             }
             betoneira.setFabricante(fabricante);
         }
 
         repository.persist(betoneira);
         return toResponseDTO(betoneira);
     }
 
     @Transactional
     public BetoneiraResponseDTO update(Long id, BetoneiraRequestDTO dto) {
         LOGGER.info("Updating betoneira with ID: " + id);
         Betoneira betoneira = repository.findById(id);
         if (betoneira == null) {
             throw new ServiceException("Betoneira not found with ID: " + id, Response.Status.NOT_FOUND);
         }
         betoneira.setMarca(dto.getMarca());
         betoneira.setModelo(dto.getModelo());
         betoneira.setCapacidade(dto.getCapacidade());
         betoneira.setTipo(dto.getTipo());
 
         // Fetch the Fabricante entity
         if (dto.getFabricanteId() != null) {
             Fabricante fabricante = fabricanteRepository.findById(dto.getFabricanteId());
             if (fabricante == null) {
                 throw new ServiceException("Fabricante not found with ID: " + dto.getFabricanteId(), Response.Status.NOT_FOUND);
             }
             betoneira.setFabricante(fabricante);
         }
 
         repository.persist(betoneira);
         return toResponseDTO(betoneira);
     }
 
     @Transactional
     public void delete(Long id) {
         LOGGER.info("Deleting betoneira with ID: " + id);
         if (!repository.deleteById(id)) {
             throw new ServiceException("Betoneira not found with ID: " + id, Response.Status.NOT_FOUND);
         }
     }
 
     private BetoneiraResponseDTO toResponseDTO(Betoneira betoneira) {
         BetoneiraResponseDTO responseDTO = new BetoneiraResponseDTO();
         if (betoneira != null) {
             responseDTO.setId(betoneira.getId());
             responseDTO.setMarca(betoneira.getMarca());
             responseDTO.setModelo(betoneira.getModelo());
             responseDTO.setCapacidade(betoneira.getCapacidade());
             responseDTO.setTipo(betoneira.getTipo());
             if (betoneira.getFabricante() != null) {
                 responseDTO.setFabricante(toFabricanteResponseDTO(betoneira.getFabricante()));
             }
         }
         return responseDTO;
     }
 
     private FabricanteResponseDTO toFabricanteResponseDTO(Fabricante fabricante) {
         FabricanteResponseDTO dto = new FabricanteResponseDTO();
         dto.setId(fabricante.getId());
         dto.setNome(fabricante.getNome());
         dto.setCnpj(fabricante.getCnpj());
         return dto;
     }
 }