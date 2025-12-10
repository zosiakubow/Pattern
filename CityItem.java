package Pattern;

public class CityItem {
    public int id;
    public String description;
    public CityItem(int id, String description)
    {
        this.id= id;
        this.description = description;

    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }


    public void setID(int id) {this.id = id;}

    public void setDescription(String description) {this.description = description;}

    public String toString() {return description;}
}
