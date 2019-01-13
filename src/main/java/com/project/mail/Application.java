package com.project.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Application implements ApplicationRunner {

    private static Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private EmailService emailService;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }


    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info("Sending Bug Report");

        Mail mail = new Mail();
        mail.setFrom("from@example.com");
        mail.setTo("send@example.com");
        mail.setSubject("Subject");
        OpenBugData openBugData= new OpenBugData();
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", "User");
        model.put("TotalBugs", openBugData.getNoOfBugs());
        model.put("BugsWithGoogles", openBugData.getNoOfBugsWithGooglers());
        model.put("NeedInfoBugs", openBugData.getNoOfNeedInfoBugs());
        model.put("P1Bugs", openBugData.getNoOfP1Bugs());
        model.put("P2Bugs", openBugData.getNoOfP2Bugs());
        model.put("P3Bugs", openBugData.getNoOfP3Bugs());
        model.put("P4Bugs", openBugData.getNoOfP4Bugs());
        model.put("PendingClarification", openBugData.getNoOfPendingClarification());
        model.put("WorkableBugsCount", openBugData.getNoOfWorkableBugs());
        model.put("TotalAgeing", openBugData.getTotalAgeing());
        model.put("P1Ageing", openBugData.getP1Ageing());
        model.put("P2Ageing", openBugData.getP2Ageing());
        model.put("P3Ageing", openBugData.getP3Ageing());
        model.put("P4Ageing", openBugData.getP4Ageing());
        model.put("LowComplexity", openBugData.getNoOfLowComplexity());
        model.put("MediumComplexity", openBugData.getNoOfMediumComplexity());
        model.put("HighComplexity", openBugData.getNoOfHighComplexity());
        model.put("BlankComplexity", openBugData.getNoOfBlankComplexity());
        model.put("WorkableBugs", openBugData.getWorkableBugs());
        model.put("signature", "Thanks, <br> Saurav");


        mail.setModel(model);

        emailService.sendSimpleMessage(mail);
    }
}
