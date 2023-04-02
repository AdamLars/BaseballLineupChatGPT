package App;
import java.awt.*;
import javax.swing.*;

//Creates the panel where the predicted lineup is displayed
public class PredictedLineupPanel extends JPanel {
    //Labels for which spot the predicted lineup will be.
    static JLabel[] allAIPositionListing = new JLabel[9];

    public PredictedLineupPanel()
    {
        //Panel layer
        this.setBounds(400, 100, 390, 250);
        //this.setBackground(Color.BLUE);
        this.setLayout(new GridLayout(10, 1));

        //Creates title label and adds it to the panel
        JLabel aiLineUpPredictorTitleLabel = new JLabel();
        aiLineUpPredictorTitleLabel.setText("AI Predicted Lineup:");
        aiLineUpPredictorTitleLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        aiLineUpPredictorTitleLabel.setForeground(Color.BLACK);
        aiLineUpPredictorTitleLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(aiLineUpPredictorTitleLabel);

        //Initializing all the positions with a set text and then adding to panel
        for(int i = 0; i < allAIPositionListing.length; i++)
        {
            allAIPositionListing[i] = new JLabel();
            allAIPositionListing[i].setText(i+1 + ". ");
            allAIPositionListing[i].setFont(new Font("Verdana", Font.PLAIN, 12));
            allAIPositionListing[i].setForeground(Color.BLACK);
            this.add(allAIPositionListing[i]);
        }
    }

    //Updates the positions ChatGPT decides is best
    public static void updateAILineups(String[] s)
    {
        for(int i = 0; i < allAIPositionListing.length; i++)
            allAIPositionListing[i].setText(i+1 + ". " + s[i]);
    }
}
