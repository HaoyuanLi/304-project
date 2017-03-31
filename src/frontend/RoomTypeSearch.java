package frontend;

import backend.Residence;
import backend.RoomType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by GL on 2017-03-28.
 */
public class RoomTypeSearch extends JPanel {

    private JCheckBox typeBox = new JCheckBox("Type", true);
    private JCheckBox accommodationBox = new JCheckBox("accommodation", true);
    private JCheckBox sizeBox = new JCheckBox("Size", true);
    private JCheckBox rateBox = new JCheckBox("Rate", true);
    private JCheckBox featuresBox = new JCheckBox("Features", true);

    private JTextField accommodationText = new JTextField(8);
    private JTextField sizeText = new JTextField(8);
    private JTextField rateText = new JTextField(8);
    private JCheckBox kitchenBox = new JCheckBox("Kitchen");
    private JCheckBox bathroomBox = new JCheckBox("Bathroom");
    private JCheckBox loungeBox = new JCheckBox("Lounge");
    String[] symbolString = {"<", ">", "=", "<=", ">="};
    private JComboBox accommodationDrop = new JComboBox(symbolString);
    private JComboBox sizeDrop = new JComboBox(symbolString);
    private JComboBox rateDrop = new JComboBox(symbolString);
    private JButton searchButton = new JButton("Search");

    String[] aggregationString = {"maximum", "minimum", "average", "number", "sum"};
    private JComboBox aggregationDrop = new JComboBox(aggregationString);
    String[] attributeString = {"accommodation", "size", "rate"};
    private JComboBox attributeDrop = new JComboBox(attributeString);
    private JButton findButton = new JButton("Find");
    private JButton doubleAggButton = new JButton("DoubleAggregation");

