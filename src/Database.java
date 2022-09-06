import java.sql.*;

public class Database {

    //This method executes a query and returns the number of albums for the artist with name artistName
    public int getTitles(String artistName) {
        //Implement this method
        //Prevent SQL injections!

        int titleNum = 0;

        try{
            Class.forName("org.postgresql.Driver"); // maybe remove
            Connection con1 = DriverManager.getConnection(Credentials.URL, Credentials.USERNAME, Credentials.PASSWORD);

            String sql = "SELECT * FROM artist JOIN album ON artist.artistid = album.artistid WHERE artist.name = ?";
            PreparedStatement ps = con1.prepareStatement(sql);
            ps.clearParameters();

            ps.setString(1, artistName);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                titleNum++;
            }

            rs.close();
            ps.close();
            con1.close();

        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return titleNum;
    }

    // This method establishes a DB connection & returns a boolean status
    public boolean establishDBConnection() {
        //5 sec timeout

        try{
            Class.forName("org.postgresql.Driver");
            Connection con2 = DriverManager.getConnection(Credentials.URL, Credentials.USERNAME, Credentials.PASSWORD);
            return con2.isValid(5);

        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return false;
    }
}