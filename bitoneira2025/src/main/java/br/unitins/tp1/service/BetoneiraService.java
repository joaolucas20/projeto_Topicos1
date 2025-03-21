package br.unitins.tp1.service;

import br.unitins.tp1.dto.BetoneiraRequestDTO;
import br.unitins.tp1.dto.BetoneiraResponseDTO;
import br.unitins.tp1.model.Betoneira;
import br.unitins.tp1.repository.BetoneiraRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BetoneiraService {

    @Inject
    BetoneiraRepository repository;

    public List<BetoneiraResponseDTO> getAll() {
        return repository.listAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public BetoneiraResponseDTO findById(Long id) {
        return toResponseDTO(repository.findById(id));
    }

    public List<BetoneiraResponseDTO> findByModelo(String modelo) {
        return repository.find("modelo", modelo)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public BetoneiraResponseDTO create(BetoneiraRequestDTO dto) {
        Betoneira betoneira = new Betoneira();
        betoneira.marca = dto.marca;
        betoneira.modelo = dto.modelo;
        betoneira.capacidade = dto.capacidade;
        betoneira.tipo = dto.tipo;
        repository.persist(betoneira);
        return toResponseDTO(betoneira);
    }

    @Transactional
    public BetoneiraResponseDTO update(Long id, BetoneiraRequestDTO dto) {
        Betoneira betoneira = repository.findById(id);
        betoneira.marca = dto.marca;
        betoneira.modelo = dto.modelo;
        betoneira.capacidade = dto.capacidade;
        betoneira.tipo = dto.tipo;
        repository.persist(betoneira);
        return toResponseDTO(betoneira);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private BetoneiraResponseDTO toResponseDTO(Betoneira betoneira) {
        BetoneiraResponseDTO responseDTO = new BetoneiraResponseDTO();
        responseDTO.id = betoneira.id;
        responseDTO.marca = betoneira.marca;
        responseDTO.modelo = betoneira.modelo;
        responseDTO.capacidade = betoneira.capacidade;
        responseDTO.tipo = betoneira.tipo;
        return responseDTO;
    }
}