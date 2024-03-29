// $Id: SettingsTabNotation.java 17109 2009-04-13 18:44:21Z tfmorris $
// Copyright (c) 1996-2009 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.notation.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.argouml.application.api.Argo;
import org.argouml.configuration.Configuration;
import org.argouml.configuration.ConfigurationKey;
import org.argouml.i18n.Translator;
import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectSettings;
import org.argouml.notation.Notation;
import org.argouml.notation.NotationName;
import org.argouml.notation.NotationSettings;
import org.argouml.swingext.JLinkButton;
import org.argouml.ui.ActionProjectSettings;
import org.argouml.ui.GUIProjectSettingsTabInterface;

/**
 * Settings tab panel for handling Notation settings.
 * <p>
 * 
 * It supports different scopes: application and project. The former is stored
 * in the properties file in the user-directory, the latter in the project file
 * (.zargo,...).
 * <p>
 * 
 * This class is written in a way that supports adding more scopes easily.
 * 
 * @author Thierry Lach
 * @since 0.9.4
 */
public class SettingsTabNotation extends JPanel implements
        GUIProjectSettingsTabInterface {

    private JPanel topPanel;

    // JAVA
    // UML
    // private JComboBox notationLanguage;

  //GUILLEMOTS
    //private JCheckBox useGuillemots;

    private JCheckBox showAssociationNames;

    private JCheckBox showVisibility;

    private JCheckBox showMultiplicity;

    private JCheckBox showInitialValue;

    private JCheckBox showProperties;

    private JCheckBox showTypes;

    private JCheckBox showStereotypes;

    private JCheckBox showSingularMultiplicities;

    private int scope;

    private Project p;

    /**
     * The constructor. We currently support 2 scopes, but this class is written
     * in a way to easily extend that.
     * 
     * @param settingsScope the scope of the settings
     */
    public SettingsTabNotation(int settingsScope) {
        super();
        scope = settingsScope;
    }

    private void buildPanel() {
        setLayout(new BorderLayout());

        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        if (scope == Argo.SCOPE_APPLICATION) {
            JPanel warning = new JPanel();
            warning.setLayout(new BoxLayout(warning, BoxLayout.PAGE_AXIS));
            JLabel warningLabel = new JLabel(Translator
                    .localize("label.warning"));
            warningLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
            warning.add(warningLabel);

            JLinkButton projectSettings = new JLinkButton();
            projectSettings.setAction(new ActionProjectSettings());
            projectSettings.setText(Translator
                    .localize("button.project-settings"));
            projectSettings.setIcon(null);
            projectSettings.setAlignmentX(Component.RIGHT_ALIGNMENT);
            warning.add(projectSettings);

            topPanel.add(warning, BorderLayout.NORTH);
        }

        JPanel settings = new JPanel();
        settings.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 1.0;
        constraints.insets = new Insets(0, 30, 0, 4);

        constraints.gridy = GridBagConstraints.RELATIVE;

        JPanel notationLanguagePanel = new JPanel(new FlowLayout(
                FlowLayout.LEFT, 5, 0));
        JLabel notationLanguageLabel = createLabel("label.notation-language");
        // COMBO
        createComboHook(notationLanguageLabel);
        notationLanguagePanel.add(notationLanguageLabel);
        JLabel notationLanguageChosenLabel = labelCreationHook();
        // COMBO
        addLanguageLabelPanelHook(notationLanguagePanel);
        // LABEL
        addLabelPanelHook(notationLanguagePanel, notationLanguageChosenLabel);
        settings.add(notationLanguagePanel, constraints);
        //GUILLEMOTS
        createCheckBoxGuillemotsHook(settings, constraints);

        // 2002-07-31
        // Jaap Branderhorst
        // from here made visibility etc. configurable

        showAssociationNames = createCheckBox("label.show-associationnames");
        settings.add(showAssociationNames, constraints);

        showVisibility = createCheckBox("label.show-visibility");
        settings.add(showVisibility, constraints);

        showMultiplicity = createCheckBox("label.show-multiplicity");
        settings.add(showMultiplicity, constraints);

        showInitialValue = createCheckBox("label.show-initialvalue");
        settings.add(showInitialValue, constraints);

        showProperties = createCheckBox("label.show-properties");
        settings.add(showProperties, constraints);

        showTypes = createCheckBox("label.show-types");
        settings.add(showTypes, constraints);

        showStereotypes = createCheckBox("label.show-stereotypes");
        settings.add(showStereotypes, constraints);

        showSingularMultiplicities = createCheckBox("label.show-singular-multiplicities");
        settings.add(showSingularMultiplicities, constraints);

        topPanel.add(settings, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
    }

    private void createCheckBoxGuillemotsHook(JPanel settings,
            GridBagConstraints constraints) {
//        useGuillemots = createCheckBox("label.use-guillemots");
//        settings.add(useGuillemots, constraints);
    }

    // COMBO
    private void addLanguageLabelPanelHook(JPanel notationLanguagePanel) {
        // notationLanguagePanel.add(notationLanguage);
    }

    // COMBO
    private void createComboHook(JLabel notationLanguageLabel) {
        // notationLanguage = new NotationComboBox();
        // notationLanguageLabel.setLabelFor(notationLanguage);
    }

    // LABEL
    private void addLabelPanelHook(JPanel notationLanguagePanel,
            JLabel notationLanguageChosenLabel) {
        // notationLanguagePanel.add(notationLanguageChosenLabel);
    }

    // LABEL
    private JLabel labelCreationHook() {
        // JLabel notationLanguageChosenLabel = createLabel(Notation
        // .getConfiguredNotation().getTitle());
        // return notationLanguageChosenLabel;
        return null;
    }

    /*
     * @see org.argouml.ui.GUISettingsTabInterface#handleSettingsTabRefresh()
     */
    public void handleSettingsTabRefresh() {
        if (scope == Argo.SCOPE_APPLICATION) {
            //GUILLEMOTS
            setSelectedGuillemotsHook();
            // COMBO
            setSelectedItemHook();
            showAssociationNames.setSelected(Configuration.getBoolean(
                    Notation.KEY_SHOW_ASSOCIATION_NAMES, true));
            showVisibility
                    .setSelected(getBoolean(Notation.KEY_SHOW_VISIBILITY));
            showInitialValue
                    .setSelected(getBoolean(Notation.KEY_SHOW_INITIAL_VALUE));
            showProperties
                    .setSelected(getBoolean(Notation.KEY_SHOW_PROPERTIES));
            /*
             * The next one defaults to TRUE, to stay compatible with older
             * ArgoUML versions that did not have this setting:
             */
            showTypes.setSelected(Configuration.getBoolean(
                    Notation.KEY_SHOW_TYPES, true));
            showMultiplicity
                    .setSelected(getBoolean(Notation.KEY_SHOW_MULTIPLICITY));
            showStereotypes
                    .setSelected(getBoolean(Notation.KEY_SHOW_STEREOTYPES));
            /*
             * The next one defaults to TRUE, despite that this is NOT
             * compatible with older ArgoUML versions (before 0.24) that did not
             * have this setting - see issue 1395 for the rationale:
             */
            showSingularMultiplicities.setSelected(Configuration.getBoolean(
                    Notation.KEY_SHOW_SINGULAR_MULTIPLICITIES, true));
        }
        if (scope == Argo.SCOPE_PROJECT) {
            assert p != null;
            ProjectSettings ps = p.getProjectSettings();
            NotationSettings ns = ps.getNotationSettings();
            // COMBO
            setSelectedItemHook2(ps);
            //GUILLEMOTS
            setSelectedPsGuillemotsHook(ps);
            showAssociationNames.setSelected(ns.isShowAssociationNames());
            showVisibility.setSelected(ns.isShowVisibilities());
            showMultiplicity.setSelected(ns.isShowMultiplicities());
            showInitialValue.setSelected(ns.isShowInitialValues());
            showProperties.setSelected(ns.isShowProperties());
            showTypes.setSelected(ns.isShowTypes());
            showStereotypes.setSelected(ps.getShowStereotypesValue());
            showSingularMultiplicities.setSelected(ns
                    .isShowSingularMultiplicities());
        }
    }
    //GUILLEMOTS
    private void setSelectedPsGuillemotsHook(ProjectSettings ps) {
        //useGuillemots.setSelected(ps.getUseGuillemotsValue());
    }
  //GUILLEMOTS
    private void setSelectedGuillemotsHook() {
        //useGuillemots.setSelected(getBoolean(Notation.KEY_USE_GUILLEMOTS));
    }

    // COMBO
    private void setSelectedItemHook2(ProjectSettings ps) {
        // notationLanguage.setSelectedItem(Notation.findNotation(ps
        // .getNotationLanguage()));
    }

    // COMBO
    private void setSelectedItemHook() {
        // notationLanguage.setSelectedItem(Notation.getConfiguredNotation());
    }

    /**
     * Get a boolean from the configuration.
     * 
     * @param key a notation key.
     * @return a boolean
     */
    // changed to public
    public static boolean getBoolean(ConfigurationKey key) {
        return Configuration.getBoolean(key, false);
    }

    /*
     * @see org.argouml.ui.GUISettingsTabInterface#handleSettingsTabSave()
     */
    public void handleSettingsTabSave() {
        if (scope == Argo.SCOPE_APPLICATION) {
            // COMBO
            setDefaultNotationHook();
            //GUILLEMOTS
            setBooleanGuillemotsHook();
            Configuration.setBoolean(Notation.KEY_SHOW_ASSOCIATION_NAMES,
                    showAssociationNames.isSelected());
            Configuration.setBoolean(Notation.KEY_SHOW_VISIBILITY,
                    showVisibility.isSelected());
            Configuration.setBoolean(Notation.KEY_SHOW_MULTIPLICITY,
                    showMultiplicity.isSelected());
            Configuration.setBoolean(Notation.KEY_SHOW_INITIAL_VALUE,
                    showInitialValue.isSelected());
            Configuration.setBoolean(Notation.KEY_SHOW_PROPERTIES,
                    showProperties.isSelected());
            Configuration.setBoolean(Notation.KEY_SHOW_TYPES, showTypes
                    .isSelected());
            Configuration.setBoolean(Notation.KEY_SHOW_STEREOTYPES,
                    showStereotypes.isSelected());
            Configuration.setBoolean(Notation.KEY_SHOW_SINGULAR_MULTIPLICITIES,
                    showSingularMultiplicities.isSelected());
        }
        if (scope == Argo.SCOPE_PROJECT) {
            assert p != null;
            ProjectSettings ps = p.getProjectSettings();
            NotationSettings ns = ps.getNotationSettings();
            // COMBO
            setNotationLanguageHook(ps);
            //GUILLEMOTS
            setUseGuillemotsHook(ps);
            ns.setShowAssociationNames(showAssociationNames.isSelected());
            ns.setShowVisibilities(showVisibility.isSelected());
            ns.setShowMultiplicities(showMultiplicity.isSelected());
            ns.setShowInitialValues(showInitialValue.isSelected());
            ns.setShowProperties(showProperties.isSelected());
            ns.setShowTypes(showTypes.isSelected());
            ps.setShowStereotypes(showStereotypes.isSelected());
            ns.setShowSingularMultiplicities(showSingularMultiplicities
                    .isSelected());
        }
    }

  //GUILLEMOTS
    private void setUseGuillemotsHook(ProjectSettings ps) {
        //ps.setUseGuillemots(useGuillemots.isSelected());
    }

    //GUILLEMOTS
    private void setBooleanGuillemotsHook() {
//        Configuration.setBoolean(Notation.KEY_USE_GUILLEMOTS, useGuillemots
//                .isSelected());
    }

    // COMBO
    private void setNotationLanguageHook(ProjectSettings ps) {
        // NotationName nn = (NotationName)
        // notationLanguage.getSelectedItem();
        // if (nn != null) {
        // ps.setNotationLanguage(nn.getConfigurationValue());
        // }
    }

    // COMBO
    private void setDefaultNotationHook() {
        // Notation.setDefaultNotation((NotationName) notationLanguage
        // .getSelectedItem());
    }

    /*
     * @see org.argouml.ui.GUISettingsTabInterface#handleSettingsTabCancel()
     */
    public void handleSettingsTabCancel() {
        handleSettingsTabRefresh();
    }

    /*
     * @see org.argouml.ui.GUISettingsTabInterface#handleResetToDefault()
     */
    public void handleResetToDefault() {
        if (scope == Argo.SCOPE_PROJECT) {
            // COMBO
            getConfiguredNotationHook();
            
            //GUILLEMOTS
            setSelectedGuillemotsHook();
            //useGuillemots.setSelected(getBoolean(Notation.KEY_USE_GUILLEMOTS));
            
            showAssociationNames.setSelected(Configuration.getBoolean(
                    Notation.KEY_SHOW_ASSOCIATION_NAMES, true));
            showVisibility
                    .setSelected(getBoolean(Notation.KEY_SHOW_VISIBILITY));
            showMultiplicity
                    .setSelected(getBoolean(Notation.KEY_SHOW_MULTIPLICITY));
            showInitialValue
                    .setSelected(getBoolean(Notation.KEY_SHOW_INITIAL_VALUE));
            showProperties.setSelected(Configuration
                    .getBoolean(Notation.KEY_SHOW_PROPERTIES));
            showTypes.setSelected(Configuration.getBoolean(
                    Notation.KEY_SHOW_TYPES, true));
            showStereotypes.setSelected(Configuration
                    .getBoolean(Notation.KEY_SHOW_STEREOTYPES));
            showSingularMultiplicities.setSelected(Configuration
                    .getBoolean(Notation.KEY_SHOW_SINGULAR_MULTIPLICITIES));
        }
    }

    // COMBO
    private void getConfiguredNotationHook() {
        // notationLanguage.setSelectedItem(Notation.getConfiguredNotation());
    }

    /*
     * @see org.argouml.ui.GUISettingsTabInterface#getTabKey()
     */
    public String getTabKey() {
        return "tab.notation";
    }

    /*
     * @see org.argouml.ui.GUISettingsTabInterface#getTabPanel()
     */
    public JPanel getTabPanel() {
        if (topPanel == null) {
            buildPanel();
        }
        return this;
    }

    /**
     * Create a localized JCheckBox.
     * 
     * @param key the key for the string to be localized
     * @return a new checkbox with localized text
     */
    // changed to public
    public JCheckBox createCheckBox(String key) {
        JCheckBox j = new JCheckBox(Translator.localize(key));
        return j;
    }

    /**
     * Create a localized JLabel.
     * 
     * @param key the key of the text for the label
     * @return a new label with a localized text for the given key
     */
    protected JLabel createLabel(String key) {
        return new JLabel(Translator.localize(key));
    }

    /*
     * @see javax.swing.JComponent#setVisible(boolean)
     */
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            handleSettingsTabRefresh();
        }
    }

    public void setProject(Project project) {
        assert project != null;
        p = project;
    }
}
