package manoj.jms.sqs;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import manoj.jms.core.ResourceBuilderContext;

public class SQSResourceBuilderContext implements ResourceBuilderContext {
	public static final String DEFAULT_QUEUE_NAME = "SQSJMSClientExampleQueue";
	private Region region;


	public static SQSResourceBuilderContext parseConfig(PropertiesConfiguration prop) throws Exception {
		return new SQSResourceBuilderContext(prop);
	}

	private SQSResourceBuilderContext(PropertiesConfiguration prop) throws Exception {

		String regionName = prop.getString("SQS.Region");
		try {
			if(StringUtils.isEmpty(regionName))
				throw new IllegalArgumentException("Region type is missing. Please add SQS.Region in property file.");

			setRegion(Region.getRegion(Regions.fromName(regionName)));
		} catch( IllegalArgumentException e ) {
			throw new IllegalArgumentException( "Unrecognized region " + regionName );

		}

		String accessKey = prop.getString("SQS.accessKey");
		String secretKey = prop.getString("SQS.secretKey");

		if(StringUtils.isEmpty(secretKey))
			throw new IllegalArgumentException("accessKey is missing. Please add SQS.accessKey in property file.");

		if(StringUtils.isEmpty(secretKey))
			throw new IllegalArgumentException("secretKey is missing. Please add SQS.secretKey in property file.");

		setCredentialsProvider(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)));
	}

	private AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public AWSCredentialsProvider getCredentialsProvider() {
		return credentialsProvider;
	}

	public void setCredentialsProvider(AWSCredentialsProvider credentialsProvider) {
		credentialsProvider.getCredentials();
		this.credentialsProvider = credentialsProvider;
	}
}