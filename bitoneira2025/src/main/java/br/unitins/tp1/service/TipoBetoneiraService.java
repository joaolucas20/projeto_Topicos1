package br.unitins.tp1.service;

    import br.unitins.tp1.exception.ServiceException; // Add this line
    import br.unitins.tp1.model.TipoBetoneiraEntity;
    import br.unitins.tp1.repository.TipoBetoneiraRepository;
    import jakarta.enterprise.context.ApplicationScoped;
    import jakarta.inject.Inject;
    import jakarta.transaction.Transactional;
    import jakarta.ws.rs.core.Response;
    import java.util.List;
    import java.util.logging.Logger;

    @ApplicationScoped
    public class TipoBetoneiraService {

        private static final Logger LOGGER = Logger.getLogger(TipoBetoneiraService.class.getName());

        @Inject
        TipoBetoneiraRepository repository;

        public List<TipoBetoneiraEntity> getAll() {
            LOGGER.info("Getting all TipoBetoneiraEntity");
            return repository.listAll();
        }

        public TipoBetoneiraEntity findById(Long id) {
            LOGGER.info("Finding TipoBetoneiraEntity by ID: " + id);
            TipoBetoneiraEntity entity = repository.findById(id);
            if (entity == null) {
                throw new ServiceException("TipoBetoneiraEntity not found with ID: " + id, Response.Status.NOT_FOUND);
            }
            return entity;
        }

        @Transactional
        public TipoBetoneiraEntity create(TipoBetoneiraEntity tipoBetoneira) {
            LOGGER.info("Creating a new TipoBetoneiraEntity");
            repository.persist(tipoBetoneira);
            return tipoBetoneira;
        }

        @Transactional
        public TipoBetoneiraEntity update(Long id, TipoBetoneiraEntity tipoBetoneira) {
            LOGGER.info("Updating TipoBetoneiraEntity with ID: " + id);
            TipoBetoneiraEntity entity = repository.findById(id);
            if (entity == null) {
                throw new ServiceException("TipoBetoneiraEntity not found with ID: " + id, Response.Status.NOT_FOUND);
            }
            entity.setTipo(tipoBetoneira.getTipo());
            repository.persist(entity);
            return entity;
        }

        @Transactional
        public void delete(Long id) {
            LOGGER.info("Deleting TipoBetoneiraEntity with ID: " + id);
            if (!repository.deleteById(id)) {
                throw new ServiceException("TipoBetoneiraEntity not found with ID: " + id, Response.Status.NOT_FOUND);
            }
        }
    }