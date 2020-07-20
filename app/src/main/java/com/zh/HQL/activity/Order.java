package com.zh.HQL.activity;

import org.json.JSONObject;

import java.io.Serializable;

public class Order implements Serializable {

	public String id;

	public String name;

	public String hanqilang1;

	public String hanqiliang2;
    public String hanqiliangpj;
	public String ceShiShiJian;

	
	public Order(String id, String name, String hanqilang1, String hanqiliang2,String hanqiliangpj, String ceShiShiJian) {
		this.id = id;
		this.name = name;
		this.hanqilang1 = hanqilang1;
		
		this.hanqiliang2 = hanqiliang2;
		this.hanqiliangpj=hanqiliangpj;
		this.ceShiShiJian=ceShiShiJian;
	}

	public Order(JSONObject obj) {
		this.id = obj.optString("id");
		this.name = obj.optString("name");
		this.hanqilang1 = obj.optString("hanqilang1");
		this.hanqiliang2 = obj.optString("hanqiliang2");
		this.hanqiliangpj=obj.optString("hanqiliangpj");
		this.ceShiShiJian = obj.optString("ceShiShiJian");
	}
}
