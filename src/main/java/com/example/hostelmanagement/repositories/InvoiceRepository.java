package com.example.hostelmanagement.repositories;


import com.example.hostelmanagement.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;

import java.sql.Date;
import java.util.List;

@Controller
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    @Query(nativeQuery = true,value = "select i.paymentDate, i.totalAmount,i.invoiceID,i.invoiceCreateDate,i.invoiceID,i.invoiceName,i.invoiceStatus,i.note from tbl_Contracts c join tbl_Invoice i on c.roomID = i.roomID where c.userID = :userID AND c.contractID = :contractID And i.invoiceCreateDate = :createDate " )
    public List<Invoice> getHistoryBillByUserID(@Param("userID") int userID, @Param("contractID") int contractID, @Param("createDate")Date invoiceCreateDate);
}
