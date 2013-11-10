package edu.sjsu.cmpe.procurement.jobs;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.activemq.transport.stomp.StompFrame;
import org.apache.activemq.transport.stomp.Stomp.Headers.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.spinscale.dropwizard.jobs.Job;
import de.spinscale.dropwizard.jobs.annotations.Every;
import edu.sjsu.cmpe.procurement.ProcurementService;

/**
 * This job will run at every 5 second.
 */
@Every("5min")
public class ProcurementSchedulerJob extends Job {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void doJob() {
	String strResponse = ProcurementService.jerseyClient.resource(
		"http://ip.jsontest.com/").get(String.class);
	log.debug("Response from jsontest.com: {}", strResponse);
	try {
		StompConnection connection = new StompConnection();
		connection.open("54.215.210.214", 61613);
		connection.subscribe("/queue/61044.book.orders", Subscribe.AckModeValues.CLIENT);
		//connection.begin("tx2");
		StompFrame message = connection.receive();
		System.out.println(message.getBody());
		//connection.ack(message, "tx2");
		//connection.commit("tx2");
		connection.disconnect();
	} catch (UnknownHostException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
    }
}
