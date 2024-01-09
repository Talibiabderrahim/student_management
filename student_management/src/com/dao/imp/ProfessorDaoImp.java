package com.dao.imp;

import com.dao.Connexion;
import com.dao.ProfessorDao;
import com.entities.Department;
import com.entities.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfessorDaoImp implements ProfessorDao {
    private Connection cnx = Connexion.getConnexion();

    @Override
    public boolean create(Professor o) {
        if(cnx == null) return false;

        try {
            PreparedStatement ps = cnx.prepareStatement("INSERT INTO professors (fname,lname,login,passwrd,department_id) VALUES (?,?,?,?,?);",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, o.getFname());
            ps.setString(2, o.getLname());
            ps.setString(3, o.getLogin());
            ps.setString(4, o.getPasswrd());
            ps.setInt(5, o.getDepartment().getId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) o.setId(rs.getInt(1));

            rs.close();
            ps.close();

            return true;
        }catch(Exception e)
        {
            System.out.println("Error - ProfessorDaoImp > create() " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Professor o) {
        if(cnx == null) return false;

        try {
            PreparedStatement ps = cnx.prepareStatement("UPDATE professors SET fname = ?, lname = ?, login = ?, passwrd = ?, department_id = ? WHERE id = ?;");
            ps.setString(1,o.getFname());
            ps.setString(2,o.getLname());
            ps.setString(3, o.getLogin());
            ps.setString(4, o.getPasswrd());
            ps.setInt(5,o.getDepartment().getId());

            ps.executeUpdate();

            ps.close();

            return true;
        }catch(Exception e)
        {
            System.out.println("Error - ProfessorDaoImp > update() " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean saveOrUpdate(Professor o) {
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
            StringBuilder query = new StringBuilder("DELETE FROM professors WHERE ");

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
            System.out.println("Error - ProfessorDaoImp > delete() " + e.getMessage());
        }
        return false;
    }

    @Override
    public Professor getById(Map<String, Integer> keys) {
        if(cnx == null) return null;

        try {
            StringBuilder query = new StringBuilder("SELECT * FROM professors WHERE ");

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

            Professor p = null;

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Department d = new Department();
                d.setId(rs.getInt(6));

                p = new Professor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), d);
            }
            rs.close();
            ps.close();

            return p;
        }catch(Exception e)
        {
            System.out.println("Error - ProfessorDaoImp > getById() " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Professor> getAll() {

        try {
            PreparedStatement ps = cnx.prepareStatement("SELECT * FROM professors ;");

            Professor p = null;
            List<Professor> lp = new ArrayList<Professor>();

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Department d = new Department();
                d.setId(rs.getInt(6));

                p = new Professor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), d);
                lp.add(p);
            }

            rs.close();
            ps.close();

            return lp;
        }catch(Exception e)
        {
            System.out.println("Error - ProfessorDaoImp > getAll() " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean isProfessor(String login, String password) {
        if (cnx == null || login == null || login.isEmpty() || password == null || password.isEmpty())
            return false;

        try {
            PreparedStatement ps = cnx.prepareStatement("SELECT * FROM  professors WHERE login = ? AND passwrd = ?;",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, login);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ps.close();
                return true;
            }

            ps.close();

        }catch(Exception e)
        {
            System.out.println("Error - ProfessorDaoImp > isProfessor() " + e.getMessage());
        }
        return false;

    }
}
