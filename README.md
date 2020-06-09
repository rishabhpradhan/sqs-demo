# AWS SQS Spring Cloud  Demo

This is a sample service that can read and write data into AWS SQS

## Prerequisites
### What you will need:

    JDK 11
    Maven 3.5+


## Build
From the root of project directory (where pom.xml located) run the following command:

	mvn clean install     

Run

	java -jar target/sqs-demo.jar


## Other

 ### Configurations
    Create an IAM user that has access to the queue in aws, and add the access and secret key on application.properties
    
    cloud.aws.credentials.accessKey=xxxxxxxxxx
    cloud.aws.credentials.secretKey=xxxxxxxxxxxxxxxxxxxxxx
    cloud.aws.region.static=us-west-2
    cloud.aws.region.auto=false
    cloud.aws.sqs.url=https://sqs.us-west-2.amazonaws.com/xxxxxxxxxxx/my_queue.fifo
    cloud.aws.stack.auto=false
 
 ### Spring Configurations
 
   Setup AmazonSQSClient
       
     @Bean
         @Primary
         public AmazonSQSAsync amazonSQSAsync() {
             return AmazonSQSAsyncClientBuilder
                            .standard()
                            .withRegion(region)
                            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
                            .build();
     
     
         }
     
   Sample Code To Send Message to SQS
         
     SendMessageRequest sendMessageRequest = new SendMessageRequest(sqsUrl, message);
             sendMessageRequest.setMessageGroupId("IAUR");
             log.debug("Sending message {}", sendMessageRequest);
             SendMessageResult result = amazonSQSAsync.sendMessage(sendMessageRequest);
             log.info("Message sent to queue :: messageId :: " + result.getMessageId());    
   
   Sample Listener for SQS
    
       @SqsListener(value = "oce_test.fifo", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
           public void getMessage(String message, @Headers Map<String, String> headers ) {
               log.info(headers.toString());
               log.info("Received message :: {}", message);
           }


