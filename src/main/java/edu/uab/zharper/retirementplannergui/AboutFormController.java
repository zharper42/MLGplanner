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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.AudioClip;

/**
 * FXML Controller class
 *
 * @author mdpineda
 */
public class AboutFormController implements Initializable {

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  /** 
   * Go Back button 
   *
   * @throws IOException
   */
  @FXML
  public void goBackClick() throws IOException {
      
    //Go back to the page you came from
    if (StaticTemp.getWhichPage() == true)
        App.setRoot("inputpage");
    else
        App.setRoot("outputpage");
    
    //Play audio
    AudioClip note = new AudioClip(this.getClass().getResource("/HITMARKER.mp3").toString());
    note.play();
  }
}
