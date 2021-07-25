package com.lmsu.users;

import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO implements Serializable {

    private List<UserDTO> listAccount;

    public List<UserDTO> getListAccount() {
        return listAccount;
    }

    public UserDTO checkLogin(String email, String password) throws SQLException, NamingException {
        UserDTO user = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [name], [roleID],[semester_no], [password], [email], [phoneNumber], "
                        + "[profilePicturePath], [activeStatus], [isNotifyArrival], [isNotifyPopular], [isDelete] "
                        + "FROM [Users] "
                        + "WHERE [email] = ? AND [password] = ? AND isDelete = 0";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String roleID = rs.getString("roleID");
                    String semester_no = rs.getString("semester_no");
                    String passwordCol = rs.getString("password");
                    String emailCol = rs.getString("email");
                    String phoneNumber = rs.getString("phoneNumber");
                    String profilePicturePath = rs.getString("profilePicturePath");
                    boolean activeStatus = rs.getBoolean("activeStatus");
                    boolean isNotifyArrival = rs.getBoolean("isNotifyArrival");
                    boolean isNotifyPopular = rs.getBoolean("isNotifyPopular");
                    boolean isDelete = rs.getBoolean("isDelete");
                    user = new UserDTO(id, name, roleID, passwordCol, emailCol, phoneNumber, semester_no, profilePicturePath,
                            activeStatus, isNotifyArrival, isNotifyPopular, isDelete);
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return user;
    }

    public boolean checkUserExisted(String idOrEmail) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "select [id] "
                        + "from [Users] "
                        + "where [id] LIKE ? OR [email] LIKE ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, idOrEmail);
                stm.setString(2, idOrEmail);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public boolean addUser(String userID, String userName, String roleID, String password, String email, String phoneNumber,
                           String semester, String profilePicture, boolean activeStatus, boolean isNotifyArrival,
                           boolean isNotifyPopular, boolean isDelete) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO Users([id], [name], [roleID], [semester_no], [password], [email], " +
                        "[phoneNumber], [profilePicturePath], [activeStatus], [isNotifyArrival], [isNotifyPopular], [isDelete]) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setString(2, userName);
                stm.setString(3, roleID);
                stm.setString(4, semester);
                stm.setString(5, password);
                stm.setString(6, email);
                stm.setString(7, phoneNumber);
                stm.setString(8, profilePicture);
                stm.setBoolean(9, activeStatus);
                stm.setBoolean(10, isNotifyArrival);
                stm.setBoolean(11, isNotifyPopular);
                stm.setBoolean(12, isDelete);

                int row = stm.executeUpdate();
                if (row > 0) return true;
            }
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public void viewUserList() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id], [name], [roleID], [semester_no], [password], [email], [phoneNumber], " +
                        "[profilePicturePath], [activeStatus], [isNotifyArrival], [isNotifyPopular], [isDelete] " +
                        "FROM [Users] " +
                        "WHERE roleID = 4";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String roleID = rs.getString("roleID");
                    String password = rs.getString("password");
                    String email = rs.getString("email");
                    String phoneNumber = rs.getString("phoneNumber");
                    String semester = rs.getString("semester_no");
                    String profilePicturePath = rs.getString("profilePicturePath");
                    boolean activeStatus = rs.getBoolean("activeStatus");
                    boolean isNotifyArrival = rs.getBoolean("isNotifyArrival");
                    boolean isNotifyPopular = rs.getBoolean("isNotifyPopular");
                    boolean isDelete = rs.getBoolean("isDelete");

                    UserDTO dto = new UserDTO(id, name, roleID, password, email, phoneNumber, semester, profilePicturePath,
                            activeStatus, isNotifyArrival, isNotifyPopular, isDelete);

                    if (this.listAccount == null) {
                        this.listAccount = new ArrayList<UserDTO>();
                    } //end if book list not existed
                    this.listAccount.add(dto);
                } //end while traversing result set
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public void viewStaffList() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id], [name], [roleID], [semester_no], [password], [email], [phoneNumber], " +
                        "[profilePicturePath], [activeStatus], [isNotifyArrival], [isNotifyPopular], [isDelete] " +
                        "FROM [Users] " +
                        "WHERE roleID = 1 OR roleID = 2 OR roleID = 3";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String roleID = rs.getString("roleID");
                    String password = rs.getString("password");
                    String email = rs.getString("email");
                    String phoneNumber = rs.getString("phoneNumber");
                    String semester = rs.getString("semester_no");
                    String profilePicturePath = rs.getString("profilePicturePath");
                    boolean activeStatus = rs.getBoolean("activeStatus");
                    boolean isNotifyArrival = rs.getBoolean("isNotifyArrival");
                    boolean isNotifyPopular = rs.getBoolean("isNotifyPopular");
                    boolean isDelete = rs.getBoolean("isDelete");

                    UserDTO dto = new UserDTO(id, name, roleID, password, email, phoneNumber, semester, profilePicturePath,
                            activeStatus, isNotifyArrival, isNotifyPopular, isDelete);

                    if (this.listAccount == null) {
                        this.listAccount = new ArrayList<UserDTO>();
                    } //end if book list not existed
                    this.listAccount.add(dto);
                } //end while traversing result set
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public boolean updatePassword(String id, String password) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE [Users] " +
                        "SET [password] = ? " +
                        "WHERE [id] = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, password);
                stm.setString(2, id);

                int row = stm.executeUpdate();
                if (row > 0) return true;
            }
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public boolean updatePhone(String id, String phoneNumber) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE [Users] " +
                        "SET [phoneNumber] = ? " +
                        "WHERE [id] = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, phoneNumber);
                stm.setString(2, id);

                int row = stm.executeUpdate();
                if (row > 0) return true;
            }

        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public void searchUserByName(String searchValue) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id], [name], [roleID], [semester_no], [password], [email], [phoneNumber], " +
                        "[profilePicturePath], [activeStatus], [isNotifyArrival], [isNotifyPopular], [isDelete]  " +
                        "FROM [Users] " +
                        "WHERE [name] LIKE ? AND roleID = 4";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String roleID = rs.getString("roleID");
                    String password = rs.getString("password");
                    String email = rs.getString("email");
                    String phoneNumber = rs.getString("phoneNumber");
                    String semester = rs.getString("semester_no");
                    String profilePicturePath = rs.getString("profilePicturePath");
                    boolean activeStatus = rs.getBoolean("activeStatus");
                    boolean isNotifyArrival = rs.getBoolean("isNotifyArrival");
                    boolean isNotifyPopular = rs.getBoolean("isNotifyPopular");
                    boolean isDelete = rs.getBoolean("isDelete");

                    UserDTO dto = new UserDTO(id, name, roleID, password, email, phoneNumber, semester, profilePicturePath,
                            activeStatus, isNotifyArrival, isNotifyPopular, isDelete);
                    if (this.listAccount == null) {
                        this.listAccount = new ArrayList<UserDTO>();
                    } //end if book list not existed
                    this.listAccount.add(dto);
                } //end while traversing result set
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public void searchStaffByName(String searchValue) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id], [name], [roleID], [semester_no], [password], [email], [phoneNumber], " +
                        "[profilePicturePath], [activeStatus], [isNotifyArrival], [isNotifyPopular], [isDelete]  " +
                        "FROM [Users] " +
                        "WHERE [name] LIKE ? AND (roleID = 1 OR roleID = 2 OR roleID = 3)";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String roleID = rs.getString("roleID");
                    String password = rs.getString("password");
                    String email = rs.getString("email");
                    String phoneNumber = rs.getString("phoneNumber");
                    String semester = rs.getString("semester_no");
                    String profilePicturePath = rs.getString("profilePicturePath");
                    boolean activeStatus = rs.getBoolean("activeStatus");
                    boolean isNotifyArrival = rs.getBoolean("isNotifyArrival");
                    boolean isNotifyPopular = rs.getBoolean("isNotifyPopular");
                    boolean isDelete = rs.getBoolean("isDelete");

                    UserDTO dto = new UserDTO(id, name, roleID, password, email, phoneNumber, semester, profilePicturePath,
                            activeStatus, isNotifyArrival, isNotifyPopular, isDelete);
                    if (this.listAccount == null) {
                        this.listAccount = new ArrayList<UserDTO>();
                    } //end if book list not existed
                    this.listAccount.add(dto);
                } //end while traversing result set
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public boolean deleteUser(String id) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE [Users] " +
                        "SET [isDelete] = 1, [activeStatus] = 0 " +
                        "WHERE [id] = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, id);

                int row = stm.executeUpdate();
                if (row > 0) return true;
            }
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public boolean undeleteUser(String id) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE [Users] " +
                        "SET [isDelete] = 0 " +
                        "WHERE [id] = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, id);

                int row = stm.executeUpdate();
                if (row > 0) return true;
            }
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public UserDTO getUserByID(String userID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id], [name], [roleID], [semester_no], [password], [email], [phoneNumber], " +
                        "[profilePicturePath], [activeStatus], [isNotifyArrival], [isNotifyPopular], [isDelete]  " +
                        "FROM [Users] " +
                        "WHERE [id] = ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                if (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String roleID = rs.getString("roleID");
                    String semester = rs.getString("semester_no");
                    String password = rs.getString("password");
                    String email = rs.getString("email");
                    String phoneNumber = rs.getString("phoneNumber");
                    String profilePicturePath = rs.getString("profilePicturePath");
                    boolean activeStatus = rs.getBoolean("activeStatus");
                    boolean isNotifyArrival = rs.getBoolean("isNotifyArrival");
                    boolean isNotifyPopular = rs.getBoolean("isNotifyPopular");
                    boolean isDelete = rs.getBoolean("isDelete");

                    UserDTO dto = new UserDTO(id, name, roleID, password, email, phoneNumber, semester, profilePicturePath,
                            activeStatus, isNotifyArrival, isNotifyPopular, isDelete);
                    return dto;
                } //end while traversing result set
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return null;
    }

    public boolean updateOnFirstLogin(String email, String password, String profilePicturePath) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE [Users] " +
                        "SET [password] = ? , [profilePicturePath] = ?, [activeStatus] = 1 " +
                        "WHERE [email] = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, password);
                stm.setString(2, profilePicturePath);
                stm.setString(3, email);

                int row = stm.executeUpdate();
                if (row > 0) return true;
            }

        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public boolean updateProfilePictureOnLogin(String email, String profilePicturePath) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE [Users] " +
                        "SET [profilePicturePath] = ? " +
                        "WHERE [email] = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, profilePicturePath);
                stm.setString(2, email);

                int row = stm.executeUpdate();
                if (row > 0) return true;
            }

        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public boolean isActive(String email) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [activeStatus] "
                        + "FROM [Users] "
                        + "WHERE [email] = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                rs = stm.executeQuery();
                if (rs.next()) {
                    boolean isActive = rs.getBoolean("activeStatus");
                    if (isActive) {
                        return true;
                    }
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public boolean isDelete(String emailOrID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [isDelete] "
                        + "FROM [Users] "
                        + "WHERE [email] = ? OR [id] = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, emailOrID);
                stm.setString(2, emailOrID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    boolean isActive = rs.getBoolean("isDelete");
                    if (isActive) {
                        return true;
                    }
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public Map<String, List<String>> getUserWithBorrowedBooks() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Map<String, List<String>> map = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT Users.id, Users.[name], Users.email, Books.title FROM Users\n"
                        + "JOIN Orders ON Users.id = Orders.memberID\n"
                        + "JOIN OrderItems ON Orders.id = OrderItems.orderID\n"
                        + "JOIN Books ON OrderItems.bookID = Books.id\n"
                        + "WHERE Orders.activeStatus = 2";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String bookTitle = rs.getString("title");

                    String userDTO = id + ", " + name + ", " + email;

                    if (map == null) {
                        map = new HashMap<>();
                    }
                    map.computeIfAbsent(userDTO, k -> new ArrayList<>()).add(bookTitle);
                }
                return map;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public List<String> getListUserBorrowingBooks() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<String> list = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT Users.id FROM [Users]\n" +
                        "JOIN Orders ON Orders.memberID = Users.id\n" +
                        "WHERE Orders.activeStatus = 2";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();

                while (rs.next()) {
                    String id = rs.getString("id");

                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(id);
                }
                return list;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public boolean changeBorrowedStatus(String id) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "";
                stm = con.prepareStatement(sql);
                stm.setString(1, id);
                int row = stm.executeUpdate();
                if (row > 0) return true;
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public Map<String, List<String>> getUserOverdue() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Map<String, List<String>> map = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT Users.id, Users.[name], Users.email, Books.title FROM Users\n"
                        + "JOIN Orders ON Users.id = Orders.memberID\n"
                        + "JOIN OrderItems ON Orders.id = OrderItems.orderID\n"
                        + "JOIN Books ON OrderItems.bookID = Books.id\n"
                        + "WHERE Orders.activeStatus = 5";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String bookTitle = rs.getString("title");

                    String userDTO = id + ", " + name + ", " + email;

                    if (map == null) {
                        map = new HashMap<>();
                    }
                    map.computeIfAbsent(userDTO, k -> new ArrayList<>()).add(bookTitle);
                }
                return map;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public boolean updateMember(String id,
                                String profilePicturePath,
                                String phoneNumber,
                                boolean activeStatus,
                                String name)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE [Users] " +
                        "SET [profilePicturePath] = ? , [phoneNumber] = ?, [activeStatus] = ? , [name] = ? " +
                        "WHERE [id] = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, profilePicturePath);
                stm.setString(2, phoneNumber);
                stm.setBoolean(3, activeStatus);
                stm.setString(4, name);
                stm.setString(5, id);
                int row = stm.executeUpdate();
                if (row > 0) return true;
            }

        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public boolean updateMemberNotificationSetting(String id, boolean isNotifyArrival, boolean isNotifyPopular)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE [Users] " +
                        "SET [isNotifyArrival] = ? , " +
                        "[isNotifyPopular] = ? " +
                        "WHERE [id] = ?";
                stm = con.prepareStatement(sql);
                stm.setBoolean(1, isNotifyArrival);
                stm.setBoolean(2, isNotifyPopular);
                stm.setString(3, id);
                int row = stm.executeUpdate();
                if (row > 0) return true;
            }
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public void getListUserNotifyHighestRatedBook() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [name], email, isNotifyPopular " +
                        "FROM Users " +
                        "WHERE isNotifyPopular = 1";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();

                while (rs.next()) {
                    String name = rs.getString("name");
                    String email = rs.getString("email");

                    UserDTO dto = new UserDTO(name, email);
                    if (this.listAccount == null) {
                        this.listAccount = new ArrayList<>();
                    }
                    this.listAccount.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public void getListUserNotifyNewArrivalBook() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [name], email, isNotifyArrival " +
                        "FROM Users " +
                        "WHERE isNotifyArrival = 1";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();

                while (rs.next()) {
                    String name = rs.getString("name");
                    String email = rs.getString("email");

                    UserDTO dto = new UserDTO(name, email);
                    if (this.listAccount == null) {
                        this.listAccount = new ArrayList<>();
                    }
                    this.listAccount.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public int totalNumberOfUsers() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT(id) AS total\n" +
                        "FROM Users";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int total = rs.getInt("total");
                    return total;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return 0;
    }
}