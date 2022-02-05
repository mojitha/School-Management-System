package com.item;

import com.connection.ConnectSM;
import com.string.Strings;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemDao {
    private static Connection con;
    private static PreparedStatement ps;
    private static ResultSet rs;
    
    // gives the all item list
    public static ObservableList<Item> getItemList() {
        ObservableList<Item> list = FXCollections.observableArrayList();
        
        try {
            con = ConnectSM.connect();
            String queryA = "SELECT * FROM Item ;" ;
            ps = con.prepareStatement(queryA);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                Item item = new Item();
                
                item.setResourceID(rs.getString(1));
                item.setDescription(rs.getString(2));
                item.setCategory(rs.getString(3));
                item.setSupplyDate(rs.getString(4));
                item.setUpdatedOn(rs.getString(5));
                item.setStatus(rs.getString(6));
                item.setQuantity(rs.getString(7));
                item.setUnit(rs.getString(8));
                item.setCost(rs.getString(9));
                item.setExpireDate(rs.getString(10));
                
                list.add(item);
            }
            
            return list;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }
    
    // gives items on searched keyword
    public static ObservableList<Item> getItemList(String keyword) {
        ObservableList<Item> list = FXCollections.observableArrayList();
        
        try {
            con = ConnectSM.connect();
            String queryB = "SELECT * FROM Item WHERE resourceID LIKE ? OR "
                                                    + "description LIKE ? OR "
                                                    + "supplyDate LIKE ? OR "
                                                    + "updatedOn LIKE ? OR "
                                                    + "status LIKE ? OR "
                                                    + "quantity LIKE ? OR "
                                                    + "cost LIKE ? OR "
                                                    + "expireDate LIKE ? ;" ;
            ps = con.prepareStatement(queryB);
            
            keyword = "%"+keyword+"%";
            
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ps.setString(3, keyword);
            ps.setString(4, keyword);
            ps.setString(5, keyword);
            ps.setString(6, keyword);
            ps.setString(7, keyword);
            ps.setString(8, keyword);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                Item item = new Item();
                
                item.setResourceID(rs.getString(1));
                item.setDescription(rs.getString(2));
                item.setCategory(rs.getString(3));
                item.setSupplyDate(rs.getString(4));
                item.setUpdatedOn(rs.getString(5));
                item.setStatus(rs.getString(6));
                item.setQuantity(rs.getString(7));
                item.setUnit(rs.getString(8));
                item.setCost(rs.getString(9));
                item.setExpireDate(rs.getString(10));
                
                list.add(item);
            }
            
            return list;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }
    
    // gives qunatity based items list
    public static ObservableList<Item> getItemListOnQuantities(int range1, int range2) {
        ObservableList<Item> list = FXCollections.observableArrayList();
        
        try {
            con = ConnectSM.connect();
            String queryC = "SELECT * FROM Item WHERE quantity BETWEEN ? AND ? ;" ;
            ps = con.prepareStatement(queryC);
            ps.setInt(1, range1);
            ps.setInt(2, range2);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                Item item = new Item();
                
                item.setResourceID(rs.getString(1));
                item.setDescription(rs.getString(2));
                item.setCategory(rs.getString(3));
                item.setSupplyDate(rs.getString(4));
                item.setUpdatedOn(rs.getString(5));
                item.setStatus(rs.getString(6));
                item.setQuantity(rs.getString(7));
                item.setUnit(rs.getString(8));
                item.setCost(rs.getString(9));
                item.setExpireDate(rs.getString(10));
                
                list.add(item);
            }
            
            return list;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
    return null;
    }
    
    // gives unit based items list 
    public static ObservableList<Item> getItemListOnUnits(String unit) {
        ObservableList<Item> list = FXCollections.observableArrayList();
        
        try {
            con = ConnectSM.connect();
            String queryD = "SELECT * FROM Item WHERE unit = ? ;" ;
            ps = con.prepareStatement(queryD);
            ps.setString(1, unit);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                Item item = new Item();
                
                item.setResourceID(rs.getString(1));
                item.setDescription(rs.getString(2));
                item.setCategory(rs.getString(3));
                item.setSupplyDate(rs.getString(4));
                item.setUpdatedOn(rs.getString(5));
                item.setStatus(rs.getString(6));
                item.setQuantity(rs.getString(7));
                item.setUnit(rs.getString(8));
                item.setCost(rs.getString(9));
                item.setExpireDate(rs.getString(10));
                
                list.add(item);
            }
            
            return list;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }
    
    // gives category based items list
    public static ObservableList<Item> getItemListOnCategories(String category) {
        ObservableList<Item> list = FXCollections.observableArrayList();
        
        try {
            con = ConnectSM.connect();
            String queryE = "SELECT * FROM Item WHERE category = ? ;" ;
            ps = con.prepareStatement(queryE);
            ps.setString(1, category);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                Item item = new Item();
                
                item.setResourceID(rs.getString(1));
                item.setDescription(rs.getString(2));
                item.setCategory(rs.getString(3));
                item.setSupplyDate(rs.getString(4));
                item.setUpdatedOn(rs.getString(5));
                item.setStatus(rs.getString(6));
                item.setQuantity(rs.getString(7));
                item.setUnit(rs.getString(8));
                item.setCost(rs.getString(9));
                item.setExpireDate(rs.getString(10));
                
                list.add(item);
            }
            
            return list;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }
    
    // gives status based items list
    public static ObservableList<Item> getItemListOnStatus(String status) {
        ObservableList<Item> list = FXCollections.observableArrayList();
        
        try {
            con = ConnectSM.connect();
            String queryF = "SELECT * FROM Item WHERE status = ? ;" ;
            ps = con.prepareStatement(queryF);
            ps.setString(1, status);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Item item = new Item();
                
                item.setResourceID(rs.getString(1));
                item.setDescription(rs.getString(2));
                item.setCategory(rs.getString(3));
                item.setSupplyDate(rs.getString(4));
                item.setUpdatedOn(rs.getString(5));
                item.setStatus(rs.getString(6));
                item.setQuantity(rs.getString(7));
                item.setUnit(rs.getString(8));
                item.setCost(rs.getString(9));
                item.setExpireDate(rs.getString(10));
                
                list.add(item);
            }
            
            return list;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }
    
    // saves an item in the database
    public static boolean saveItem(Item item) {
        try {
            con = ConnectSM.connect();
            String queryG = "INSERT INTO Item(resourceID,description,category,supplyDate,updatedOn,status,quantity,unit,cost,expireDate) "
                    + "VALUES(?,?,?,?,CURDATE(),?,?,?,?,?) ;" ;
            ps = con.prepareStatement(queryG);
            
            ps.setString(1, item.getResourceID());
            ps.setString(2, item.getDescription());
            ps.setString(3, item.getCategory());
            ps.setString(4, item.getSupplyDate());
            ps.setString(5, item.getStatus());
            ps.setString(6, item.getQuantity());
            ps.setString(7, item.getUnit());
            ps.setString(8, item.getCost());
            ps.setString(9, item.getExpireDate());
            
            int count = ps.executeUpdate();
            
            if(count > 0)
                return true;
        } 
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return false;
    }
    
    // undo last added item
    public static boolean removeLast() {
        try {
            con = ConnectSM.connect();
            String queryH = "SELECT MAX(resourceID) FROM Item ;" ;
            ps = con.prepareStatement(queryH);
            rs = ps.executeQuery();
            
            String itemID = null;
            
            while(rs.next())
                itemID = rs.getString(1);
            
            try {
                String queryI = "DELETE FROM Item WHERE resourceID = ? ;" ;
                ps = con.prepareStatement(queryI);
                ps.setString(1, itemID);
                
                int count = ps.executeUpdate();
                
                if(count > 0)
                    return true;
            }
            catch(SQLException sqle) {
                System.out.println(sqle);
            }
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return false;
    }
    
    // gets last itemID
    public static String returnID() {
        try {
            con = ConnectSM.connect();
            String prevID = "";
            String queryJ = "SELECT MAX(resourceID) FROM Item ;" ;
            ps = con.prepareStatement(queryJ);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                prevID = rs.getString(1);
            }
            
            return prevID;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }
    
    // load categories
    public static ObservableList<String> getCategories() {
        ObservableList<String> list = FXCollections.observableArrayList();
        
        try {
            con = ConnectSM.connect();
            String queryK = "SELECT category FROM ItemCategories ;" ;
            ps = con.prepareStatement(queryK);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                list.add(rs.getString(1));
            }
            
            return list;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return  null;
    }
    
    // sets and loads unit for an item
    public static ObservableList<String> getUnits() {
        ObservableList<String> list = FXCollections.observableArrayList();
        
        try {
            con = ConnectSM.connect();
            String queryL = "SELECT unit FROM ItemUnits ;" ;
            ps = con.prepareStatement(queryL);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                list.add(rs.getString(1));
            }
            
            return list;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }
    
    // adds a new category in ItemCategories
    public static boolean saveCategory(String categoryID, String category) {
        try {
            con = ConnectSM.connect();
            String queryM = "INSERT INTO ItemCategories(categoryID,category) VALUES(?,?) ;" ;
            ps = con.prepareStatement(queryM);
            ps.setString(1, categoryID);
            ps.setString(2, category);

            int count = ps.executeUpdate();

            if(count > 0) 
                return true;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }   
        
        return false;
    }
    
    // gets last categoryID
    public static String returnCategoryID() {
        try {
            con = ConnectSM.connect();
            String prevID = "";
            String queryN = "SELECT MAX(categoryID) FROM ItemCategories ;" ;
            ps = con.prepareStatement(queryN);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                prevID = rs.getString(1);
            }
            
            return prevID;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }
    
    // updates a category
    public static boolean updateCategory(String changedCategory,String newCategory) {
        try {
            con = ConnectSM.connect();
            String categoryID = "";
            String queryO = "SELECT categoryID FROM ItemCategories WHERE category = ? ;" ;
            ps = con.prepareStatement(queryO);
            ps.setString(1, changedCategory);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                categoryID = rs.getString(1);
            }
            
            try {
                String queryP = "UPDATE ItemCategories SET category = ? WHERE categoryID = ? ;" ;
                ps = con.prepareStatement(queryP);
                ps.setString(1, newCategory);
                ps.setString(2, categoryID);
                
                int count = ps.executeUpdate();
                
                if(count > 0)
                    return true;
            }
            catch(SQLException sqle) {
                System.out.println(sqle);
            }
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return false;
    }
    
    // search categories of items
    public static ObservableList<String> searchCategories(String keyword) {
        ObservableList<String> list = FXCollections.observableArrayList();
        
        try {
            con = ConnectSM.connect();
            String queryQ = "SELECT category FROM ItemCategories WHERE category LIKE ? ;" ;
            ps = con.prepareStatement(queryQ);
            ps.setString(1, "%"+keyword+"%");
            rs = ps.executeQuery();
            
            while(rs.next()) {
                list.add(rs.getString(1));
            }
            
            return list;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }
    
    // find and save the current item from db as static item
    public static void setCurrentItem(String itemID) {
        try {
            con = ConnectSM.connect();
            String queryR = "SELECT * FROM Item WHERE resourceID = ? ;" ;
            ps = con.prepareStatement(queryR);
            ps.setString(1, itemID);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                Item currItem = Item.getCurrentItem();
                
                currItem.setResourceID(rs.getString(1));
                currItem.setDescription(rs.getString(2));
                currItem.setCategory(rs.getString(3));
                currItem.setSupplyDate(rs.getString(4));
                currItem.setUpdatedOn(rs.getString(5));
                currItem.setStatus(rs.getString(6));
                currItem.setQuantity(rs.getString(7));
                currItem.setUnit(rs.getString(8));
                currItem.setCost(rs.getString(9));
                currItem.setExpireDate(rs.getString(10));
                
                System.out.println(Strings.savedCurrentItem);
            }
            
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
    }

    // loads last item in the Item table
    public static ObservableList<Item> getLastItem() {
        ObservableList<Item> list = FXCollections.observableArrayList();
        String lastItemID = "";
        
        try {
            con = ConnectSM.connect();
            String queryS = "SELECT MAX(resourceID) FROM Item ;" ;
            ps = con.prepareStatement(queryS);
            rs = ps.executeQuery();
            
            while(rs.next()) {
               lastItemID = rs.getString(1);
            }
            
            try {
                String queryT = "SELECT * FROM Item WHERE resourceID = ? ;" ;
                ps = con.prepareStatement(queryT);
                ps.setString(1, lastItemID);
                rs = ps.executeQuery();

                while(rs.next()) {
                   Item item = new Item();
                
                    item.setResourceID(rs.getString(1));
                    item.setDescription(rs.getString(2));
                    item.setCategory(rs.getString(3));
                    item.setSupplyDate(rs.getString(4));
                    item.setUpdatedOn(rs.getString(5));
                    item.setStatus(rs.getString(6));
                    item.setQuantity(rs.getString(7));
                    item.setUnit(rs.getString(8));
                    item.setCost(rs.getString(9));
                    item.setExpireDate(rs.getString(10));

                    list.add(item);
                }
                
                return list;
            }
            catch(SQLException sqle) {
                System.out.println(sqle);
            }
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }

    public static boolean updateItem(Item itemToUp) {
        try {
            con = ConnectSM.connect();
            String queryU = "UPDATE Item "
                        + "SET resourceID = ? ,description = ? ,status = ? ,quantity = ?,cost = ?,updatedOn = ? "
                        + "WHERE resourceID = ? ;" ;
            ps = con.prepareStatement(queryU);
            
            ps.setString(1, itemToUp.getResourceID());
            ps.setString(2, itemToUp.getDescription());
            ps.setString(3, itemToUp.getStatus());
            ps.setString(4, itemToUp.getQuantity());
            ps.setString(5, itemToUp.getCost());
            ps.setString(6, itemToUp.getUpdatedOn());
            ps.setString(7, itemToUp.getResourceID());
            
            int count = ps.executeUpdate();
            
            if(count > 0)
                return true;
        } 
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return false;
    }

    public static boolean removeItem(String itemID) {
        try {
            con = ConnectSM.connect();
            String queryV = "UPDATE Item SET status = ? WHERE resourceID = ? ;" ;
            ps = con.prepareStatement(queryV);
            
            ps.setString(1, "Out of Stock");
            ps.setString(2, itemID);
            
            int count = ps.executeUpdate();
            
            if(count > 0)
                return true;
        } 
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return false;
    }

    public static String getItemsCount() {
        String itemsCount = "";
        
        try {
            con = ConnectSM.connect();
            String queryW = "SELECT count(resourceID) FROM Item ;" ;
            ps = con.prepareStatement(queryW);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                itemsCount = rs.getInt(1)+"";
                
                return itemsCount;
            }
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }

    public static String getItemsCostPerMonth(String monthInt, String year) {
        String itemsCost = "";
        String dateX = "%"+year+"-"+monthInt+"%";
        
        try {
            con = ConnectSM.connect();
            String queryX = "SELECT SUM(cost) FROM Item WHERE supplyDate LIKE ? ;" ;
            ps = con.prepareStatement(queryX);
            ps.setString(1, dateX);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                itemsCost = rs.getString(1);
                
                return itemsCost;
            }
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }
    
    // loads an item given the itemID
    public static ObservableList<Item> getItem(String itemID) {
        ObservableList<Item> list = FXCollections.observableArrayList();
        
        try {
            con = ConnectSM.connect();
            String queryY = "SELECT * FROM Item WHERE resourceID = ? ;";
            ps = con.prepareStatement(queryY);
            ps.setString(1, itemID);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                Item item = new Item();
                
                    item.setResourceID(rs.getString(1));
                    item.setDescription(rs.getString(2));
                    item.setCategory(rs.getString(3));
                    item.setSupplyDate(rs.getString(4));
                    item.setUpdatedOn(rs.getString(5));
                    item.setStatus(rs.getString(6));
                    item.setQuantity(rs.getString(7));
                    item.setUnit(rs.getString(8));
                    item.setCost(rs.getString(9));
                    item.setExpireDate(rs.getString(10));

                list.add(item);
            }
            
            return list;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }
    
    
}