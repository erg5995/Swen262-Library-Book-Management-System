package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import system.Client;

import javafx.event.ActionEvent;

public class GUIController
{
    enum State {
        SEARCH, BUY, ARRIVE, DEPART, BORROWED, RETURN,
        INFO, BORROW, ADVANCE, DATETIME, PAY, REPORT,
        REGISTER
    }
    private Client client;
    private State state;
    @FXML
    private TextField text1, text2, text3, text4, text5;
    @FXML
    private Label label1, label2, label3, label4, label5;
    @FXML
    private TextArea labelOut;

    public GUIController()
    {
        client = new Client();
        state = State.REGISTER;
    }

    /** Methods for user input */
    public void searchPressed(ActionEvent e)
    {
        state = State.SEARCH;
        label1.setText("Title:");
        label2.setText("Authors*:");
        label3.setText("ISBN*:");
        label4.setText("Publisher*:");
        label5.setText("Sort Order*:");
        setVisible(5);
        clearInput();
    }
    public void buyPressed(ActionEvent e)
    {
        state = State.BUY;
        label1.setText("Quantity:");
        label2.setText("Book IDs:");
        setVisible(2);
        clearInput();
    }
    public void arrivePressed(ActionEvent e)
    {
        state = State.ARRIVE;
        label1.setText("Visitor ID:");
        setVisible(1);
        clearInput();
    }
    public void departPressed(ActionEvent e)
    {
        state = State.DEPART;
        label1.setText("Visitor ID:");
        setVisible(1);
        clearInput();
    }
    public void borrowedPressed(ActionEvent e)
    {
        state = State.BORROWED;
        label1.setText("Visitor ID:");
        setVisible(1);
        clearInput();
    }
    public void returnPressed(ActionEvent e)
    {
        state = State.RETURN;
        label1.setText("Visitor ID:");
        label2.setText("Book IDs:");
        setVisible(2);
        clearInput();
    }
    public void infoPressed(ActionEvent e)
    {
        state = State.INFO;
        label1.setText("Title:");
        label2.setText("Authors:");
        label3.setText("ISBN*:");
        label4.setText("Publisher*:");
        label5.setText("Sort Order*:");
        setVisible(5);
        clearInput();
    }
    public void borrowPressed(ActionEvent e)
    {
        state = State.BORROW;
        label1.setText("Visitor ID:");
        label2.setText("Books IDs:");
        setVisible(2);
        clearInput();
    }
    public void advancePressed(ActionEvent e)
    {
        state = State.ADVANCE;
        label1.setText("Num. of Days:");
        label2.setText("Num. of Hours*:");
        setVisible(2);
        clearInput();
    }
    public void datetimePressed(ActionEvent e)
    {
        state = State.DATETIME;
        setVisible(0);
        clearInput();
    }
    public void payPressed(ActionEvent e)
    {
        state = State.PAY;
        label1.setText("Visitor ID:");
        label2.setText("Amount:");
        setVisible(2);
        clearInput();
    }
    public void reportPressed(ActionEvent e)
    {
        state = State.REPORT;
        label1.setText("Num. of Days*:");
        setVisible(1);
        clearInput();
    }
    public void registerPressed(ActionEvent e)
    {
        state = State.REGISTER;
        label1.setText("First Name:");
        label2.setText("Last Name:");
        label3.setText("Address:");
        label4.setText("Phone Number:");
        setVisible(4);
        clearInput();
    }
    public void clearPressed(ActionEvent e) {
        labelOut.clear();
    }
    public void submitPressed(ActionEvent e)
    {
        String request = "";
        switch(state) {
            case BUY:
            case PAY:
            case RETURN:
                request = text1.getText() + "," + text2.getText();
                break;
            case INFO:
            case SEARCH:
                request = text1.getText();
                if (text2.getText().length() > 0) {
                    request += ",{" + text2.getText() + "}";
                    if (text3.getText().length() > 0) {
                        request += "," + text3.getText();
                        if (text4.getText().length() > 0) {
                            request += "," + text4.getText();
                            if (text5.getText().length() > 0)
                                request += "," + text5.getText();
                        }
                    }
                }
                break;
            case ARRIVE:
            case DEPART:
            case BORROWED:
                request = text1.getText();
                break;
            case BORROW:
                request = text1.getText() + ",{" + text2.getText() + "}";
                break;
            case REPORT:
                if (text1.getText().length() > 0)
                    request = text1.getText();
                break;
            case ADVANCE:
                request = text1.getText();
                if (text2.getText().length() > 0)
                    request += "," + text2.getText();
                break;
            case DATETIME:
                break;
            case REGISTER:
                request = text1.getText() + "," + text2.getText() + "," + text3.getText() + "," + text4.getText();
        }
        labelOut.setText(client.input(state.toString().toLowerCase() + "," + request) + "\n\n" + labelOut.getText());
    }

    private void setVisible(int num)
    {
        label1.setVisible(false);
        text1.setVisible(false);
        label2.setVisible(false);
        text2.setVisible(false);
        label3.setVisible(false);
        text3.setVisible(false);
        label4.setVisible(false);
        text4.setVisible(false);
        label5.setVisible(false);
        text5.setVisible(false);
        if (num >= 1) {
            label1.setVisible(true);
            text1.setVisible(true);
            if (num >= 2) {
                label2.setVisible(true);
                text2.setVisible(true);
                if (num >= 3) {
                    label3.setVisible(true);
                    text3.setVisible(true);
                    if (num >= 4) {
                        label4.setVisible(true);
                        text4.setVisible(true);
                        if (num == 5) {
                            label5.setVisible(true);
                            text5.setVisible(true);
                        }
                    }
                }
            }
        }
    }
    private void clearInput() {
        text1.clear();
        text2.clear();
        text3.clear();
        text4.clear();
        text5.clear();
    }
}
