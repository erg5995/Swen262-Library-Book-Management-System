package gui;

import javafx.fxml.FXML;
import system.Client;

import javafx.event.ActionEvent;

import java.awt.*;

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
    private Label label1, label2, label3, label4, label5, labelOut;

    public GUIController(Client c)
    {
        client = c;
        registerPressed(new ActionEvent());
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
        clearPressed(e);
    }
    public void buyPressed(ActionEvent e)
    {
        state = State.BUY;
        label1.setText("Quantity:");
        label2.setText("Book IDs:");
        setVisible(2);
        clearPressed(e);
    }
    public void arrivePressed(ActionEvent e)
    {
        state = State.ARRIVE;
        label1.setText("Visitor ID:");
        setVisible(1);
        clearPressed(e);
    }
    public void departPressed(ActionEvent e)
    {
        state = State.DEPART;
        label1.setText("Visitor ID:");
        setVisible(1);
        clearPressed(e);
    }
    public void borrowedPressed(ActionEvent e)
    {
        state = State.BORROWED;
        label1.setText("Visitor ID:");
        setVisible(1);
        clearPressed(e);
    }
    public void returnPressed(ActionEvent e)
    {
        state = State.RETURN;
        label1.setText("Visitor ID:");
        label2.setText("Book IDs:");
        setVisible(2);
        clearPressed(e);
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
        clearPressed(e);
    }
    public void borrowPressed(ActionEvent e)
    {
        state = State.BORROW;
        label1.setText("Visitor ID:");
        label2.setText("Books IDs:");
        setVisible(2);
        clearPressed(e);
    }
    public void advancePressed(ActionEvent e)
    {
        state = State.ADVANCE;
        label1.setText("Number of Days:");
        label2.setText("Number of Hours*:");
        setVisible(2);
        clearPressed(e);
    }
    public void datetimePressed(ActionEvent e)
    {
        state = State.DATETIME;
        setVisible(0);
        clearPressed(e);
    }
    public void payPressed(ActionEvent e)
    {
        state = State.PAY;
        label1.setText("Visitor ID:");
        label2.setText("Amount:");
        setVisible(2);
        clearPressed(e);
    }
    public void reportPressed(ActionEvent e)
    {
        state = State.REPORT;
        label1.setText("Number of Days*:");
        setVisible(1);
        clearPressed(e);
    }
    public void registerPressed(ActionEvent e)
    {
        state = State.REGISTER;
        label1.setText("First Name:");
        label2.setText("Last Name:");
        label3.setText("Address:");
        label4.setText("Phone Number:");
        setVisible(4);
        clearPressed(e);
    }
    public void clearPressed(ActionEvent e)
    {
        text1.setText("");
        text2.setText("");
        text3.setText("");
        text4.setText("");
        text5.setText("");
    }
    public void submitPressed(ActionEvent e)
    {}

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
}
