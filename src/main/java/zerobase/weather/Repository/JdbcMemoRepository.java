package zerobase.weather.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import zerobase.weather.domain.Memo;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Repository //레퍼지토리 파일이다.
public class JdbcMemoRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired //알아서 application.properties 파일에서 가져옴
    public JdbcMemoRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Memo save(Memo memo) {
        String sql = "insert into memo values (?,?) ";

        jdbcTemplate.update(sql, memo.getId(), memo.getText());
        return memo;
    }

    public List<Memo> findAll(){
        String sql = "SELECT * FROM memo";
        return jdbcTemplate.query(sql,memoRowMapper());
    }

    public Optional<Memo> findById(int id){
        String sql = "SELECT * FROM memo WHERE id = ?";
        return jdbcTemplate.query(sql,memoRowMapper(),id).stream().findFirst();
    }

    //resultset 형태로 가져온데이터 memo로 매퍼해준다.
    private RowMapper<Memo> memoRowMapper() {
        return (rs, rowNum) -> new Memo(rs.getInt("id"),rs.getString("text"));
    }
}
