package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("""
                       UPDATE users AS usr SET name=:name, email=:email, password=:password,
                       registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) == 0) {
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
                """, new UserWithRoleExtractor(), id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("""
                        SELECT * FROM users LEFT JOIN user_role ON users.id = user_role.user_id WHERE email=?
                """, new UserWithRoleExtractor(), email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("""
                    SELECT * FROM users LEFT JOIN user_role ON users.id = user_role.user_id ORDER BY name, email
                """, new UserWithRoleExtractor());
    }

    private void insertRoles(Set<Role> roles, int userId) {
        jdbcTemplate.update("DELETE FROM user_role WHERE user_id=?", userId);
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

    private static class UserWithRoleExtractor implements ResultSetExtractor<List<User>> {

        @Override
        public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Integer, User> users = new LinkedHashMap<>();
            Set<Role> roles = new HashSet<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                User user = users.get(id);
                if (user == null) {
                    user = new User();
                    user.setId(id);
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRegistered(rs.getTimestamp("registered"));
                    user.setEnabled(rs.getBoolean("enabled"));
                    user.setCaloriesPerDay(rs.getInt("calories_per_day"));
                    users.put(id, user);
                }
                String userId = rs.getString("user_id");
                if (userId != null ) {
                    roles.add(Role.valueOf(rs.getString("role")));
                    users.get(id).setRoles(roles);
                } else {
                    roles.clear();
                    user.setRoles(EnumSet.noneOf(Role.class));
                }
            }
            return new ArrayList<>(users.values());
        }
    }
}
