package ekabadi_backend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import ekabadi_backend.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByFirebaseUid(String firebaseUid);
    Optional<User> findByPhone(String phone);
}