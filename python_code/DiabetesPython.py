import csv
import math

class HospitalRecord(object):
    def __init__(self,results):
        if results[3] =="Male":
            self.gender =1
        else:
            self.gender =0
        if results[4] == "[0-10)":
            self.age =5
        elif results[4] == "[10-20)":
            self.age = 15
        elif results[4] =="[20-30)":
            self.age = 25
        elif results[4] == "[30-40)":
            self.age = 35
        elif results[4] == "[40-50)":
            self.age = 45
        elif results[4] == "[50-60)":
            self.age = 55
        elif results[4] == "[60-70)":
            self.age = 65
        elif results[4] == "[70-80)":
            self.age = 75
        elif results[4] == "[80-90)":
            self.age = 85
        elif results[4] == "[90-100)":
            self.age = 95
        else:
            raise Exception("invalid age: %s" %(results[4],))



        self.admission_type_id = int(results[6])
        self.discharge_disposition_id = int(results[7])
        self.time_in_hospital = int(results[9])
        self.readmitted = results[49]
        self.distance=0.0
    def set_distance(self,other):
        self.distance = math.sqrt((self.age - other.age) ** 2) + (self.gender -other.gender) **2
        self.distance += (self.discharge_disposition_id -other.discharge_disposition_id ) **2
        self.distance += (self.admission_type_id - other.admission_type_id) **2
        self.distance += (self.time_in_hospital - other.time_in_hospital) ** 2

    def __str__(self):
        return "%f %d %d %d %d %d %s" % (
        self.distance, self.gender, self.age, self.admission_type_id, self.discharge_disposition_id,
        self.time_in_hospital, self.readmitted)

def read_data(filename):

    f = open(filename, "r")
    reader = csv.reader (f)
    patients=[]
    for row in reader:
        patients.append(HospitalRecord(row))
    print "Read %d records from file %s" %(len(patients),filename)
    return patients



def traindata(training_data,testing_data ):
    matchedcounttotal = 0
    matchedcounttotalyesno = 0
    for training_1 in training_data:
        for training_2 in training_data:

            training_1.set_distance(training_2)
        training_data.sort(key=lambda x:x.distance)
        print ("Patient test: %s" % training_1)

        no_admit_count = 0
        less_30count = 0
        greater_30 = 0

        most_frequent_policy = "NO"
        most_frequent_policy_yes_no = "NO"

        for j in training_data[:15]:
            if j.readmitted == "NO":
                no_admit_count += 1
            if j.readmitted == "<30":
                less_30count += 1
            if j.readmitted == "<30":
                greater_30 += 1
         if (less_30count >= no_admit_count) and (less_30count >= greater_30):
             most_frequent_policy = "<30"





training_data = read_data("/Users/ayeshabaigplus3it/Documents/GitHub/diabetesdata/data/training.csv")
testing_data = read_data("/Users/ayeshabaigplus3it/Documents/GitHub/diabetesdata/data/testing.csv")
traindata(training_data, testing_data)



