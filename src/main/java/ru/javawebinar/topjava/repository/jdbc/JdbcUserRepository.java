package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public User save(@NotNull User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("""
                       UPDATE users SET name=:name, email=:email, password=:password,
                       registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) != 0) {
            deleteRoles(user.getId());
        } else {
            return null;
        }
        insertRoles(user.getRoles(), user.getId());
        return user;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("""
                       SELECT * FROM users
                       LEFT JOIN user_role ON users.id = user_role.user_id
                       WHERE id=?
                """, userWithRoleExtractor, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(@Email @NotNull @Size(max = 128) String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("""
                        SELECT * FROM users LEFT JOIN user_role ON users.id = user_role.user_id WHERE email=?
                """, userWithRoleExtractor, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("""
                    SELECT * FROM users LEFT JOIN user_role ON users.id = user_role.user_id ORDER BY name, email
                """, userWithRoleExtractor);
    }

    private void deleteRoles(int userId) {
        jdbcTemplate.update("DELETE FROM user_role WHERE user_id=?", userId);
    }

    private void insertRoles(Set<Role> roles, int userId) {
        jdbcTemplate.batchUpdate("""
                INSERT INTO user_role(user_id, role) VALUES (?, ?)
                """, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Role role = (Role) roles.toArray()[i];
                ps.setInt(1, userId);
                ps.setString(2, role.name());
            }

            @Override
            public int getBatchSize() {
                return roles.size();
            }
        });
    }

    private final ResultSetExtractor<List<User>> userWithRoleExtractor = rse -> {
        Map<Integer, User> users = new LinkedHashMap<>();
        Set<Role> roles = new HashSet<>();
        while (rse.next()) {
            int id = rse.getInt("id");
            User user = users.get(id);
            if (user == null) {
                user = new User();
                user.setId(id);
                user.setName(rse.getString("name"));
                user.setEmail(rse.getString("email"));
                user.setPassword(rse.getString("password"));
                user.setRegistered(rse.getTimestamp("registered"));
                user.setEnabled(rse.getBoolean("enabled"));
                user.setCaloriesPerDay(rse.getInt("calories_per_day"));
                users.put(id, user);
            }
            String userId = rse.getString("user_id");
            if (userId != null) {
                roles.add(Role.valueOf(rse.getString("role")));
                users.get(id).setRoles(roles);
            } else {
                roles.clear();
                user.setRoles(EnumSet.noneOf(Role.class));
            }
        }
        return new ArrayList<>(users.values());

    };
}
