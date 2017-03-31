package frontend;

import backend.RoomType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Rebecca on 2017-03-30.
 */
public class RoomTypeAdvanceSearch extends JPanel{

//    private JCheckBox typeBox = new JCheckBox("Type", true);
//    private JCheckBox accommodationBox = new JCheckBox("accommodation", true);
//    private JCheckBox sizeBox = new JCheckBox("Size", true);
//    private JCheckBox rateBox = new JCheckBox("Rate", true);
//    private JCheckBox featuresBox = new JCheckBox("Features", true);

    String[] attributeString = {"Type","accommodation", "size", "rate", "Features"};
    private JComboBox attributeDrop = new JComboBox(attributeString);

    String[] aggregationString = {"maximum", "minimum", "average", "number", "sum"};
    private JComboBox aggregationDrop1 = new JComboBox(aggregationString);
    private JComboBox aggregationDrop2 = new JComboBox(aggregationString);

    private JButton findButton = new JButton("Find");

    public RoomTypeAdvanceSearch(int width, int height) {
        super(new GridBagLayout());
        setPreferredSize(new Dimension(width, height));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 20, 20, 20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(createAttributePanel(), gbc);

        gbc.gridy = 1;
        add(createAggregationPanel(), gbc);

        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel resultVal = RoomType.doubleAggregation( aggregationDrop1.getSelectedIndex(), aggregationDrop2.getSelectedIndex(), attributeDrop.getSelectedIndex());


                new ResultTable(resultVal).setVisible(true);
            }
        });


    }

    private JPanel createAttributePanel() {
        JPanel attributePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        attributePanel.add(new JLabel("Advanced Search Room Type Rate"), gbc);
        gbc.gridy = 1;
        //attributePanel.add(createCheckBoxPanel(typeBox, accommodationBox, sizeBox, rateBox, featuresBox), gbc);

        return attributePanel;
    }

    private JPanel createAggregationPanel() {
        JPanel attributePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        attributePanel.add(new JLabel("Group by: "), gbc);
        gbc.gridx= 1;
        attributePanel.add(attributeDrop, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        attributePanel.add(new JLabel("Aggregations: "),gbc);
        gbc.gridx = 1;
        attributePanel.add(aggregationDrop1, gbc);
        gbc.gridx = 2;
        attributePanel.add(aggregationDrop2, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        attributePanel.add(findButton, gbc);

        return attributePanel;

    }

    private JPanel createCheckBoxPanel(JCheckBox... checkBoxes) {
        JPanel checkBoxPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridy = 0;
        int i = 0;
        for (JCheckBox jcb : checkBoxes) {
            if (i >= 5) {
                gbc.gridy++;
                i = i - 5;
            }
            gbc.gridx = i;
            checkBoxPanel.add(jcb, gbc);
            i++;
        }

        return checkBoxPanel;
    }
}
