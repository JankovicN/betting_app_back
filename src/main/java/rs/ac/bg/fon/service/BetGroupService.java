package rs.ac.bg.fon.service;

import rs.ac.bg.fon.entity.BetGroup;

import java.util.List;


public interface BetGroupService {

    BetGroup saveBetGroup(BetGroup betGroup);

    List<BetGroup> getAllBetGroups();

    void deleteBetGroup(int id);

    List<BetGroup> getBetGroupsByFixture(int fixture);
}
