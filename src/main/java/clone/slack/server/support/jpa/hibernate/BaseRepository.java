package clone.slack.server.support.jpa.hibernate;

import clone.slack.server.support.domain.AggregateRoot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public abstract class BaseRepository<AR extends AggregateRoot<AR, ARID>, ARID, R extends JpaRepository<AR, ARID>> implements Repository<AR , ARID> {
    protected R repository;

    protected BaseRepository(R repository) {
        this.repository = repository;
    }

    public void add(AR root) {
        repository.save(root);
    }

    public AR find(ARID id) {
        return repository.findById(id).orElse(null);
    }

    public void remove(ARID id) {
        repository.deleteById(id);
    }

    public void remove(AR root) {
        repository.delete(root);
    }
}
