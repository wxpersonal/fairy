package me.weix.fairy.service;

import java.util.List;

/**
 * @Author: WeiX
 * @Date: 2017/4/25
 * @Description:
 */
public interface IBaseService<pojo> {

	Integer insert(pojo obj);

	pojo getById(Integer id);

	Integer update(pojo obj);

	Integer delete(String ids);

	List<pojo> list();

	void initMapper();
}
