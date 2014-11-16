package sample.demo.aux;

import javax.swing.JOptionPane;
import org.apache.commons.lang3.StringUtils;
import eu.ggnet.saft.core.aux.OnOk;
import sample.old.*;

/**
 *
 * @author pascal.perau
 */
public class DocumentAdressUpdateView extends javax.swing.JPanel implements OnOk {

    private final String originalAddress;

    private final long customerId;

    private final boolean invoice;

    public DocumentAdressUpdateView(long customerId, String address, boolean invoice) {
        initComponents();
        this.customerId = customerId;
        this.originalAddress = address;
        adressArea.setText(address);
        this.invoice = invoice;
    }

    public String getAddress() {
        return adressArea.getText();
    }

    @Override
    public boolean onOk() {
        if (StringUtils.isBlank(adressArea.getText())) {
            JOptionPane.showMessageDialog(this, "Addressfeld ist leer...");
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        adressArea = new javax.swing.JTextArea();
        resetToOriginalButton = new javax.swing.JButton();
        resetToCustomerButton = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(300, 300));
        setLayout(new java.awt.GridBagLayout());

        adressArea.setColumns(20);
        adressArea.setRows(5);
        jScrollPane1.setViewportView(adressArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jScrollPane1, gridBagConstraints);

        resetToOriginalButton.setText("<html>Auf Originaladresse<br />zurücksetzen</html>");
        resetToOriginalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetToOriginalButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        add(resetToOriginalButton, gridBagConstraints);

        resetToCustomerButton.setText("<html>Auf Kundenadresse<br />zurücksetzen</html>");
        resetToCustomerButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        resetToCustomerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetToCustomerButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(resetToCustomerButton, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void resetToOriginalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetToOriginalButtonActionPerformed
        adressArea.setText(originalAddress);
    }//GEN-LAST:event_resetToOriginalButtonActionPerformed

    private void resetToCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetToCustomerButtonActionPerformed
        System.out.println("Would do a reset");
    }//GEN-LAST:event_resetToCustomerButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea adressArea;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton resetToCustomerButton;
    private javax.swing.JButton resetToOriginalButton;
    // End of variables declaration//GEN-END:variables

    public static void main(String[] args) {
        String adress = "Hans Mustermann\nMusterstrasse 22\n12345 Musterhausen";

        DocumentAdressUpdateView view = new DocumentAdressUpdateView(1, adress, true);
        OkCancelDialog<DocumentAdressUpdateView> dialog = new OkCancelDialog<>("TOLLER TITEL", view);
        dialog.setVisible(true);
        if (dialog.isOk()) {
            System.out.println("OK pressed");
            System.out.println(view.getAddress());
        } else {
            System.out.println("Closed without OK");
        }
        dialog.dispose();
    }
}
