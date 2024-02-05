package com.nanoka.weblibreria.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Conexion {
    private Connection con;
    
    public void conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/libreria_jsp?useUnicode=true&characterEncoding=UTF-8","root","password");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar a la base de datos: "+e.getMessage());
        }
    }
    
    public void desconectar() {
        try {
            if(!con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: "+e.getMessage());
        }
    }
}