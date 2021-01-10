package com.wirebrain.friends.controller;

import com.wirebrain.friends.model.Friend;
import com.wirebrain.friends.service.FriendService;
import com.wirebrain.friends.util.ErrorMessage;
import com.wirebrain.friends.util.FieldErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class FriendController {

    @Autowired
    FriendService friendService;

    @GetMapping("/friend")
    public Iterable<Friend> read() {
        return friendService.findAll();
    }

    @PostMapping("/friend")
    public Friend create(@Valid @RequestBody Friend friend){
         return friendService.save(friend);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors= e.getBindingResult().getFieldErrors();
        List<FieldErrorMessage> fieldErrorMessages = fieldErrors.stream().map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage())).collect(Collectors.toList());
        return fieldErrorMessages;
    }

    @PutMapping("/friend")
    ResponseEntity<Friend> update(@RequestBody Friend friend) {
        if (friendService.findById(friend.getId()).isPresent())
        return new ResponseEntity(friendService.save(friend), HttpStatus.OK);
        else {
            return new ResponseEntity(friend, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/friend/{id}")
    Optional<Friend> findById(@PathVariable Integer id) {
        return friendService.findById(id);
    }


    @DeleteMapping("/friend/{id}")
    public void delete(@PathVariable Integer id) {
        friendService.deleteById(id);
    }

    @GetMapping("/friend/search")
    Iterable<Friend> findByQuery(@RequestParam(value="first", required=false) String firstName,
                                 @RequestParam(value="last", required = false) String lastName) {
        if (firstName !=null && lastName !=null)
            return friendService.findByFirstNameAndLastName(firstName, lastName);
        else if (firstName!=null)
            return friendService.findByFirstName(firstName);
        else if(lastName != null)
            return friendService.findByLastName(lastName);
        else
            return friendService.findAll();
    }

    @GetMapping("/wrong")
    public Friend somethingIsWrong() throws ValidationException {
        throw new ValidationException("Something is wrong");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    String exceptionHandler(ValidationException e) {
        return e.getMessage();
    }


}
