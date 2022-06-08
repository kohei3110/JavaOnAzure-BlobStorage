package com.kohei3110.NotifyApp.service;

import com.kohei3110.NotifyApp.repository.CreateItemRepository;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.OutputBinding;

public class CreateItemService {

    private CreateItemRepository repository;
    
    public CreateItemService(CreateItemRepository repository) {
        this.repository = repository;
    }

    public void outputLogs(OutputBinding<String> outputItem, String content, ExecutionContext context) throws Exception {
        repository.outputLogs(outputItem, content, context);
    }
}
