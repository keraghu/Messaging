package com.cts.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.QueueingConsumer;



@Path("/service")
public class MessagingService {
	
	 @POST
	 @Path("/pushMessage")
	 public String pushMessage(@FormParam("key") String message){
		System.out.println("In pushMessage Entry in MessagingService key>>>>>"+message);
	  	publishMessage(message);
	    return "Success";
	 }
	 
	 @GET
	 @Path("/pullMessage")
	// @Produces(MediaType.APPLICATION_JSON)
	 public List<String> pullMessage(){
		 
		 System.out.println("In pullMessage Entry Update>>>>>");
		
		 List<String> consumedMessages =  subscibeMessage();
		 System.out.println("consumedMessages size:"+consumedMessages.size());
		 
		 System.out.println("In pullMessage Exit>>>>>");
		 return consumedMessages;
		 
	 }
	 
	 	/*
	    @GET
		@Path("/{param}")
		public Response printMessage(@PathParam("param") String msg) {
			System.out.println("In printMessage >>>>>>>>>>");
	 
			String result = "Restful example : " + msg;
	 
			return Response.status(200).entity(result).build();
	 
		}*/
	 
	 
	 public void publishMessage(String message){
			System.out.println("In publishMessage of MQProducer Entry>>>>> "+message);
			ConnectionFactory factory = null;
			Connection connection = null;
			Channel channel = null;

			try {
				factory = new ConnectionFactory();
				//factory.setHost(host);
				factory.setUsername(Configuration.USERNAME);
				factory.setPassword(Configuration.PASSWORD);
				factory.setHost(Configuration.HOSTNAME);
				factory.setPort(Configuration.PORT);
				factory.setVirtualHost("/");
				connection = factory.newConnection();
				channel = connection.createChannel();
			   // channel.queueDeclare("Queue4", false, false, false, null);
				channel.queueDeclare(Configuration.QUEUE_NAME, false, false, false, null);
			    channel.exchangeDeclare(Configuration.EXCHANGE, "direct", true);
			    channel.queueBind(Configuration.QUEUE_NAME, Configuration.EXCHANGE, Configuration.ROUTING_KEY);
			    // String message = "Welcome To Java";
			    AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties().builder();
			   // builder.messageId("123456789");
			   
			     channel.basicPublish(Configuration.EXCHANGE, Configuration.ROUTING_KEY
			    		 ,MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes()) ;
			 	System.out.println("Published Message Successfully>>>>> ");
			    //channel.basicPublish("Exchange1", "Queue1", builder.build(), message.getBytes());
				System.out.println("In publishMessage of MQProducer Exit>>>>> ");

			} catch (IOException e) {
			    e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally  {
				System.out.println("In publishMessage finally block");
				try{
				channel.close();
				connection.close();
				}catch (IOException e) {
					System.out.println("In publishMessage finally IOException block");
				    e.printStackTrace();
				} catch (TimeoutException e) {
					System.out.println("In publishMessage finally TimeoutException block");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO Auto-generated catch block
			
			}
		}
	 	 
		public List<String> subscibeMessage(){
			System.out.println("In subscibeMessage of MQProducer Entry>>>>> ");
			List<String> messages = new ArrayList();
			
			ConnectionFactory factory = null;
			Connection connection = null;
			Channel channel = null;
			

			try {
				factory = new ConnectionFactory();
				//factory.setHost(host);
				factory.setUsername(Configuration.USERNAME);
				factory.setPassword(Configuration.PASSWORD);
				factory.setHost(Configuration.HOSTNAME);
				factory.setPort(Configuration.PORT);
				factory.setVirtualHost("/");
				connection = factory.newConnection();
				channel = connection.createChannel();
				channel.queueDeclare(Configuration.QUEUE_NAME, false, false, false, null);
				channel.exchangeDeclare(Configuration.EXCHANGE, "direct", true);
			    channel.queueBind(Configuration.QUEUE_NAME, Configuration.EXCHANGE, Configuration.ROUTING_KEY);
				QueueingConsumer consumer = new QueueingConsumer(channel);
				channel.basicConsume(Configuration.QUEUE_NAME, true, consumer);
				System.out.println("Consumed messages here>>>>> ");
				System.out.println("In subscibeMessage of MQProducer Exit>>>>> ");
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());
				System.out.println("MessagingService Message Received >>>>>>>>>: '" + message + "'");
				messages.add(message);
			
				
			}	 catch (IOException e) {
			    e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	finally  {
				try{
					System.out.println("In subscibeMessage finally try block");
				channel.close();
				connection.close();
				}catch (IOException e) {
					System.out.println("In subscibeMessage finally IOException block");
				    e.printStackTrace();
				} catch (TimeoutException e) {
					System.out.println("In subscibeMessage finally TimeoutException block");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO Auto-generated catch block
				
			}
			
			return messages;
			
		}
	 

}
