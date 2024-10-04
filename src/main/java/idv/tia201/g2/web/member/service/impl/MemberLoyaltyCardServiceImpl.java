package idv.tia201.g2.web.member.service.impl;

import idv.tia201.g2.web.member.dao.MemberLoyaltyCardRepository;
import idv.tia201.g2.web.member.service.MemberLoyaltyCardService;
import idv.tia201.g2.web.store.vo.CustomerLoyaltyCard;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberLoyaltyCardServiceImpl implements MemberLoyaltyCardService {


    private MemberLoyaltyCardRepository memberLoyaltyCardRepository;
    @Autowired
    public MemberLoyaltyCardServiceImpl(MemberLoyaltyCardRepository memberLoyaltyCardRepository) {
        this.memberLoyaltyCardRepository = memberLoyaltyCardRepository;
    }

    @Override
    public CustomerLoyaltyCard UpdatePoints(Integer loyaltyCardId, Integer decreasePoints) {
        CustomerLoyaltyCard data = GetCustomerLoyaltyCard(loyaltyCardId);
        Integer newPoints = data.getPoints() - decreasePoints;
        data.setPoints(newPoints);
        return memberLoyaltyCardRepository.save(data);

    }

    @Override
    public CustomerLoyaltyCard GetCustomerLoyaltyCard(Integer loyaltyCardId) {
        return memberLoyaltyCardRepository.findByLoyaltyCardId(loyaltyCardId);
    }

    @Override
    public List<CustomerLoyaltyCard> GetCustomerLoyaltyCards(Integer memberId) {
        return memberLoyaltyCardRepository.findCustomerLoyaltyCardsByMemberId(memberId);
    }

    @Override
    public boolean AddCustomerLoyaltyCard(CustomerLoyaltyCard customerLoyaltyCard) {
        //確認是否是新增
        CustomerLoyaltyCard data = memberLoyaltyCardRepository.findByStoreIdAndMemberId(customerLoyaltyCard.getStoreId(), customerLoyaltyCard.getMemberId());
        if (data != null) {return false;}
        memberLoyaltyCardRepository.save(customerLoyaltyCard);
        return true;
    }


    @Override
    public CustomerLoyaltyCard updateMemberStoreLoyaltyPoints(Integer storeId, Integer memberId, Integer totalMoney) {
        //檢查這個客戶是否已經有該家集點卡  沒有就新增
        CustomerLoyaltyCard customerLoyaltyCard = new CustomerLoyaltyCard();
        customerLoyaltyCard.setStoreId(storeId);
        customerLoyaltyCard.setMemberId(memberId);
        AddCustomerLoyaltyCard(customerLoyaltyCard);

        CustomerLoyaltyCard data = memberLoyaltyCardRepository.findByStoreIdAndMemberId(storeId,memberId);
        //檢查是否集點卡使用中
        Store store = data.getStore();
        if(store.getValidStatus()){
            //使用中才需要執行以下動作
            Integer point = (Integer) totalMoney/store.getExchangeRate();
            Integer sum = data.getPoints() + point;
            data.setPoints(sum);
            return memberLoyaltyCardRepository.save(data);
        }
        return null;

    }


}
