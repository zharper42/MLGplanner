/*
 * Group2:
 *      Willaim Benefield <wbb31@uab.edu>
 *      Clayton Dalton <cgdalton@uab.edu>
 *      Luis Figueroa <alefigue@uab.edu>
 *      Zachary Harper <zharper@uab.edu>
 *      Daniel Pineda <mdpineda@uab.edu>
 *      Joe Wong <jawong1@uab.edu>
 *
 * Assignment:  Retirement Planner - EE333 Spring 2022
 */
package edu.uab.zharper.retirementplannergui;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.media.AudioClip;
import javafx.scene.layout.BorderPane;
/**
 * Input Page Controller class
 *
 * @author Zachary Harper
 */
public class InputpageController implements Initializable {
    
  private final DecimalFormat numberFormat = new DecimalFormat("#.00");
  int num = 0;

  //Background Pane
  @FXML private BorderPane bPane;
  
  //Input text fields
  @FXML private TextField initialTextField;
  @FXML private TextField monthlyTextField;
  @FXML private TextField aprTextField;
  @FXML private TextField yearsTextField;
  
  //Text field warning labels
  @FXML private Label initialInvalid;
  @FXML private Label monthlyInvalid;
  @FXML private Label aprInvalid;
  @FXML private Label yearsInvalid;
  
  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
      
    Image mlg = new Image("MLG.jpg");
        BackgroundImage myBI= new BackgroundImage(mlg, null, null, null, null);
        Background mlgBack = new Background(myBI);
        
        bPane.setBackground(mlgBack);

  }

  /** 
   * About Page button 
   *
   * @throws IOException
   */
  @FXML
  private void switchToAbout() throws IOException {
      
    //Let StaticTemp know which page you came from
    StaticTemp.setWhichPage(true);
    App.setRoot("aboutForm");
    
    //Play audio
    AudioClip note = new AudioClip(this.getClass().getResource("/HITMARKER.mp3").toString());
    note.play();
  }
  
  /** 
   * Check if any fields are invalid 
   *
   * @return true or false
   */
  @FXML
  public boolean checkFields() {
   
      double doubleValue;
      int intValue;
      num = 0;
      
      //Label any invalid fields
      try {
        doubleValue = Double.parseDouble((initialTextField.getText()));
      } catch (NumberFormatException a) {
        initialInvalid.setText("Invalid Field");
        num += 1;
      }
        
      try {
        doubleValue = Double.parseDouble((monthlyTextField.getText()));
      } catch (NumberFormatException b) {
        monthlyInvalid.setText("Invalid Field");
        num += 1;
      }
        
      try {
        doubleValue = Double.parseDouble((aprTextField.getText()));
      } catch (NumberFormatException c) {
        aprInvalid.setText("Invalid Field");
        num += 1;
      }
        
      try {
        intValue = Integer.parseInt((yearsTextField.getText()));
      } catch (NumberFormatException d) {
        yearsInvalid.setText("Invalid Field");
        num += 1;
      }
      
      //Play audio
    if (num == 3){
        AudioClip note = new AudioClip(this.getClass().getResource("/triple.mp3").toString());
        note.play();
      }
    else{
        AudioClip note = new AudioClip(this.getClass().getResource("/noscope.mp3").toString());
        note.play();
    }
      
      //Return false if any fields are labeled invalid
      if ((initialInvalid.getText()).equals("Invalid Field"))
          return false;
      if ((monthlyInvalid.getText()).equals("Invalid Field"))
          return false;
      if ((aprInvalid.getText()).equals("Invalid Field"))
          return false;
      if ((yearsInvalid.getText()).equals("Invalid Field"))
          return false;
      
      return true;
  }
    
  /** 
   * Button to calculate 
   *
   * @throws IOException
   */
  @FXML
  public void CalculateClick() throws IOException {
      
    //Resets warning labels and checks if fields are valid
    initialInvalid.setText("");
    monthlyInvalid.setText("");
    aprInvalid.setText("");
    yearsInvalid.setText("");
    if (checkFields() == false)
        return;
    
    //Variables
    double balance = Double.valueOf(initialTextField.getText());
    double monthly = Double.valueOf(monthlyTextField.getText());
    double percent = Double.valueOf(aprTextField.getText());
    int years = Integer.valueOf(yearsTextField.getText());
    double directlyInvested, dividend, yrate, mrate;
    double infBalance, infDividend;
    double inflation = 0.025;

    //Yearly/monthly return
    yrate = percent * 0.01;
    mrate = yrate / 12;
    directlyInvested = balance;

    //Runs once per year
    for (int i = 1; !(i > years); i++) {

      balance = Compound(yrate, mrate, monthly, balance);
      directlyInvested += (monthly * 12);
    }

    //Dividend is 4%, Inflation 2.5% per year
    dividend = (balance * 0.04) / 12;
    inflation = (inflation * years) + 1;
    infDividend = dividend / inflation;
    infBalance = balance /inflation;
    
    /* Formats and pipes the results to the static class.
    The OutputpageController can retrieve results from there */

    //After every run, reset the result values that displays
    StaticTemp.setBalance("" + balance);
    StaticTemp.setDirectlyInvested("" + directlyInvested);
    StaticTemp.setDividend("" + dividend);
    StaticTemp.setInfBalance("" + infBalance);
    StaticTemp.setInfDividend("" + infDividend);

    //Formats and pipes the results to staticTemp
    StaticTemp.setBalance("" + numberFormat.format(balance));
    StaticTemp.setDirectlyInvested("" + numberFormat.format(directlyInvested));
    StaticTemp.setDividend("" + numberFormat.format(dividend));
    StaticTemp.setInfBalance("" + numberFormat.format(infBalance));
    StaticTemp.setInfDividend("" + numberFormat.format(infDividend));

    //Switches scenes
    App.setRoot("outputpage");

  }

  /** 
   * Compounds the balance for every month in a year 
   *
   * @param yrate
   * @param mrate
   * @param monthly
   * @param balance
   * @return balance
   */
  public static double Compound(double yrate, double mrate, double monthly, double balance) {

    //Variables
    double monthYield, yearTotal = 0;

    //Run for every month
    for (int i = 1; i < 13; i++) {

      //Basically magic
      yearTotal += monthly;
      monthYield = yearTotal * mrate;
      yearTotal += monthYield;
    }

    balance += (balance * yrate); //Compound for entire balance
    balance += yearTotal; //Compound for this specific year

    return balance;
  }
 
}
