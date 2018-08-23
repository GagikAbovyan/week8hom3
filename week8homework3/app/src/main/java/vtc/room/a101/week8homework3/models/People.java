package vtc.room.a101.week8homework3.models;

public class People {
    private String name;
    private String surname;
    private String price;

    public People() {
    }

    public People(String name, String surname, String price) {
        this.name = name;
        this.surname = surname;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
