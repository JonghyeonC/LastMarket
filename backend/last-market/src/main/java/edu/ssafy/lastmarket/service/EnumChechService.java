package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.DealState;
import edu.ssafy.lastmarket.domain.entity.Lifestyle;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Service;

@Service
public class EnumChechService {
    public DealState checkDealStateEnum(String dealStateString){
        if(StringUtil.isNullOrEmpty(dealStateString)){
            return null;
        }
        try{
            DealState dealState = DealState.valueOf(dealStateString);
            return  dealState;
        }catch (IllegalArgumentException e){
            return null;
        }
    }

    public Lifestyle checkLifestyleEnum(String lifestyleString){
        if(StringUtil.isNullOrEmpty(lifestyleString)){
            return null;
        }
        try{
            Lifestyle lifestyle = Lifestyle.valueOf(lifestyleString);
            return  lifestyle;
        }catch (IllegalArgumentException e){
            return null;
        }
    }
}
