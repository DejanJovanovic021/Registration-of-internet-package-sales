
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SaleModel {
    
    List<SaleModel> saleList;
    
    private final IntegerProperty id = new SimpleIntegerProperty(this,"id"); 
    private final StringProperty firstLastName = new SimpleStringProperty(this,"firstLastName");
    private final StringProperty address = new SimpleStringProperty(this,"address");
    private final IntegerProperty speed = new SimpleIntegerProperty(this,"speed");
    private final ObjectProperty flow = new SimpleObjectProperty(this,"flow"); 
    private final IntegerProperty contractDuration = new SimpleIntegerProperty(this,"contractDuration");  
    
    public SaleModel(){
        
    }
    
    public SaleModel(int id, String firstLastName, String address, int speed, Object flow, int contractDuration){
        
        this.id.set(id);
        this.firstLastName.set(firstLastName);
        this.address.set(address);
        this.speed.set(speed);
        this.flow.set(flow);
        this.contractDuration.set(contractDuration);
    }
    
    public int getId(){
        return id.get();
    }
    public void setId(int id){
        this.id.set(id);
    }
    public IntegerProperty idProperty(){
        return id;
    }
    public String getFirstLastName(){
        return firstLastName.get();
    }
    public void setFirstLastName(String firstLastName){
        this.firstLastName.set(firstLastName);
    }
    public StringProperty firstLastNameProperty(){
        return firstLastName;
    }
    public String getAddress(){
        return address.get();
    }
    public void setAddress(String address){
        this.address.set(address);
    }
    public StringProperty addressProperty(){
        return address;
    }
    public int getSpeed(){
        return speed.get();
    }
    public void setSpeed(int speed){
        this.speed.set(speed);
    }
    public IntegerProperty speedProperty(){
        return speed;
    }
    public Object getFlow(){
        return flow.get();
    }
    public void setFlow(Object flow){
        this.flow.set(flow);
    }
    public ObjectProperty flowProperty(){
        return flow;
    }
    public int getContractDuration(){
        return contractDuration.get();
    }
    public void setContractDuration(int contractDuration){
        this.contractDuration.set(contractDuration);
    }
    public IntegerProperty contractDurationProperty(){
        return contractDuration;
    }
    
    
    
    private final ObjectProperty<ArrayList<String>> errorList = new SimpleObjectProperty<>(this, "errorList", new ArrayList<>());
    
    public ObjectProperty<ArrayList<String>> errorsProperty(){
        return errorList;
    }
    
    
    
    public boolean isValid(){
        
        boolean isValid = true;
        
        if(firstLastName.get() == null){
            errorList.getValue().add("First and last name must be entered!");
            isValid = false;
        }
        if(address.get() == null){
            errorList.getValue().add("Address must be entered!");
            isValid = false;
        }
        if(speed.get() == 0){
            errorList.getValue().add("Speed must be choosen!");
            isValid = false;
        }
        if(flow.get() == null){
            errorList.getValue().add("Flow must be choosen!");
            isValid = false;
        }
        if(contractDuration.get() == 0){
            errorList.getValue().add("Contract duration must be choosen!");
        }
        return isValid;
    }
    
    
    public String save() throws ClassNotFoundException{
        
        String msg = "Entry completed successfully";
        
        if(isValid()){
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/internet_package_sale?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false", "root", "");){
                
                PreparedStatement st = conn.prepareStatement("INSERT INTO sale(firstLastName, address, speed, flow, contractDuration) VALUES (?,?,?,?,?)");
                st.setString(1, firstLastName.get());
                st.setString(2, address.get());
                st.setString(3, Integer.toString(speed.get()));
                st.setString(4, flow.get().toString());
                st.setString(5, Integer.toString(contractDuration.get()));
                st.execute();
                
            }
            catch (SQLException ex) {
                
                msg = "ERROR! " + ex.getMessage();
            }
        }
        return msg;
    }
    
    @Override
    public String toString(){
        
        return 
                "Id: " + id.get() + "\n" +
                "First and last name : " + firstLastName.get() + "\n" +
                "Address : " + address.get() + "\n" + 
                "Speed: " + speed.get() + " Mbit/s" + "\n" +
                "Flow: " + flow.get() + " GB" + "\n" + 
                "Contract duration: " + contractDuration.get() + " year";
    }
}
