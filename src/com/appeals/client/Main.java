package com.appeals.client;


import java.net.URI;
import java.util.ArrayList;

import com.appeals.object.AppealStatus;
import com.appeals.object.Identifier;
import com.appeals.object.StudentAppeal;
import com.appeals.representation.AppealsRepresentation;
import com.appeals.representation.AppealsUri;
import com.appeals.representation.Link;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

public class Main {
    
    private static final String APPEALS_MEDIA_TYPE = "application/vnd-cse564-appeals+xml";
    
    //private static final String ENTRY_POINT_URI = "http://localhost:8080/Hateaos-Appeals-Server/webresources/appeals";
    
    // Already deployed on personal remote host - uncomment below entry point in case you want to test on already deployed server code.
    private static final String ENTRY_POINT_URI = "http://ganga.la.asu.edu:8080/Hateaos-Appeals-Server/webresources/appeals";
    
    private static ArrayList<AppealsRepresentation> appealID = new ArrayList<AppealsRepresentation>();

    public static void main(String[] args) throws Exception {
        URI serviceUri = new URI(ENTRY_POINT_URI);
        happyCase(serviceUri);
        GetAppealInfo(serviceUri);
        updateAppealCase(serviceUri);
        abandonCase(serviceUri);
        forgotCase(serviceUri);
        badStartCase(serviceUri);
        badIDCase(serviceUri);
    }

