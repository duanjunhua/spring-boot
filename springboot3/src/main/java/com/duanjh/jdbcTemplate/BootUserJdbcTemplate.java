package com.duanjh.jdbcTemplate;

import com.duanjh.jpa.entity.BootUser;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-06 周五 15:26
 * @Version: v1.0
 * @Description: JdbcTemplate使用
 */
@Component
public class BootUserJdbcTemplate {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer counts(){
        return jdbcTemplate.queryForObject("select count(*) from t_boot_user", Integer.class);
    }

    public BootUser selectByName(String username){
        return jdbcTemplate.queryForObject("select * from t_boot_user where username = ?", (rs, i) -> {
            BootUser user = new BootUser();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            return user;
        }, username);
    }

    public List<BootUser> list(){
        return jdbcTemplate.query("select * from t_boot_user", (rs, i) -> {
            BootUser user = new BootUser();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            return user;
        });
    }

    /**
     * 使用RowMapper
     */
    private final RowMapper<BootUser> bootUserRowMapper = (rs, row) -> {
        BootUser user = new BootUser();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        return user;
    };

    /**
     * 使用RowMapper
     * @return
     */
    public List<BootUser> findAll(){
        return jdbcTemplate.query("select * from t_boot_user where username = ?", bootUserRowMapper);
    }

    /**
     * 批量更新
     */
    public void batchUpdate(List<BootUser> updates){
        if(CollectionUtils.isEmpty(updates)) return;
        jdbcTemplate.batchUpdate("update t_boot_user set password = ?, email = ? where id = ?", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                // 表示更新密码与邮箱
                ps.setString(1, updates.get(i).getPassword());
                ps.setString(2, updates.get(i).getEmail());

                // 主键
                ps.setLong(3, updates.get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return updates.size();
            }
        });
    }

    /**
     * 批量更新
     */
    public void batchAdd(List<BootUser> updates){
        if(CollectionUtils.isEmpty(updates)) return ;
        jdbcTemplate.batchUpdate("insert into t_boot_user(username, password, email) values(?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                // 表示更新密码与邮箱
                ps.setString(1, updates.get(i).getUsername());
                ps.setString(2, updates.get(i).getPassword());
                ps.setString(3, updates.get(i).getEmail());
            }

            @Override
            public int getBatchSize() {
                return updates.size();
            }
        });
    }
}
