
package com.example.lib;

import java.awt.AWTException;
import java.awt.FlowLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class KeepAlive extends JFrame {
  JLabel leftLabel, rightLabel;
  Robot robot;
  JTextField hourField, minuteField;
  boolean stopTiming = false;
  // in second
  private final int TIME_FOR_EACH_MOVE = 30;

  void mouseMoving(final long sleepingTime) {

    new Thread() {
      @Override
      public void run() {
        try {
          stopTiming = false;
          Date time = Calendar.getInstance().getTime();
          long currentTime = time.getTime();
          long goalTme = currentTime + sleepingTime;
          Robot r = new Robot();
          while (currentTime < goalTme) {
            if (stopTiming)
              break;
            double random = Math.random() * 100;
            r.mouseMove(700, (int)(100 + random));
            Thread.sleep(1000 * TIME_FOR_EACH_MOVE);
            currentTime += 1000 * TIME_FOR_EACH_MOVE;
          }

        } catch (AWTException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }.start();
  }

  void leftClickSlowlyIn8h() { mouseMoving(8 * 60 * 60 * 1000); }

  void leftClickQuicklyIn10s() {
    try {
      Date time = Calendar.getInstance().getTime();
      long currentTime = time.getTime();
      long goalTme = currentTime + 1000 * 10;
      Robot r = new Robot();
      while (currentTime < goalTme) {
        double random = Math.random() * 100;
        r.mouseMove(700, (int)(100 + random));
        Thread.sleep(1000);
        currentTime += 1000;
      }

    } catch (AWTException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  void start() {
    try {
      JFrame f = new JFrame("Fun");
      JTextField txt = new JTextField(20);
      leftLabel = new JLabel("hour");
      rightLabel = new JLabel("minute");
      JLabel l2 =
          new JLabel("enter the hours on the left minutes on the right");
      hourField = new JTextField(6);
      minuteField = new JTextField(6);
      JButton start = new JButton("run");
      final JButton stop = new JButton("stop");
      start.setEnabled(false);
      f.add(l2);
      f.add(hourField);
      f.add(leftLabel);
      f.add(minuteField);
      f.add(rightLabel);
      f.add(start);
      f.add(stop);
      f.setExtendedState(JFrame.MAXIMIZED_BOTH);
      f.setVisible(true);
      f.setLayout(new FlowLayout());
      f.setDefaultCloseOperation(EXIT_ON_CLOSE);
      f.setLocation(400, 400);
      robot = new Robot();
      start.setEnabled(true);
      stop.setEnabled(true);
      start.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          int hour = 0;
          int minute = 0;
          if (!hourField.getText().isEmpty() &&
              !hourField.getText().equals("0"))
            hour = Integer.valueOf(hourField.getText());
          if (!minuteField.getText().isEmpty() &&
              !minuteField.getText().equals("0"))
            minute = Integer.valueOf(minuteField.getText());
          long sleepingTime = hour * 60 * 60 * 1000 + minute * 60 * 1000;
          mouseMoving(sleepingTime);
        }
      });
      stop.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          stopTiming = true;
        }
      });
    } catch (Exception e) {
    }
  }

  public static void main(String args[]) { new KeepAlive().start(); }
}