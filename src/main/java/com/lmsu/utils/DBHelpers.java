package com.lmsu.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

/*
 * @author NDungx
 */
public class DBHelpers implements Serializable {

    public static Connection makeConnection()
            throws NamingException, SQLException {
        Context currentContext = new InitialContext();
        Context tomcatContext = (Context) currentContext.lookup("java:comp/env");
        DataSource ds = (DataSource) tomcatContext.lookup("DS");
        Connection con = ds.getConnection();
        return con;
    }
}
