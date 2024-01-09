package com.dao;

import com.entities.Department;

public interface DepartmentDao extends IDao<Department>{
    int getDepartmentId(String dname);
}
