package com.romart.ad_login_jwt.repository.ismnet;

import com.romart.ad_login_jwt.domain.ismnet.EventPlanSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventPlanSubscriptionRepository extends JpaRepository<EventPlanSubscription, Integer> {

    @Query(value = "select\n" +
            "\tfsb.id,\n" +
            "\tfup.partneroid as poid,\n" +
            "\tfsb.durchfuehrungid as dfoid\n" +
            "from\n" +
            "\t`2fcms280_stundenplan_bookmark` fsb\n" +
            "join `2fcms280_users_pisalink` fup on\n" +
            "\tfsb.uid = fup.uid where fup.partneroid = ?1", nativeQuery = true)
    Optional<EventPlanSubscription> getSubscriptionByPoid(String poid);

}
