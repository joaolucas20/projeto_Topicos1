package br.unitins.tp1.repository;

import br.unitins.tp1.model.Betoneira;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface BetoneiraRepository extends PanacheRepository<Betoneira> {
}