    private static void happyCase(URI serviceUri) throws Exception {
        System.out.println("\n***************************************************************************\n");
        System.out.println("Starting Happy Case with Service URI \n" + serviceUri);
        // Initializing appeal attributes
        StudentAppeal appeal = new StudentAppeal();
        appeal.setClassNumber("CSE564");
        appeal.setStudentid("1234");
        appeal.setWorkItem("Quiz 1");
        appeal.setStudent_grades("30");
        appeal.setAppealComment("Please re-grade");
        appeal.setAppeal_status(AppealStatus.CREATED);
       
        System.out.println(String.format("About to start happy path test. Creating Appeal at [%s] via POST", serviceUri.toString()));
        Client client = Client.create();
        AppealsRepresentation appealRepresentation = client.resource(serviceUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(AppealsRepresentation.class, appeal);
        System.out.println(String.format("Appeal created at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
        System.out.println("Appeal State changed from  : " + appeal.getAppeal_status() + " to " + appealRepresentation.getStatus());
        appealID.add(appealRepresentation);
        System.out.println("Happy Case Ends");
    }
    
    private static void GetAppealInfo(URI serviceUri) throws Exception {
        System.out.println("\n***************************************************************************\n");
        System.out.println("Getting appeal info with Service URI \n" + serviceUri);
        // Getting happy Case appeal for Get Case test
        AppealsRepresentation happycaseappeal = appealID.get(0);
        
        Client client = Client.create();
        String getinfoLink = happycaseappeal.getSelfLink().getUri().toString();
        ClientResponse followupResponse = client.resource(getinfoLink).accept(APPEALS_MEDIA_TYPE).get(ClientResponse.class);
        
        if (followupResponse.getStatus() == 200){
        	AppealsRepresentation response = followupResponse.getEntity(AppealsRepresentation.class);
        	System.out.println("Below are appeal details : ");
        	System.out.println(response.getAppeal().objectToString());
        }else if (followupResponse.getStatus() == 404){
        	System.out.println(String.format("Appeal not found"));
        }else if (followupResponse.getStatus() == 403){
        	System.out.println("Appeal already accepted or rejected");
        }else{
        	System.out.println(String.format("Internal Error "));
        }
        System.out.println("Get Appeal info Ends");
    }
    
    private static void updateAppealCase(URI serviceUri) throws Exception {
    	//Appeal update with bad URL.
        System.out.println("\n***************************************************************************\n");
        System.out.println("Starting Update Appeal Test");
        System.out.println("Creating appeal update");
    
        // Getting happy Case appeal for bad start case test
        AppealsRepresentation happycaseappeal = appealID.get(0);
        
        // Creating appeal with updated information
        StudentAppeal appeal = happycaseappeal.getAppeal();
        appeal.setAppealComment("Updated Comment : Please grade last question for missing points");
        
        System.out.println("About to start update appeal case");
        Client client = Client.create();
        String updateURL = happycaseappeal.getUpdateLink().getUri().toString();
        AppealsUri uri = new AppealsUri(happycaseappeal.getUpdateLink().getUri());
        Identifier id = uri.getId();

        ClientResponse badResponse = client.resource(updateURL).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).put(ClientResponse.class, appeal);
        if (badResponse.getStatus() == 200){
        	System.out.println("New information added to appeal : " + id);
        }else if (badResponse.getStatus() == 404){
        	System.out.println(String.format("Bad URL Test status : Update Failed" + "\nBad URL : " + updateURL));
        }else{
        	System.out.println("Internal Error");
        }
        System.out.println("Update Appeal Case Ends");
    }

    
    private static void abandonCase(URI serviceUri) throws Exception {
        System.out.println("\n***************************************************************************\n");

        System.out.println("Starting Abandon Case with Service URI \n" + serviceUri);
        System.out.println("First creating appeal");
        // Initializing appeal attributes
        StudentAppeal appeal = new StudentAppeal();
        appeal.setClassNumber("CSE564");
        appeal.setStudentid("1234");
        appeal.setWorkItem("Midterm");
        appeal.setStudent_grades("90");
        appeal.setAppealComment("Please re-grade for missing 10 points");
        appeal.setAppeal_status(AppealStatus.CREATED);
       
        System.out.println(String.format("About to start Abandon path test. Creating Appeal at [%s] via POST", serviceUri.toString()));
        Client client = Client.create();
        AppealsRepresentation appealRepresentation = client.resource(serviceUri).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).post(AppealsRepresentation.class, appeal);
        System.out.println(String.format("Appeal created at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
        AppealsUri uri = new AppealsUri(appealRepresentation.getSelfLink().getUri());
        Identifier id = uri.getId();
        System.out.println(String.format("Starting Appeal abandon test for appeal : " + id.toString()));
        
        Link deleteLink = appealRepresentation.getDeleteLink();
        ClientResponse deleteRepresentation = client.resource(deleteLink.getUri()).delete(ClientResponse.class);
        if (deleteRepresentation.getStatus() == 200){
        	System.out.println(String.format("Appeal ID : " + id.toString() + "\nAbandon status : Deleted Successfully"));
        }else if (deleteRepresentation.getStatus() == 404){
        	System.out.println(String.format("Appeal ID : " + id.toString() + "\nAbandon status : Appeal Not Found/Wrong Appeal ID"));
        }else if (deleteRepresentation.getStatus() == 406){
        	System.out.println(String.format("Appeal ID : " + id.toString() + "\nAbandon status : Appeal already accepted/rejected"));
        }else{
        	System.out.println(String.format("Appeal ID : " + id.toString() + "\nAbandon status : Delete Falied - Internal Error !!"));
        }
        System.out.println("Abandon Case Ends");
    }
    
    private static void forgotCase(URI serviceUri) throws Exception {
    	//Creating followup for appeal and getting appeal status
        System.out.println("\n***************************************************************************\n");

        System.out.println("Starting forgot case test");
        System.out.println("Creating appeal followup");
    
        // Getting happy Case appeal for forgotcase test
        AppealsRepresentation happycaseappeal = appealID.get(0);
        
        // Creating appeal with updated information
        StudentAppeal appeal = happycaseappeal.getAppeal();
        appeal.setStudent_followup("Follow up 1");
        
        System.out.println("About to start forgot case  test");
        Client client = Client.create();
        String followupLink = happycaseappeal.getFollowUpLink().getUri().toString();
        ClientResponse followupResponse = client.resource(followupLink).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).put(ClientResponse.class, appeal);
        
        if (followupResponse.getStatus() == 200){
        	AppealsRepresentation response = followupResponse.getEntity(AppealsRepresentation.class);
        	System.out.println("Appeal followup comment added successfully at : " + response.getSelfLink().getUri().toString());
        	System.out.println("Current Appeal Status : " + response.getStatus());
        }else if (followupResponse.getStatus() == 404){
        	System.out.println(String.format("Appeal not found"));
        }else if (followupResponse.getStatus() == 403){
        	System.out.println("Appeal already accepted or rejected");
        }else{
        	System.out.println(String.format("Internal Error "));
        }
        System.out.println("Forgot/followup appeal Case Ends");
    }
    
    private static void badStartCase(URI serviceUri) throws Exception {
    	//Appeal update with bad URL.
        System.out.println("\n***************************************************************************\n");
        System.out.println("Starting bad URL test");
        System.out.println("Creating appeal update");
    
        // Getting happy Case appeal for bad start case test
        AppealsRepresentation happycaseappeal = appealID.get(0);
        
        // Creating appeal with updated information
        StudentAppeal appeal = happycaseappeal.getAppeal();
        appeal.setAppealComment("Updated Comment : Please grade last question for missing points	");
        
        System.out.println("About to start bad URL test");
        Client client = Client.create();
        String badURL = happycaseappeal.getUpdateLink().getUri().toString() + "/badURL";
        ClientResponse badResponse = client.resource(badURL).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).put(ClientResponse.class, appeal);

        if (badResponse.getStatus() == 404){
        	System.out.println(String.format("Bad URL Test status : Update Failed" + "\nBad URL : " + badURL));
        }else{
        	System.out.println(String.format("Bad URL Test status : Internal Error "));
        }
        System.out.println("bad URL Case Ends");
    }
    
    private static void badIDCase(URI serviceUri) throws Exception {
    	//Appeal update with bad URL.
        System.out.println("\n***************************************************************************\n");
        System.out.println("Starting bad ID test");
        System.out.println("Creating appeal followup");
    
        // Getting happy Case appeal for bad start case test
        AppealsRepresentation happycaseappeal = appealID.get(0);
        
        // Creating appeal with updated information
        StudentAppeal appeal = happycaseappeal.getAppeal();
        appeal.setStudent_followup("Follow up 1");
        
        System.out.println("About to start bad ID test");
        Client client = Client.create();
        String badURL = happycaseappeal.getFollowUpLink().getUri().toString() + "-00000";
        ClientResponse badResponse = client.resource(badURL).accept(APPEALS_MEDIA_TYPE).type(APPEALS_MEDIA_TYPE).put(ClientResponse.class, appeal);

        if (badResponse.getStatus() == 404){
        	System.out.println(String.format("Bad Appeal ID Test status : Followup Failed" + "\nBad Appeal ID : " + badURL));
        }else{
        	System.out.println(String.format("Bad Appeal ID Test status : Internal Error "));
        }
        System.out.println("bad URL Case Ends");
        System.out.println("\n***************************************************************************\n");

    }
}
