package com.batook.ex2.data;

import com.batook.ex2.controller.MyController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OraRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(OraRepository.class);

    private Connection conn;
    private Statement stmnt;

    public OraRepository(DataSource dataSource) throws SQLException {
        Locale.setDefault(Locale.ENGLISH);
        conn = dataSource.getConnection();
        stmnt = conn.createStatement();
        LOGGER.info(">>> stmnt{}",stmnt);
        //        InitialContext ctx = new InitialContext();
        //        DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/OracleDS");
        //
        //        DataSource ds = new JndiDataSourceLookup().getDataSource("java:comp/env/jdbc/OracleDS");
    }

    @Cacheable("Banner")
    public List<String> getBanner() throws SQLException {
        List<String> list = new ArrayList<>();
        ResultSet rs = stmnt.executeQuery("select banner from v$version");
        while (rs.next()) {
            LOGGER.info(">>> {}", rs.getString(1));
            list.add(rs.getString(1));
        }
        return list;
    }
}
