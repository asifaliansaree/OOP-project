package utill;

import Model.Patients;

import java.util.*;

public class TrendAnalyzer {
    // method get all HeartRate of patient for Drawing graph
    public static List<Double> getHeartRateTrend(Patients patient) {
        List<Double> trend = new ArrayList<>();
        for (Vitals v : patient.getVitalsList()) {
            trend.add(v.getHeartRate());
        }
        return trend;
    }
    // method get all oxygen level of patient for Drawing graph
    public static List<Double> getOxygenLevelTrend(Patients patient) {
        List<Double> trend = new ArrayList<>();
        for (Vitals v : patient.getVitalsList()) {
            trend.add(v.getOxygenLevel());
        }
        return trend;
    }
    // method get all temperature of patient for Drawing graph
    public static List<Double> getTemperatureTrend(Patients patient) {
        List<Double> trend = new ArrayList<>();
        for (Vitals v : patient.getVitalsList()) {
            trend.add(v.getTemperature());
        }
        return trend;
    }
    // method get all time stamp of patient for Drawing graph
    public static List<String> getTimestamps(Patients patient) {
        List<String> trend = new ArrayList<>();
        for (Vitals v : patient.getVitalsList()) {
            trend.add(v.getTimestamp().toString());
        }
        return trend;
    }
}

