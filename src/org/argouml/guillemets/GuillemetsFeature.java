// $Id: eclipse-argo-codetemplates.xml 11347 2006-10-26 22:37:44Z linus $
// Copyright (c) 2013 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies. This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason. IN NO EVENT SHALL THE
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

package org.argouml.guillemets;

import java.awt.GridBagConstraints;
import java.beans.PropertyChangeEvent;

import javax.swing.JPanel;

import org.argouml.configuration.Configuration;
import org.argouml.configuration.ConfigurationKey;
import org.argouml.kernel.ProjectSettings;
import org.argouml.notation.Notation;
import org.argouml.notation.NotationSettings;
import org.argouml.notation.ui.SettingsTabNotation;
import org.argouml.persistence.ArgoParser;
import org.argouml.persistence.ArgoTokenTable;
import org.argouml.persistence.XMLElement;
import org.argouml.ui.explorer.ExplorerEventAdaptor;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.tigris.gef.undo.Memento;

@Aspect
public class GuillemetsFeature {

    @Pointcut("execution(* org.argouml.persistence.ArgoParser.handleUseGuillemots(..)) && this(cthis) && args(e)")
    public void handleUseGuillemots(ArgoParser cthis, XMLElement e) {}
    
    @Before("handleUseGuillemots(cthis, e)")
    public void before1(ArgoParser cthis, XMLElement e) {
        String ug = e.getText().trim();
        cthis.ps.setUseGuillemots(ug);
    }

    @Pointcut("execution(* org.argouml.persistence.ArgoTokenTable.addTokenGuillemetsHook(..)) && this(cthis)")
    public void addTokenGuillemetsHook(ArgoTokenTable cthis)  {}
    
    @Before("addTokenGuillemetsHook(cthis)")
    public void before2(ArgoTokenTable cthis) {
        cthis.addToken(ArgoTokenTable.STRING_USEGUILLEMOTS,
        Integer.valueOf(ArgoTokenTable.TOKEN_USEGUILLEMOTS));
    }
    
    @Pointcut("execution(* org.argouml.ui.explorer.ExplorerEventAdaptor.addListenerGuillemotsHook(..)) && this(cthis)")
    public void addListenerGuillemotsHook(ExplorerEventAdaptor cthis) {}
    
    @Before("addListenerGuillemotsHook(cthis)")
    public void before3(ExplorerEventAdaptor cthis) {
        Configuration.addListener(Notation.KEY_USE_GUILLEMOTS, cthis);
    }

    @Pointcut("execution(* org.argouml.ui.explorer.ExplorerEventAdaptor.isChangePropertyGuillemetsHook(..)) && args(pce)")
    public void isChangePropertyGuillemetsHook(final PropertyChangeEvent pce) {}
    
    @Around("isChangePropertyGuillemetsHook(pce)")
    public boolean around1(final PropertyChangeEvent pce) {
        return Notation.KEY_USE_GUILLEMOTS.isChangedProperty(pce);
    }

    @Pointcut("execution(* org.argouml.notation.ui.SettingsTabNotation.createCheckBoxGuillemotsHook(..)) && args(settings, constraints) && this(cthis)")
    public void createCheckBoxGuillemotsHook(JPanel settings, GridBagConstraints constraints, SettingsTabNotation cthis) {}
    
    @Before("createCheckBoxGuillemotsHook(settings, constraints, cthis)")
    public void before4(JPanel settings, GridBagConstraints constraints, SettingsTabNotation cthis) {
        cthis.useGuillemots = cthis.createCheckBox("label.use-guillemots");
        settings.add(cthis.useGuillemots, constraints);
    }

    @Pointcut("execution(* org.argouml.notation.ui.SettingsTabNotation.setSelectedGuillemotsHook(..)) && this(cthis)")
    public void setSelectedGuillemotsHook(SettingsTabNotation cthis) {}
    
    @Before("setSelectedGuillemotsHook(cthis)")
    public void before5(SettingsTabNotation cthis) {
        cthis.useGuillemots.setSelected(SettingsTabNotation.getBoolean(Notation.KEY_USE_GUILLEMOTS));
    }

    @Pointcut("execution(* org.argouml.notation.ui.SettingsTabNotation.setBooleanGuillemotsHook(..)) && this(cthis)")
    public void setBooleanGuillemotsHook(SettingsTabNotation cthis) {}
    
    @Before("setBooleanGuillemotsHook(cthis)")
    public void before6(SettingsTabNotation cthis) {
        Configuration.setBoolean(Notation.KEY_USE_GUILLEMOTS,
                cthis.useGuillemots.isSelected());
    }

    @Pointcut("execution(* org.argouml.notation.ui.SettingsTabNotation.setUseGuillemotsHook(..))  && this(cthis) && args(ps)")
    public void setUseGuillemotsHook(SettingsTabNotation cthis, ProjectSettings ps)  {}
    
