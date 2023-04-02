package App;
import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {

    public TitlePanel()
    {
        //Title text
        JLabel aiLineUpCreator = new JLabel();
        aiLineUpCreator.setText("AI Lineup Creator");
        aiLineUpCreator.setForeground(Color.BLACK);
        aiLineUpCreator.setFont(new Font("Verdana", Font.BOLD, 20));
        aiLineUpCreator.setHorizontalAlignment(JLabel.CENTER);

        //Title jpanel
        this.setBounds(0, 0, 400, 100);
        this.setLayout(new BorderLayout());
        this.add(aiLineUpCreator, BorderLayout.CENTER);
    }
}
