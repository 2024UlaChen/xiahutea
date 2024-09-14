package idv.tia201.g2.web.member.dao;

import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    List<Member> find();

    Member findMemberById(int memberId);

    Member findMemberByPhone(String phone);

    Member findByMemberEmail(String email);

    @Query(value = "select * from CUSTOMER where customer_phone= :phone and customer_password= :password")
    Member findMemberForLogin(String phone, String password);

    List<Member> findByMemberValidStatus(boolean status);

    List<MemberAddress> findMemberAddressByMemberId(String memberId);

    //update
    boolean updateMemberInfo(Member member);

    boolean updateMemberAddress(MemberAddress memberAddress);

    //delete
    int deleteByMemberAddressId(int memberId);

    //create
    boolean createMember(Member member);

    boolean createMemberAddress(MemberAddress memberAddress);

}
