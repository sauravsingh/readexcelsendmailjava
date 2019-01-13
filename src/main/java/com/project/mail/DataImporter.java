package com.project.mail;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataImporter {

  static Set<BugDetails> dataSet = null;
  static int p1Count = 0;
  static int p2Count = 0;
  static int p3Count = 0;
  static int p4Count = 0;
  static int p1Ageing = 0;
  static int p2Ageing= 0;
  static int p3Ageing = 0;
  static int p4Ageing = 0;
  static int needInfoCount = 0;
  static int pendingClarificationCount = 0;
  static int bugAsgnToGooglers = 0;
  static int totalAgeing = 0;
  static int lowComplexity = 0;
  static int mediumComplexity = 0;
  static int highComplexity = 0;
  static int noneComplexity = 0;
  static List<BugDetails> workableBugsDetails = new ArrayList<BugDetails>();

  static { dataSet = generateValue(); }

  private static Set<BugDetails> generateValue() {
    dataSet = new HashSet<BugDetails>();
    BugDetails bugDetails;

    Fillo fillo = new Fillo();
    Connection connection = null;
    try {
      connection = fillo
          .getConnection("/usr/local/google/home/sumansaurav/Documents/issues.xlsx");

      String strQuery = "SELECT * FROM issues";
      Recordset recordset = connection.executeQuery(strQuery);

      while (recordset.next()) {
        bugDetails = new BugDetails();
        bugDetails.setPriority(recordset.getField("PRIORITY"));
        bugDetails.setType(recordset.getField("TYPE"));
        bugDetails.setTitle(recordset.getField("TITLE"));
        bugDetails.setAssignee(recordset.getField("ASSIGNEE"));
        bugDetails.setStatus(recordset.getField("STATUS"));
        bugDetails.setWorkStatus(recordset.getField("Work Status"));
        bugDetails.setIssueId(recordset.getField("ISSUE_ID"));
        bugDetails.setComplexity(recordset.getField("Complexity"));
        bugDetails.setCreatedTime(recordset.getField("CREATED_TIME (UTC)"));
        bugDetails.setModifiedTime(recordset.getField("MODIFIED_TIME (UTC)"));
        dataSet.add(bugDetails);
      }

      populateCommonFields();

    } catch (FilloException e) {
      e.printStackTrace();
    }
    return dataSet;
  }
  /*
  * get total number of open bugs*/
  public int getTotalNoOfOpenBugs(){
    return dataSet.size();
  }

  /*
  get bugs with googlers
   */
  public int getNoOfBugsWithGooglers(){
    return bugAsgnToGooglers;
  }
  /*
  get no of needInfo Bugs
   */
  public int getNoOfNeedInfoBugs(){
    return needInfoCount;
  }
  /*
  get no of pending clarification
   */
  public int getNoOfPendingClarification(){
    return pendingClarificationCount;
  }
  /*
  get no of workable bugs
   */
  public int getNoOfWorkableBugs(){
    return workableBugsDetails.size();
  }
  /*
  get no of p1 bugs
   */
  public int getNoOfP1Bugs(){
    return p1Count;
  }
  /*
  get no of p2 bugs
   */
  public int getNoOfP2Bugs(){
    return p2Count;
  }
  /*
  get no of p3 bugs
   */
  public int getNoOfP3Bugs(){
    return p3Count;
  }
  /*
  get no of p4 bugs
   */
  public int getNoOfP4Bugs(){
    return p4Count;
  }
  /*
  get no of total p1, p2, p3, p4 ageing
   */
  public int getNoOfTotalAgeing(){
    return totalAgeing/dataSet.size();
  }
  /*
  get no of p1 ageing
   */
  public int getNoOfP1Ageing(){
    return p1Ageing/p1Count;
  }
  /*
  get no of p2 ageing
   */
  public int getNoOfP2Ageing(){
    return p2Ageing/p2Count;
  }
  /*
  get no of p3 ageing
   */
  public int getNoOfP3Ageing(){
    return p3Ageing/p3Count;
  }
  /*
  get no of p4 ageing
   */
  public int getNoOfP4Ageing(){
    return p4Ageing/p4Count;
  }
  /*
  get no of low complexity
   */
  public int getNoOfLowComplexity(){
    return lowComplexity;
  }
  /*
  get no of medium complexity
   */
  public int getNoOfMediumComplexity(){
    return mediumComplexity;
  }
  /*
  get no of high complexity bugs
   */
  public int getNoOfHighComplexity(){
    return highComplexity;
  }
  /*
  get no of others except low, medium, high complexity bugs
   */
  public int getNoOfNullComplexity(){
    return noneComplexity;
  }
  /*
  get workable list
   */
  public List<BugDetails> getWorkableBugs(){
    return workableBugsDetails;
  }

  /*
  populate common fields data
   */
  public static void populateCommonFields(){
    Iterator it = dataSet.iterator();
    Set openDataSet = new HashSet<OpenBugData>();
    while(it.hasNext()){
      {
        BugDetails bugDtl = (BugDetails) it.next();
        //priority count
        if (bugDtl.getPriority().contains("P1")) {
          p1Count++;
          p1Ageing+=bugDtl.getAging();
        } else if (bugDtl.getPriority().contains("P2")) {
          p2Count++;
          p2Ageing+=bugDtl.getAging();
        } else if (bugDtl.getPriority().contains("P3")) {
          p3Count++;
          p3Ageing+=bugDtl.getAging();
        } else if (bugDtl.getPriority().contains("P4")) {
          p4Count++;
          p4Ageing+=bugDtl.getAging();
        }
        totalAgeing+=bugDtl.getAging();
        //work status count
        if (bugDtl.getWorkStatus().contains("Needs Info") && nonGooglers()
            .contains(bugDtl.getAssignee())) {
          needInfoCount++;
        } else if (bugDtl.getWorkStatus().contains("Pending Clarification") && nonGooglers()
            .contains(bugDtl.getAssignee())) {
          pendingClarificationCount++;
        } else if (!bugDtl.getWorkStatus().contains("Needs Info") && !bugDtl.getWorkStatus()
            .contains("Pending Clarification") && nonGooglers().contains(bugDtl.getAssignee())) {
          workableBugsDetails.add(bugDtl);
        }
        //complexity
        if (bugDtl.getComplexity().contains("Low")){
          lowComplexity++;
        }
        else if(bugDtl.getComplexity().contains("Medium")){
          mediumComplexity++;
        }
        else if(bugDtl.getComplexity().contains("High")){
          highComplexity++;
        }
        else if(!bugDtl.getComplexity().contains("Low") && !bugDtl.getComplexity().contains("Medium") && !bugDtl.getComplexity().contains("High")){
          noneComplexity++;
        }
        //non googlers count
        if (!nonGooglers().contains(bugDtl.getAssignee())) {
          bugAsgnToGooglers++;
        }
        //get tat

      }
    }
  }


  /*
  set non-googlers
   */
  public static Set<String> nonGooglers(){
    Set<String> setNonGoogler = new HashSet<>();

    setNonGoogler.add("ahujap@google.com");
    setNonGoogler.add("sichandan@google.com");
    setNonGoogler.add("ankursethi@google.com");
    setNonGoogler.add("ashishkmr@google.com");
    setNonGoogler.add("anjanat@google.com");
    setNonGoogler.add("neelakshiy@google.com");
    setNonGoogler.add("charulp@google.com");
    setNonGoogler.add("shafquatullah@google.com");
    setNonGoogler.add("sinderjeet@google.com");
    setNonGoogler.add("siddhic@google.com");
    setNonGoogler.add("kumavikash@google.com");
    setNonGoogler.add("gauravshrmm@google.com");
    setNonGoogler.add("sarikap@google.com");
    setNonGoogler.add("wynndee@google.com");
    setNonGoogler.add("laceres@google.com");
    setNonGoogler.add("ilaojr@google.com");
    setNonGoogler.add("racca@google.com");
    setNonGoogler.add("dcrizaldo@google.com");
    setNonGoogler.add("erickristoper@google.com");
    setNonGoogler.add("lorenzopablo@google.com");
    return setNonGoogler;
  }

  /*
  tat map
   */
  public static Map<String, Integer> tatMap(){
    Map<String, Integer> setTatMap = new HashMap<String, Integer>();
    setTatMap.put("P1Low", 2);
    setTatMap.put("P1Medium", 4);
    setTatMap.put("P1High", 6);
    setTatMap.put("P2Low", 4);
    setTatMap.put("P2Medium", 8);
    setTatMap.put("P2High", 12);
    setTatMap.put("P3Low", 8);
    setTatMap.put("P3Medium", 16);
    setTatMap.put("P3High", 24);
    setTatMap.put("P4Low", 16);
    setTatMap.put("P4Medium", 32);
    setTatMap.put("P4High", 64);
    return setTatMap;
  }
}