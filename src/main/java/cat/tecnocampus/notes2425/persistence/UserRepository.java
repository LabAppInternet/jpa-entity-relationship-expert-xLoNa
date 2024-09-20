package cat.tecnocampus.notes2425.persistence;

import cat.tecnocampus.notes2425.application.dtos.UserDTO;
import cat.tecnocampus.notes2425.domain.UserLab;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcClient jdbcClient;

    public UserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<UserDTO> findByUsername(String username) {
        String query = "select * from user_lab where username = ?";
        return jdbcClient.sql(query).param(username).query(UserDTO.class).optional();
    }

    public Optional<UserLab> findById(long id) {
        String query = "select * from user_lab where id = ?";
        return jdbcClient.sql(query).param(id).query(UserLab.class).optional();
    }
}
