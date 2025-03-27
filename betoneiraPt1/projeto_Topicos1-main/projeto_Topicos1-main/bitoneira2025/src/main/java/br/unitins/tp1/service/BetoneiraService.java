// betoneira-api/src/main/java/br/unitins/tp1/service/BetoneiraService.java
package br.unitins.tp1.service;

import br.unitins.tp1.dto.BetoneiraRequestDTO;
import br.unitins.tp1.dto.BetoneiraResponseDTO;
import br.unitins.tp1.dto.FabricanteResponseDTO;
import br.unitins.tp1.model.Betoneira;
import br.unitins.tp1.model.Fabricante;
import br.unitins.tp1.repository.BetoneiraRepository;
import br.unitins.tp1.repository.FabricanteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BetoneiraService {

    @Inject
    BetoneiraRepository betoneiraRepository;

    @Inject
    FabricanteRepository fabricanteRepository;

    public List<BetoneiraResponseDTO> getAll() {
        return betoneiraRepository.listAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public BetoneiraResponseDTO findById(Long id) {
        Betoneira betoneira = betoneiraRepository.findById(id);
        if (betoneira == null) {
            throw new NotFoundException("Betoneira não encontrada com o ID: " + id);
        }
        return toResponseDTO(betoneira);
    }

    public List<BetoneiraResponseDTO> findByModelo(String modelo) {
        return betoneiraRepository.find("modelo", modelo)
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

        if (dto.fabricanteId != null) {
            Fabricante fabricante = fabricanteRepository.findById(dto.fabricanteId);
            if (fabricante == null) {
                throw new NotFoundException("Fabricante não encontrado com o ID: " + dto.fabricanteId);
            }
            betoneira.fabricante = fabricante;
        }

        betoneiraRepository.persist(betoneira);
        return toResponseDTO(betoneira);
    }

    @Transactional
    public BetoneiraResponseDTO update(Long id, BetoneiraRequestDTO dto) {
        Betoneira betoneira = betoneiraRepository.findById(id);
        if (betoneira == null) {
            throw new NotFoundException("Betoneira não encontrada com o ID: " + id);
        }
        betoneira.marca = dto.marca;
        betoneira.modelo = dto.modelo;
        betoneira.capacidade = dto.capacidade;
        betoneira.tipo = dto.tipo;

        if (dto.fabricanteId != null) {
            Fabricante fabricante = fabricanteRepository.findById(dto.fabricanteId);
            if (fabricante == null) {
                throw new NotFoundException("Fabricante não encontrado com o ID: " + dto.fabricanteId);
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
        if (!betoneiraRepository.deleteById(id)) {
            throw new NotFoundException("Betoneira não encontrada com o ID: " + id);
        }
    }

    private BetoneiraResponseDTO toResponseDTO(Betoneira betoneira) {
        BetoneiraResponseDTO responseDTO = new BetoneiraResponseDTO();
        responseDTO.id = betoneira.id;
        responseDTO.marca = betoneira.marca;
        responseDTO.modelo = betoneira.modelo;
        responseDTO.capacidade = betoneira.capacidade;
        responseDTO.tipo = betoneira.tipo;
        if (betoneira.fabricante != null) {
            FabricanteResponseDTO fabricanteDTO = new FabricanteResponseDTO();
            fabricanteDTO.id = betoneira.fabricante.id;
            fabricanteDTO.nome = betoneira.fabricante.nome;
            fabricanteDTO.cnpj = betoneira.fabricante.cnpj;
            responseDTO.fabricante = fabricanteDTO;
        }
        return responseDTO;
    }
}