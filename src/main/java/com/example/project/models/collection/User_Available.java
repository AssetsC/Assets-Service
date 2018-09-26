package com.example.project.models.collection;

import com.example.project.models.Items;
import lombok.Data;

@Data
public class User_Available {

	private int item_id;
	private String item_code;
	private String item_date;
	private String item_model;
	private String item_name;
	private int item_status;
	private String item_url;
	private int available_Item_id;
	private int available_state;

	public void setItem(Items item) {
		if(item != null) {
			this.item_id   = item.getItem_id();
			this.item_code = item.getItem_code(); 
			this.item_url  = item.getItem_url();
			this.item_name = item.getItem_name();
			this.item_model = item.getItem_model();
			this.item_status = item.getItem_status();
			this.item_date  = item.getItem_date();
		}
	}
}
