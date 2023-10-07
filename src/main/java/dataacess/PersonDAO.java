/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataacess;

import common.Library;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import model.Person;
/**
 *
 * @author Administrator
 */
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
public class PersonDAO {
    private static PersonDAO instance = null;
    Library l;

    public PersonDAO() {
        l = new Library();
    }
    
    public static PersonDAO Instance() {
        if (instance == null) {
            synchronized (PersonDAO.class) {
                if (instance == null) {
                    instance = new PersonDAO();
                }
            }
        }
        return instance;
    }
    
    //allow user find person info
    public void findPersonInfo() {
        ArrayList<Person> lp = new ArrayList<>();
        String pathFile = l.checkInputPathFile("Enter path file: ");
        double money = l.checkInputDouble("Enter number");
        lp = getListPerson(pathFile,money);
        if(lp == null) {
            return;
        }
        printListPerson(lp);
    }

    //allow user copy text to new file
    public void copyNewFile() {
        String pathFileInput = l.checkInputPathFile("Enter Source: ");
        String pathFileOutput = l.checkInputPathFile("Enter new file name: ");
        String content = getNewContent(pathFileInput);
        System.out.println(content);
        writeNewContent(pathFileOutput, content);
    }

    //get list person by path file
    public ArrayList<Person> getListPerson(String pathFile,double money) {
        ArrayList<Person> lp = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(pathFile);
            BufferedReader bufferReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferReader.readLine()) != null) {
                String[] infoPerson = line.split(";");
                if(getSalary(infoPerson[2]) > money) {
                    lp.add(new Person(infoPerson[0], infoPerson[1],
                        getSalary(infoPerson[2])));
                }
            }
        } catch (Exception e) {
            System.err.println("Can't read file.");
            return null;
        }
        return lp;
    }

    //get salary 
    public double getSalary(String salary) {
        double salaryResult = 0;
        try {
            salaryResult = Double.parseDouble(salary);
        } catch (NumberFormatException e) {
            salaryResult = 0;
        } finally {
            return salaryResult;
        }
    }

    //display list person
    public void printListPerson(ArrayList<Person> lp) {
        if(lp.isEmpty()) {
            System.out.println("No result");
            return;
        }
        System.out.printf("%-20s%-20s%-20s\n", "Name", "Address", "Money");
        for (Person person : lp) {
            System.out.printf("%-20s%-20s%-20.1f\n", person.getName(),
                        person.getAddress(), person.getMoney());
        }
        Collections.sort(lp);
        System.out.println("Max: " + lp.get(0).getName());
        System.out.println("Min: " + lp.get(lp.size() - 1).getName());
    }

    //get new content
    public String getNewContent(String pathFileInput) {
        HashSet<String> newContent = new HashSet<>();
        File file = new File(pathFileInput);
        try {
            Scanner input = new Scanner(file);
            int count = 0;
            while (input.hasNext()) {
                String word = input.next();
                newContent.add(word + " ");
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Can’t read file");
        }
        String content = "";
        for (String line : newContent) {
            content += line;
        }
        return content;
    }
    
    //write new content to file
    public void writeNewContent(String pathFileOutput, String content) {
        FileWriter fileWriter = null;
        File file = new File(pathFileOutput);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            bufferWriter.write(content);
            bufferWriter.close();
            System.err.println("Write successful");
        } catch (IOException ex) {
            System.err.println("Can’t write file");
        } finally {
            try {
                fileWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
}
