package com.example.hp.recipeapp.model.entities;

public class Product {


	/** The item short desc. */
	private String description = "";

	/** The item detail. */
	private String longDescription = "";

	/** The mrp. */
	private String mrp;

	/** The discount. */
	private String discount;

	/** The sell mrp. */
	private String salePrice;

	/** The quantity. */
	private String orderQty;

	/** The image url. */
	private String imageUrl = "";

	/** The item name. */
	private String productName = "";

	private String productId = "";

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @param itemName
	 * @param itemShortDesc
	 * @param itemDetail
	 * @param MRP
	 * @param discount
	 * @param sellMRP
	 * @param quantity
	 * @param imageURL
	 */
	public Product(String itemName, String itemShortDesc, String itemDetail,
				   String MRP, String discount, String sellMRP, String quantity,
				   String imageURL, String orderId) {
		this.productName = itemName;
		this.description = itemShortDesc;
		this.longDescription = itemDetail;
		this.mrp = MRP;
		this.discount = discount;
		this.salePrice = sellMRP;
		this.orderQty = quantity;
		this.imageUrl = imageURL;
		this.productId = orderId;
	}

	public String getItemName() {
		return productName;
	}

	public void setItemName(String itemName) {
		this.productName = itemName;
	}

	public String getItemShortDesc() {
		return description;
	}

	public void setItemShortDesc(String itemShortDesc) {
		this.description = itemShortDesc;
	}

	public String getItemDetail() {
		return longDescription;
	}

	public void setItemDetail(String itemDetail) {
		this.longDescription = itemDetail;
	}

	public String getMRP() {
		return this.mrp;
	}

	public void setMRP(String MRP) {
		this.mrp = MRP;
	}

	public String getDiscount() {
		return discount + "%";
	}

	public String getDiscountNumeric() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getSellMRP() {
		return salePrice;
	}

	public void setSellMRP(String sellMRP) {
		this.salePrice = sellMRP;
	}

	public String getQuantity() {
		return orderQty;
	}

	public void setQuantity(String quantity) {
		this.orderQty = quantity;
	}

	public String getImageURL() {
		return imageUrl;
	}

	public void setImageURL(String imageURL) {
		this.imageUrl = imageURL;
	}

}
