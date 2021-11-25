/*

Menu driven program which allows for the creation of people objects.
The program then serializes the objects to file and
the user is able to do basic crud operations on the objects.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String creation = ""; // init globals
        int switchAction = 0;
        File file = new File("data.txt");
        Scanner scanner = new Scanner(System.in);
        List<Person> persons = new ArrayList<>();

        //main while loop
        while(switchAction != 5){
            System.out.println("What would you like to do?");
            System.out.println("1.Read information from file ");
            System.out.println("2.Add information to file");
            System.out.println("3.Delete information");
            System.out.println("4.update information");
            System.out.println("5.Exit");
            System.out.println("Enter a number : ");
            switchAction = scanner.nextInt(); // grab user action

            //main switch
            switch(switchAction){
                //if user wants to view files
                case 1:
                        if(!file.exists()){ //check if file exists
                            System.out.println("No file currently exists.");
                        }
                        else{ // else if file exists
                            try {
                                //create the list and output objects
                                List<Person> personsList = readFromFile(file);
                                for(Person p : personsList){
                                    System.out.println(p);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                //case if user wants to add objects
                case 2:
                    //loop object creation
                    while(!(creation.equals("done"))) {
                        System.out.println("Enter name: ");
                        String name = scanner.next();
                        System.out.println("Enter phoneNumber: ");
                        String phoneNumber = scanner.next();
                        System.out.println("Enter date of person ex. 11/23/2000");
                        String dob = scanner.next();
                        System.out.println("Enter email address");
                        String emailAddress = scanner.next();
                        persons.add(new Person(name, phoneNumber,dob, emailAddress));  //add object to list
                        System.out.println("Are you finished creating objects? type done to add objects to file or simply type anything to continue adding");
                        creation = scanner.next();
                    }
                    try {
                        writeList(persons);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                //case if user wants to delete object
                case 3:
                    //check if file exists
                    if(!file.exists()){
                        System.out.println("No file currently exists.");
                    }
                    else{
                        List<Person> personsList = null;
                        try {
                            //read the list from file and output
                            int i = 1;
                            personsList = readFromFile(file);
                            for(Person p : personsList){
                                System.out.println(i + ". " + p);
                                i++;
                            }
                            System.out.println("Enter the number of the person to delete: ");
                            int del = scanner.nextInt();  //grab user deletion choice
                            personsList.remove(del-1); //remove from list
                            writeList(personsList);  //reserialize
                            System.out.println("****************");
                            System.out.println("Delete complete!");
                            System.out.println("****************");
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                //case if user wants to update
                case 4:
                    //check if file exists
                    if(!file.exists()){
                        System.out.println("No file currently exists.");
                    }
                    else{
                        List<Person> personsList = null;
                        try {
                            //read list from file and output
                            int i = 1;
                            personsList = readFromFile(file);
                            for(Person p : personsList){
                                System.out.println(i + ". " + p);
                                i++;
                            }

                            //grab new object details
                            System.out.println("Enter the number of the person to update: ");
                            int update = scanner.nextInt();
                            System.out.println("Enter name: ");
                            String name = scanner.next();
                            System.out.println("Enter phoneNumber: ");
                            String phoneNumber = scanner.next();
                            System.out.println("Enter date of person ex. 11/23/2000");
                            String dob = scanner.next();
                            System.out.println("Enter email address");
                            String emailAddress = scanner.next(); //grab user input

                            //set new details
                            personsList.get(update-1).setName(name);
                            personsList.get(update-1).setPhoneNumber(phoneNumber);
                            personsList.get(update-1).setDob(dob);
                            personsList.get(update-1).setEmailAddress(emailAddress);
                            writeList(personsList); //write to list
                            System.out.println("****************");
                            System.out.println("Update complete!");
                            System.out.println("****************");
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
            }
        }
    }

    //method to read write list to file
    public static void writeList(List<Person> list) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("data.txt"));
        oos.writeObject(list);
        oos.close();
    }
    //method to read list from file
    public static List<Person> readFromFile(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file));
        List<Person> deserializedPersons = (List<Person>) ois.readObject();
        ois.close();
        return deserializedPersons;

    }

}
