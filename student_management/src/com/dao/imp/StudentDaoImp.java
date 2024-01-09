package com.dao.imp;

import com.dao.Connexion;
import com.dao.StudentDao;
import com.entities.Result;
import com.entities.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDaoImp implements StudentDao {
    private Connection cnx = Connexion.getConnexion();

    @Override
    public boolean create(Student o) {
        if(cnx == null) return false;

        try {
            PreparedStatement ps = cnx.prepareStatement("INSERT INTO students (fname,lname) VALUES (?,?);",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, o.getFname());
            ps.setString(2, o.getLname());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) o.setId(rs.getInt(1));

            rs.close();
            ps.close();

            return true;
        }catch(Exception e)
        {
            System.out.println("Error - StudentDaoImp > create() " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Student o) {
        if(cnx == null) return false;

        try {
            PreparedStatement ps = cnx.prepareStatement("UPDATE students SET fname = ?, lname = ? WHERE id = ?;");
            ps.setString(1,o.getFname());
            ps.setString(2,o.getLname());
            ps.setInt(3,o.getId());

            ps.executeUpdate();

            ps.close();

            return true;
        }catch(Exception e)
        {
            System.out.println("Error - StudentDaoImp > update() " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean saveOrUpdate(Student o) {
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
            StringBuilder query = new StringBuilder("DELETE FROM students WHERE ");

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
            System.out.println("Error - StudentDaoImp > delete() " + e.getMessage());
        }
        return false;
    }

    @Override
    public Student getById(Map<String, Integer> keys) {
        if(cnx == null) return null;

        try {
            StringBuilder query = new StringBuilder("SELECT * FROM students WHERE ");

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

            Student s = null;

            ResultSet rs = ps.executeQuery();
            if(rs.next())
                s = new Student(rs.getInt(1),rs.getString(2),rs.getString(3));

            rs.close();
            ps.close();

            return s;
        }catch(Exception e)
        {
            System.out.println("Error - StudentDaoImp > getById() " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Student> getAll() {

        try {
            PreparedStatement ps = cnx.prepareStatement("SELECT * FROM students ;");

            Student s = null;
            List<Student> ls = new ArrayList<Student>();

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                s = new Student(rs.getInt(1), rs.getString(2), rs.getString(3));
                ls.add(s);
            }

            rs.close();
            ps.close();

            return ls;
        }catch(Exception e)
        {
            System.out.println("Error - StudentDaoImp > getAll() " + e.getMessage());
        }
        return null;
    }
}
