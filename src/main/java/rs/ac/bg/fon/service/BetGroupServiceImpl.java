package rs.ac.bg.fon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.entity.BetGroup;
import rs.ac.bg.fon.repository.BetGroupRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BetGroupServiceImpl implements BetGroupService{
    BetGroupRepository betGroupRepository;
@Autowired
    public void setBetGroupRepository(BetGroupRepository betGroupRepository) {
        this.betGroupRepository = betGroupRepository;
    }

    @Override
    public BetGroup saveBetGroup(BetGroup betGroup) {
        return betGroupRepository.save(betGroup);
    }

    @Override
    public List<BetGroup> getAllBetGroups() {
        return this.betGroupRepository.findAll();
    }

    @Override
    public void deleteBetGroup(int id) {
        this.betGroupRepository.deleteById(id);
    }

    @Override
    public List<BetGroup> getBetGroupsByFixture(int fixture) {
        return betGroupRepository.findByOddsFixtureId(fixture);
    }
}
