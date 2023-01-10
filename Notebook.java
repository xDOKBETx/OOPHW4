package HW4;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Notebook {
    ArrayList<Task> sheet = new ArrayList<>();
    String name;

    public ArrayList<Task> getSheet() {
        return sheet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSheet(ArrayList<Task> sheet) {
        this.sheet = sheet;
    }

    public void createName() {
        Scanner input = new Scanner(System.in);
        System.out.println("Введите название страницы");
        setName(input.nextLine());
    }
    public Author createAuthor() {
        Scanner input = new Scanner(System.in);
        Author author = new Author();
        System.out.println("Введите Вашу фамилию: ");
        author.setSurname(input.nextLine());
        System.out.println("Введите Ваше имя: ");
        author.setName(input.nextLine());
        System.out.println("Введите Вашу должность: ");
        author.setPosition(input.nextLine());
        return author;
    }
    public void fillSheet() {
        boolean flag = true;
        ArrayList<Task> sheet = new ArrayList<>();
        Author author = createAuthor();
        while (flag) {
            System.out.println("Ввести новую задачу? 1 - Да, 2 - Нет");
            Scanner input = new Scanner(System.in);
            int a = input.nextInt();
            if (a == 1) {
                Task task = new Task();
                task.setAuthor(author);
                task.createName();
                task.createPriority();
                task.createDeadline();
                sheet.add(task);
            } else if (a == 2) {
                System.out.println("Лист задач сохранен");
                flag = false;
            } else {
                System.out.println("Введите 1 - Да, 2 - Нет");
                flag = true;
            }
        }
        setSheet(sheet);
    }


    public void outputToTxtFile(String filename, ArrayList<Task> n) throws IOException {
        filename = filename + ".txt";
        FileWriter fileWriter = new FileWriter(filename);
        for (Task x : n) {
            fileWriter.write("Задача: " + x.getName() + ". Приоритет: " + x.getPriority() + ". Дата создания: " + x.getStartDateTime() + ".\n");

        }
        fileWriter.flush();
    }

    public void outputToCsvFile(String filename, ArrayList<Task> n) throws IOException {
        filename = filename + ".csv";
        FileWriter fileWriter = new FileWriter(filename);
        for (Task x : n) {
            fileWriter.write(x.getName() + "," + x.getPriority() + "," + x.getStartDateTime() + ","+
                    x.getAuthor().getName() + "," +x.getAuthor().getSurname() + "," + x.getAuthor().getPosition()+
                    ","+ x.getDeadline()+"\n");
        }
        fileWriter.flush();
    }

    public ArrayList<Task> inputFile(String filename) throws IOException, ParseException {
        String[] parsing = new String[3];
        ArrayList<Task> output = new ArrayList<>();
        File file = new File(filename +".csv");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            parsing = scanner.nextLine().split(",");
            Task task = new Task();
            task.setName(parsing[0]);
            task.setPriority(Integer.parseInt(parsing[1]));
            SimpleDateFormat sd = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy", Locale.ENGLISH);
            task.setStartDateTime(sd.parse (parsing[2]));
            task.author = new Author();
            task.author.setName(parsing[3]);
            task.author.setSurname(parsing[4]);
            task.author.setPosition(parsing[5]);
            task.setDeadline(sd.parse (parsing[6]));
            output.add(task);
        }
        return output;
    }
    public void showPriority (String filename) throws IOException, ParseException {
        ArrayList <Task> base = inputFile(filename);
        ArrayList <Task> result = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        System.out.println("Введите приоритет: 1 - низкий, 2 - средний, 3- высокий, 4- критический");
        int priority = input.nextInt();
        System.out.println(priority);
        for (Task x:base
             ) {
            if (priority == x.getPriority()){
                result.add (x);
            }
            outputToCsvFile (filename+"-"+priority, result);
        }
    }
}
