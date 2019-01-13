package com.project.mail;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OpenBugData {

   DataImporter dataImporter = new DataImporter();

  public int getNoOfBugs() {
    return dataImporter.getTotalNoOfOpenBugs();
  }

  public int getNoOfNeedInfoBugs() {
    return dataImporter.getNoOfNeedInfoBugs();
  }

  public int getNoOfPendingClarification() {
    return dataImporter.getNoOfPendingClarification();
  }

  public int getNoOfBugsWithGooglers() {
    return dataImporter.getNoOfBugsWithGooglers();
  }

  public int getNoOfWorkableBugs() {
    return dataImporter.getNoOfWorkableBugs();
  }

  public int getNoOfP1Bugs() {
    return dataImporter.getNoOfP1Bugs();
  }

  public int getNoOfP2Bugs() {
    return dataImporter.getNoOfP2Bugs();
  }

  public int getNoOfP3Bugs() {
    return dataImporter.getNoOfP3Bugs();
  }

  public int getNoOfP4Bugs() {
    return dataImporter.getNoOfP4Bugs();
  }

  public int getTotalAgeing() {
    return dataImporter.getNoOfTotalAgeing();
  }

  public int getP1Ageing() {
    return dataImporter.getNoOfP1Ageing();
  }

  public int getP2Ageing() {
    return dataImporter.getNoOfP2Ageing();
  }

  public int getP3Ageing() {
    return dataImporter.getNoOfP3Ageing();
  }

  public int getP4Ageing() {
    return dataImporter.getNoOfP4Ageing();
  }

  public int getNoOfLowComplexity() {
    return dataImporter.getNoOfLowComplexity();
  }

  public int getNoOfMediumComplexity() {
    return dataImporter.getNoOfMediumComplexity();
  }

  public int getNoOfHighComplexity() {
    return dataImporter.getNoOfHighComplexity();
  }

  public int getNoOfBlankComplexity() {
    return dataImporter.getNoOfNullComplexity();
  }

  public List<BugDetails> getWorkableBugs() {
    Collections.sort(dataImporter.getWorkableBugs(), new Comparator<BugDetails>() {
      @Override
      public int compare(BugDetails bugDetails, BugDetails bugDetails1) {
        return bugDetails.getAssignee().compareTo(bugDetails1.getAssignee());
      }
    });
    return dataImporter.getWorkableBugs();
  }

}
