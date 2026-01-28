package com.aditi_midterm.financemanager.transaction;

import com.aditi_midterm.financemanager.transaction.dto.AddTransactionRequest;
import com.aditi_midterm.financemanager.transaction.dto.TransactionResponse;
import com.aditi_midterm.financemanager.transaction.dto.UpdateTransactionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "transactionType", target = "type")
    Transaction toTransaction(AddTransactionRequest addTransactionRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target =  "createdAt", ignore = true)
    @Mapping(source = "transactionType", target = "type")
    void updateEntity(
            UpdateTransactionRequest updateTransactionRequest,
            @MappingTarget Transaction transaction
    );

    @Mapping(source = "type", target = "transactionType")
    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "createdAt", target = "createdDate")
    TransactionResponse toTransactionResponse(Transaction transaction);
}
