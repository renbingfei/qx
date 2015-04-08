package com.qx.rbf.utils;

import java.util.LinkedList;

import android.util.SparseArray;

public class FilterDataSource {

	public static final int FILTER_DATA_TYPE_LOCATION = 1;
	public static final int FILTER_DATA_TYPE_DISTANCE = 2;
	public static final int FILTER_DATA_TYPE_PRICE = 3;
	public static final int FILTER_DATA_TYPE_SORT = 4;

	public static String[] createTypeFilterItems() {
		String[] names = { "ȫ��","ˮ��","�߲�","���","����","��ʳ" };
		return names;
	}

	public static String[] createSortFilterItems() {
		String names[] = new String[] { "�������", "�۸����", "�ۿ����", "�������"};
		return names;
	}

	public static String getDataTypeName(int dataType) {
		if (FILTER_DATA_TYPE_LOCATION == dataType) {
			return "λ��";
		} else if (FILTER_DATA_TYPE_DISTANCE == dataType) {
			return "����";
		} else if (FILTER_DATA_TYPE_PRICE == dataType) {
			return "�۸�";
		} else if (FILTER_DATA_TYPE_SORT == dataType) {
			return "����";
		}
		return "";
	}
	
	/**
	 * ��ȡλ��  ����
	 */
	public static LinkedList<String> getGroupData(){
		LinkedList<String> tGroup = new LinkedList<String>();
		for (int i = 0; i < 8; i++) {
			tGroup.add(i+1+"��");
		}
		
		return tGroup;
	}
	
	public static SparseArray<LinkedList<String>> getItemData(){
		SparseArray<LinkedList<String>> tItem = new SparseArray<LinkedList<String>>();
		for(int i=0;i<8;i++){
			LinkedList<String> tItemEle = new LinkedList<String>();
			for (int j = 0; j < 12; j++) {
	
				tItemEle.add(i+1 + "��" + (j+1) + "��");
			}
			tItem.append(i, tItemEle);
		}
		
		return tItem;
	}
}
