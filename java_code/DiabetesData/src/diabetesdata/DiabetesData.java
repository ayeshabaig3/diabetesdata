package diabetesdata;

import java.io.*;
import java.util.*;
import java.lang.*;




public class DiabetesData {

  static  ArrayList<HospitalRecord> diabetesTrainingData;
  static  ArrayList<HospitalRecord> diabetesTestData;
  
  static int NUMNEARESTNEIGHBORS = 15;

// convert the result to integer representation and add to diabetesData
// Strings to integer for Gender: 1 = Female 2 = Male
// Age: [80-90) convert to integer eg 85 here. 
static HospitalRecord convertData(String[] result)
{
  
  //if (result.length == 50)
  //    System.out.println(" result readmitted = " + result[49]);
  //we only need 5 attributes to train the dataset
  HospitalRecord hospitalrecord = new HospitalRecord();

  if (result.length == 0) return null;

  // gender which is result[3]
  if (result[3].equals("Male")) 
      hospitalrecord.gender = 1;
  else hospitalrecord.gender = 0;
 
  // age which is result[4]
  if (result[4].equals("[0-10)"))  { hospitalrecord.age = 5; }
  else if (result[4].equals("[10-20)")) { hospitalrecord.age = 15;  }
  else if (result[4].equals("[20-30)")) { hospitalrecord.age = 25; }
  else if (result[4].equals("[30-40)")) { hospitalrecord.age = 35;  }
  else if (result[4].equals("[40-50)")) { hospitalrecord.age = 45;  }
  else if (result[4].equals("[50-60)")) { hospitalrecord.age = 55;  }
  else if (result[4].equals("[60-70)")) { hospitalrecord.age = 65;  }
  else if (result[4].equals("[70-80)")) { hospitalrecord.age = 75;  }
  else if (result[4].equals("[80-90)")) { hospitalrecord.age = 85;  }
  else if (result[4].equals("[90-100)")) { hospitalrecord.age = 95;  }
 
  // adm type = convert from string to int directly result 6
  try {
     hospitalrecord.admissiontypeid  = Integer.parseInt(result[6]);
  } catch (NumberFormatException e) {
        System.out.println(result[6]);
  }
  // discharge disp  = convert from string to int directly result 7 
  try {
     hospitalrecord.dischargedispositionid  = Integer.parseInt(result[7]);
  } catch (NumberFormatException e) {
        System.out.println(result[7]);
  }
  
  // timeinhospital  = convert from string to int directly result 9 
  try {
         hospitalrecord.timeinhospital = Integer.parseInt(result[9]);
  } catch (NumberFormatException e) {
        System.out.println(result[9]);
  }

  // readmitted? 
  try {
     if (result.length == 50)
        hospitalrecord.readmitted  = result[49];
  } catch (NumberFormatException e) {
        System.out.println(e.getLocalizedMessage());
  }

  return hospitalrecord;
}

static void readTrainingData()
{
    FileInputStream fis = null;
    try {
        fis = new FileInputStream("/Users/ayeshabaigplus3it/Documents/GitHub/diabetesdata/data/diabetestest.csv");
    } catch (FileNotFoundException e) {
        System.out.println(e.getLocalizedMessage());
    }
    InputStreamReader input = new InputStreamReader(fis);
    BufferedReader br = new BufferedReader(input);

    String data;

    diabetesTrainingData = new ArrayList<HospitalRecord>();
    
    try {
      while ((data = br.readLine()) != null) {

// TOKENIZE the lines to get the fields in result
// result[2] = gender result[3] = age result[5] adm type result[6] = disch disp
        String[] result = data.split(",");

// convert the result to integer representation and add to diabetesData
// eg. call a helper function to do the conversion
        
        HospitalRecord hospitalrecord = new HospitalRecord();
        hospitalrecord = convertData(result); 
         if (hospitalrecord != null)
          diabetesTrainingData.add(hospitalrecord);
     }
    } catch (IOException e) {
        System.out.println(e.getLocalizedMessage());
    }

    System.out.println("The training data in array\n");
    System.out.println(" gender age admissiontype dischargedisposition timeinhospital rehospitalized?\n");
    for (int i = 0; i < diabetesTrainingData.size(); i++) {
      HospitalRecord hospitalrecord = diabetesTrainingData.get(i);
        System.out.print("Patient " + i + " " + hospitalrecord.gender + " " + hospitalrecord.age + " " + hospitalrecord.admissiontypeid
           + " " + hospitalrecord.dischargedispositionid + " " + hospitalrecord.timeinhospital + " " + hospitalrecord.readmitted);
      System.out.println( "");
    }
    System.out.println( "Total number of training patients = " + diabetesTrainingData.size());
  
}

static void readTestData()
{
    FileInputStream fis = null;
    try {
       fis = new FileInputStream("/Users/gmadkat/tutor/ayesha/diabetestest.csv");
    } catch (FileNotFoundException e) {
        System.out.println(e.getLocalizedMessage());
    }
    InputStreamReader input = new InputStreamReader(fis);
    BufferedReader br = new BufferedReader(input);

    String data;

     diabetesTestData = new ArrayList<HospitalRecord>();
 
    try {
     while ((data = br.readLine()) != null) {

// TOKENIZE the lines to get the fields in result
// result[2] = gender result[3] = age result[5] adm type result[6] = disch disp
        String[] result = data.split(",");

// convert the result to integer representation and add to diabetesData
// eg. call a helper function to do the conversion
        
        HospitalRecord hospitalrecord = new HospitalRecord();
        hospitalrecord = convertData(result); 
         if (hospitalrecord != null)
          diabetesTestData.add(hospitalrecord);
    }
    } catch (IOException e) {
        System.out.println(e.getLocalizedMessage());
    }
    
    System.out.println("Reading the test data in array\n");
    System.out.println(" gender age admissiontype dischargedisposition timeinhospital readmitted?\n");
    for (int i = 0; i < diabetesTestData.size(); i++) {
      HospitalRecord hospitalrecord = diabetesTestData.get(i);
        System.out.print("Patient " + i + " " + hospitalrecord.gender + " " + hospitalrecord.age + " " + hospitalrecord.admissiontypeid
           + " " + hospitalrecord.dischargedispositionid + " " + hospitalrecord.timeinhospital + " " + hospitalrecord.readmitted);
      System.out.println( "");
    }
    System.out.println( "Total number of testing patients = " + diabetesTestData.size());
  
}



public static void trainData () {
    
    int matchedcounttotal = 0;
    int matchedcounttotalyesno = 0;
    for (int i = 0; i < diabetesTestData.size(); i++) {
        //if (i > 30) break;  // just testing for now
        for (int j = 0; j < diabetesTrainingData.size(); j++) {
            
            diabetesTrainingData.get(j).setDistance(diabetesTestData.get(i));
        }
        
        Collections.sort(diabetesTrainingData, new Comparator<HospitalRecord>(){
              @Override
           public int compare(HospitalRecord s1, HospitalRecord s2) {
              Double obj1 = new Double(s1.distance);
              Double obj2 = new Double(s2.distance);
              return obj1.compareTo(obj2);
            }
         });
     
        System.out.println( "");
        HospitalRecord hospitalrecord1 = diabetesTestData.get(i);
        System.out.print("Patient test " + i + " " +hospitalrecord1.distance + " " + hospitalrecord1.gender + " " + hospitalrecord1.age + " " + hospitalrecord1.admissiontypeid
                 + " " + hospitalrecord1.dischargedispositionid + " " + hospitalrecord1.timeinhospital + " " + hospitalrecord1.readmitted);
        System.out.println( "");
     
        int noadmitcount = 0;
        int less30count = 0;
        int greater30count = 0;

        String mostfrequentpolicy = "NO";
        String mostfrequentpolicyyesno = "NO";
        
        for (int j1 = 0; j1 < NUMNEARESTNEIGHBORS; j1++) {
             
            HospitalRecord hospitalrecord = diabetesTrainingData.get(j1);
            if (hospitalrecord.readmitted.equals("NO")) 
                noadmitcount++;
            if (hospitalrecord.readmitted.equals("<30")) 
            {   
                less30count++;
            }
           if (hospitalrecord.readmitted.equals(">30")) 
            {
                greater30count++;
            }  
 
            
            System.out.print("Patient sorted " + j1 + " " +hospitalrecord.distance + " " + hospitalrecord.gender + " " + hospitalrecord.age + " " + hospitalrecord.admissiontypeid
                 + " " + hospitalrecord.dischargedispositionid + " " + hospitalrecord.timeinhospital + " " + hospitalrecord.readmitted);
            System.out.println( "");
         }
        
         if (less30count >= noadmitcount && less30count >= greater30count) mostfrequentpolicy = "<30";
         if (greater30count >= less30count && greater30count >= noadmitcount) mostfrequentpolicy = ">30";
         if (noadmitcount >= less30count && noadmitcount >= greater30count) mostfrequentpolicy = "NO";
         if ((less30count + greater30count) >= noadmitcount) mostfrequentpolicyyesno = "YES";
         
         System.out.println("Patient sorted " + i + " Count of no admitted = " + noadmitcount + " Count of <30 = " + less30count +
                 " Count of >30 = " + greater30count);


         System.out.println("Patient sorted " + i + " Predicted outcome by KNN = " + mostfrequentpolicy +
                  " Actual outcome in reality = " + hospitalrecord1.readmitted);
         
         if (hospitalrecord1.readmitted.equals(mostfrequentpolicy)) matchedcounttotal++;
         
         if (mostfrequentpolicyyesno.equals("YES"))
            if (hospitalrecord1.readmitted.equals(">30") || hospitalrecord1.readmitted.equals("<30")) 
                matchedcounttotalyesno++;
         if (mostfrequentpolicyyesno.equals("NO") && hospitalrecord1.readmitted.equals("NO")) 
                matchedcounttotalyesno++;

    }
    System.out.println("Total patients where outcome matches = " + 
            matchedcounttotal + " Total test patient count = " + diabetesTestData.size());
    System.out.println("Total patients where outcome matches (admitted or not) = " + 
            matchedcounttotalyesno + " Total test patient count = " + diabetesTestData.size());

}
        
public static void main(String[] args) throws FileNotFoundException, IOException {
 
    // read the data to train and determine outcome
    readTrainingData();
    
    // read the data to determine the rehospitalization prediction
    readTestData();
    
    // train the testing data one record at a time
    //1. loop over the training data and measure distance from the 
    //   test record to each record in training data
    //2. Find the Kth nearest neighbors, use K = 5 to begin with
    //3. Check readmittance of the 5 and determine majority of those and use
    //   the majority as the prediction
    trainData();

   }

}
