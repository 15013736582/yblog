package com.yanger.blog.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yanger.blog.po.ArticleKinds;
import com.yanger.mybatis.core.MybatisBaseDao;

/**
 *
 * 表article_kinds对应的基于MyBatis实现的Dao接口<br/>
 * 在其中添加自定义方法
 *
 */
@Mapper
public interface ArticleKindsDao extends MybatisBaseDao<ArticleKinds, Integer> {

}