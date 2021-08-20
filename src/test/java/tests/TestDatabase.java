package tests;

import dataBaseConnect.JDBCConnection;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Проверка подключения к БД sakila и отправки запросов")
public class TestDatabase {

   @Test
   @Order(1)
   @DisplayName("Проверка создания таблицы в БД")
   public void testCreateTable() {
       String query = "CREATE TABLE users ("
               + "ID int(6) NOT NULL,"
               + "Login VARCHAR(45) NOT NULL,"
               + "Password VARCHAR(45) NOT NULL,"
               + "PRIMARY KEY (ID))";
       JDBCConnection.createTable(query);
   }


   @Test
   @Order(2)
   @DisplayName("Отправка INSERT запроса")
   public void testInsertRequest() {
       String query = "INSERT INTO users (ID, Login, Password) VALUES (11, 'Summer', 'time')";
       JDBCConnection.insertIntoTable(query);

       String selectQuery = "SELECT * FROM users";
       ResultSet rs = JDBCConnection.selectFromTable(selectQuery);
       assertAll("Should return inserted data",
               () -> assertEquals("11", rs.getString("ID")),
               () -> assertEquals("Summer", rs.getString("Login")),
               () -> assertEquals("time", rs.getString("Password")));
   }

   @Test
    @Order(3)
    public void testUpdateRequest() throws SQLException {
        String query = "UPDATE users SET Login = 'Autumn' WHERE ID='11'";
        JDBCConnection.updateInTable(query);
        String selectQuery = "SELECT Login FROM users WHERE ID=11";
        ResultSet rs = JDBCConnection.selectFromTable(selectQuery);
        String expectedLogin = "Autumn";
        String actualLogin = rs.getString("Login");
        assertEquals(expectedLogin, actualLogin, "Actual login is '" + actualLogin + "'. Expected - '" + expectedLogin + "'.");
    }

     @Test
    @Order(4)
    @DisplayName("Отправка DELETE запроса")
    public void testDeleteRequest() {
        String query = "DELETE FROM users WHERE ID=11";
        JDBCConnection.deleteFromTable(query);
    }

    @Test
    @Order(5)
    @DisplayName("Проверка удаления таблицы из БД")
    public void test_dropTable() {
        JDBCConnection.dropTable("users");
    }

    @Test
    @Order(6)
    @DisplayName("Отправка простого SELECT запроса. Проверка города")
    public void testSelectRequest_checkCity() throws SQLException {
        String query = "SELECT * FROM city WHERE country_id=101";
        ResultSet rs = JDBCConnection.selectFromTable(query);
        String expectedCity = "Abu Dhabi";
        String actualCity = rs.getString("city");
        assertEquals(expectedCity, actualCity, "Actual address is '" + actualCity + "'. Expected - '" + expectedCity + "'.");
    }

    @Test
    @Order(7)
    @DisplayName("Отправка SELECT JOIN запроса. Проверка соответствия фамилии и округа в таблицах address и customer ")
    public void testSelectWithJoinRequest_CheckDistrictAndLastName() throws SQLException {
        String query = "SELECT ad.district, cust.last_name from address ad JOIN customer cust ON ad.address_id=cust.address_id WHERE last_name='SMITH'";
        ResultSet rs = JDBCConnection.selectFromTable(query);
        String expectedLastName = "SMITH";
        String actualLastName = rs.getString("last_name");
        assertEquals(expectedLastName, actualLastName, "Actual last name is '" + actualLastName + "'. Expected - '" + expectedLastName + "'.");
    }

}
