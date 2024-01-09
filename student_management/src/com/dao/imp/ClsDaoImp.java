package com.dao.imp;

import com.dao.ClsDao;
import com.dao.Connexion;
import com.entities.Cls;
import com.entities.Professor;
import com.entities.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClsDaoImp implements ClsDao {
    private Connection cnx = Connexion.getConnexion();

    @Override
    public boolean create(Cls o) {
        if(cnx == null) return false;

        try {
            PreparedStatement ps = cnx.prepareStatement("INSERT INTO classes (cname, professor_id) VALUES (?,?);",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, o.getCname());
            ps.setInt(2, o.getProfessor().getId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) o.setId(rs.getInt(1));

            rs.close();
            ps.close();

            return true;
        }catch(Exception e)
        {
            System.out.println("Error - ClsDaoImp > create() " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Cls o) {
        if(cnx == null) return false;

        try {
            PreparedStatement ps = cnx.prepareStatement("UPDATE classes SET cname = ?, professor_id = ? WHERE id = ?;");
            ps.setString(1,o.getCname());
            ps.setInt(2,o.getProfessor().getId());
            ps.setInt(3,o.getId());

            ps.executeUpdate();

            ps.close();

            return true;
        }catch(Exception e)
        {
            System.out.println("Error - ClassDaoImp > update() " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean saveOrUpdate(Cls o) {
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
            StringBuilder query = new StringBuilder("DELETE FROM classes WHERE ");

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
            System.out.println("Error - ClsDaoImp > delete() " + e.getMessage());
        }
        return false;
    }

    @Override
    public Cls getById(Map<String, Integer> keys) {
        if(cnx == null) return null;

        try {
            StringBuilder query = new StringBuilder("SELECT * FROM classes WHERE ");

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

            Cls c = null;
            Professor p = new Professor();

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                p.setId(rs.getInt(3));
                c = new Cls(rs.getInt(1), rs.getString(2), p);
            }
            rs.close();
            ps.close();

            return c;
        }catch(Exception e)
        {
            System.out.println("Error - ClsDaoImp > getById() " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Cls> getAll() {
        try {
            PreparedStatement ps = cnx.prepareStatement("SELECT * FROM classes ;");

            Cls c = null;
            List<Cls> lc = new ArrayList<Cls>();

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Professor p = new Professor();
                p.setId(rs.getInt(3));

                c = new Cls(rs.getInt(1), rs.getString(2), p);
                lc.add(c);
            }

            rs.close();
            ps.close();

            return lc;
        }catch(Exception e)
        {
            System.out.println("Error - ClsDaoImp > getAll() " + e.getMessage());
        }
        return null;
    }
}
