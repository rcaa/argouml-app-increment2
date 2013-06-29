package org.argouml.notationfeature;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.argouml.kernel.ProjectSettings;
import org.argouml.notation.Notation;
import org.argouml.notation.NotationName;
import org.argouml.notation.ui.NotationComboBox;
import org.argouml.notation.ui.SettingsTabNotation;

public aspect ArgoUMLNotationComboAspect {

    private JComboBox SettingsTabNotation.notationLanguage;

    pointcut createComboHook(JLabel notationLanguageLabel,
            SettingsTabNotation cthis) 
        : execution(* SettingsTabNotation.createComboHook(..)) 
         && args(notationLanguageLabel) && this(cthis);

    before(JLabel notationLanguageLabel, SettingsTabNotation cthis) 
            : createComboHook(notationLanguageLabel, cthis) {
        cthis.notationLanguage = new NotationComboBox();
        notationLanguageLabel.setLabelFor(cthis.notationLanguage);
    }

    pointcut addLanguageLabelPanelHook(JPanel notationLanguagePanel,
            SettingsTabNotation cthis) 
    : execution(* SettingsTabNotation.addLanguageLabelPanelHook(..)) 
     && args(notationLanguagePanel) && this(cthis);

    before(JPanel notationLanguagePanel, SettingsTabNotation cthis) 
        : addLanguageLabelPanelHook(notationLanguagePanel, cthis) {
        notationLanguagePanel.add(cthis.notationLanguage);
    }

    pointcut setSelectedItemHook(SettingsTabNotation cthis) 
        : execution(* SettingsTabNotation.setSelectedItemHook(..)) 
        && this(cthis);

    before(SettingsTabNotation cthis) 
        : setSelectedItemHook(cthis) {
        cthis.notationLanguage
                .setSelectedItem(Notation.getConfiguredNotation());
    }

    pointcut setSelectedItemHook2(ProjectSettings ps, SettingsTabNotation cthis) 
        : execution(* SettingsTabNotation.setSelectedItemHook2(..)) 
        && args(ps) && this(cthis);

    before(ProjectSettings ps, SettingsTabNotation cthis) 
        : setSelectedItemHook2(ps, cthis) {
        cthis.notationLanguage.setSelectedItem(Notation.findNotation(ps
                .getNotationLanguage()));
    }

    pointcut setDefaultNotationHook(SettingsTabNotation cthis) 
        : execution(* SettingsTabNotation.setDefaultNotationHook(..)) 
        && this(cthis);

    before(SettingsTabNotation cthis) 
        : setDefaultNotationHook(cthis) {
        Notation.setDefaultNotation((NotationName) cthis.notationLanguage
                .getSelectedItem());
    }

    pointcut setNotationLanguageHook(ProjectSettings ps,
            SettingsTabNotation cthis) 
        : execution(* SettingsTabNotation.setNotationLanguageHook(..)) 
        && args(ps) && this(cthis);

    before(ProjectSettings ps, SettingsTabNotation cthis) 
        : setNotationLanguageHook(ps, cthis) {
        NotationName nn = (NotationName) cthis.notationLanguage
                .getSelectedItem();
        if (nn != null) {
            ps.setNotationLanguage(nn.getConfigurationValue());
        }
    }

    pointcut getConfiguredNotationHook(SettingsTabNotation cthis) 
        : execution(* SettingsTabNotation.getConfiguredNotationHook(..)) 
        && this(cthis);

    before(SettingsTabNotation cthis) 
        : getConfiguredNotationHook(cthis) {
        cthis.notationLanguage
                .setSelectedItem(Notation.getConfiguredNotation());
    }
}
