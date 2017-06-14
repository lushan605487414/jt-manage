package com.jt.manage.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jt.common.service.BaseService;
import com.jt.common.vo.EasyUIResult;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
@Service
public class ItemService extends BaseService<Item>{
	@Autowired
	private ItemMapper itemMapper;
	
	//查询商品列表，按修改时间倒叙
	public EasyUIResult queryItemList(Integer pageNum,Integer pageSize){
		//标识分页开始， mybatis规则：拦截器只拦截下面第一条查询的SQL语句
		PageHelper.startPage(pageNum, pageSize);
		List<Item> itemList = itemMapper.queryItemList();
		//List<Item> itemList1 = itemMapper.queryItemList();
		//封装当前页和记录总数对象
		PageInfo<Item> pageInfo = new PageInfo<Item>(itemList);
		
		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
		
	}
	
	//新增商品，商品描述
	public void saveItem(Item item){
		item.setStatus(1);	//1正常，2删除
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		
		itemMapper.insertSelective(item);
	}
	
	//修改商品
	public void updateItem(Item item){
		item.setUpdated(new Date());
		itemMapper.updateByPrimaryKeySelective(item);
	}
	
	//批量删除
	public void deleteItem(Long[] ids){
		itemMapper.deleteByIDS(ids);
	}
	
	//修改状态
	public void updateStatus(Integer val,Long[] ids){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("status", val);
		params.put("ids", ids);
		
		itemMapper.updateStatus(params);
	}

}
