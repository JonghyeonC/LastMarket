package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.DealState;
import edu.ssafy.lastmarket.domain.entity.Lifestyle;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class EnumChechService {
    public Optional<DealState> checkDealStateEnum(String dealStateString){
        if(dealStateString==null){
            dealStateString="";
        }
        try{
            DealState dealState = DealState.valueOf(dealStateString);
            return  Optional.of(dealState);
        }catch (IllegalArgumentException e){
            return Optional.ofNullable(null);
        }

    }

    public Optional<Lifestyle> checkLifestyleEnum(String lifestyleString){
        if(lifestyleString==null){
            lifestyleString="";
        }
        try{
            Lifestyle result = Lifestyle.valueOf(lifestyleString);
            return  Optional.of(result);
        }catch (IllegalArgumentException e){
            return Optional.ofNullable(null);
        }

    }



}
