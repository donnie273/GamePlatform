package my.game.app;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity 
public class Item {
	@Id
	@SequenceGenerator(
			name = "item_id_sequence",
			sequenceName = "item_id_sequence",
			allocationSize = 1
			)
	@GeneratedValue(
			generator = "item_id_sequence",
			strategy = GenerationType.AUTO
			)
	private Integer itemId;
	private String itemName;
	private String category;
	private Float cost;
	private Integer quantity;
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Float getCost() {
		return cost;
	}
	public void setCost(Float cost) {
		this.cost = cost;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
