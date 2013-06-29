package org.argouml.notationfeature;

import javax.swing.Icon;

import org.argouml.application.SubsystemUtility;
import org.argouml.application.helpers.ResourceLoaderWrapper;
import org.argouml.notation.providers.java.InitNotationJava;
import org.argouml.notation.providers.uml.InitNotationUml;
import org.argouml.notation.Notation;
import org.argouml.application.Main;
import org.argouml.configuration.ConfigurationFactory;
import org.argouml.configuration.ConfigurationHandler;
import org.argouml.configuration.IConfigurationFactory;

public privileged aspect ArgoUMLNotationJavaUMLAspect {

    public static String Notation.DEFAULT_NOTATION_NAME;

    public static String Notation.DEFAULT_NOTATION_VERSION;

    public static Icon Notation.ICON_NOTATION;

    public static IConfigurationFactory getFactory() {
        return ConfigurationFactory.getInstance();
    }

    private static ConfigurationHandler config = getFactory()
            .getConfigurationHandler();

    private static String driver = config.getValue("argo.notation.default", "");

    pointcut driverUML() : if(driver.equals("UML 1.4"));

    pointcut driverJava() : if(driver.equals("Java"));

    pointcut uml_initialization() : staticinitialization(Notation) && driverUML();

    pointcut java_initialization() : staticinitialization(Notation) && driverJava();

    before() : uml_initialization() {
        Notation.DEFAULT_NOTATION_NAME = "UML";
        Notation.DEFAULT_NOTATION_VERSION = "1.4";
        Notation.ICON_NOTATION = ResourceLoaderWrapper
                .lookupIconResource("UmlNotation");
    }

    before() : java_initialization() {
        Notation.DEFAULT_NOTATION_NAME = "Java";
        Notation.DEFAULT_NOTATION_VERSION = "";
        Notation.ICON_NOTATION = ResourceLoaderWrapper
                .lookupIconResource("JavaNotation");
    }

    pointcut initNotationUmlHook() : execution(* Main.initNotationUmlHook()) && driverUML();

    before() : initNotationUmlHook() {
        SubsystemUtility.initSubsystem(new InitNotationUml());
    }

    pointcut initNotationJavaHook() : execution(* Main.initNotationJavaHook()) && driverJava();

    before() : initNotationJavaHook() {
        SubsystemUtility.initSubsystem(new InitNotationJava());
    }
}
