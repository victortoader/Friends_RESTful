package com.wirebrain.friends;


import com.wirebrain.friends.controller.FriendController;
import com.wirebrain.friends.model.Friend;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.ValidationException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTests {

    @Autowired
    FriendController friendController;

    @Test
    public void teasCreateReadDelete() {
        Friend friend = new Friend("Gordon", "Moore");
        Friend friendResult = friendController.create(friend);

        Iterable<Friend> friends = friendController.read();
        Assertions.assertThat(friends).first().hasFieldOrPropertyWithValue("firstName", "Gordon");

        friendController.delete(friendResult.getId());
        Assertions.assertThat(friendController.read()).isEmpty();


    }





//    @Test(expected = ValidationException.class)
//    void errorHandlingValidationExceptionThrown() throws ValidationException {
//        friendController.somethingIsWrong();
//    }


}
