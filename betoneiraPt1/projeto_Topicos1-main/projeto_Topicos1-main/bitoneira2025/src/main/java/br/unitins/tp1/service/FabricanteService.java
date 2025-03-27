// betoneira-api/src/main/java/br/unitins/tp1/service/FabricanteService.java
package br.unitins.tp1.service;

import br.unitins.tp1.dto.FabricanteRequestDTO;
import br.unitins.tp1.dto.FabricanteResponseDTO;
import br.unitins.tp1.model.Fabricante;
import br.unitins.tp1.repository.FabricanteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FabricanteService {

    @Inject
    FabricanteRepository repository;

    public List<FabricanteResponseDTO> getAll() {
        return repository.listAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public FabricanteResponseDTO findById(Long id) {
        Fabricante fabricante = repository.findById(id);
        if (fabricante == null) {
            throw new NotFoundException("Fabricante não encontrado com o ID: " + id);
        }
        return toResponseDTO(fabricante);
    }

    @Transactional
    public FabricanteResponseDTO create(FabricanteRequestDTO dto) {
        Fabricante fabricante = new Fabricante();
        fabricante.nome = dto.nome;
        fabricante.cnpj = dto.cnpj;
        repository.persist(fabricante);
        return toResponseDTO(fabricante);
    }

    @Transactional
    public FabricanteResponseDTO update(Long id, FabricanteRequestDTO dto) {
        Fabricante fabricante = repository.findById(id);
        if (fabricante == null) {
            throw new NotFoundException("Fabricante não encontrado com o ID: " + id);
        }
        fabricante.nome = dto.nome;
        fabricante.cnpj = dto.cnpj;
        repository.persist(fabricante);
        return toResponseDTO(fabricante);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Fabricante não encontrado com o ID: " + id);
        }
    }

    private FabricanteResponseDTO toResponseDTO(Fabricante fabricante) {
        FabricanteResponseDTO responseDTO = new FabricanteResponseDTO();
        responseDTO.id = fabricante.id;
        responseDTO.nome = fabricante.nome;
        responseDTO.cnpj = fabricante.cnpj;
        return responseDTO;
    }
}