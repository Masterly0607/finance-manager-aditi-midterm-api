package com.aditi_midterm.financemanager.transfer;

import com.aditi_midterm.financemanager.account.Account;
import com.aditi_midterm.financemanager.transfer.dto.TransferRequest;
import com.aditi_midterm.financemanager.transfer.dto.TransferResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransferMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "fromAccount", source = "fromAccount")
  @Mapping(target = "toAccount", source = "toAccount")
  @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
  Transfer toTransfer(TransferRequest request, Account fromAccount, Account toAccount);

  @Mapping(target = "transferId", source = "id")
  @Mapping(target = "fromAccountId", source = "fromAccount.id")
  @Mapping(target = "toAccountId", source = "toAccount.id")
  @Mapping(target = "amount", source = "amount")
  @Mapping(target = "note", source = "note")
  @Mapping(target = "transferredAt", source = "createdAt")
  @Mapping(target = "status", constant = "SUCCESS")
  TransferResponse toResponse(Transfer transfer);
}
