# FileAnonymizer

Java-Application that anonymizes files by removing a regex-defined string from the filename, as well as redefining the lastModifiedDate.

# Requirements
- Java 1.8 or higher
- Make sure the .jar file is executable and a Java1.8-JRE is in your Environment/Path variable 

# Usage

### Disclaimer
This app will modify all selected files (no directories) inplace. This means, the files are not copied! Thus, make sure to make a data-backup before doing this.
### Start
- Double click the .jar file, or...

- ...execute in your command line:
```
java -jar <path to the jar file>
```
### Use

1. (Optional) Modify the REGEX to match the expression you want to delete from every file name (excluding filetype ending)
2. (Optional) Pick a date as "last access date" (it will be 00:00 + UTC timeoffset on this date)
3. Click "Choose files... and go!", select all *files* you want to anonymize.


# FAQ
- Q:  What if modifying the file name results in multiple files with the same name? 

A: To avoid duplicate files, the app automatically appends an index to the files, in order of processing. e.g.
```
myfile.csv
myfile__1.csv
myfile__2.csv
```
- Q: Who to contact if there are problems with executing?

A: First, check your java installation by executing
```
java -version
```
in the command line, this should say something including "Java 1.8" or a higher version (i.e. 10, 11 etc.). If not, try reinstalling Java / setting the Environment/Path variable.

- Q; Who is the contact if I still can't get it to run?

clemens.schartmueller@thi.de

# REPO-URL
 
 Access is granted upon request by the repo-owner (clemens.schartmueller@thi.de). 
 In general, the repo is located
 
  - on bitbucket.org, 
  
  - in the "**HCIG_THI**" team, 
  
  - project "**Evaluation**"
  
  - repo name "**FileAnonymizer**"
  