/*
 * Copyright 2001-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.learn.accesscontrol.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.learn.accesscontrol.domain.BaseDomain;
import com.learn.accesscontrol.common.mapper.BaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo Weifeng
 * @version: 1.0
 * @create: 2020/6/30 9:15
 */
@Transactional(readOnly = true, rollbackFor = Exception.class)
public abstract class BaseService<D extends BaseDomain, M extends BaseMapper<D>> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 持久层对象
     */
    @Autowired
    protected M mapper;

    /**
     * 获取单挑数据
     * @param id
     * @return
     */
    public D findById(String id) {
        return this.mapper.selectByPrimaryKey(id);
    }

    /***
     * 单条查询，不支持模糊查询
     * @param param
     * @return
     */
    public List<D> findListByWhere(D param) {
        return this.mapper.select(param);
    }

    /**
     * 多条件查询，支持模糊查询
     * @param entity
     * @return
     */
    public List<D> findList(D entity) {
        return this.mapper.findList(entity);
    }

    /**
     * 根据参数进行分页查询
     * @param entity
     * @return
     */
    public PageInfo<D> findAllPageInfo(D entity) {
        if (entity != null && entity.getPage() != null && entity.getRows() != null) {
            PageHelper.startPage(entity.getPage(), entity.getRows());
        }

        return new PageInfo<D>(this.mapper.findList(entity));
    }

    /**
     * 单表查询记录数
     * @param param
     * @return
     */
    public Integer getCount(D param) {
        return this.mapper.selectCount(param);
    }

    /**
     * 单表查询一条记录
     * @param param
     * @return
     */
    public D findOne(D param) {
        return this.mapper.selectOne(param);
    }

    /**
     * 保存数据
     * @param entity
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void saveOrUpdate(D entity) {
        if (entity.getId() != null && !entity.getId().isEmpty()) {
            this.mapper.updateByPrimaryKey(entity);
        } else {
            this.mapper.insert(entity);
        }
    }

    /**
     * 删除单条数据
     * @param id
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void deleteById(String id) {
        this.mapper.deleteByPrimaryKey(id);
    }

    /**
     * 删除单条数据
     * @param entity
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delety(D entity) {
        this.mapper.delete(entity);
    }

    /**
     * 批量删除数据
     * @param ids
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void deleteByIds(Class<D> clazz, List<String> ids) {
        if (ids.isEmpty()) {
            return;
        }

        Example example = new Example(clazz);
        example.createCriteria().andIn("id", ids);

        this.mapper.deleteByExample(example);
    }
}
