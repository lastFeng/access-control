DROP DATABASE IF EXISTS access;
CREATE DATABASE access DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;

CREATE TABLE IF NOT EXISTS department(
    id VARCHAR(64) NOT NULL COMMENT '编号',
    name VARCHAR(64) COMMENT '名称',
    sn VARCHAR(128) COMMENT '部门序列号',
    PRIMARY KEY(id)
) COMMENT = '部门表';

CREATE TABLE IF NOT EXISTS menu(
    id VARCHAR(64) NOT NULL COMMENT '编号',
    name VARCHAR(64) COMMENT '菜单名称',
    PRIMARY KEY(id)
) COMMENT = '菜单表';

CREATE TABLE IF NOT EXISTS permission(
    id VARCHAR(64) NOT NULL COMMENT '编号',
    express VARCHAR(64) COMMENT '权限表达式',
    name VARCHAR(64) COMMENT '权限名',
    PRIMARY KEY(id)
) COMMENT = '权限表';

CREATE TABLE IF NOT EXISTS role(
    id VARCHAR(64) NOT NULL COMMENT '编号',
    name VARCHAR(64) COMMENT '角色名称',
    sn VARCHAR(128) COMMENT '角色序列号',
    permission_id VARCHAR(64) COMMENT '部门编号',
    menu_id VARCHAR(64) COMMENT '菜单编号',
    PRIMARY KEY(id),
    FOREIGN KEY(permission_id) REFERENCES permission(id),
    FOREIGN KEY(menu_id) REFERENCES menu(id)
) COMMENT ='角色表';

CREATE TABLE IF NOT EXISTS employee(
    id VARCHAR(64) NOT NULL COMMENT '编号',
    name VARCHAR(64) COMMENT '员工名',
    email VARCHAR(64) COMMENT '邮件地址',
    age INT COMMENT '年龄',
    admin TINYINT(1) COMMENT '是否是超级管理员',
    department_id VARCHAR(64) COMMENT '部门编号',
    role_id VARCHAR(64) COMMENT '角色编号',
    PRIMARY KEY(id),
    FOREIGN KEY(department_id) REFERENCES department(id),
    FOREIGN KEY(role_id) REFERENCES role(id)
) COMMENT = '员工表';