import csv
f = open("/Users/ayeshabaigplus3it/Documents/GitHub/diabetesdata/data/diabetestest.csv", "r")
reader = csv.reader (f)
for row in reader:
    print(row[0])