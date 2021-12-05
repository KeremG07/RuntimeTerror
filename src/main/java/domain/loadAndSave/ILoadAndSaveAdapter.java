package domain.loadAndSave;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public interface ILoadAndSaveAdapter {
    void saveGame(ArrayList<String> saveList, String username) throws IOException;
    ArrayList<String> loadGame(String username) throws IOException;
}
