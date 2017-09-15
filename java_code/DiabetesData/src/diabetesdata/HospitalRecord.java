/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diabetesdata;

import java.util.*;
import java.io.*;
import java.lang.Math;

/**
 *
 * @author abaig
 */
public class HospitalRecord {
    double age;
    double gender;
    double dischargedispositionid;
    double admissiontypeid;
    double timeinhospital;
    String readmitted;
    double distance;
    String readmittedPredict; // used onlu for test array
    
    public void setDistance(HospitalRecord  hr) {
        
        distance = Math.sqrt((Math.pow((this.age - hr.age), 2))
                + Math.pow((this.gender - hr.gender), 2)
                + Math.pow((this.dischargedispositionid-hr.dischargedispositionid), 2)
                + Math.pow((this.admissiontypeid - hr.admissiontypeid), 2)
                + Math.pow((this.timeinhospital-hr.timeinhospital), 2));
    }
}
