package com.artcorb.cards.service.impl;

import java.util.Optional;
import java.util.Random;
import org.springframework.stereotype.Service;
import com.artcorb.cards.dto.CardDto;
import com.artcorb.cards.entity.Card;
import com.artcorb.cards.exception.CardAlreadyExistsException;
import com.artcorb.cards.exception.ResourceNotFoundException;
import com.artcorb.cards.mapper.CardMapper;
import com.artcorb.cards.repository.CardRepository;
import com.artcorb.cards.service.ICardService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CardServiceImpl implements ICardService {

  private CardRepository cardRepository;

  @Override
  public void createCard(String mobileNumber) {
    Optional<Card> optionalCards = cardRepository.findByMobileNumber(mobileNumber);
    if (optionalCards.isPresent()) {
      throw new CardAlreadyExistsException(
          "Card already registered with given mobileNumber " + mobileNumber);
    }
    cardRepository.save(createNewCard(mobileNumber));
  }

  /**
   * @param mobileNumber - Mobile Number of the Customer
   * @return the new card details
   */
  private Card createNewCard(String mobileNumber) {
    Card newCard = new Card();
    long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
    newCard.setCardNumber(Long.toString(randomCardNumber));
    newCard.setMobileNumber(mobileNumber);
    newCard.setCardType("Place Holder");
    newCard.setTotalLimit(1000000);
    newCard.setAmountUsed(0);
    newCard.setAvailableAmount(2000000);

    return newCard;
  }

  @Override
  public CardDto fetchCard(String mobileNumber) {
    Card card = cardRepository.findByMobileNumber(mobileNumber)
        .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
    return CardMapper.mapToCardDto(card, new CardDto());
  }

  @Override
  public boolean updateCard(CardDto cardDto) {
    Card card = cardRepository.findByCardNumber(cardDto.getCardNumber()).orElseThrow(
        () -> new ResourceNotFoundException("Card", "CardNumber", cardDto.getCardNumber()));
    cardRepository.save(CardMapper.mapToCard(cardDto, card));
    return true;
  }

  @Override
  public boolean deleteCard(String mobileNumber) {
    Card card = cardRepository.findByMobileNumber(mobileNumber)
        .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
    cardRepository.deleteById(card.getCardId());
    return true;
  }

}
