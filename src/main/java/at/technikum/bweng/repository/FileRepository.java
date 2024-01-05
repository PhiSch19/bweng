package at.technikum.bweng.repository;

import at.technikum.bweng.entity.File;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FileRepository extends CrudRepository<File, UUID> {
}
