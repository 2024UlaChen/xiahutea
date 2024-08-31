package idv.tia201.g2.web.member.service.impl;

import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    @Override
    public Member register(Member member) {
        return null;
    }

    @Override
    public Member login(Member member) {
        return null;
    }

    @Override
    public Member edit(Member member) {
        return null;
    }

    @Override
    public List<Member> findAll() {
        return List.of();
    }

    @Override
    public boolean remove(Integer id) {
        return false;
    }

    @Override
    public boolean save(Member member) {
        return false;
    }
}
