package domain.loadAndSave;

import java.io.*;
import java.util.ArrayList;

public class LocalLoadAndSave implements ILoadAndSaveAdapter {
    @Override
    public void saveGame(ArrayList<String> saveList, String username) throws IOException {
        String filepath = "src/main/java/saves/"+ username+".txt";
        File savedFile = new File(filepath);
        if(!savedFile.exists()) savedFile.createNewFile();

        FileWriter fileWriter = new FileWriter(savedFile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for(String item:saveList){
            bufferedWriter.write(item+"\n");
        }
        System.out.println("Game is locally saved to "+username +".txt in saves package.");
        bufferedWriter.close();
        fileWriter.close();

    }

    @Override
    public ArrayList<String> loadGame(String username) throws IOException {
        String filepath = "src/main/java/saves/"+ username+".txt";
        File savedFile = new File(filepath);
        FileReader fileReader = new FileReader(savedFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> loadList= new ArrayList<>();
        String line= bufferedReader.readLine();
        while(line!=null){
            loadList.add(line);
            line= bufferedReader.readLine();
        }
        bufferedReader.close();
        fileReader.close();
        System.out.println("Game is locally loaded from "+ username+".txt.");

        return loadList;
    }
}
