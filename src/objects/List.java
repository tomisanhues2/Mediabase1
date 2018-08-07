package objects;

import resources.IObservableLists;

public class List implements IObservableLists {

    private int ID;
    private String listName;
    private String authorName;


    public List(int ID, String listName, String authorName) {
        this.ID = ID;
        this.listName = listName;
        this.authorName = authorName;
    }

    public List() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
