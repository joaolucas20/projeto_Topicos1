// betoneira-api/src/main/java/br/unitins/tp1/repository/FabricanteRepository.java
package br.unitins.tp1.repository;

import br.unitins.tp1.model.Fabricante;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface FabricanteRepository extends PanacheRepository<Fabricante> {
}