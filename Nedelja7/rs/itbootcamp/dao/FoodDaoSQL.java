package SestaNedelja.rs.itbootcamp.dao;

import SestaNedelja.rs.itbootcamp.db.DatabaseConnection;
import SestaNedelja.rs.itbootcamp.model.FoodModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodDaoSQL implements FoodDao {
    private static final Connection conn = DatabaseConnection.getConnection();

    @Override
    public void insert(FoodModel fm) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO food VALUES (?,?,?,?,?,?)");
            st.setInt(1, fm.getFood_id());
            st.setString(2, fm.getFood_name());
            st.setDouble(3, fm.getFood_kcal());
            st.setDouble(4, fm.getFood_proteins());
            st.setDouble(5, fm.getFood_carbohydrates());
            st.setDouble(6, fm.getFood_fat());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addToMeal(int meal_id, int food_id, double mass) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO mealfood VALUES (?,?,?)");
            st.setInt(1,meal_id);
            st.setInt(2,food_id);
            st.setDouble(3,mass);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM food WHERE food_id=?");
            st.setInt(1, id);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(FoodModel fm) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE food " +
                    "SET food_name=?," +
                    "food_kcal=?," +
                    "food_proteins=?," +
                    "food_carbs=?," +
                    "food_fat=? " +
                    "WHERE food_id = ?");
            st.setString(1, fm.getFood_name());
            st.setDouble(2, fm.getFood_kcal());
            st.setDouble(3, fm.getFood_proteins());
            st.setDouble(4, fm.getFood_carbohydrates());
            st.setDouble(5, fm.getFood_fat());
            st.setInt(6, fm.getFood_id());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public FoodModel getFood(int id) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM food WHERE food_id = " + id);
            rs.next();
            return new FoodModel(rs.getInt(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getDouble(4),
                    rs.getDouble(5),
                    rs.getDouble(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<FoodModel> getAllFood() {
        List<FoodModel> allFood = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM food");
            while (rs.next()) {
                FoodModel newFood = new FoodModel(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getDouble(6)
                );

                allFood.add(newFood);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allFood;
    }
}
