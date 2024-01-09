package com.dao.imp;

import com.dao.Connexion;
import com.dao.ResultDao;
import com.entities.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultDaoImp implements ResultDao {
    private Connection cnx = Connexion.getConnexion();

    @Override
    public boolean create(Result o) {
        if(cnx == null) return false;

        try {
            PreparedStatement ps = cnx.prepareStatement("INSERT INTO results (student_id, class_id, grad) VALUES (?,?,?);",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, o.getStudent().getId());
            ps.setInt(2, o.getCls().getId());
            ps.setFloat(3, o.getGrad());


            ps.executeUpdate();

            ps.close();

            return true;
        }catch(Exception e)
        {
            System.out.println("Error - ResultDaoImp > create() " + e.getMessage());
        }
        return false;

    }

    @Override
    public boolean update(Result o) {
        if(cnx == null) return false;

        try {
            PreparedStatement ps = cnx.prepareStatement("UPDATE results SET grad = ? WHERE student_id = ? AND class_id = ?;");
            ps.setFloat(1, o.getGrad());
            ps.setInt(2, o.getStudent().getId());
            ps.setInt(3, o.getCls().getId());

            ps.executeUpdate();

            ps.close();

            return true;
        }catch(Exception e)
        {
            System.out.println("Error - ResultDaoImp > update() " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean saveOrUpdate(Result o) {

        if(cnx == null) return false;

        Map<String, Integer> keys = new HashMap<>();
        keys.put("student_id", Integer.valueOf(o.getStudent().getId()));
        keys.put("class_id", Integer.valueOf(o.getCls().getId()));

        if(o.getStudent().getId() != 0 && o.getCls().getId() != 0 && this.getById(keys) != null)
            return this.update(o);

        return this.create(o);
    }

    @Override
    public boolean delete(Map<String, Integer> keys) {

        if(cnx == null) return false;

        try {
            StringBuilder query = new StringBuilder("DELETE FROM results WHERE ");

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
            System.out.println("Error - ResultDaoImp > delete() " + e.getMessage());
        }
        return false;
    }

    @Override
    public Result getById(Map<String, Integer> keys) {

        if(cnx == null) return null;

        try {
            StringBuilder query = new StringBuilder("SELECT * FROM results WHERE ");

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

            Result r = null;

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt(1));

                Cls c = new Cls();
                c.setId(rs.getInt(2));

                r = new Result(s, c, rs.getFloat(3));
            }
            rs.close();
            ps.close();

            return r;
        }catch(Exception e)
        {
            System.out.println("Error - ResultDaoImp > getById() " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Result> getAll() {
        try {
            PreparedStatement ps = cnx.prepareStatement("SELECT * FROM results ;");

            Result r = null;
            List<Result> lr = new ArrayList<Result>();

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt(1));

                Cls c = new Cls();
                c.setId(rs.getInt(2));

                r = new Result(s, c, rs.getFloat(3));
                lr.add(r);
            }

            rs.close();
            ps.close();

            return lr;
        }catch(Exception e)
        {
            System.out.println("Error - ResultDaoImp > getAll() " + e.getMessage());
        }
        return null;
    }
}
