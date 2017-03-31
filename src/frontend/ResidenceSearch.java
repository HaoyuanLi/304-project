package frontend;

import backend.Residence;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

/**
 * Created by GL on 2017-03-28.
 */
public class ResidenceSearch extends JPanel {

    private JCheckBox rnameBox = new JCheckBox("Residence Name", true);
    private JCheckBox addressBox = new JCheckBox("Address", true);
    private JCheckBox snameBox = new JCheckBox("School Name", true);

    private JTextField rnameText = new JTextField(40);
    private JTextField addressText = new JTextField(40);
    private JTextField snameText = new JTextField(40);
    private JButton searchButton = new JButton("Search");

    private JCheckBox obrBox = new JCheckBox("One Bedrooms");
    private JCheckBox tbrBox = new JCheckBox("Two Bedrooms");
    private JCheckBox fbrBox = new JCheckBox("Four Bedrooms");
    private JCheckBox sbrBox = new JCheckBox("Six Bedrooms");
    private JCheckBox studioBox = new JCheckBox("Studio");
    private JButton findButton = new JButton("Find");

    public ResidenceSearch(int width, int height) {
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
                if (!rnameBox.isSelected() && !addressBox.isSelected() && !snameBox.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Please select at least one attribute", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (rnameText.getText().equals("") && addressText.getText().equals("") && snameText.getText().equals("")) {
                    // JOptionPane.showMessageDialog(null, "Please fill in at least one field", "Error", JOptionPane.ERROR_MESSAGE);
                    DefaultTableModel dtm = Residence.searchResidence(rnameBox.isSelected(), addressBox.isSelected(), snameBox.isSelected());
                    new ResultTable(dtm).setVisible(true);
                }
                else {
                    DefaultTableModel dtm = Residence.searchResidence(rnameBox.isSelected(), addressBox.isSelected(), snameBox.isSelected(),
                            rnameText.getText(), addressText.getText(), snameText.getText());
                    new ResultTable(dtm).setVisible(true);
                }

            }
        });

//        private JCheckBox obrBox = new JCheckBox("One Bedrooms");
//        private JCheckBox tbrBox = new JCheckBox("Two Bedrooms");
//        private JCheckBox fbrBox = new JCheckBox("Four Bedrooms");
//        private JCheckBox sbrBox = new JCheckBox("Six Bedrooms");
//        private JCheckBox studioBox = new JCheckBox("Studio");
//        private JButton findButton = new JButton("Find");
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!obrBox.isSelected() && !tbrBox.isSelected() && !fbrBox.isSelected() && !sbrBox.isSelected() && !studioBox.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Please select at least one roomtype", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    DefaultTableModel dtm = Residence.searchResidenceByRoomType(obrBox.isSelected(), tbrBox.isSelected(), fbrBox.isSelected(),
                            sbrBox.isSelected(), studioBox.isSelected());
                    new ResultTable(dtm).setVisible(true);
                }

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
        attributePanel.add(createCheckBoxPanel(rnameBox, addressBox, snameBox), gbc);

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
        searchPanel.add(new JLabel("Residence Name: "), gbc);
        gbc.gridx = 1;
        searchPanel.add(rnameText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        searchPanel.add(new JLabel("Address: "), gbc);
        gbc.gridx = 1;
        searchPanel.add(addressText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        searchPanel.add(new JLabel("School Name: "), gbc);
        gbc.gridx = 1;
        searchPanel.add(snameText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
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
        findPanel.add(new JLabel("Find residences by room types:"), gbc);

        gbc.gridy = 1;
        findPanel.add(createCheckBoxPanel(obrBox, tbrBox, fbrBox, sbrBox, studioBox), gbc);

        gbc.gridy = 2;
        findPanel.add(findButton, gbc);

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
}