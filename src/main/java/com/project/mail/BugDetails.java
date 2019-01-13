package com.project.mail;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

public class BugDetails {

  String priority;
  String type;
  String title;
  String assignee;
  String status;
  String workStatus;
  String issueId;
  Date createdTime;
  String createdDate;
  String complexity;
  Date modifiedTime;
  String tat = "Complexity not updated.";

  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getWorkStatus() {
    return workStatus;
  }

  public void setWorkStatus(String workStatus) {
    this.workStatus = workStatus;
  }

  public String getIssueId() {
    return issueId;
  }

  public void setIssueId(String issueId) {
    this.issueId = issueId;
  }

  public Date getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(String createdTime) {
    try {
      Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createdTime);
      this.createdTime = date;
      setCreatedDate(date.toString());
    } catch (Exception ex) {
      System.err.println(ex);
    }
  }

  public String getComplexity() {
    return complexity;
  }

  public void setComplexity(String complexity) {
    this.complexity = complexity;
  }

  public Date getModifiedTime() {
    return modifiedTime;
  }

  public void setModifiedTime(String modifiedTime) {
    try {
      Date date = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss").parse(modifiedTime);
      this.modifiedTime = date;
    } catch (Exception ex) {
      System.err.println(ex);
    }
  }

  public int getAging() {
    long p = ChronoUnit.DAYS.between(this.getCreatedTime().toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate(), LocalDate.now());
    return Math.toIntExact(p);
  }

  public String getTat(){
    Map mp = DataImporter.tatMap();
    String tatCombination = this.getPriority()+this.getComplexity();
    if (null != mp.get(tatCombination)){
    tat = Integer.toString( DataImporter.tatMap().get(tatCombination)- this.getAging());
    }
    System.out.println("tat "+tat);
    return tat;
  }

  public String setCreatedDate(String createdTime){
   return this.createdDate = createdTime;
  }

  public String getCreatedDate(){
    return this.createdDate;
  }

}