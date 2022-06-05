package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.models.Contract;
import com.example.hostelmanagement.models.TblInvoiceEntity;
import com.example.hostelmanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM tbl_Users AS u Where u.userName LIKE %:userName%")
    public List<User> getAllByAName(@Param("userName") String userName);

    @Query(nativeQuery = true, value = "SELECT * FROM tbl_Users AS u Where u.userName = :userName AND u.password = :password AND u.userStatus = 1")
    public User getUserByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);
    @Query(nativeQuery = true, value = "SELECT * FROM tbl_Contracts c where c.userID = :userID ")
    public List<Contract> getHistoryOfUserByUserID(@Param("userID") int userID);
    @Query(nativeQuery = true,value = "SELECT * FROM tbl_Contracts c where c.userID = :userID AND c.contractID = :contractID" )
    public Contract getContract(@Param("userID") int userID,@Param("contractID") int contractID);
    @Query(nativeQuery = true,value = "select i.paymentDate, i.totalAmount,i.invoiceID,i.invoiceCreateDate,i.invoiceID,i.invoiceName,i.invoiceStatus,i.note from tbl_Contracts c join tbl_Invoice i on c.roomID = i.roomID where c.userID = :userID AND c.contractID = :contractID  " )
    public List<TblInvoiceEntity> getHistoryBillByUserID(@Param("userID") int userID,@Param("contractID") int contractID);

}
