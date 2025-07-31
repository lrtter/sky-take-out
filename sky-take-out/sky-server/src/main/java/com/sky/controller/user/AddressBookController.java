package com.sky.controller.user;

import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
public class AddressBookController {
    @Autowired
    private AddressBookService service;
    @PostMapping
    public Result save(@RequestBody AddressBook addressBook){
        service.save(addressBook);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(){
        List<AddressBook> list=service.list();
        return Result.success(list);
    }

    @PutMapping("/default")
    public Result setdefault(@RequestBody AddressBook addressBook){
        service.setdefault(addressBook);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        AddressBook result=service.getById(id);
        return Result.success(result);

    }

    @DeleteMapping
    public Result delete(Integer id){
        service.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody AddressBook addressBook){
        service.update(addressBook);
        return Result.success();
    }

    @GetMapping("/default")
    public Result getdefault(){
        AddressBook result=service.getdefault();
        return Result.success(result);
    }
}
