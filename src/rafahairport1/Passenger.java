package rafahairport1;

public class Passenger implements Comparable<Passenger> {

    private String passportNumber;
    private String name;
    private String nationality;
    private String phone;
    private String email;
    private String boardingPass;

    public Passenger(String passportNumber, String name, String nationality, String phone, String email) {
        this.passportNumber = passportNumber;
        this.name = name;
        this.nationality = nationality;
        this.phone = phone;
        this.email = email;
        this.boardingPass = null;
    }

    // Getters 
    public String getPassportNumber() {
        return passportNumber;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getBoardingPass() {
        return boardingPass;
    }

    // Setters 
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name.trim();
        }
    }

    public void setPhone(String phone) {
        if (phone != null && !phone.trim().isEmpty()) {
            this.phone = phone.trim();
        }
    }

    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email;
        }
    }

    public void setBoardingPass(String boardingPass) {
        this.boardingPass = boardingPass;
    }

    //Implement Comparable interface
    @Override
    public int compareTo(Passenger other) {
        return this.name.compareTo(other.name); // يقارن اسماء المسافرين لعرضهم عند الطباعة مرتبين
    }

    // Override
    @Override
    public String toString() {
        return name + " (" + passportNumber + ") - " + nationality;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Passenger) {
            return this.passportNumber.equals(((Passenger) obj).passportNumber);
        }
        return false;
    }
}
