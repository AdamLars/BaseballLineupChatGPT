package App;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.*;

//https://code.visualstudio.com/docs/java/java-project

public class LineupApplication {

    public LineupApplication(){
        JFrame frame = new JFrame("ChatGPT Lineup Constructor");
        frame.setBackground(Color.WHITE);

        //Title jpanel
        JPanel appTitle = new TitlePanel();

        //Create data entry fields & buttons on panel
        JPanel dataEntry = new DataEntryPanel();

        //Create instructions panel
        JPanel instructionsJPanel = new InstructionsPanel();

        //Create Predicted Lineup UI panel
        JPanel PredictedLineup = new PredictedLineupPanel();

        //Create picture and add it to bottom right corner
        ImageIcon MLBPositions = retrieveImage("MLBPositions.png");
        JLabel positionsPicture = new JLabel(MLBPositions);
        positionsPicture.setBounds(0, 0, 380, 294);
        JPanel positionsPictureContainer = new JPanel();
        positionsPictureContainer.setBounds(390, 350, 400, 300);
        positionsPictureContainer.add(positionsPicture);

        //Make image for the icon of application
        ImageIcon baseballIcon = retrieveImage("BaseballIcon.png");

        //Set frame look, add panels to frame
        frame.setSize(800, 700);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setIconImage(baseballIcon.getImage());
        frame.add(appTitle);
        frame.add(dataEntry);
        frame.add(PredictedLineup);
        frame.add(instructionsJPanel);
        frame.add(positionsPictureContainer);
        frame.setResizable(false);
        //frame.pack();

    }

    private ImageIcon retrieveImage(String url)
    {
        ImageIcon icon;
        InputStream inputUrl = getClass().getResourceAsStream(url);
        try{
            icon = new ImageIcon(ImageIO.read(inputUrl));
        } catch(IOException e)
        {
            icon = new ImageIcon();
            System.out.println("Problem");
        }
        return icon;
    }
}
