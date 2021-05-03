package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class TableManager {

	Connection connectionPoint;
	
	public TableManager(Connection connectionPoint) {
		this.connectionPoint = connectionPoint;
	}
	
	public void Insert(Passenger passenger) {
		
	}
	
	public List < Passenger > Select(int rollId) {
		List < Passenger > passengerList = new ArrayList < > ();
        String SQL_SELECT = "Select * from STUDENT where roll =" + rollId;
        
        try (PreparedStatement preparedStatement = connectionPoint.prepareStatement(SQL_SELECT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            
            passengerList = ListForming(resultSet);

        } catch (SQLException e) {
            System.out.print(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return passengerList;
	}
	
	public void Update(Passenger passenger) {
		
	}
	
	public void Delete() {
		
	}
	
	public Map <String, Integer> MaleFemaleRatio () {
		
		String SQL_SELECT = "select \"Sex\", count(\"Sex\") from titanic group by \"Sex\";";
		
		Map <String, Integer> mfRatio = new HashMap <String, Integer>();
		
		try (PreparedStatement preparedStatement = connectionPoint.prepareStatement(SQL_SELECT)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				String sex = resultSet.getString("Sex");
				int count = resultSet.getInt("count");
				
				mfRatio.put(sex, count);
			}
			
		} catch (SQLException e) {
            System.out.print(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return  mfRatio;
	}
	
	public Map <String, NumOfSurvivors> GetSurvived () {
		
		String SQL_SELECT = "select \"Sex\", \"Survived\", count(\"Sex\") from titanic group by \"Sex\", \"Survived\";";
		
		Map <String, NumOfSurvivors> numOfSurvivors = new HashMap <String, NumOfSurvivors>();
		
		NumOfSurvivors maleSurvival = new NumOfSurvivors();
		NumOfSurvivors femaleSurvival = new NumOfSurvivors();
		maleSurvival.setSex("male");
		femaleSurvival.setSex("female");
		
		try (PreparedStatement preparedStatement = connectionPoint.prepareStatement(SQL_SELECT)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				String sex = resultSet.getString("Sex");
				int survived = resultSet.getInt("Survived");
				int count = resultSet.getInt("count");
				
				if (Objects.equals(sex, "male")) {
					if (survived == 1) {
						maleSurvival.setSurvived(count);
					}
					else {
						maleSurvival.setDied(count);
					}
				}
				else {
					if (survived == 1) {
						femaleSurvival.setSurvived(count);
					}
					else {
						femaleSurvival.setDied(count);
					}
				}
			}
			
			numOfSurvivors.put("Male", maleSurvival);
			numOfSurvivors.put("Female", femaleSurvival);
			
		} catch (SQLException e) {
            System.out.print(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return  numOfSurvivors;
	}
	
	public List <Passenger> GetRawList(int listFrom, int listTo) {
		
		String SQL_SELECT = "Select * from titanic WHERE \"PassengerId\" BETWEEN " + listFrom + " AND " + listTo;
		
		List < Passenger > passengerList = new ArrayList < > ();
		
		try (PreparedStatement preparedStatement = connectionPoint.prepareStatement(SQL_SELECT)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			
			passengerList = ListForming(resultSet);
			
		} catch (SQLException e) {
            System.out.print(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return  passengerList;
	}
	
	public List <Passenger> GetRawList() {
		
		String SQL_SELECT = "Select * from titanic";
		
		List < Passenger > passengerList = new ArrayList < > ();
		
		try (PreparedStatement preparedStatement = connectionPoint.prepareStatement(SQL_SELECT)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			
			passengerList = ListForming(resultSet);
			
		} catch (SQLException e) {
            System.out.print(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return  passengerList;
	}

	private List < Passenger > ListForming (ResultSet resultSet) {
	
		List < Passenger > passengerList = new ArrayList < > ();
		try {
			while (resultSet.next()) {
		        
				int passengerId = resultSet.getInt("PassengerId");
		        int survived = resultSet.getInt("Survived");
		        int pclass = resultSet.getInt("Pclass");
		        String name = resultSet.getString("Name");
		        String sex = resultSet.getString("Sex");
		        double age = resultSet.getDouble("Age");
		        int sibSp = resultSet.getInt("SibSp");
		        int parch = resultSet.getInt("Parch");
		        String ticket = resultSet.getString("Ticket");
		        double fare = resultSet.getDouble("Fare");
		        String cabin = resultSet.getString("Cabin");
		        String embarked = resultSet.getString("Embarked");
		        
		        
		        Passenger passenger = new Passenger();
		        passenger.setPassengerId(passengerId);
		        passenger.setSurvived(survived);
		        passenger.setPclass(pclass);
		        passenger.setName(name);
		        passenger.setSex(sex);
		        passenger.setAge(age);
		        passenger.setSibSp(sibSp);
		        passenger.setParch(parch);
		        passenger.setTicket(ticket);
		        passenger.setFare(fare);
		        passenger.setCabin(cabin);
		        passenger.setEmbarked(embarked);
		        
		        passengerList.add(passenger);
		    }
		
		} catch (SQLException e) {
            System.out.print(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } 
		
		return passengerList;
	}
}