    public RoomTypeSearch(int width, int height) {
        super(new GridBagLayout());
        setPreferredSize(new Dimension(width, height));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 20, 20, 20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(createAttributePanel(), gbc);

        gbc.gridy = 1;
        add(createSearchPanel(), gbc);

        gbc.gridy = 2;
        add(createFindPanel(), gbc);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!typeBox.isSelected() && !accommodationBox.isSelected() && !sizeBox.isSelected() && !rateBox.isSelected() && !featuresBox.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Please select at least one attribute", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (accommodationText.getText().equals("") && sizeText.getText().equals("") && rateText.getText().equals("") && !kitchenBox.isSelected() && !bathroomBox.isSelected() && !loungeBox.isSelected()) {
                    DefaultTableModel dtm = RoomType.searchRoomType(typeBox.isSelected(), accommodationBox.isSelected(), sizeBox.isSelected(), rateBox.isSelected(), featuresBox.isSelected());
                    new ResultTable(dtm).setVisible(true);
                } else {
                    try {
                        int accommodationVal = -1;
                        int sizeVal = -1;
                        float rateVal = -1;
                        if (!accommodationText.getText().equals("")) {
                            accommodationVal = Integer.parseInt(accommodationText.getText());
                            if (accommodationVal < 0) {
                                throw new NumberFormatException();
                            }
                        }
                        if (!sizeText.getText().equals("")) {
                            sizeVal = Integer.parseInt(sizeText.getText());
                            if (sizeVal < 0) {
                                throw new NumberFormatException();
                            }
                        }
                        if (!rateText.getText().equals("")) {
                            rateVal = Float.parseFloat(rateText.getText());
                            if (rateVal < 0) {
                                throw new NumberFormatException();
                            }
                        }
                        DefaultTableModel dtm = RoomType.searchRoomType(typeBox.isSelected(), accommodationBox.isSelected(), sizeBox.isSelected(), rateBox.isSelected(), featuresBox.isSelected(),
                                accommodationDrop.getSelectedIndex(), accommodationVal, sizeDrop.getSelectedIndex(), sizeVal, rateDrop.getSelectedIndex(), rateVal,
                                kitchenBox.isSelected(), bathroomBox.isSelected(), loungeBox.isSelected());
                        new ResultTable(dtm).setVisible(true);
                    } catch (NumberFormatException ne) {
                        JOptionPane.showMessageDialog(null, "Please enter valid numbers", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Number resultVal = RoomType.findAggregate(aggregationDrop.getSelectedIndex(), attributeDrop.getSelectedIndex());
                String result = "";
                if (aggregationDrop.getSelectedIndex() == 0) {
                    result = "The maximum ";
                }
                if (aggregationDrop.getSelectedIndex() == 1) {
                    result = "The minimum ";
                }
                if (aggregationDrop.getSelectedIndex() == 2) {
                    result = "The average ";
                }
                //TODO
                if (aggregationDrop.getSelectedIndex() == 4) {
                    result = "The sum ";
                }
                if (aggregationDrop.getSelectedIndex() == 3) {
                    result = "There are ";
                    result = result + resultVal.intValue();
                    if (attributeDrop.getSelectedIndex() == 0) {
                        result = result + " different accomodation values";
                    }
                    if (attributeDrop.getSelectedIndex() == 1) {
                        result = result + " different size values";
                    }
                    if (attributeDrop.getSelectedIndex() == 2) {
                        result = result + " different rate values";
                    }
                } else {
                    if (attributeDrop.getSelectedIndex() == 0) {
                        result = result + "accomodation is ";
                        result = result + resultVal;
                    }
                    if (attributeDrop.getSelectedIndex() == 1) {
                        result = result + "size is ";
                        result = result + resultVal;
                    }
                    if (attributeDrop.getSelectedIndex() == 2) {
                        result = result + "rate is ";
                        result = result + resultVal;
                    }

                }
                JOptionPane.showMessageDialog(null, result, "Find Result", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        doubleAggButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Number resultVal = RoomType.doubleAggregation();

                JOptionPane.showMessageDialog(null, resultVal, "Aggregation Result: ", JOptionPane.INFORMATION_MESSAGE);
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
        attributePanel.add(new JLabel("Show attributes:"), gbc);
        gbc.gridy = 1;
        attributePanel.add(createCheckBoxPanel(typeBox, accommodationBox, sizeBox, rateBox, featuresBox), gbc);

        return attributePanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(new JLabel("Search by:"), gbc);

        gbc.gridy = 1;
        searchPanel.add(new JLabel("Accommodation: "), gbc);
        gbc.gridx = 1;
        searchPanel.add(createDropTextPanel(accommodationDrop, accommodationText), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        searchPanel.add(new JLabel("Size: "), gbc);
        gbc.gridx = 1;
        searchPanel.add(createDropTextPanel(sizeDrop, sizeText), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        searchPanel.add(new JLabel("Rate: "), gbc);
        gbc.gridx = 1;
        searchPanel.add(createDropTextPanel(rateDrop, rateText), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        searchPanel.add(new JLabel("Features: "), gbc);
        gbc.gridx = 1;
        searchPanel.add(createCheckBoxPanel(kitchenBox, bathroomBox, loungeBox), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        searchPanel.add(searchButton, gbc);

        return searchPanel;
    }

    private JPanel createFindPanel() {
        JPanel findPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 10, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        findPanel.add(new JLabel("Look for:"), gbc);

        gbc.gridy = 1;
        findPanel.add(new JLabel("Find the"), gbc);
        gbc.gridx = 1;
        findPanel.add(aggregationDrop, gbc);
        gbc.gridx = 2;
        findPanel.add(new JLabel("of"), gbc);
        gbc.gridx = 3;
        findPanel.add(attributeDrop, gbc);
        gbc.gridx = 0;

        gbc.gridy = 2;
        findPanel.add(findButton, gbc);


        //TODO
        gbc.gridy = 10;
        findPanel.add(doubleAggButton, gbc);


        return findPanel;
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

    private JPanel createDropTextPanel(JComboBox dropDown, JTextField textField) {
        JPanel dropTextPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 10, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        dropTextPanel.add(dropDown);
        gbc.gridx = 1;
        dropTextPanel.add(textField);

        return dropTextPanel;
    }
}
