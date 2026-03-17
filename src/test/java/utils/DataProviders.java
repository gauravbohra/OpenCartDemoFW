package utils;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

    @DataProvider(name = "LoginData")
    public Object[][] getLoginData() throws IOException {
        String filePath = System.getProperty("user.dir") +"//testData//Opencart_LoginData.xlsx";
        ExcelUtility excelUtility = new ExcelUtility(filePath);
        String sheetName = "Sheet1";
        int rows = excelUtility.getRowCount(sheetName);
        int cols = excelUtility.getCellCount(sheetName, 0);
        String[][] loginData = new String[rows][cols];  //Creating a 2D array to store the Excel rows and cols

        for(int i = 0; i<rows; i++){
//            cols = excelUtility.getCellCount(sheetName, i);    //Get the col count for the ith row
//            loginData[i] = new String[cols];      //Creating array to store cells in the ith row

            for (int j = 0 ; j< cols; j++){
                // getting Cell data from the (i+1)th row as 0th row contains column Header
                 loginData[i][j] =
                         excelUtility.getCellData(sheetName, i+1, j);
            }

        }
        return loginData;
    }
}
