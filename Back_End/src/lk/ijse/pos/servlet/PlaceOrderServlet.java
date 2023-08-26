package lk.ijse.pos.servlet;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = "/placeOrder")
public class PlaceOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Content-type", "application/json");
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");

        String option= req.getParameter("option");


        switch (option){
            case "customer":

                String cusID= req.getParameter("id");
                resp.getWriter().print(getCustomer(cusID));
                break;
            case "items":

                String itemID= req.getParameter("id");
                resp.getWriter().print(getItem(itemID));
                break;
        }

    }

    public JsonObject getCustomer(String customerId){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/webpos?allowPublicKeyRetrieval=true&useSSL=false", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("select * from customerinfo where cusID=?");
            pstm.setObject(1, customerId);
            ResultSet rst = pstm.executeQuery();

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            if (rst.next()) {

                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                String contact = rst.getString(4);


                objectBuilder.add("id", id);
                objectBuilder.add("name", name);
                objectBuilder.add("address", address);
                objectBuilder.add("contact", contact);

                System.out.println(id+" "+name+" "+address+" "+contact);

            }

            return objectBuilder.build();

        }catch (ClassNotFoundException e){

            throw new RuntimeException();

        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    public JsonObject getItem(String customerId){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/webpos?allowPublicKeyRetrieval=true&useSSL=false", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("select * from iteminfo where itemID=?");
            pstm.setObject(1, customerId);
            ResultSet rst = pstm.executeQuery();

            /*connection.setAutoCommit(false);
            connection.commit();
            connection.setAutoCommit(true);*/

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            if (rst.next()) {

                String id = rst.getString(1);
                String desc = rst.getString(2);
                String price = rst.getString(3);
                String qty = rst.getString(4);

                objectBuilder.add("id", id);
                objectBuilder.add("desc", desc);
                objectBuilder.add("price", price);
                objectBuilder.add("qty", qty);

            }

            return objectBuilder.build();

        }catch (ClassNotFoundException e){

            throw new RuntimeException();

        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Content-type", "application/json");
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT");

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject readObject = reader.readObject();

        String orderID = readObject.getString("orderID");
        String date = readObject.getString("date");
        int amount = readObject.getInt("amount");
        String customerID = readObject.getString("customer");

        JsonArray details = readObject.getJsonArray("details");


        for (int i = 0; i < details.size(); i++) {
            JsonArray innerArray = details.getJsonArray(i);

            String value1 = innerArray.getString(0);
            String value2 = innerArray.getString(1);
            String value3 = innerArray.getString(2);

            System.out.println(value1);
            System.out.println(value2);
            System.out.println(value3);
        }

    }

    public JsonObject placeOrder(String orderID,String date,String amount,String customerID,JsonArray details){
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/webpos?allowPublicKeyRetrieval=true&useSSL=false", "root", "1234");

            try {
                connection.setAutoCommit(false);

                PreparedStatement pstm = connection.prepareStatement("insert into orders values(?,?,?,?)");
                pstm.setObject(1, orderID);
                pstm.setObject(2, date);
                pstm.setObject(3, amount);
                pstm.setObject(4, customerID);

                if (pstm.executeUpdate() > 0) {

                    for (int i = 0; i <details.size(); i++) {
                        PreparedStatement pstm2 = connection.prepareStatement("insert into ordersdetails values(?,?,?,?)");
                        pstm2.setObject(1, orderID);
                        pstm2.setObject(2, details.getJsonArray(i).getString(0));
                        pstm2.setObject(3, details.getJsonArray(i).getString(3));
                        pstm2.setObject(4, details.getJsonArray(i).getString(2));
                    }

                }

                connection.rollback();
            }finally {
                connection.setAutoCommit(true);
            }

        } catch (SQLException e){
            throw new RuntimeException(e);

        } catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        return objectBuilder.build();
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    private JsonObject addJSONObject(String message, String state) {

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("state", state);
        objectBuilder.add("message", message);
        objectBuilder.add("data", "[]");


        return objectBuilder.build();
    }
}
