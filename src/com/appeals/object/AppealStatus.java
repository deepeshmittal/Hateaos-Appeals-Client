package com.appeals.object;

import javax.xml.bind.annotation.XmlEnumValue;


public enum AppealStatus {
    @XmlEnumValue(value="created")
    CREATED,
    @XmlEnumValue(value="inprogress")
    INPROGRESS, 
    @XmlEnumValue(value="rejected")
    REJECTED, 
    @XmlEnumValue(value="accepted")
    ACCEPTED
}
