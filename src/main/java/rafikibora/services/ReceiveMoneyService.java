package rafikibora.services;

import rafikibora.dto.ReceiveMoneyRequestDto;
import rafikibora.dto.ReceiveMoneyResponseDto;

import java.text.ParseException;

public interface ReceiveMoneyService {
    public ReceiveMoneyResponseDto receiveMoney(ReceiveMoneyRequestDto receiveMoneyRequestDto) throws ParseException;
}
