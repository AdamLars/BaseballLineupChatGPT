package App;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

public class DataEntryPanel extends JPanel implements ActionListener {
    JButton submitButton;
    static JLabel errorLabel;
    //Holds all text fields for stat entry
    //Row 0 catcher, Row 1 1st base, Row 2 2nd base, Row 3 3rd base, Row 4 shortstop, Row 5 Left field
    //Row 6 center field, row 7 right field, row 8 DH
    JTextField[][] statEntryPointsTextField = new JTextField[9][3];
    //Labels for each row
    JLabel[] positionLabels = new JLabel[9];
    //All position names I will use
    String[] positionNames = {"Catcher", "1st Base", "2nd Base", "3rd Base", "Shortstop", "Left Field", "Center Field", 
    "Right Field", "Designated Hitter"};

    //Regex format
    String REGEX = "0*\\.[0-9][0-9]?[0-9]?";

    public DataEntryPanel(){
        //Panel size
        this.setBounds(0, 50, 400, 575);
        //this.setBackground(Color.RED);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        
        //Submission button creation
        submitButton = new JButton();
        submitButton.addActionListener(this);
        submitButton.setText("Submit");
        submitButton.setFocusable(false);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
        submitButton.setSize(20, 30);

        //Creates default textbox dimension and labels for the top of textbox columns
        Dimension textBox = new Dimension(80, 20);
        JLabel positionLabel = new JLabel();
        positionLabel.setText("Position");
        positionLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        positionLabel.setForeground(Color.BLACK);
        JLabel avgLabel = new JLabel();
        avgLabel.setText("AVG");
        avgLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        avgLabel.setForeground(Color.BLACK);
        JLabel obpLabel = new JLabel();
        obpLabel.setText("OBP");
        obpLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        obpLabel.setForeground(Color.BLACK);
        JLabel slgLabel = new JLabel();
        slgLabel.setText("SLG");
        slgLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        slgLabel.setForeground(Color.BLACK);

        //errorLabel label
        errorLabel = new JLabel();
        errorLabel.setVisible(false);

        //Add tops of columns
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(positionLabel, gbc);
        gbc.gridx += 1;
        this.add(avgLabel, gbc);
        gbc.gridx += 1;
        this.add(obpLabel, gbc);
        gbc.gridx += 1;
        this.add(slgLabel, gbc);

        //Initialize position labels and entry points
        //Then input names and set the size.
        for(int i = 0; i < statEntryPointsTextField.length; i++)
        {
            //Set position label name (will create the "Catcher" label etc.)
            positionLabels[i] = new JLabel();
            positionLabels[i].setText(positionNames[i]);
            positionLabels[i].setVerticalAlignment(JLabel.CENTER);
            positionLabels[i].setFont(new Font("Verdana", Font.PLAIN, 12));
            positionLabels[i].setForeground(Color.BLACK);
            gbc.gridx = 0;
            gbc.gridy += 1;
            this.add(positionLabels[i], gbc);
            //Adds in JTextFields to the panel in the order of avg, obp, slg
            for(int j = 0; j < statEntryPointsTextField[0].length; j++)
            {
                gbc.gridx += 1;
                statEntryPointsTextField[i][j] = new JTextField();
                statEntryPointsTextField[i][j].setPreferredSize(textBox);
                this.add(statEntryPointsTextField[i][j], gbc);
            }
        }
        //Adds in submit button and the error label in case there is an error.
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(submitButton, gbc);
        gbc.gridy = 11;
        this.add(errorLabel, gbc);

        /*
        //Some default values from Dodgers' 2022 stats.
        statEntryPointsTextField[0][0].setText(".260");
        statEntryPointsTextField[0][1].setText(".343");
        statEntryPointsTextField[0][2].setText(".465");
        statEntryPointsTextField[1][0].setText(".325");
        statEntryPointsTextField[1][1].setText(".407");
        statEntryPointsTextField[1][2].setText(".511");
        statEntryPointsTextField[2][0].setText(".276");
        statEntryPointsTextField[2][1].setText(".346");
        statEntryPointsTextField[2][2].setText(".399");
        statEntryPointsTextField[3][0].setText(".196");
        statEntryPointsTextField[3][1].setText(".329");
        statEntryPointsTextField[3][2].setText(".384");
        statEntryPointsTextField[4][0].setText(".298");
        statEntryPointsTextField[4][1].setText(".343");
        statEntryPointsTextField[4][2].setText(".466");
        statEntryPointsTextField[5][0].setText(".221");
        statEntryPointsTextField[5][1].setText(".304");
        statEntryPointsTextField[5][2].setText(".373");
        statEntryPointsTextField[6][0].setText(".210");
        statEntryPointsTextField[6][1].setText(".265");
        statEntryPointsTextField[6][2].setText(".389");
        statEntryPointsTextField[7][0].setText(".269");
        statEntryPointsTextField[7][1].setText(".340");
        statEntryPointsTextField[7][2].setText(".533");
        statEntryPointsTextField[8][0].setText(".278");
        statEntryPointsTextField[8][1].setText(".350");
        statEntryPointsTextField[8][2].setText(".438");
        */
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == submitButton)
        {
            //Create string array of all the different slash lines to be given to gpt
            String[] allStats = new String[27];
            int counter = 0;
            boolean dataValidated = true;
            boolean avgIsBiggerThanOBPorSLG = false;
            Pattern statFormat = Pattern.compile(REGEX);
            errorLabel.setVisible(false);
            //Loops through the JTextField array and then adds the text to the string array, also checks with regex
            //to make sure that input is usable. If it is not then the loop will break and set bool to false.
            DataProblem:
            for(int i = 0; i < statEntryPointsTextField.length; i++)
                for(int j = 0; j < statEntryPointsTextField[0].length; j++)
                {
                    allStats[counter] = statEntryPointsTextField[i][j].getText();
                    Matcher matched = statFormat.matcher(allStats[counter]);
                    if(!matched.matches())
                    {
                        dataValidated = false;
                        break DataProblem;
                    }
                    if(j > 0 && Double.parseDouble(statEntryPointsTextField[i][0].getText()) > Double.parseDouble(statEntryPointsTextField[i][j].getText()))
                    {    
                        avgIsBiggerThanOBPorSLG = true;
                    }
                    counter++;
                }    
            //Passes query to GPT if data is usable then updates lineup in the predicted lineup panel.
            //Otherwise sets the error JLabel to show an error message and alert the user. 
            if(dataValidated){
                if(avgIsBiggerThanOBPorSLG)
                    setErrorLabel("Warning: AVG can never be larger than OBP and SLG.");
                ChatGPTInteraction newQuery = new ChatGPTInteraction(allStats);
                PredictedLineupPanel.updateAILineups(newQuery.getAiLineup());
            }
            else
                setErrorLabel("Error: Slash lines not configured properly.");
            
        }
    }

    //Sets the error label to be visible and changes the text.
    public static void setErrorLabel(String s){
        errorLabel.setVisible(true);
        errorLabel.setText(s);
    }
}
