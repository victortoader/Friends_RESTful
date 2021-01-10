package com.wirebrain.friends.service;

//import com.wirebrain.friends.controller.FriendController;
import com.wirebrain.friends.model.Friend;
import com.wirebrain.friends.service.FriendService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ServiceTests {

    @Autowired
    FriendService friendService;

    @Test
    public void teasCreateReadDelete() {
        Friend friend = new Friend("Gordon", "Moore");

        friendService.save(friend);

        Iterable<Friend> friends = friendService.findAll();

        Assertions.assertThat(friends).extracting(Friend::getFirstName).containsOnly("Gordon");

        friendService.deleteAll();
        Assertions.assertThat(friendService.findAll()).isEmpty();
    }

}
