package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
    void save(AddressBook addressBook);


    List<AddressBook> list();

    void setdefault(AddressBook addressBook);

    AddressBook getById(Integer id);

    void delete(Integer id);

    void update(AddressBook addressBook);

    AddressBook getdefault();


}
