package services;

import Model.Patients;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import utill.FeedBack;
import utill.Prescriptions;
import utill.Vitals;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.List;

public class ReportGenerator {

   public static void generatePatientReport(Patients patients){
      try{
           final String DIR="Report/patient"+patients.getUserId();

          File file=new File(DIR);
          if(!file.exists()){file.mkdirs();}
          Document document=new Document();
          PdfWriter.getInstance(document,new FileOutputStream(DIR+"_.pdf"));
          document.open();
          // add title
          Font titlefont= FontFactory.getFont(FontFactory.HELVETICA_BOLD,18, BaseColor.BLACK);
          Paragraph title= new Paragraph("Remote Patient Monitoring System Report.\n\n",titlefont);
          title.setAlignment(Element.ALIGN_CENTER);
          document.add(title);
          // add patient info
          document.add(new Paragraph("Patient Name: "+patients.getName()));
          document.add((new Paragraph("Age: "+patients.getAge())));
          document.add((new Paragraph("Sex: "+patients.getGender())));
          document.add(new Paragraph("ID: "+patients.getUserId()));
          document.add((new Paragraph("Email: "+patients.getEmail())));
          document.add(new Paragraph("-----------------------------------------------\n"));
          // add vital history
          document.add(new Paragraph("Vital History"));
          List<Vitals> vitalsList=patients.getVitalsList();
          if(vitalsList.isEmpty()){
              document.add(new Paragraph("vitals Not Recorded!"));
          }else {
              for(Vitals v:vitalsList) {
                  document.add(new Paragraph(v.getTimestamp()+" | HR: "+v.getHeartRate()
                          +" | Oxygen: "+v.getOxygenLevel()+" | Temp: "+v.getTemperature()
                  +" | BloodPressure: "+v.getBloodPressure()));
              }

          }
          document.add(new Paragraph("------------------------------------------------\n"));
          document.add(new Paragraph("DoctorFeedBack "));
          List<FeedBack>feedBacks=patients.getDoctorFeedback();
          if(feedBacks.isEmpty()){
              document.add(new Paragraph("FeedBack not Recorded!"));
          } else {
              for (FeedBack f : feedBacks) {
                  document.add(new Paragraph(f.getFeedBackDate() + "|-->" + f.getFeedBackText()));
              }
          }
          document.add(new Paragraph("------------------------------------------------\n"));
          // adding Prescription
          document.add(new Paragraph("Prescription History"));
          List<Prescriptions>prescriptions=patients.getPrescriptions();
          if(prescriptions.isEmpty()){
              document.add(new Paragraph("Not Prescription issued!"));
          }else {
              for (Prescriptions p : prescriptions) {
                  document.add(new Paragraph("MedicationName--> "+p.getMedicationName() + "| Dosage--> " + p.getDosage()+
                          "| Schedule--> "+p.getSchedule()));
              }
          }
          document.add(new Paragraph("------------------------------------------------\n"));
          document.add(new Paragraph("Medication Effectiveness Analyse"));
          if(MedicationEffectiveness.isPatientImproving(patients)){
              document.add(new Paragraph("patient vitals showing improvement after recent treatment."));
          }else {
              document.add(new Paragraph("No significant improvement detected,"));
          }
          document.add(new Paragraph("\n--------->>>>>><<<<<<<<-------------\n"));

          document.add(new Paragraph("Report Generated On: "+java.time.LocalDateTime.now()));
          document.close();
          System.out.println("Report Generated Successfully.");

      }catch (Exception e){
          System.err.println("Error in Generating report!"+e.getMessage());
      }

   }















}
