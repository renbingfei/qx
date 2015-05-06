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
			 names = new String[]{ "ȫ��","ƻ��","��","�㽶","����","������","����","����", "��ݮ","����","����","��֦"};
		}else if("vegetable".equals(goodsType)){
			names = new String[]{ "ȫ��","�ײ�","�ܲ�","�ƹ�","����","�۲�","����","����","����"};
		}else if("cookie".equals(goodsType)){
			names = new String[]{ "ȫ��","�̶���","��ɳ��","�ඹ��","÷����","���","����"};
		}else if("fish".equals(goodsType)){
			names = new String[]{ "ȫ��","����","����","����","ţ��","¿��","����"};
		}else if("cooked".equals(goodsType)){
			names = new String[]{ "ȫ��","��ţ��","������","������","������Ѽ"};
		}else if("all".equals(goodsType)){
			names = new String[]{ "ȫ��","ˮ��","�߲�","���","����","��ʳ"};
		}
		return names;
	}

	public static String[] createSortFilterItems(String goodsType) {
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
	//�̼�����
	public static String[] createShopTypeFilterItems() {
		String[] names = null;
		names = new String[]{ "ȫ��","����","�˵�","ˮ����","����","���ʵ�","��ʳ��"};
		return names;
	}

	public static String[] createShopSortFilterItems() {
		String names[] = new String[] { "�������", "��Ʒ���", "�ɵ����", "�������"};
		return names;
	}
	/**
	 * ��ȡ����λ��  ����
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
		//��¼�Ѿ��������������
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
