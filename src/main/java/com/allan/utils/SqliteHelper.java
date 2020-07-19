package com.allan.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.beans.PropertyVetoException;


public class SqliteHelper {

    private static JdbcTemplate jdbcTemplate;

    public static JdbcTemplate getJdbcTemplate(){
        if (jdbcTemplate == null) {
            ComboPooledDataSource  dataSource = new ComboPooledDataSource();
//            dataSource.setDriverClassName();

            try {
                dataSource.setDriverClass("org.sqlite.JDBC");
//            dataSource.setUrl();
                dataSource.setJdbcUrl("jdbc:sqlite:eyePro.db");
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
            jdbcTemplate = new JdbcTemplate(dataSource);
        }
        return jdbcTemplate;
    }
}