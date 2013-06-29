package org.argouml.notationfeature;

import javax.swing.Icon;

import org.argouml.application.SubsystemUtility;
import org.argouml.notation.Notation;
import org.argouml.notation.providers.java.InitNotationJava;
import org.argouml.application.Main;
import org.argouml.application.helpers.ResourceLoaderWrapper;
import org.argouml.configuration.ConfigurationProperties;

public privileged aspect ArgoUMLNotationJavaAspect {

    public static String Notation.DEFAULT_NOTATION_NAME = "Java";

    public static String Notation.DEFAULT_NOTATION_VERSION = "";

    public static Icon Notation.ICON_NOTATION = ResourceLoaderWrapper
            .lookupIconResource("JavaNotation");

    pointcut initNotationJavaHook() : execution(* Main.initNotationJavaHook());

    before() : initNotationJavaHook() {
        SubsystemUtility.initSubsystem(new InitNotationJava());
    }
}