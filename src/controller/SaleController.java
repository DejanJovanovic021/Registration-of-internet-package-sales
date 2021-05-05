
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.SaleModel;

public class SaleController {
    
    SaleModel sale;
    
    public SaleController(){
        
    }
    
    @FXML
    private TextField firstLastName;
    @FXML
    private TextField address;
    @FXML
    private ChoiceBox speed;
    @FXML
    private ChoiceBox flow;
    @FXML
    private ChoiceBox contractDuration;
    @FXML
    private ListView listView;
    
    
    ObservableList<SaleModel> saleList = FXCollections.observableArrayList();
    
    
    
    private ObservableList<SaleModel> display() throws ClassNotFoundException{
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        ObservableList<SaleModel> saleList1 = FXCollections.observableArrayList();
        
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/internet_package_sale?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false", "root", "");){
            
            Statement st = conn.createStatement();
            st.executeQuery("SELECT * FROM sale");
            ResultSet rs = st.getResultSet();
            while(rs.next()){
                
                sale = new SaleModel(Integer.parseInt(rs.getString("id")),
                        rs.getString("firstLastName"),
                        rs.getString("address"),
                        Integer.parseInt(rs.getString("speed")),
                        rs.getString("flow"),
                        Integer.parseInt(rs.getString("contractDuration")));
                
                saleList1.add(sale);
                saleList = saleList1;
            }
        }
        catch (SQLException ex) {
            
            System.out.println("ERROR: " + ex.getMessage());
        }
        
        return saleList1;
    }
    
    
    
    @FXML
    private void initialize() throws ClassNotFoundException{
        
        sale = new SaleModel();
        
        firstLastName.textProperty().bindBidirectional(sale.firstLastNameProperty());
        address.textProperty().bindBidirectional(sale.addressProperty());
        speed.getItems().addAll(2, 5, 10, 20, 50, 100);
        speed.valueProperty().bindBidirectional(sale.speedProperty());
        flow.getItems().addAll(1, 5, 10, 100, "Flat");
        flow.valueProperty().bindBidirectional(sale.flowProperty());
        contractDuration.getItems().addAll(1, 2);
        contractDuration.valueProperty().bindBidirectional(sale.contractDurationProperty());
        display();
        listView.setItems(saleList);
    }
    
    
    
    @FXML
    private void sell() throws ClassNotFoundException{
        
        if(sale.isValid()){
            
            sale.setId(saleList.size() + 1);
            sale.save();
            listView.setItems(display());
            
        }
        else{
            
            StringBuilder errMsg = new StringBuilder();
            
            ArrayList<String> errList = sale.errorsProperty().get();
            
            for(String errList1 : errList){
                errMsg.append(errList1);
            }
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sell can't be done!");
            alert.setHeaderText(null);
            alert.setContentText(errMsg.toString());
            alert.showAndWait();
            errList.clear();
        }
    }
    
    
    
    
    
    
    @FXML
    private void delete() throws ClassNotFoundException{
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        int saleId = listView.getSelectionModel().getSelectedIndex();
        String modId = null;
        
        for(SaleModel model : saleList){
            if(saleId == saleList.indexOf(model)){
                modId = Integer.toString(model.getId());
            }
        }
        
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/internet_package_sale?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false" , "root", "");){
            
            PreparedStatement st = conn.prepareStatement("DELETE FROM sale WHERE id=?");
            st.setString(1, modId);
            st.execute();
            listView.setItems(display());
            
        }
        catch (SQLException ex) {
            
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
    
    
    
    
    
    @FXML
    private void clear(){
        
        sale.firstLastNameProperty().set("");
        sale.addressProperty().set("");
    }
    
}
