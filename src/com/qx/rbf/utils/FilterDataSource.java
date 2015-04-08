package com.qx.rbf.utils;

import java.util.LinkedList;

import android.util.SparseArray;

public class FilterDataSource {

	public static final int FILTER_DATA_TYPE_LOCATION = 1;
	public static final int FILTER_DATA_TYPE_DISTANCE = 2;
	public static final int FILTER_DATA_TYPE_PRICE = 3;
	public static final int FILTER_DATA_TYPE_SORT = 4;

	public static String[] createTypeFilterItems() {
		String[] names = { "全部","水果","蔬菜","糕点","生鲜","熟食" };
		return names;
	}

	public static String[] createSortFilterItems() {
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
	
	/**
	 * 获取位置  数据
	 */
	public static LinkedList<String> getGroupData(){
		LinkedList<String> tGroup = new LinkedList<String>();
		for (int i = 0; i < 8; i++) {
			tGroup.add(i+1+"行");
		}
		
		return tGroup;
	}
	
	public static SparseArray<LinkedList<String>> getItemData(){
		SparseArray<LinkedList<String>> tItem = new SparseArray<LinkedList<String>>();
		for(int i=0;i<8;i++){
			LinkedList<String> tItemEle = new LinkedList<String>();
			for (int j = 0; j < 12; j++) {
	
				tItemEle.add(i+1 + "行" + (j+1) + "列");
			}
			tItem.append(i, tItemEle);
		}
		
		return tItem;
	}
}
