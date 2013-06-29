package org.argouml.notationfeature;

import javax.swing.Icon;
import org.argouml.application.SubsystemUtility;
import org.argouml.notation.providers.uml.InitNotationUml;
import org.argouml.application.Main;
import org.argouml.application.helpers.ResourceLoaderWrapper;
import org.argouml.notation.Notation;

public privileged aspect ArgoUMLNotationUMLAspect {

    public static String Notation.DEFAULT_NOTATION_NAME = "UML";

    public static String Notation.DEFAULT_NOTATION_VERSION = "1.4";

    public static Icon Notation.ICON_NOTATION = ResourceLoaderWrapper
            .lookupIconResource("UmlNotation");

    pointcut initNotationUmlHook() : execution(* Main.initNotationUmlHook());

    before() : initNotationUmlHook() {
        SubsystemUtility.initSubsystem(new InitNotationUml());
    }
}