package com.bensler.woopu;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;

import com.bensler.woopu.model.Field;
import com.bensler.woopu.ui.ComponentIconAdapter;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DesertYellow;

/**
 * WooPu application main class bootstrapping everything.
 */
public class App implements ActionListener {

  public static void main(String[] args) throws UnsupportedLookAndFeelException, IOException {
    new App();
  }

  private final Map<JButton, Field> btnToField;
  private final FieldComponent mainFieldComp;

  public App() throws UnsupportedLookAndFeelException, IOException {
    LookUtils.setLookAndTheme(new PlasticLookAndFeel(), new DesertYellow());
    btnToField = new LinkedHashMap<>();

    final ImageSource imgSrc = new ImageSource();
    final JPanel mainPanel = new JPanel(new FormLayout(
      "4dlu, c:p:g, 10dlu, p, 4dlu",
      "4dlu, t:p:g, 4dlu"
    ));

    mainPanel.add(mainFieldComp = new InteractiveFieldComponent(3, imgSrc, new Field()), new CellConstraints(2, 2));
    mainPanel.add(createButtonPanel(imgSrc), new CellConstraints(4, 2));
    new ApplicationFrame("WooPu", imgSrc.windowIcon.getImage(), mainPanel);
  }

  private JPanel createButtonPanel(ImageSource imgSrc) {
    final Fields[] fields = Fields.values();
    final JPanel buttonPanel = new JPanel(new FormLayout(
      "p",
      "p, 4dlu, p, 4dlu, p, 4dlu, p, 4dlu, p, 4dlu, p, 4dlu, p, 4dlu, p" // TODO
    ));

    for (int i = 0; i < fields.length; i++) {
      final Field field = fields[i].getField();
      final JButton btn = new JButton(new ComponentIconAdapter(new FieldComponent(0.3f, imgSrc, field)));

      btn.setMargin(new Insets(5, 5, 5, 5));
      btnToField.put(btn, field);
      btn.addActionListener(this);
      buttonPanel.add(btn, new CellConstraints(1, (i * 2) + 1));
    }
    return buttonPanel;
  }

  @Override
  public void actionPerformed(ActionEvent evt) {
    mainFieldComp.setField(btnToField.get(evt.getSource()));
  }

}
