package rafikibora.services;

import rafikibora.dto.ReceiveMoneyRequestDto;
import rafikibora.dto.ReceiveMoneyResponseDto;
import rafikibora.exceptions.AccountTransactionException;


public interface ReceiveMoneyService {
    ReceiveMoneyResponseDto withdrawMoney(ReceiveMoneyRequestDto receiveMoneyRequestDto) throws AccountTransactionException;
    ReceiveMoneyResponseDto receiveMoney(ReceiveMoneyRequestDto receiveMoneyRequestDto) throws AccountTransactionException;
    ReceiveMoneyResponseDto details(ReceiveMoneyRequestDto receiveMoneyRequestDto) throws AccountTransactionException;
}
