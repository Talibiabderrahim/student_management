package com.dao.imp;

import com.dao.Connexion;
import com.dao.DepartmentDao;
import com.entities.Department;
import com.entities.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentDaoImp implements DepartmentDao {
    private Connection cnx = Connexion.getConnexion();

    @Override
    public boolean create(Department o) {

        if(cnx == null) return false;

        try {
            PreparedStatement ps = cnx.prepareStatement("INSERT INTO departments (dname) VALUES (?);",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, o.getDname());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) o.setId(rs.getInt(1));

            rs.close();
            ps.close();

            return true;
        }catch(Exception e)
        {
            System.out.println("Error - DepartmentDaoImp > create() " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Department o) {

        if(cnx == null) return false;

        try {
            PreparedStatement ps = cnx.prepareStatement("UPDATE departments SET dname = ? WHERE id = ?;");
            ps.setString(1,o.getDname());
            ps.setInt(3,o.getId());

            ps.executeUpdate();

            ps.close();

            return true;
        }catch(Exception e)
        {
            System.out.println("Error - DepartmentDaoImp > update() " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean saveOrUpdate(Department o) {

        if(cnx == null) return false;

        Map<String, Integer> keys = new HashMap<>();
        keys.put("id", Integer.valueOf(o.getId()));

        if(o.getId() != 0 && this.getById(keys) != null)
            return this.update(o);

        return this.create(o);
    }

    @Override
    public boolean delete(Map<String, Integer> keys) {
        if(cnx == null) return false;

        try {
            StringBuilder query = new StringBuilder("DELETE FROM departments WHERE ");

            int size = keys.size(), count = 0;
            for (String key : keys.keySet()) {
                if (count < size - 1)
                    query.append(key).append(" = ? AND ");
                else
                    query.append(key).append(" = ? ;");
                count++;
            }

            PreparedStatement ps = cnx.prepareStatement(query.toString());

            count = 1;
            for (Integer id : keys.values()) {
                ps.setInt(count, id);
                count++;
            }

            ps.executeUpdate();

            ps.close();

            return true;
        }catch(Exception e)
        {
            System.out.println("Error - DepartmentDaoImp > delete() " + e.getMessage());
        }
        return false;
    }

    @Override
    public Department getById(Map<String, Integer> keys) {

        if(cnx == null) return null;

        try {
            StringBuilder query = new StringBuilder("SELECT * FROM departments WHERE ");

            int size = keys.size(), count = 0;
            for (String key : keys.keySet()) {
                if (count < size - 1)
                    query.append(key).append(" = ? AND ");
                else
                    query.append(key).append(" = ? ;");
                count++;
            }

            PreparedStatement ps = cnx.prepareStatement(query.toString());

            count = 1;
            for (Integer id : keys.values()) {
                ps.setInt(count, id);
                count++;
            }

            Department d = null;

            ResultSet rs = ps.executeQuery();
            if(rs.next())
                d = new Department(rs.getInt(1),rs.getString(2));

            rs.close();
            ps.close();

            return d;
        }catch(Exception e)
        {
            System.out.println("Error - DepartmentDaoImp > getById() " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Department> getAll() {
        if(cnx == null) return null;

        try {
            PreparedStatement ps = cnx.prepareStatement("SELECT * FROM departments ;");

            Department d = null;
            List<Department> ld = new ArrayList<Department>();

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                d = new Department(rs.getInt(1), rs.getString(2));
                ld.add(d);
            }

            rs.close();
            ps.close();

            return ld;
        }catch(Exception e)
        {
            System.out.println("Error - DepartmentDaoImp > getAll() " + e.getMessage());
        }
        return null;
    }

    @Override
    public int getDepartmentId(String dname) {
        if(cnx == null) return 0;

        try {
            int id = 0;

            PreparedStatement ps = cnx.prepareStatement("SELECT * FROM departments WHERE dname = ? ;");
            ps.setString(1, dname);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            rs.close();
            ps.close();

            return id;
        }catch(Exception e)
        {
            System.out.println("Error - DepartmentDaoImp > getDepartmentId() " + e.getMessage());
        }
        return 0;
    }
}
