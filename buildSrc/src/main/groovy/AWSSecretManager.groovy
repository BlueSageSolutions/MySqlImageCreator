import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse

@CompileStatic
class AWSSecretManger {
    SecretsManagerClient client

    AWSSecretManger() {
        client = SecretsManagerClient.builder().region(Region.US_EAST_1).credentialsProvider(EnvironmentVariableCredentialsProvider.create()).build()
    }

    Map getSecret(String id) {
        GetSecretValueRequest request = GetSecretValueRequest.builder().secretId(id).build() as GetSecretValueRequest
        GetSecretValueResponse response = client.getSecretValue(request)
        JsonSlurper json = new JsonSlurper()

        if (response.secretString() != null) {
              return  (Map) json.parseText(response.secretString())
          }
          else {
            String secret = new String(Base64.getDecoder().decode(response.secretBinary().asByteBuffer()).array())
              return (Map) json.parseText(secret)
          }

    }
}