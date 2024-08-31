package idv.tia201.g2.web.member.service;

import idv.tia201.g2.web.member.vo.Member;

import java.util.List;

public interface MemberService {
    Member register(Member member);

    Member login(Member member);

    Member edit(Member member);

    List<Member> findAll();

    boolean remove(Integer id);

    boolean save(Member member);
}
