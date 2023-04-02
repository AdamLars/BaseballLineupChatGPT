package App;
import javax.swing.*;
import java.awt.*;

public class InstructionsPanel extends JPanel{
    public InstructionsPanel()
    {
        //Set instructions title label
        JLabel instructionTitleJLabel = new JLabel();
        instructionTitleJLabel.setText("Instructions:");
        instructionTitleJLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        instructionTitleJLabel.setForeground(Color.BLACK);
        instructionTitleJLabel.setHorizontalAlignment(JLabel.CENTER);

        //Needs two JLabel bodies to get all of the body on the UI
        JLabel instructionsBodyJLabel1 = new JLabel();
        instructionsBodyJLabel1.setText("Enter in each players' slash lines going out to 3 decimal places. Then");
        instructionsBodyJLabel1.setFont(new Font("Verdana", Font.PLAIN, 10));
        instructionsBodyJLabel1.setForeground(Color.BLACK);

        JLabel instructionsBodyJLabel2 = new JLabel();
        instructionsBodyJLabel2.setText("click submit and ChatGPT will try to figure out the best possible lineup.");
        instructionsBodyJLabel2.setFont(new Font("Verdana", Font.PLAIN, 10));
        instructionsBodyJLabel2.setForeground(Color.BLACK);

        this.setBounds(400, 25, 400, 50);
        this.setLayout(new GridLayout(3, 1));
        //this.setBackground(Color.PINK);
        this.add(instructionTitleJLabel);
        this.add(instructionsBodyJLabel1);
        this.add(instructionsBodyJLabel2);
    }
}
