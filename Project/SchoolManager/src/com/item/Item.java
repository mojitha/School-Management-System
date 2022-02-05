package com.item;

import com.string.Strings;

public class Item {
    private String resourceID,description,category,supplyDate,updatedOn,status,quantity,unit,cost,expireDate;
    private static Item currentItem;
    
    public Item() {
        super();
    }
    
    public static Item getCurrentItem() {
        if(currentItem == null) {
            synchronized(Item.class) {
                currentItem = new Item();
                System.out.println(Strings.created_currentItemSingletonCreated);
            }
        }
        
        return currentItem;
    }
    
    public Item(String resourceID, String description, String category, String supplyDate, String updatedOn, String status, String quantity, String unit, String cost, String expireDate) {
        this.resourceID = resourceID;
        this.description = description;
        this.category = category;
        this.supplyDate = supplyDate;
        this.updatedOn = updatedOn;
        this.status = status;
        this.quantity = quantity;
        this.unit = unit;
        this.cost = cost;
        this.expireDate = expireDate;
    }

    public String getResourceID() {
        return resourceID;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupplyDate() {
        return supplyDate;
    }

    public void setSupplyDate(String supplyDate) {
        this.supplyDate = supplyDate;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    
}
