package manoj.jms.sqs;

import javax.jms.Message;

import com.amazon.sqs.javamessaging.message.SQSObjectMessage;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import manoj.jms.core.PhaseProcessor;

public class SQSPostPhaseProcessor implements PhaseProcessor {
	AmazonSQS sqs;
	public SQSPostPhaseProcessor(AmazonSQSClientBuilder amazonSQSClientBuilder) {
		sqs = amazonSQSClientBuilder.build();
	}

	@Override
	public void process(Message message) {
		SQSObjectMessage sqsObjectMessage = (SQSObjectMessage) message;
		sqs.deleteMessage(new DeleteMessageRequest(sqsObjectMessage.getQueueUrl(),
				sqsObjectMessage.getReceiptHandle()));
	}

}
