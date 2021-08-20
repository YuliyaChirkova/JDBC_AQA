package tests;

import dataBaseConnect.JDBCConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import utils.Log;

public class TestSetups {
    @BeforeEach
    public void setUp(TestInfo testInfo) {
        Log.info("------- Started test: " + testInfo.getDisplayName() + " -------");
        Assertions.assertNotNull(JDBCConnection.connectToDB());
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        JDBCConnection.closeConnection();
        Log.info("------- Finished test: " + testInfo.getDisplayName() + " -------");
    }
}
