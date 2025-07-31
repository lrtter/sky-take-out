package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookMapper mapper;
    @Override
    public void save(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(0);
        mapper.save(addressBook);
    }

    @Override
    public List<AddressBook> list() {
        return mapper.list(BaseContext.getCurrentId());
    }

    @Override
    public void setdefault(AddressBook addressBook) {
        Long id = addressBook.getId();
        mapper.reset();
        mapper.setdefault(Integer.parseInt(id.toString()));
    }

    @Override
    public AddressBook getById(Integer id) {
        return mapper.getById(id);
    }

    @Override
    public void delete(Integer id) {
        mapper.delete(id);
    }

    @Override
    public void update(AddressBook addressBook) {
        mapper.update(addressBook);
    }

    @Override
    public AddressBook getdefault() {
        return mapper.get(BaseContext.getCurrentId());
    }

}
