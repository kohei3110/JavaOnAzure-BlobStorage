package com.kohei3110.NotifyApp.repository;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.OutputBinding;

public class CreateItemRepository {
    
    public void outputLogs(
        OutputBinding<String> outputItem,
        String content,
        ExecutionContext context
    ) throws Exception {
        try {
            outputItem.setValue(content);
        } catch (Exception e) {
            context.getLogger().warning(e.getMessage());
            throw new Exception("Create item operation has failed.");
        }
    }
}
