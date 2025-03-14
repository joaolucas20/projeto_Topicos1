package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.EstadoDTO;
import br.unitins.tp1.model.Estado;

public interface EstadoService {

    Estado create(EstadoDTO estado);
    void update(long id, Estado estado);
    void delete(long id);
    Estado findById(long id);
    Estado findBySigla(String sigla);
    List<Estado> findAll();
    
}
