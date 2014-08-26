package com.michaelwillemse.mwrestuser.rest;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Michael on 26/08/14.
 */

@XmlRootElement
public class Result {
    private String value;

    public Result() {
    }

    public Result(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
