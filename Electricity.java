package com.test1.jsf.bean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List; 
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
@ManagedBean(name="data")
@SessionScoped
public class Electricity {
    String meterId="101";
    String current_Meter_Reading;
    String previous_Meter_Reading;
    String zone;
    String consumer_Name;
    String subsidy;
    String  unts_cnsd;
    long result=0;
    private static final List<Electric1> eleList=new ArrayList<>();
    int flag=0;
    public String addStudent() {
        if(result==0)
        {
            return "/home.xhtml";
        }

        eleList.add(new Electric1(meterId,current_Meter_Reading,previous_Meter_Reading,zone,result));
    return "/Details.xhtml";
    }

    public void setMeterId(String meterId)
    {
        this.meterId=meterId;
    }
   public String getMeterId() {
        return meterId;
    }
    public String getCurrent_Meter_Reading() {
        return current_Meter_Reading;
    }
    public void setCurrent_Meter_Reading(String current_Meter_Reading) {
        this.current_Meter_Reading = current_Meter_Reading;
    }
    public String getPrevious_Meter_Reading() {
        return previous_Meter_Reading;
    }
    public void setPrevious_Meter_Reading(String previous_Meter_Reading) {
        this.previous_Meter_Reading = previous_Meter_Reading;
    }
    public String getZone() {
        return zone;
    }
 public void setZone(String zone) {
        this.zone = zone;
    }

    public long addData() throws ClassNotFoundException, SQLException
    {
     Connection conn = DatabaseConnection.initializeDatabase();

        if(flag==0)
        {
            PreparedStatement st;
            PreparedStatement st1;
            String query="select * from Current_bill1";
            String query1="UPDATE Current_bill1 SET Cost=?,current_Meter_Reading=?,previous_Meter_Reading=?,Units_Consumed=?,Subsidy=?  where meterId=? ";
            flag=1;
            try {
                st=conn.prepareStatement(query);
                st1=conn.prepareStatement(query1);
                ResultSet rs = st.executeQuery();
            int temp=0;
                while(rs.next())
                {
                    System.out.println(getMeterId());
                    System.out.println(rs.getString(1));
                    System.out.println(rs.getString(1).equalsIgnoreCase(getMeterId()));
                    if(rs.getString(1).equalsIgnoreCase(getMeterId()))
                    {
                            System.out.println("hello");
                        consumer_Name=rs.getString("Name");
                        temp=1;
                        long cmrh=Long.parseLong(getCurrent_Meter_Reading());
                        long pmrh=Long.parseLong(getPrevious_Meter_Reading());
                        unts_cnsd=String.valueOf(cmrh-pmrh);
                        st1.setString(4, unts_cnsd);
                        if(getZone().equalsIgnoreCase("urban"))
                        {
                        	result=(cmrh-pmrh)*6;
                        }
                        else if(getZone().equalsIgnoreCase("rural"))
                        {
                            result=(cmrh-pmrh)*4;
                        }

                        if((cmrh-pmrh)<100)
                        {
                            result=result-100;
                            st1.setString(5, "Applicable(₹100 only...)");
                            subsidy="Applicable(₹100 only...)";
                        }
                        else
                        {
                            st1.setString(5, "Not Applicable");
                            subsidy="Not Applicable";
                        }
                        st1.setLong(1, result);
                        st1.setString(2, getCurrent_Meter_Reading());
                        st1.setString(3, getPrevious_Meter_Reading());
                        st1.setString(6, getMeterId());
                        st1.executeUpdate();
                    }
                }
                if(temp==0)
                {
                    System.out.println("check me....");
                }
            } 
            catch (SQLException e) {
             e.printStackTrace();
            }

        }
        //ud.addStudent();
        return result;
    }
    public long getResult() {
        return result;
    }
    public String getConsumer_Name() {
        return consumer_Name;
    }
    public String getSubsidy() {
        return subsidy;
    }

 
    public String getUnts_cnsd() {
        return unts_cnsd;
    }

    public List<Electric1> getStudents(){

    return eleList;
    }
    }