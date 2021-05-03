package application;

public class Passenger {
	
	private int PassengerId;
	private int Survived;
	private int Pclass;
	private String Name;
	private String Sex;
	private double Age;
	private int SibSp;
	private int Parch;
	private String Ticket;
	private double Fare;
	private String Cabin;
	private String Embarked;
	
	
	public int getPassengerId() {
		return PassengerId;
	}
	public void setPassengerId(int passengerId) {
		PassengerId = passengerId;
	}
	public int getSurvived() {
		return Survived;
	}
	public void setSurvived(int survived) {
		Survived = survived;
	}
	public int getPclass() {
		return Pclass;
	}
	public void setPclass(int pclass) {
		Pclass = pclass;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public double getAge() {
		return Age;
	}
	public void setAge(double age) {
		Age = age;
	}
	public int getSibSp() {
		return SibSp;
	}
	public void setSibSp(int sibSp) {
		SibSp = sibSp;
	}
	public int getParch() {
		return Parch;
	}
	public void setParch(int parch) {
		Parch = parch;
	}
	public String getTicket() {
		return Ticket;
	}
	public void setTicket(String ticket) {
		Ticket = ticket;
	}
	public double getFare() {
		return Fare;
	}
	public void setFare(double fare) {
		Fare = fare;
	}
	public String getCabin() {
		return Cabin;
	}
	public void setCabin(String cabin) {
		Cabin = cabin;
	}
	public String getEmbarked() {
		return Embarked;
	}
	public void setEmbarked(String embarked) {
		Embarked = embarked;
	}
	
	
}
