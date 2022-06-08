package com.kohei3110.NotifyApp;

import com.kohei3110.NotifyApp.repository.CreateItemRepository;
import com.kohei3110.NotifyApp.service.CreateItemService;

public class Factory {
    private CreateItemRepository createItemRepository;
    private CreateItemService createItemService;

    public Factory() {
        this.createItemRepository = new CreateItemRepository();
        this.createItemService = new CreateItemService(this.createItemRepository);
    }

    public CreateItemService getCreateItemServiceInstance() {
        return new Factory().createItemService;
    }
}
