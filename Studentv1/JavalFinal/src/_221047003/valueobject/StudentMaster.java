package _221047003.valueobject;
public class StudentMaster {
	  private int rollNumber;
	  private String name;
	  private String address;
	  private String beBranch;

	  public StudentMaster(int rollNumber, String name, String address, String beBranch) {
	    this.rollNumber = rollNumber;
	    this.name = name;
	    this.address = address;
	    this.beBranch = beBranch;
	  }
	  public StudentMaster() {
		// TODO Auto-generated constructor stub
	}
	public int getRollNumber() {
	    return rollNumber;
	  }

	  public void setRollNumber(int rollNumber) {
	    this.rollNumber = rollNumber;
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

	  public String getBeBranch() {
	    return beBranch;
	  }

	  public void setBeBranch(String beBranch) {
	    this.beBranch = beBranch;
	  }

	  @Override
	  public String toString() {
	    return "StudentMaster{" +
	      "rollNumber=" + rollNumber +
	      ", name='" + name + '\'' +
	      ", address='" + address + '\'' +
	      ", beBranch='" + beBranch + '\'' +
	      '}';
	  }
	}

