package com.appeals.object;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.appeals.object.AppealStatus;
import com.appeals.representation.Representation;


@XmlRootElement(name = "appeal", namespace = Representation.CSE564APPEALS_NAMESPACE)
public class StudentAppeal {
    
    private String classNumber;
    private String studentid;
    private String workItem;
    private String student_grades;
    private String appealComment;
    private String student_followup;
    private String instructorComment;
    private AppealStatus appeal_status;
    
	public String getClassNumber() {
		return classNumber;
	}
	
	@XmlElement(name = "classnumber", namespace = Representation.CSE564APPEALS_NAMESPACE)
	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}
	public String getStudentid() {
		return studentid;
	}
	
    @XmlElement(name = "studentID", namespace = Representation.CSE564APPEALS_NAMESPACE)
	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}
	public String getWorkItem() {
		return workItem;
	}
	
    @XmlElement(name = "workitem", namespace = Representation.CSE564APPEALS_NAMESPACE)
	public void setWorkItem(String workItem) {
		this.workItem = workItem;
	}
	public String getStudent_grades() {
		return student_grades;
	}
	
    @XmlElement(name = "grades", namespace = Representation.CSE564APPEALS_NAMESPACE)
	public void setStudent_grades(String student_grades) {
		this.student_grades = student_grades;
	}
	public String getAppealComment() {
		return appealComment;
	}
	
    @XmlElement(name = "appealcomment", namespace = Representation.CSE564APPEALS_NAMESPACE)
	public void setAppealComment(String appealComment) {
		this.appealComment = appealComment;
	}
	public String getStudent_followup() {
		return student_followup;
	}
	
    @XmlElement(name = "followup", namespace = Representation.CSE564APPEALS_NAMESPACE)
	public void setStudent_followup(String student_followup) {
		this.student_followup = student_followup;
	}
	public String getInstructorComment() {
		return instructorComment;
	}
	
    @XmlElement(name = "instructorcomment", namespace = Representation.CSE564APPEALS_NAMESPACE)
	public void setInstructorComment(String instructorComment) {
		this.instructorComment = instructorComment;
	}
	public AppealStatus getAppeal_status() {
		return appeal_status;
	}
	
    @XmlElement(name = "status", namespace = Representation.CSE564APPEALS_NAMESPACE)
	public void setAppeal_status(AppealStatus appeal_status) {
		this.appeal_status = appeal_status;
	}

    
    @Override
    public String toString() {
        try {
            JAXBContext context = JAXBContext.newInstance(StudentAppeal.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public String objectToString() {
        try {
            StringBuilder stringWriter = new StringBuilder();
            if(this.classNumber != null)
            stringWriter.append("ClassNumber : " + this.classNumber);
            else
            	stringWriter.append("ClassNumber : " + "Not Avaliable");
            if(this.studentid != null)
            stringWriter.append("\nStudentID : " + this.studentid);
            else
            	stringWriter.append("\nStudentID : " + "Not Avaliable");
            if(this.workItem != null)
            stringWriter.append("\nWorkItem : " + this.workItem);
            else
            	stringWriter.append("\nWorkItem : " + "Not Avaliable");
            if(this.student_grades != null)
            stringWriter.append("\nGrade : " + this.student_grades);
            else
            	stringWriter.append("\nGrade : " + "Not Avaliable");
            if(this.appealComment != null)
            stringWriter.append("\nAppeal Comment : " + this.appealComment);
            else
            	stringWriter.append("\nAppeal Comment " + "Not Avaliable");
            if(this.student_followup != null)
            stringWriter.append("\nFollow Up Comment : " + this.student_followup);
            else
            	stringWriter.append("\nFollow Up Comment : " + "Not Avaliable");
            if(this.instructorComment != null)
            stringWriter.append("\nInstructor Comment : " + this.instructorComment);
            else
            	stringWriter.append("\nInstructor Comment : " + "Not Avaliable");
            if(this.appeal_status != null)
            stringWriter.append("\nAppeal Status : " + this.appeal_status);
            else
            	stringWriter.append("\nAppeal Status : " + "Not Avaliable");

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }        
    }


}