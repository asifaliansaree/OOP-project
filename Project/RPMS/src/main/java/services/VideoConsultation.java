package services;

import java.time.LocalDateTime;

public class VideoConsultation {

    private String patientId;
    private String doctorId;
    private String plateForm;
    private String link;
    private LocalDateTime time;
    private boolean Approved;
    public VideoConsultation(String patientId,String doctorId,String plateForm,
                             String link,LocalDateTime time,boolean state)
    {
        this.Approved=state;
        this.doctorId=doctorId;
        this.plateForm=plateForm;
        this.time=time;
        this.link=link;
        this.patientId=patientId;

    }

    public String getPatientId() {
        return patientId;
    }

    public boolean isApproved() {
        return Approved;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getPlateForm() {
        return plateForm;
    }

    public String getLink() {
        return link;
    }
   // setter method;
    public void setApproved(boolean approved) {
        Approved = approved;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPlateForm(String plateForm) {
        this.plateForm = plateForm;
    }
}
