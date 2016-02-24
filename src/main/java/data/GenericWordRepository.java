package data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenericWordRepository extends JpaRepository<GenericWordEntity, String> {
}
