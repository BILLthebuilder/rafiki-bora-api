package rafikibora.services;

import rafikibora.dto.ReceiveMoneyRequestDto;
import rafikibora.dto.ReceiveMoneyResponseDto;
import rafikibora.exceptions.AccountTransactionException;


public interface ReceiveMoneyService {
    ReceiveMoneyResponseDto receiveMoney(ReceiveMoneyRequestDto receiveMoneyRequestDto) throws AccountTransactionException;
}
