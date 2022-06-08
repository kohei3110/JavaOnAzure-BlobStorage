package com.kohei3110;

import com.kohei3110.NotifyApp.Factory;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.OutputBinding;
import com.microsoft.azure.functions.annotation.BlobInput;
import com.microsoft.azure.functions.annotation.CosmosDBOutput;
import com.microsoft.azure.functions.annotation.EventGridTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;

public class Controller {

    static Factory factory = new Factory();

    @FunctionName("uploadNotification")
    public static void notifyUpload( 
        @EventGridTrigger(
            name = "event"
        ) String content,
        final ExecutionContext context,
        @BlobInput(
            name = "file",
            dataType = "binary",
            path = "{data.url}",
            connection = "STORAGE_CONNECTION_STRING"
        ) byte[] fileContent,
        @CosmosDBOutput(
            name = "output",
            databaseName = "Blobs",
            collectionName = "BlobHistory",
            connectionStringSetting = "DB_CONNECTION_STRING"
        ) OutputBinding<String> outputItem   
     ) throws Exception {
        context.getLogger().info("File content: " + fileContent.toString());
        factory.getCreateItemServiceInstance().outputLogs(outputItem, content, context);
    }
    
}
