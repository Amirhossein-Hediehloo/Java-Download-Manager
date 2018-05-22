package com.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Settings { //todo: place components in right locations
 //todo:when user change L&F a pop up panel says "PLZ Re run program to change L&F"
    //fields
    private JDialog frame = new JDialog();
    private JComboBox lookAndFeelChooser;
    private JComboBox downloadLimit;
    private JButton openFile;
    private JFileChooser address;
    private JButton ok = new JButton(new ImageIcon("Files//ok.png"));
    private JButton cancel = new JButton(new ImageIcon("Files//cancel.png"));
    private String dir = new String("F://Projects//term2//JDM//Files");

    //constructor
    public Settings(){
        frame.setLocation(670,300);
        frame.setTitle("Settings");
        frame.setModal(true);
        frame.setSize(500,300);
        //BoxLayout layout = new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS);
        frame.getContentPane().setBackground(Color.decode("#c8e2ba")); // green color
        //frame.getContentPane().setBackground(Color.decode("#32363f")); //gray color
        frame.setLayout(null);
        frame.setResizable(false);
        lookAndFeelChooser = new JComboBox(sysLookAndFeels());
        String[] possibleNumbers = new String[4];
        possibleNumbers[0] = "Unlimited"; possibleNumbers[1] = "1"; possibleNumbers[2] = "4"; possibleNumbers[3] = "8";
        downloadLimit = new JComboBox(possibleNumbers);
        updateSettings();
        makeLookComboBox();
        makeDownloadLimitComboBox();
        makeFileChooser();
        addOkAction();
        addCancelAction();
        frame.setVisible(true);
    }

    //methods

    private void makeFileChooser(){
        address = new JFileChooser();
        openFile = new JButton("Set Path");
        openFile.setSize(140,30);
        openFile.setLocation(180,120);
        openFile.addActionListener(e -> {
            int i = address.showOpenDialog(frame);
            if( i == JFileChooser.APPROVE_OPTION){
                File file = address.getCurrentDirectory();
                dir = file.getAbsolutePath();
            }
        });
        frame.getContentPane().add(openFile);
    }

    private void makeDownloadLimitComboBox(){ //add a combo box which contains some numbers for setting maximum number of download files
        JLabel label = new JLabel("Files at same time :");
        label.setSize(120,20);
        label.setLocation(60,80);
        frame.getContentPane().add(label);

        downloadLimit.setSize(140,27);
        downloadLimit.setLocation(180,75);
        frame.getContentPane().add(downloadLimit);
    }

    private void makeLookComboBox(){ //add a combo box including system look and feels to frame
        JLabel label = new JLabel("Look And Feel :");
        label.setSize(120,20);
        label.setLocation(60,35);
        frame.getContentPane().add(label);
        lookAndFeelChooser.setSize(140,27);
        lookAndFeelChooser.setLocation(180,30);
        frame.getContentPane().add(lookAndFeelChooser);
    }

    private String[] sysLookAndFeels(){ //return System look and feels: metal - nimbus - CDE/Motif - Windows - Windows Classic
        UIManager.LookAndFeelInfo plaf[] = UIManager.getInstalledLookAndFeels();
        String[] list = new String[plaf.length];
        for(int i = 0 ; i < plaf.length ; i++){
            list[i] = plaf[i].getName();
            //System.out.println(plaf[i].getName());
        }
        for(int i = 0 ; i < list.length ; i++){
            if(list[i].equals("Windows Classic")){
                list[i] = "WindowsClassic";
            }
        }
        return list;
    }

    private void addOkAction(){ //add action to OK button, it saves the setting in file directory in a text file
        ok.setSize(80,60);
        ok.setBackground(Color.decode("#c8e2ba"));
        ok.setLocation(280,195);
        frame.getContentPane().add(ok);
        ok.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File settingFile = new File("Files//Settings.txt");
                if (settingFile.exists()){
                    //System.out.println("File found");
                }
                BufferedWriter bufferedWriter = null;
                try {
                    bufferedWriter = new BufferedWriter(new FileWriter(settingFile));
                } catch (IOException e1) {
                    System.out.println("Can't Write setting File");
                }
//                String newState = "";
                 SettingFileInfo.getItems().setLookAndFeel( "" + lookAndFeelChooser.getSelectedItem() );
                SettingFileInfo.getItems().setDownloadLimit( "" + downloadLimit.getSelectedItem() );
                SettingFileInfo.getItems().setSaveDir(dir);
//                try {
//                    bufferedWriter.write(newState);
//                } catch (IOException e1) {
//                    System.out.println("Can't Write setting File");
//                }
//                finally {
//                    try {
//                        bufferedWriter.close();
//                        frame.setVisible(false);
//                    } catch (IOException e1) {
//                    }
//                }
            }
        });

    }

    private void addCancelAction(){
        cancel.setSize(80,60);
        cancel.setLocation(380,195);
        cancel.setBackground(Color.decode("#c8e2ba"));
        frame.getContentPane().add(cancel);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
    }



    public void updateSettings(){ // update settings
        switch (SettingFileInfo.getItems().lookAndFeel){
            case "Metal":{
                lookAndFeelChooser.setSelectedItem("Metal");
                break;
            }
            case "Nimbus":{
                lookAndFeelChooser.setSelectedItem("Nimbus");
                break;
            }
            case "Windows":{
                lookAndFeelChooser.setSelectedItem("Windows");
                break;
            }
            case "WindowsClassic":{
                lookAndFeelChooser.setSelectedItem("WindowsClassic");
                break;
            }
            case "CDE/Motif":{
                lookAndFeelChooser.setSelectedItem("CDE/Motif");
                break;
            }
        }

        switch (SettingFileInfo.getItems().downloadLimit){
            case "Unlimited":{
                downloadLimit.setSelectedItem("Unlimited");
                break;
            }
            case "1":{
                downloadLimit.setSelectedItem("1");
                break;
            }
            case "4":{
                downloadLimit.setSelectedItem("4");
                break;
            }
            case "8":{
                downloadLimit.setSelectedItem("8");
                break;
            }
        }

    }
}
