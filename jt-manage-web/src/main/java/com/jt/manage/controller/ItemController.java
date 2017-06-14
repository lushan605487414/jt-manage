package com.jt.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	//查询，/item/query
	@RequestMapping("/query")
	@ResponseBody
	public EasyUIResult queryItemList(Integer page,Integer rows){
		return itemService.queryItemList(page, rows);
	}
	
	//新增
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveItem(Item item){
		try{
			itemService.saveItem(item);
			return SysResult.oK();		
		}catch(Exception e){
			return SysResult.build(201, "");
		}
		
	}
	
	//修改
	@RequestMapping("/update")
	@ResponseBody
	public SysResult updateItem(Item item){
		try{
			itemService.updateItem(item);
			return SysResult.oK();
		}catch(Exception e){
			return SysResult.build(201, "");
		}
		
	}
	
	//批量删除，springmvc会自动，判断是集合，自动按逗号split(",")
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deleteItem(Long[] ids){
		try{
			itemService.deleteItem(ids);
			return SysResult.oK();
		}catch(Exception e){
			return SysResult.build(201, "");
		}
	}
	
	//下架/item/instock
	@RequestMapping("/instock")
	@ResponseBody
	public SysResult instock(Long[] ids){
		try{
			itemService.updateStatus(2, ids);
			return SysResult.oK();
		}catch(Exception e){
			return SysResult.build(201, "");
		}
	}
	
	//上架
	@RequestMapping("/reshelf")
	@ResponseBody
	public SysResult reshelf(Long[] ids){
		try{
			itemService.updateStatus(1, ids);
			return SysResult.oK();
		}catch(Exception e){
			return SysResult.build(201, "");
		}
	}
	
	
	
	
	
	
	
	
}