    @Before("setUseGuillemotsHook(cthis, ps)")
    public void before7(SettingsTabNotation cthis, ProjectSettings ps) {
        ps.setUseGuillemots(cthis.useGuillemots.isSelected());
    }

    @Pointcut("execution(* org.argouml.notation.ui.SettingsTabNotation.setSelectedPsGuillemotsHook(..)) && this(cthis) && args(ps)")
    public void setSelectedPsGuillemotsHook(SettingsTabNotation cthis, ProjectSettings ps) {}
    
    @Before("setSelectedPsGuillemotsHook(cthis, ps)")
    public void before8(SettingsTabNotation cthis, ProjectSettings ps) {
        cthis.useGuillemots.setSelected(ps.getUseGuillemotsValue());
    }

    @Pointcut("execution(* org.argouml.notation.Notation.addListenerGuillemetsHook(..)) && this(cthis)")
    public void addListenerGuillemetsHook(Notation cthis) {}
    
    @Before("addListenerGuillemetsHook(cthis)")
    public void before9(Notation cthis) {
        Configuration.addListener(Notation.KEY_USE_GUILLEMOTS, cthis);
    }

    @Pointcut("execution(* org.argouml.notation.NotationSettings.setUseGuillemetsHook(..)) && args(settings)")
    public void setUseGuillemetsHook(NotationSettings settings) {}
    
    @Before("setUseGuillemetsHook(settings)")
    public void before10(NotationSettings settings) {
        settings.setUseGuillemets(false);
    }

    @Pointcut("execution(* org.argouml.notation.NotationSettings.isUseGuillemets(..)) && this(cthis)")
    public void isUseGuillemets(NotationSettings cthis) {}
    
    @Around("isUseGuillemets(cthis)")
    public boolean around2(NotationSettings cthis) {
        if (cthis.useGuillemetsSet) {
            return cthis.useGuillemets;
        } else if (cthis.parent != null) {
            return cthis.parent.isUseGuillemets();
        }
        return NotationSettings.getDefaultSettings().isUseGuillemets();
    }

    @Pointcut("execution(* org.argouml.notation.NotationSettings.setUseGuillemets(..)) && this(cthis) && args(showem)")
    public void setUseGuillemets(final NotationSettings cthis, final boolean showem) {}
    
    @Before("setUseGuillemets(cthis, showem)")
    public void before11(final NotationSettings cthis, final boolean showem) {
        if (cthis.useGuillemets == showem && cthis.useGuillemetsSet) {
            return;
        }
        final boolean oldValid = cthis.useGuillemetsSet;
        Memento memento = new Memento() {
            public void redo() {
                cthis.useGuillemets = showem;
                cthis.useGuillemetsSet = true;
            }

            public void undo() {
                cthis.useGuillemets = !showem;
                cthis.useGuillemetsSet = oldValid;
            }
        };
        cthis.doUndoable(memento);
    }
    

    @Pointcut("execution(* org.argouml.kernel.ProjectSettings.setUseGuillemetsHook(..)) && this(cthis)")
    public void setUseGuillemetsHook2(ProjectSettings cthis) {}
    
    @Before("setUseGuillemetsHook2(cthis)")
    public void before12(ProjectSettings cthis) {
        cthis.npSettings.setUseGuillemets(Configuration.getBoolean(
                Notation.KEY_USE_GUILLEMOTS, false));
    }

    @Pointcut("execution(* org.argouml.kernel.ProjectSettings.getUseGuillemotsValue(..)) && this(cthis)")
    public void getUseGuillemotsValue(ProjectSettings cthis) {}
    
    @Around("getUseGuillemotsValue(cthis)")
    public boolean around3(ProjectSettings cthis) {
        return cthis.npSettings.isUseGuillemets();
    }
    
    @Pointcut("execution(* org.argouml.kernel.ProjectSettings.setUseGuillemots(..)) && this(cthis) && args(showem)")
    public void setUseGuillemots(final ProjectSettings cthis, final boolean showem) {}
    
    
    @Before("setUseGuillemots(cthis, showem)")
    public void before13(final ProjectSettings cthis, final boolean showem) {
        if (cthis.getUseGuillemotsValue() == showem) {
            return;
        }
        Memento memento = new Memento() {
            private final ConfigurationKey key = Notation.KEY_USE_GUILLEMOTS;

            public void redo() {
                cthis.npSettings.setUseGuillemets(showem);
                cthis.fireNotationEvent(key, !showem, showem);
            }

            public void undo() {
                cthis.npSettings.setUseGuillemets(!showem);
                cthis.fireNotationEvent(key, showem, !showem);
            }
        };
        cthis.doUndoable(memento);
    }
    

  

    

    


//
    
//

//

//

//

//

//

//

//

//

//


}
