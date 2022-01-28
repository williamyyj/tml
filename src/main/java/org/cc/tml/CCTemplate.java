package org.cc.tml;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;

public class CCTemplate {
    protected static final JexlEngine jexl = new JexlBuilder().cache(4096).strict(true).silent(false).create();
}
