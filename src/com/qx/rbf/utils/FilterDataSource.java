package com.qx.rbf.utils;

import java.util.LinkedList;

import android.util.SparseArray;

public class FilterDataSource {

	public static final int FILTER_DATA_TYPE_LOCATION = 1;
	public static final int FILTER_DATA_TYPE_DISTANCE = 2;
	public static final int FILTER_DATA_TYPE_PRICE = 3;
	public static final int FILTER_DATA_TYPE_SORT = 4;
	
	
	public static String[] createTypeFilterItems(String goodsType) {
		
		String[] names = null;
		if("fruit".equals(goodsType)){
			 names = new String[]{ "全部","苹果","梨","香蕉","葡萄","火龙果","西瓜","柚子", "草莓","橘子","橙子","荔枝"};
		}else if("vegetable".equals(goodsType)){
			names = new String[]{ "全部","白菜","萝卜","黄瓜","茄子","芹菜","菠菜","番茄","大蒜"};
		}else if("cookie".equals(goodsType)){
			names = new String[]{ "全部","绿豆糕","豆沙糕","赤豆糕","梅花糕","香糕","蒸糕"};
		}else if("fish".equals(goodsType)){
			names = new String[]{ "全部","鱼肉","羊肉","猪肉","牛肉","驴肉","海鲜"};
		}else if("cooked".equals(goodsType)){
			names = new String[]{ "全部","熟牛肉","熟羊肉","熟猪肉","秘制老鸭"};
		}else if("all".equals(goodsType)){
			names = new String[]{ "全部","水果","蔬菜","糕点","生鲜","熟食"};
		}
		return names;
	}

	public static String[] createSortFilterItems(String goodsType) {
		String names[] = new String[] { "离我最近", "价格最低", "折扣最大", "评价最好"};
		return names;
	}

	public static String getDataTypeName(int dataType) {
		if (FILTER_DATA_TYPE_LOCATION == dataType) {
			return "位置";
		} else if (FILTER_DATA_TYPE_DISTANCE == dataType) {
			return "距离";
		} else if (FILTER_DATA_TYPE_PRICE == dataType) {
			return "价格";
		} else if (FILTER_DATA_TYPE_SORT == dataType) {
			return "排序";
		}
		return "";
	}
	//商家数据
	public static String[] createShopTypeFilterItems() {
		String[] names = null;
		names = new String[]{ "全部","超市","菜店","水果店","糕点店","生鲜店","熟食店"};
		return names;
	}

	public static String[] createShopSortFilterItems() {
		String names[] = new String[] { "离我最近", "产品最多", "成单最大", "评价最好"};
		return names;
	}
	/**
	 * 获取北京位置  数据
	 */
	public static LinkedList<String> getGroupData(String goodsType){
		LinkedList<String> tGroup = new LinkedList<String>();
		for (int i = 0; i < Common.beijingArea.length; i++) {
			tGroup.add(Common.beijingArea[i]);
		}
		
		return tGroup;
	}
	
	public static SparseArray<LinkedList<String>> getItemData(String goodsType){
		SparseArray<LinkedList<String>> tItem = new SparseArray<LinkedList<String>>();
		//记录已经插入多少子区域
		int totalSubArea = 0;
		for(int i=0;i<Common.beijingArea.length;i++){
			LinkedList<String> tItemEle = new LinkedList<String>();
			for (int j = 0; j < Common.beijingArea2Sub[i]; j++) {	
				tItemEle.add(Common.beijingAreaSub[totalSubArea]);
				totalSubArea++;
			}
			tItem.append(i, tItemEle);
		}
		
		return tItem;
	}
}
