package com.dao;

import com.entities.Professor;

public interface ProfessorDao extends IDao<Professor>{
    boolean isProfessor(String login, String password);
}
