import java.util.ArrayList;


public class Publisher {
    private String name;
    private String address;
    private String email;
    private ArrayList<String> phoneNumbers;
    private String bankingInfo;
    public Publisher(String name, String address, String email, ArrayList<String> phoneNumbers, String bankingInfo) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumbers = phoneNumbers;
        this.bankingInfo = bankingInfo;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public ArrayList<String> getPhoneNumbers() {
        return phoneNumbers;
    }
    public void setPhoneNumbers(ArrayList<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
    public String getBankingInfo() {
        return bankingInfo;
    }
    public void setBankingInfo(String bankingInfo) {
        this.bankingInfo = bankingInfo;
    }

    
}
