package com.example.demo.repositories;

import com.example.demo.model.User;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class UserRepository {

    private final JdbcTemplate jdbc;
    private final DatabaseSQLService databaseSQLService;

    public List<User> findAll() {
        String sql = databaseSQLService.getFindAll();

        RowMapper<User> userRowMapper = (r, i) -> {
            User rowObject = new User();
            rowObject.setId(r.getInt("id"));
            rowObject.setFirstName(r.getString("firstName"));
            rowObject.setLastName(r.getString("lastName"));
            return rowObject;
        };

        return jdbc.query(sql, userRowMapper);
    }

    public User save(User user) {
        String sql = databaseSQLService.getSave();
        jdbc.update(sql, user.getFirstName(), user.getLastName());
        return user;
    }

    public void deleteById(int id) {
        String sql = databaseSQLService.getDeleteById();
        jdbc.update(sql, id);
    }

    public void update(User user) {
        String sql = databaseSQLService.getUpdate();
        jdbc.update(sql, user.getFirstName(), user.getLastName(), user.getId());
    }

    public User getUser(int id) {
        String sql = databaseSQLService.getGetUser() + id;
        RowMapper<User> userRowMapper = (r, i) -> {
            User rowObject = new User();
            rowObject.setId(r.getInt("id"));
            rowObject.setFirstName(r.getString("firstName"));
            rowObject.setLastName(r.getString("lastName"));
            return rowObject;
        };
        return jdbc.query(sql, userRowMapper).get(0);
    }
}
