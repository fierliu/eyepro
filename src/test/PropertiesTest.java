import com.allan.dao.ConfigDao;
import com.allan.domain.Config;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;
//import org.junit.Test;

public class PropertiesTest {


    public static void main(String[] args) {

        ConfigDao configDao = new ConfigDao();
        Config config = new Config();
        config.setFreettsOn("Y");
        config.setSpvttsOn("Y");
        configDao.updateConfig(config);
    }

}
