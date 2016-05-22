package com.appeals.representation;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.appeals.object.AppealStatus;
import com.appeals.object.StudentAppeal;

@XmlRootElement(name = "appeal", namespace = Representation.CSE564APPEALS_NAMESPACE)
public class AppealsRepresentation extends Representation {
    
    @XmlElement(name = "classnumber", namespace = Representation.CSE564APPEALS_NAMESPACE)
    private String classnumber;
    @XmlElement(name = "studentID", namespace = Representation.CSE564APPEALS_NAMESPACE)
    private String studentID;
    @XmlElement(name = "workitem", namespace = Representation.CSE564APPEALS_NAMESPACE)
    private String workitem;
    @XmlElement(name = "grades", namespace = Representation.CSE564APPEALS_NAMESPACE)
    private String grades;
    @XmlElement(name = "appealcomment", namespace = Representation.CSE564APPEALS_NAMESPACE)
    private String appealcomment;
    @XmlElement(name = "followup", namespace = Representation.CSE564APPEALS_NAMESPACE)
    private String followup;
    @XmlElement(name = "instructorcomment", namespace = Representation.CSE564APPEALS_NAMESPACE)
    private String instructorcomment;
    @XmlElement(name = "status", namespace = Representation.CSE564APPEALS_NAMESPACE)
    private AppealStatus status;

    /**
     * For JAXB :-(
     */
    AppealsRepresentation() {
    }

    public static AppealsRepresentation fromXmlString(String xmlRepresentation) {
                
        AppealsRepresentation appealRepresentation = null;     
        try {
            JAXBContext context = JAXBContext.newInstance(AppealsRepresentation.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            appealRepresentation = (AppealsRepresentation) unmarshaller.unmarshal(new ByteArrayInputStream(xmlRepresentation.getBytes()));
        } catch (Exception e) {
            throw new InvalidAppealException(e);
        }
        
        return appealRepresentation;
    }
    
//    public static AppealsRepresentation createResponseOrderRepresentation(Order order, AppealsUri orderUri) {
//        LOG.info("Creating a Response Order for order = {} and order URI", order.toString(), orderUri.toString());
//        
//        AppealsRepresentation orderRepresentation = null; 
//        
//        AppealsUri paymentUri = new AppealsUri(orderUri.getBaseUri() + "/payment/" + orderUri.getId().toString());
//        LOG.debug("Payment URI = {}", paymentUri);
//        
//        if(order.getStatus() == OrderStatus.UNPAID) {
//            LOG.debug("The order status is {}", OrderStatus.UNPAID);
//            orderRepresentation = new AppealsRepresentation(order, 
//                    new Link(RELATIONS_URI + "cancel", orderUri), 
//                    new Link(RELATIONS_URI + "payment", paymentUri), 
//                    new Link(RELATIONS_URI + "update", orderUri),
//                    new Link(Representation.SELF_REL_VALUE, orderUri));
//        } else if(order.getStatus() == OrderStatus.PREPARING) {
//            LOG.debug("The order status is {}", OrderStatus.PREPARING);
//            orderRepresentation = new AppealsRepresentation(order, new Link(Representation.SELF_REL_VALUE, orderUri));
//        } else if(order.getStatus() == OrderStatus.READY) {
//            LOG.debug("The order status is {}", OrderStatus.READY);
//            orderRepresentation = new AppealsRepresentation(order, new Link(Representation.RELATIONS_URI + "reciept", UriExchange.receiptForPayment(paymentUri)));
//        } else if(order.getStatus() == OrderStatus.TAKEN) {
//            LOG.debug("The order status is {}", OrderStatus.TAKEN);
//            orderRepresentation = new AppealsRepresentation(order);            
//        } else {
//            LOG.debug("The order status is in an unknown status");
//            throw new RuntimeException("Unknown Order Status");
//        }
//        
//        LOG.debug("The order representation created for the Create Response Order Representation is {}", orderRepresentation);
//        
//        return orderRepresentation;
//    }

    public AppealsRepresentation(StudentAppeal appeal, Link... links) {
        
        try {
        	this.classnumber = appeal.getClassNumber();
    		this.studentID = appeal.getStudentid();
    		this.workitem = appeal.getWorkItem();
    		this.grades = appeal.getStudent_grades();
    		this.appealcomment = appeal.getAppealComment();
    		this.followup = appeal.getStudent_followup();
    		this.instructorcomment = appeal.getInstructorComment();
            this.status = appeal.getAppeal_status();
            this.links = java.util.Arrays.asList(links);
        } catch (Exception ex) {
            throw new InvalidAppealException(ex);
        }
        
    }

    public String toString() {
        try {
            JAXBContext context = JAXBContext.newInstance(AppealsRepresentation.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public StudentAppeal getAppeal() {
    	StudentAppeal appeal = new StudentAppeal();
    	appeal.setClassNumber(classnumber);
    	appeal.setStudentid(studentID);
    	appeal.setWorkItem(workitem);
    	appeal.setStudent_grades(grades);
    	appeal.setAppealComment(appealcomment);
    	appeal.setStudent_followup(followup);
    	appeal.setInstructorComment(instructorcomment);
    	appeal.setAppeal_status(status);
        return appeal;
    }

    public Link getDeleteLink() {
        return getLinkByName(RELATIONS_URI + "delete");
    }

    public Link getFollowUpLink() {
        return getLinkByName(RELATIONS_URI + "followup");
    }

    public Link getUpdateLink() {
        return getLinkByName(RELATIONS_URI + "update");
    }

    public Link getSelfLink() {
        return getLinkByName("self"); 
    }
    
    public AppealStatus getStatus() {
        return status;
    }
}
