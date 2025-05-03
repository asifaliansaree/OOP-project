package Database;

import services.VideoConsultation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VideoConsultationDatabase {
    private static final String DATA_DIR = "VideoConsultationFile/data";

    // ensuring of existing file
    static {
        File directory = new File(DATA_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public static void saveConsultation(List<VideoConsultation> consultation) {
        try (PrintWriter bf = new PrintWriter(new FileWriter(DATA_DIR+"vd.txt"))) {
            for (VideoConsultation v : consultation) {
                bf.println(v.getPatientId() + "," + v.getDoctorId() + "," + v.getPlateForm() + "," +
                        v.getLink() + "," + v.getTime().toString() + "," + v.isApproved());
            }

        } catch (IOException e) {
            System.err.println("Error in saving Video Consultation file!");
        }

    }

    public static List<VideoConsultation> loadConsultation() {
        List<VideoConsultation> consultations = new ArrayList<>();
        File file = new File(DATA_DIR+"vd.txt");
        if(!file.exists()){return consultations;}
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] part = line.split(",");
                VideoConsultation vd = new VideoConsultation(part[0], part[1], part[2], part[3],
                        java.time.LocalDateTime.parse(part[4]), Boolean.parseBoolean(part[5]));
                consultations.add(vd);
            }

        } catch (IOException e) {
            System.err.println("Error in Loading Video Consultation file!");
        }
        return consultations;
    }


}

